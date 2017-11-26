package next.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.db.DataBase;
import next.model.User;

public class UpdateUserController implements Controller {
    @Override
    public String excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = DataBase.findUserById(request.getParameter("userId"));
        if (!UserSessionUtils.isSameUser(request.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }

        User updateUser = new User(request.getParameter("userId"), request.getParameter("password"),
            request.getParameter("name"),
            request.getParameter("email"));
        user.update(updateUser);

        return "redirect:/";
    }
}
