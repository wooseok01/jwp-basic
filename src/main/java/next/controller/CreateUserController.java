package next.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.db.DataBase;
import next.model.User;

public class CreateUserController implements Controller {
    @Override
    public String excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User(request.getParameter("userId"), request.getParameter("password"), request.getParameter("name"),
            request.getParameter("email"));

        DataBase.addUser(user);

        return "redirect:/";
    }
}
