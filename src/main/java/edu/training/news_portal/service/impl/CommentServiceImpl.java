package edu.training.news_portal.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import edu.training.news_portal.bean.Comment;
import edu.training.news_portal.bean.User;
import edu.training.news_portal.dao.CommentDao;
import edu.training.news_portal.dao.DaoException;
import edu.training.news_portal.dao.DaoProvider;
import edu.training.news_portal.service.CommentService;
import edu.training.news_portal.service.ServiceException;

public class CommentServiceImpl implements CommentService {

	private final CommentDao commentDao = DaoProvider.getInstance().getCommentDao();

	@Override
	public List<Comment> getCommentsByNews(int newsId) throws ServiceException {
		try {
			return commentDao.getCommentsByNewsId(newsId);
		} catch (DaoException e) {
			throw new ServiceException("Error retrieving comments", e);
		}
	}

	@Override
	public boolean addComment(Comment comment) throws ServiceException {
		try {
			return commentDao.addComment(comment);
		} catch (DaoException e) {
			throw new ServiceException("Error adding comment", e);
		}
	}

	@Override
	public boolean updateComment(Comment comment, User user) throws ServiceException {
		try {
			Comment existing = commentDao.getCommentById(comment.getId());
			if (existing == null)
				return false;

			boolean isOwner = existing.getUserId() == user.getId();
			boolean isAdmin = user.getRole().equalsIgnoreCase("administrator");

			if (!isOwner && !isAdmin)
				return false;

			boolean withinTime = existing.getCreatedAt().plusMinutes(30).isAfter(LocalDateTime.now());

			if (withinTime || isAdmin) {
				return commentDao.updateComment(comment);
			}

			return false;

		} catch (DaoException e) {
			throw new ServiceException("Error updating comment", e);
		}
	}

	@Override
	public boolean deleteComment(int commentId, User user) throws ServiceException {
		try {
			if (!user.getRole().equalsIgnoreCase("administrator"))
				return false;
			return commentDao.deleteComment(commentId);
		} catch (DaoException e) {
			throw new ServiceException("Error deleting comment", e);
		}
	}
}
