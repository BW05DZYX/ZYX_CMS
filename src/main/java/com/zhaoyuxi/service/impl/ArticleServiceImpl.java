package com.zhaoyuxi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhaoyuxi.cms.dao.ArticleMapper;
import com.zhaoyuxi.cms.entity.Article;
import com.zhaoyuxi.cms.utils.HighUtil;
import com.zhaoyuxi.cms.utils.SyncUtil;
import com.zhaoyuxi.service.ArticleService;

/**
 * @author 作者:赵玉玺
 * @version 创建时间：2019年9月21日 下午3:49:25 文章业务层
 */
@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	ElasticsearchTemplate elasticsearchTemplate;

	@Autowired
	RedisTemplate<String, Article> redisTemplate;

	@Autowired
	ArticleMapper articleMapper;

	@Override
	public int post(Article article) {
		return articleMapper.add(article);
	}

	@Override
	public int update(Article article) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int logicDelete(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int logicDeleteBatch(Integer[] ids) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int add(Article article) {
		return articleMapper.add(article);
	}

	@Override
	public int uphits(Integer id) {
		return articleMapper.uphits(id);
	}

	@Override
	public int check(Integer id, Integer status) {
		return articleMapper.updateStatus(id, status);
	}

	@Override
	public int setHot(Integer id, Integer status) {
		return articleMapper.updateHot(id, status);
	}

	@Override
	public int updatea(Integer id, String title, Integer categoryId, Integer channelId, String content1) {
		return articleMapper.updatea(id, title, categoryId, channelId, content1);
	}

	@Override
	public Article findById(Integer articleId) {
		return articleMapper.findById(articleId);
	}

	@Override
	public PageInfo<Article> list(Integer pageNum, Integer channelId, Integer cid, String key) {
		PageInfo<Article> page=null;
		if (pageNum == 0) {
			//进行分页显示
			PageHelper.startPage(pageNum, articleMapper.list(0, 0).size());
			//查询文章列表
			List<Article> articles = articleMapper.list(channelId, cid);
			page=new PageInfo<Article>(articles);
		} else {
			PageHelper.startPage(pageNum, 3);
			List<Article> articles = articleMapper.list(channelId, cid);
			//包装高亮集合
			PageInfo<Article> pageInfoHigh = HighUtil.getHigh(elasticsearchTemplate, pageNum, 3, key, null,articleMapper.checkList(null).size());
			//进行替换原集合
			for (Article article : pageInfoHigh.getList()) {
				for (int i = 0; i < articles.size(); i++) {
					if (article.getId() == articles.get(i).getId()) {
						articles.set(i, article);
					}
				}

			}
			page=new PageInfo<Article>(articles);
		}
		return page;
	}

	@Override
	public PageInfo<Article> getByUserId(Integer id, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		PageInfo<Article> pageInfo = new PageInfo<Article>(articleMapper.listByUser(id));

		return pageInfo;
	}

	@Override
	public PageInfo<Article> checkList(Integer status, int pageNumber, int pageSize) {
		PageHelper.startPage(pageNumber, pageSize);
		List<Article> articles = articleMapper.checkList(status);

		return new PageInfo<Article>(articles);
	}

	@Override
	public PageInfo<Article> listhots(String title, Integer pageNum, Integer pageSize) {
		System.out.println("title   is ============ " + title);
		PageInfo<Article> page = null;
		
		PageHelper.startPage(pageNum, pageSize);
		List<Article> articleList = articleMapper.hotList(title);
		if (title != null && !"".equals(title)) {
			//进行高亮处理
			page = HighUtil.getHigh(elasticsearchTemplate, pageNum, pageSize, title, "hot",0);
		} else {
			page = new PageInfo<Article>(articleList);
		}
		return page;
	}

	@Override
	public List<Article> last(String key) {
		List<Article> articleListLast=articleMapper.lastArticles();
		//进行高亮处理
		List<Article> articleListHigh = HighUtil.getHigh(elasticsearchTemplate, 0, 0, key, null, articleMapper.checkList(null).size()).getList();
		//进行集合替换
		for (Article article : articleListHigh) {
			for (int i = 0; i < articleListLast.size(); i++) {
				if (article.getId() == articleListLast.get(i).getId()) {
					articleListLast.set(i, article);
				}
			}

		}
		return articleListLast;
	}

	@Override
	public PageInfo<Article> hitsList(int page, int pageSize) {
		PageHelper.startPage(1, 10);
		return new PageInfo<Article>(articleMapper.hitsList());
	}

	@Override
	public PageInfo<Article> commentList(int page, int pageSize) {
		PageHelper.startPage(1, 10);
		return new PageInfo<Article>(articleMapper.commentList());
	}

	@Override
	public void sync() {
		// 获取所有的文章
		List<Article> articleListAll = articleMapper.checkList(2);
		// 获取点击量文章
		List<Article> articleListHit = articleMapper.hitsList();
		SyncUtil.syncRedis(articleListAll, redisTemplate, articleListHit);
		SyncUtil.syncEla(articleListAll, elasticsearchTemplate);
	}

}
