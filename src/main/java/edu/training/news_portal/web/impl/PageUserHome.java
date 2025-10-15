package edu.training.news_portal.web.impl;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import edu.training.news_portal.bean.User;
import edu.training.news_portal.web.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class PageUserHome implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String errorMessage = "Пожалуйста авторизуйтесь!";
		String encodedMessage = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8.toString());
		if (session == null) {

			response.sendRedirect("Controller?command=page_auth&message=" + encodedMessage);

			return;
		}

		User user = (User) session.getAttribute("auth");
		if (user == null) {
			response.sendRedirect("Controller?command=page_auth&message=" + encodedMessage);
			return;
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/userHome.jsp");
		dispatcher.forward(request, response);
	}

}
