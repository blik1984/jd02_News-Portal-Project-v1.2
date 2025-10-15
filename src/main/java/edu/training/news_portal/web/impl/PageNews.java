package edu.training.news_portal.web.impl;

import java.io.IOException;
import java.util.List;

import edu.training.news_portal.bean.News;
import edu.training.news_portal.service.NewsService;
import edu.training.news_portal.service.ServiceException;
import edu.training.news_portal.service.ServiceProvider;
import edu.training.news_portal.web.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PageNews implements Command{
	private final NewsService newsService = ServiceProvider.getInstance().getNewsService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int newsId = Integer.parseInt(request.getParameter("id"));
		List<News> newsList;
		try {
			newsList = newsService.takeTopNews(3);
			for(News news : newsList) {
				if(news.getId() == newsId) {
					request.setAttribute("news", news);
					RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/news.jsp");
					dispatcher.forward(request, response);
					return ;
				}
			}
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
