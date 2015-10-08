package com.github.flyyan.http.tool;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

import com.github.flyyan.http.bean.RepoCommitItem;
import com.github.flyyan.http.bean.RepoItem;
import com.github.flyyan.tool.ImageTool;

public class HtmlParserTool {

	
	
	
	/***
	 * 获取提交的请求路径
	 * @param content
	 * @return
	 */
	public static String getCommitsPath(String content){
		Document document = Jsoup.parse(content);
		Elements lis = document.getElementsByAttributeValue("class","commits");
		String path = lis.get(0).getElementsByTag("a").get(0).attr("href");
		return path;
	}
	
	
	public static List<RepoCommitItem> parseHtml2ReposCommits(String html) {
		List<RepoCommitItem> commitItems = new ArrayList<RepoCommitItem>();
		RepoCommitItem item = new RepoCommitItem();
		Document document = Jsoup.parse(html);
		Elements lis = document
				.getElementsByAttributeValue(
						"class",
						"commit commits-list-item table-list-item js-navigation-item js-details-container js-socket-channel js-updatable-content");

		if(lis == null || lis.size() == 0)
			return null ;
		for (int i = 0; i < lis.size(); i++) {
			item = new RepoCommitItem() ;
			Element li = lis.get(i);
			Element image = li.getElementsByTag("img").get(0);
			String imagePath = image.attr("src");
			String aMeaage = li.getElementsByAttributeValue("class", "message")
					.get(0).attr("title");
			String aAuthor = li
					.getElementsByAttributeValue("class",
							"commit-author tooltipped tooltipped-s").get(0)
					.html();
			String time = li.getElementsByTag("time").get(0).html();
			
			item.setAuthorName(aAuthor);
			item.setIcon(imagePath);
			item.setUpdateTime(time);
			item.setUpdateChangeLog(aMeaage);
			item.setBmIcon(ImageTool.getImage(imagePath));
			
			commitItems.add(item);
		}
		
		
		return commitItems;
	}

	/**
	 * 将html页面中的内容解析为RepoItem对象
	 * 
	 * @param html
	 * @return
	 */
	public static List<RepoItem> parseHtml2Repos(String html) {

		Document document = Jsoup.parse(html);

		List<RepoItem> repoItems = new ArrayList<RepoItem>();

		Elements lis = document.getElementsByTag("ul").get(2)
				.getElementsByTag("li");

		for (int i = 0; i < lis.size(); i++) {
			repoItems.add(parseLi2Resp(lis.get(i)));
		}

		Log.i("asd", document.getElementsByAttribute("pagination").html());

		return repoItems;
	}

	private static RepoItem parseLi2Resp(Element li) {
		String language = "";
		String starts = "";
		String forks = "";

		if (li.getElementsByTag("div").size() == 0) {
			return null;
		}

		String[] divTexts = li.getElementsByTag("div").get(0).text().split(" ");
		if (divTexts.length == 3) {
			language = divTexts[0];
			starts = divTexts[1];
			forks = divTexts[2];
		} else {
			starts = divTexts[0];
			forks = divTexts[1];
		}
		String path = li.getElementsByTag("a").get(2).attr("href");
		// String emName =
		// li.getElementsByTag("a").get(2).getElementsByTag("em").get(0).text();
		// String normalName = path.substring(1 ,path.length()-emName.length());
		String description = li.getElementsByTag("p").get(0).text();

		RepoItem repoItem = new RepoItem();

		repoItem.setDescription(description);
		repoItem.setForks(forks);
		repoItem.setStargazers(starts);
		repoItem.setPath(path);
		repoItem.setLanguage(language);
		// repoItem.setNormalName(normalName);
		// repoItem.setEmName(emName);

		return repoItem;
	}

	/**
	 * 获取查询到的总页数
	 * 
	 * @param html
	 * @return
	 */
	public static int getPageNumber(String html) {
		Document document = Jsoup.parse(html);
		Elements elements = document.getElementsByAttributeValue("class",
				"pagination");
		if (elements == null || elements.size() == 0)
			return 0;

		Elements as = elements.get(0).getElementsByTag("a");

		if (as == null || as.size() == 0)
			return 0;
		String a = as.get(as.size() - 2).html();

		return Integer.parseInt(a);
	}
}
