package com.zhaoyuxi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhaoyuxi.cms.dao.ChannelMapper;
import com.zhaoyuxi.cms.entity.Channel;
import com.zhaoyuxi.service.ChannelService;

/**
 * @author 作者:赵玉玺
 * @version 创建时间：2019年9月21日 下午4:26:07 
 * 频道业务层
 */
@Service
public class ChannelServiceImpl implements ChannelService {
	
	@Autowired
	ChannelMapper channelMapper;

	@Override
	public List<Channel> getChannels() {
		return channelMapper.getChannels();
	}

}
