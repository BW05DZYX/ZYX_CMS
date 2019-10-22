package com.zhaoyuxi.test;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

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

	/**
	 * 数据库中添加文章数据测试
	 */
	@Test
	public void Filetest() {
		List<String> list=FileUtil.getFileList("D:\\month");//获取文件名列表
		for(String s:list) {//循环添加
			try {
				String content=FileUtil.readFile(s);//读取文件内容
				//读取标题
				String title=s.substring(s.lastIndexOf("\\")+1,s.lastIndexOf("."));
				Article article=new Article();
				//设置文章内容
				article.setContent(content);
				article.setTitle(title);
				article.setUserId(44);
				service.add(article);//添加文章
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
}
