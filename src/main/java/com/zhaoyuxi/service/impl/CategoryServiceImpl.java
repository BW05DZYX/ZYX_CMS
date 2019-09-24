package com.zhaoyuxi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhaoyuxi.cms.dao.CategoryMapper;
import com.zhaoyuxi.cms.entity.Category;
import com.zhaoyuxi.service.CategoryService;

/**
*@author 作者:赵玉玺
*@version 创建时间：2019年9月21日 下午4:25:08
*分类业务层
*/
@Service
public class CategoryServiceImpl implements CategoryService {

	
	@Autowired
	CategoryMapper categoryMapper; 
	
	@Override
	public List<Category> getCategoryByChId(Integer cid) {
		return categoryMapper.getCategoryByChId(cid);
	}

}
