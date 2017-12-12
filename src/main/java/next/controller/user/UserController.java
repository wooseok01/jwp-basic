package next.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.model.User;
import next.service.UserService;

@Controller("UserController")
public class UserController {
	private UserService userService = UserService.getInstance();

	@RequestMapping("/users/form")
	public ModelAndView registerForm() {
		return new ModelAndView(new JspView("/user/form.jsp"));
	}

	@RequestMapping("/users/loginForm")
	public ModelAndView loginForm() {
		return new ModelAndView(new JspView("/user/login.jsp"));
	}

	@RequestMapping(value = "/users/login", method = RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");


		if(userService.isSuccessLogin(userId, password)) {

		}

		if (user == null) {
			throw new NullPointerException("사용자를 찾을 수 없습니다.");
		}

		if (user.matchPassword(password)) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			return new ModelAndView(new JspView("redirect:/"));

		} else {
			throw new IllegalStateException("비밀번호가 틀립니다.");
		}
	}
}