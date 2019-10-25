package com.zhaoyuxi.cms.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.zhaoyuxi.cms.entity.Article;
import com.zhaoyuxi.service.ArticleService;

/**
*@author 作者:赵玉玺
*@version 创建时间：2019年10月22日 下午4:38:46
*类功能说明
*/
@Component
public class MessageListernerConsumerService implements MessageListener<Integer, String>{

	@Autowired
	private ArticleService service;
	
	@Override
	public void onMessage(ConsumerRecord<Integer, String> data) {
			//接收kafka信息
			String str=data.value();
			Gson gson=new Gson();
			//转储为对象
			Article art=gson.fromJson(str, Article.class);
			System.out.println(art);
			//保存到数据库
			service.add(art);
	}
	
}
