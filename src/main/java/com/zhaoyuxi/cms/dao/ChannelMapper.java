package com.zhaoyuxi.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.zhaoyuxi.cms.entity.Channel;

/**
*@author 作者:赵玉玺
*@version 创建时间：2019年9月21日 上午10:26:59
*频道Mapper
*/
@Mapper
public interface ChannelMapper {

//## 增加 ##----------------------------------------------------------------------------------------------------------

//## 删除 ##----------------------------------------------------------------------------------------------------------

//## 修改 ##----------------------------------------------------------------------------------------------------------

//## 查找 ##----------------------------------------------------------------------------------------------------------

	/**
	 * 查询所有频道
	 * @return
	 */
	List<Channel> getChannels();
}
