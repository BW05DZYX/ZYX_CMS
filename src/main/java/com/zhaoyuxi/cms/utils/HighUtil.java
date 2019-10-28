package com.zhaoyuxi.cms.utils;
/**
*@author 作者:赵玉玺
*@version 创建时间：2019年10月25日 上午8:24:13
*类功能说明
*/

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;

import com.github.pagehelper.PageInfo;
import com.zhaoyuxi.cms.entity.Article;

public class HighUtil {

	public static PageInfo<Article> getHigh(ElasticsearchTemplate elasticsearchTemplate, int pageNum, int pageSize,
			String title, String hot,Integer size) {
		AggregatedPage<Article> selectObjects=null;
		// 分页高光查询
		if("hot".equals(hot)) {
			selectObjects = (AggregatedPage<Article>) ESUtils.selectObjects(elasticsearchTemplate,
					Article.class, pageNum - 1, pageSize, "id", new String[] { "title" }, title, hot);
		}else {
			selectObjects = (AggregatedPage<Article>) ESUtils.selectObjects(elasticsearchTemplate,
					Article.class, 0, size, "id", new String[] { "title" }, title, hot);
		}
		
		List<Article> articles = selectObjects.getContent();
		// 创建分页后的数据接收对象
		PageInfo<Article> pageInfo = new PageInfo<Article>(articles);
		pageInfo.setPages(selectObjects.getTotalPages());
		pageInfo.setPageNum(pageNum);
		pageInfo.setPageSize(pageSize);
		return pageInfo;

	}

}
