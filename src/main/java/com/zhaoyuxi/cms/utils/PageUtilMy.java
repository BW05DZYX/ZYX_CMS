package com.zhaoyuxi.cms.utils;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.zhaoyuxi.cms.entity.Article;

/**
*@author 作者:赵玉玺
*@version 创建时间：2019年9月24日 下午4:57:31
*类功能说明
*/
public class PageUtilMy {
	
	
	
	public static String pageAround(PageInfo<Article> page,Integer aid) {
		StringBuffer str=new StringBuffer();
		List<Article> articleList=page.getList();
		int res=0;
		for(int i=0;i<articleList.size();i++) {
			if(articleList.get(i).getId()==aid) {
				res=i;
				break;
			}
		}
		if(articleList.size()<=1) {
			str.append("<a href='javascript:void(0)'>此频道只有此一篇文章</a>");
		}else if(res==0) {
			str.append("<a href='javascript:void(0)'>上一篇：这已经是第一篇了</a><br>");
			str.append("<a href='/article/getDetail?aId="+articleList.get(res+1).getId()+"'>下一篇："+articleList.get(res+1).getTitle()+"</a>");
		}else if(res==articleList.size()-1){
			str.append("<a href='/article/getDetail?aId="+articleList.get(res-1).getId()+"'>上一篇："+articleList.get(res-1).getTitle()+"</a><br>");
			str.append("<a href='javascript:void(0)'>下一篇：这已经是最后一篇了</a>");
		}else {
			str.append("<a href='/article/getDetail?aId="+articleList.get(res-1).getId()+"'>上一篇："+articleList.get(res-1).getTitle()+"</a><br>");
			str.append("<a href='/article/getDetail?aId="+articleList.get(res+1).getId()+"'>下一篇："+articleList.get(res+1).getTitle()+"</a>");
		}
		return str.toString();
	}
	
}

