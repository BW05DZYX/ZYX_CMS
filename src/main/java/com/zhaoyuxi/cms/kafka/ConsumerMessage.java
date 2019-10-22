package com.zhaoyuxi.cms.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;

/**
*@author 作者:赵玉玺
*@version 创建时间：2019年10月17日 上午9:33:10
*类功能说明
*/
public class ConsumerMessage implements MessageListener<Integer, String>{

	@Override
	public void onMessage(ConsumerRecord<Integer, String> data) {
		Object o = data.value();
		System.out.println(String.valueOf(o));
	}


}
