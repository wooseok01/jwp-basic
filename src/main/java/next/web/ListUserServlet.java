package next.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.db.DataBase;
import next.model.User;

@WebServlet("/user/list")
public class ListUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher requestDispatcher;
		if (isLogin(request)) {
			requestDispatcher = request.getRequestDispatcher("/user/list.jsp");
			requestDispatcher.forward(request, response);
			return;
		}

		request.setAttribute("users", DataBase.findAll());
		requestDispatcher = request.getRequestDispatcher("/user/login.jsp");
		requestDispatcher.forward(request, response);
	}

	private boolean isLogin(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if (user == null) {
			return false;
		}

		return true;
	}
}
