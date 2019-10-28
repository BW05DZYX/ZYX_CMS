package com.zhaoyuxi.test;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;
import com.zhaoyuxi.cms.entity.Article;
import com.zhaoyuxi.common.utils.FileUtil;
import com.zhaoyuxi.service.ArticleService;

/**
 * @author 作者:赵玉玺
 * @version 创建时间：2019年9月24日 下午8:35:49 类功能说明
 */
@ContextConfiguration("classpath:spring.xml")
@RunWith(SpringRunner.class)
public class FileTest {

	@Autowired
	private ArticleService service;
	
	@Autowired
	private RedisTemplate<String, Article> redisTemplate;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	private ListOperations<String, Article> opsForList =null;
	/**
	 * 数据库中添加文章数据测试
	 */
	@Test
	public void Filetest() {
		List<String> list=FileUtil.getFileList("D:\\1705DJsoup");//获取文件名列表
		opsForList=redisTemplate.opsForList();
		for(String s:list) {//循环添加
			try {
				String content=FileUtil.readFile(s);//读取文件内容
				//读取标题
				String title=s.substring(s.lastIndexOf("\\")+1,s.lastIndexOf("."));
				Article article=new Article();
				//设置文章信息
				article.setContent(content);
				article.setTitle(title);
				article.setUserId(44);
				//将文章添加到redis中
				opsForList.leftPush("add_article", article);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * redis中读取文章并通过kafka添加到mysql中
	 */
	@Test
	public void kafkaTest() {
		opsForList=redisTemplate.opsForList();
		//读取redis中的信息
		List<Article> list=opsForList.range("add_article", 0, -1);
		Gson gson=new Gson();
		//kafka负责生产
		for(Article art:list) {
			kafkaTemplate.sendDefault(gson.toJson(art));
		}
		//移除添加后的信息
		redisTemplate.delete("add_article");
	}
	
	@Test
	public void myTest001() {
	}
}
