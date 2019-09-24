package com.zhaoyuxi.service;

import java.util.List;

import com.zhaoyuxi.cms.entity.Channel;

/**
*@author 作者:赵玉玺
*@version 创建时间：2019年9月21日 下午3:39:52
*类功能说明
*/
public interface ChannelService {
	
	/**
	 * 查询频道列表
	 * @return
	 */
	List<Channel> getChannels();
}
