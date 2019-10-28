package com.zhaoyuxi.cms.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.zhaoyuxi.cms.entity.Article;
import com.zhaoyuxi.cms.entity.Channel;
import com.zhaoyuxi.cms.entity.Link;
import com.zhaoyuxi.cms.utils.PageUtil;
import com.zhaoyuxi.service.ArticleService;
import com.zhaoyuxi.service.ChannelService;

/**
*@author 作者:赵玉玺
*@version 创建时间：2019年9月24日 上午8:29:10
*主页控制层
*/
@Controller
public class IndexController {
private Logger log = Logger.getLogger(IndexController.class);
	
	@Autowired
	ChannelService cService;
	
	@Autowired
	ArticleService articleService ;
	
	/**
	 * 获取热门，最新，友情链接，以及搜索功能数据
	 * @param request
	 * @param pageSize
	 * @param pageNum
	 * @param key
	 * @return
	 */
	@RequestMapping(value= {"/index","/",""},method=RequestMethod.GET)
	public String index(HttpSession session,HttpServletRequest request,
			 @RequestParam( value="pageSize",defaultValue = "2") Integer pageSize,
			 @RequestParam(value="page",defaultValue = "1") Integer pageNum,
			 @RequestParam(defaultValue = "") String key 
			) {
		log.info("this is log test");
		//判断session中是否有值
		if(session.getAttribute("key")==null) {
			//将空白部分替换
			if(key.contains(" ")||key.contains("\b")) {
				String key2=key;
				key=key.replaceAll(" ", "");
				key=key.replaceAll("\b", "");
				//进行回显的key值
				session.setAttribute("key2", key2);
			}else {
				session.setAttribute("key2", key);
			}
			//进行操作的key值
			session.setAttribute("key", key);
		}
		
		
		List<Channel> channels = cService.getChannels();
		request.setAttribute("channels", channels);
		
		//获取热门
		PageInfo<Article> arPage = articleService.listhots(key,pageNum, pageSize);
		request.setAttribute("pageInfo", arPage);
		//获取最新
		List<Article> lastArticles = articleService.last(key);
		request.setAttribute("lasts", lastArticles);
		//友情链接
		List<Link> links =  new ArrayList<Link>();
		links.add(new Link("http://www.bwie.net","八维好厉害"));
		links.add(new Link("http://www.bwie.org","八维真牛"));
		links.add(new Link("http://www.bwie.com","八维顶呱呱"));
		request.setAttribute("links", links);
		
		String pageString = PageUtil.page(arPage.getPageNum(), arPage.getPages(), "/article/hots?key="+key, arPage.getPageSize());
		request.setAttribute("pageStr", pageString);
		return "index/index";
	}
	
}
