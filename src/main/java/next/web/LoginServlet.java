package next.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import core.db.DataBase;
import next.model.User;

/**
 * Created by wooseokSong on 2017-11-20.
 */
@WebServlet("/user/login")
public class LoginServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
		ServletException,
		IOException {
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");

		User user = new User(userId, password);

		RequestDispatcher requestDispatcher;
		if (StringUtils.isAnyEmpty(userId, password) || !isLoginSuccess(user)) {
			requestDispatcher = request.getRequestDispatcher("/user/login_failed.jsp");
			requestDispatcher.forward(request, response);
			return;
		}

		user = DataBase.findUserById(userId);
		HttpSession session = request.getSession();
		session.setAttribute("user", user);

		requestDispatcher = request.getRequestDispatcher("/index.jsp");
		requestDispatcher.forward(request, response);
	}

	private boolean isLoginSuccess(User targetUser) {
		User user = DataBase.findUserById(targetUser.getUserId());
		if (user == null) {
			return false;
		}

		if (StringUtils.equals(user.getUserId(), targetUser.getUserId())
			&& StringUtils.equals(user.getPassword(), targetUser.getPassword())) {
			return true;
		}

		return false;
	}
}
