package next.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.db.DataBase;
import next.model.User;

/**
 * Created by wooseokSong on 2017-11-27.
 */
public class UpdateUserFormController implements Controller {
	@Override
	public String excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userId");
		User user = DataBase.findUserById(userId);

		if (!UserSessionUtils.isSameUser(request.getSession(), user)) {
			throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
		}

		request.setAttribute("user", user);
		return "/user/updateForm.jsp";
	}
}
