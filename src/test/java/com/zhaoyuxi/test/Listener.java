package com.zhaoyuxi.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
*@author 作者:赵玉玺
*@version 创建时间：2019年10月25日 下午3:17:08
*类功能说明
*/
public class Listener {
	public static void main(String[] args) {
		//启动kafuka的消费者
		ClassPathXmlApplicationContext application=new ClassPathXmlApplicationContext("classpath:spring.xml");
	}
}
