package next.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.db.DataBase;


public class ListUserController implements Controller {
    @Override
    public String excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!UserSessionUtils.isLogined(request.getSession())) {
			return "redirect:/users/loginForm";
		}

		request.setAttribute("users", DataBase.findAll());
		return "/user/list.jsp";
	}
}
