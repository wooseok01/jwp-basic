package next.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import core.db.DataBase;
import next.model.User;

/**
 * Created by wooseokSong on 2017-11-20.
 */
@WebServlet("/user/update")
public class ModifyUserServlet extends HttpServlet {
	@Override
	public void init() throws ServletException {
		super.init();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(isLoginUser(request)) {
			request.setAttribute("user", request.getSession().getAttribute("user"));
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/user/update.jsp");
			requestDispatcher.forward(request, response);
			return;
		}

		response.sendRedirect("/user/list");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userId");
		String name = request.getParameter("name");
		String email = request.getParameter("email");

		User user = DataBase.findUserById(userId);
		if (user != null && StringUtils.isAnyEmpty(userId, name, email) == false) {
			user.setName(name);
			user.setEmail(email);

			DataBase.addUser(user);
			request.getSession().setAttribute("user", user);
		}

		response.sendRedirect("/user/list");
	}

	private boolean isLoginUser(HttpServletRequest request) {
		User user = (User)request.getSession().getAttribute("user");
		if (user == null) {
			return false;
		}

		return true;
	}
}