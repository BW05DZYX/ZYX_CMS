package com.zhaoyuxi.test;

import java.io.IOException;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.zhaoyuxi.common.utils.FileUtil;


/**
 * @author 作者:赵玉玺
 * @version 创建时间：2019年10月25日 下午1:35:04 类功能说明
 */

public class JsoupTest {
	
	
	@Test
	public void test_xinlang() throws IOException {
		// 记录文章数
		int count = 0;
		// 获取连接对象
		Connection connect = Jsoup.connect("https://www.qishuta.com/du/23/23535/");
		// 获取文档对象
		Document document = connect.get();
		// 获取当前文档的所有超链接
		Elements ahrefs = document.select("a[href]");
		// 遍历元素对象
		for (Element href : ahrefs) {
			// 超链接的url地址
			String url = "https://www.qishuta.com/du/23/23535/"+href.attr("href");
			// 定义表达式 https://news.163.com ***** html
			String regex = "https://www\\.qishuta\\.com/du/23/23535/\\d+\\.html";

			// 以https://news.163.com开头，以html结尾
//			url.startsWith("https://news\\\\.163\\\\.com");
//			url.endsWith("html");

			// 特殊要求
			if (url != null && Pattern.matches(regex, url)) {
				// 连接的文本内容
				String title = href.text();
				System.out.println(url + "@@@@@@@@@" + title);
				count++;

				// 获取文章的文档对象
				Document articleDoc = Jsoup.connect(url).get();
				// 获取文章的内容元素对象
				Element articleContentElement = articleDoc.getElementById("content1");
				// 判断元素是否为空
				if (articleContentElement != null) {
					// 获取纯文本内容
					String content = articleContentElement.text();

					// 去除标题中的特殊符号
					title = title.replace("?", "").replace("\"", "").replace(":", "");

					// 写入到文件中
					FileUtil.writeFile("D:\\1705DJsoup\\" + title + ".txt", content, "utf8");
				}
			}
			if(count==50) {
				break;
			}
		}
		System.out.println("首页中找到了复合条件的网址有：" + count + "篇文章");
	}
}
