package com.zhaoyuxi.cms.utils;

import java.util.List;

import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import com.zhaoyuxi.cms.entity.Article;

/**
 * @author 作者:赵玉玺
 * @version 创建时间：2019年10月26日 上午9:35:40 同步数据工具类
 */
public class SyncUtil {

	/**
	 * Redis同步最新文章，热门文章，所有文章
	 * 
	 * @param articleListAll
	 * @param redisTemplate
	 * @param articleListHit
	 */
	public static void syncRedis(List<Article> articleListAll, RedisTemplate<String, Article> redisTemplate,
			List<Article> articleListHit) {
		ListOperations<String, Article> ops = redisTemplate.opsForList();
		int count = 0;
		// 判断是否有文章
		if (articleListAll != null) {
			// 清空redis数据
			redisTemplate.delete("last_list");
			redisTemplate.delete("hot_list");
			redisTemplate.delete("all_list");
			// 进行添加
			for (Article article : articleListAll) {
				count++;
				if (count < 5) {// 最新文章添加5篇
					ops.rightPush("last_list", article);
				}
				ops.rightPush("all_list", article);
				if (article.getHot() == 1) {
					ops.rightPush("hot_list", article);
				}
			}
		}
		if (articleListHit != null) {
			redisTemplate.delete("hit_list");
			for (Article article : articleListHit) {
				ops.rightPush("hit_list", article);
			}
		}
		System.out.println("Redis同步成功");
	}

	/**
	 * elasticsearch同步审核通过文章
	 * 
	 * @param articleListAll
	 * @param elasticsearchTemplate
	 */
	public static void syncEla(List<Article> articleListAll, ElasticsearchTemplate elasticsearchTemplate) {
		if (articleListAll != null) {
			elasticsearchTemplate.deleteIndex("cms001");
			elasticsearchTemplate.createIndex("cms001");
			for (Article article : articleListAll) {
				ESUtils.saveObject(elasticsearchTemplate, article.getId() + "", article);
			}

		}
		System.out.println("Elasticsearch同步成功");
	}
}
