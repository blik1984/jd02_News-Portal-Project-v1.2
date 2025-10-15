package edu.training.news_portal.web.filters;

import java.io.IOException;
import java.util.Set;

import edu.training.news_portal.web.RequestPath;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class SecurityFilter implements Filter {

	private static final Set<RequestPath> ALLOWED_WITHOUT_AUTH = Set.of(RequestPath.PAGE_AUTH, RequestPath.PAGE_MAIN,
			RequestPath.PAGE_REGISTRATION, RequestPath.PAGE_PRIVACY, RequestPath.DO_AUTH, RequestPath.DO_REGISTRATION);

	@Override

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		// HttpServletResponse httpResponse = (HttpServletResponse) response;

		String commandParam = httpRequest.getParameter("command");
		HttpSession session = httpRequest.getSession(false);

		if (commandParam != null) {
			try {
				RequestPath command = RequestPath.valueOf(commandParam.toUpperCase());

				if (!ALLOWED_WITHOUT_AUTH.contains(command)) {
					if (session == null || session.getAttribute("auth") == null) {
						request.setAttribute("errorMessage", "Для дальнейшей работы войдите в систему!");
						request.getRequestDispatcher("Controller?command=page_auth").forward(request, response);
						return;
					}
				}
			} catch (IllegalArgumentException e) {
			}
		}
		chain.doFilter(request, response);
	}
}
