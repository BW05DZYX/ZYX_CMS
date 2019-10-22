package com.zhaoyuxi.cms.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

/**
*@author 作者:赵玉玺
*@version 创建时间：2019年10月22日 下午4:38:46
*类功能说明
*/
@Component
public class MessageListernerConsumerService implements MessageListener<Integer, String>{

	@Override
	public void onMessage(ConsumerRecord<Integer, String> data) {
			String str=data.value();
			System.out.println(str);
	}
	
}
