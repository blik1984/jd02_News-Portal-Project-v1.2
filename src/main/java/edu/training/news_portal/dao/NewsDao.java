package edu.training.news_portal.dao;

import java.util.List;

import edu.training.news_portal.bean.News;

public interface NewsDao {
	List<News> topNews(int count) throws DaoException;
	boolean addNews(News news) throws DaoException;
	News getNewsById (int id) throws DaoException;
}
