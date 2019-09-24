package com.zhaoyuxi.service;

import java.util.List;

import com.zhaoyuxi.cms.entity.Category;

/**
*@author 作者:赵玉玺
*@version 创建时间：2019年9月21日 下午3:39:45
*类功能说明
*/
public interface CategoryService {
	/**
	 * 通过频道id查询分类列表
	 * @param cid
	 * @return
	 */
	List<Category> getCategoryByChId(Integer cid);
}
