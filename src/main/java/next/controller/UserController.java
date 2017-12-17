package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.model.User;
import next.service.UserService;

@Controller
public class UserController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	private UserService userService = UserService.getInstance();

	@RequestMapping("/users/form")
	public ModelAndView createUserForm(HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("/users/form");
		return new ModelAndView(new JspView("/user/form.jsp"));
	}

	@RequestMapping("/users/loginForm")
	public ModelAndView loginForm(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView(new JspView("/user/login.jsp"));
	}

	@RequestMapping("/users")
	public ModelAndView userList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (!UserSessionUtils.isLogined(request.getSession())) {
			return new ModelAndView(new JspView("redirect:/users/loginForm"));
		}

		ModelAndView view = new ModelAndView(new JspView("/user/list.jsp"));
		view.addObject("users", userService.findAll());
		return view;
	}

	@RequestMapping(value = "/users/login", method = RequestMethod.POST)
	public ModelAndView userLogin(HttpServletRequest request, HttpServletResponse response) {
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		User user = userService.findByUserId(userId);

		Assert.notNull(user, "사용자를 찾을 수 없습니다.");
		Assert.isTrue(user.matchPassword(password), "비밀번호가 틀립니다.");

		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		return new ModelAndView(new JspView("redirect:/"));
	}

	@RequestMapping("/users/profile")
	public ModelAndView profile(HttpServletRequest request, HttpServletResponse response) {
		String userId = request.getParameter("userId");

		ModelAndView mav = new ModelAndView(new JspView("/user/profile.jsp"));
		mav.addObject("user", userService.findByUserId(userId));
		return mav;
	}

	@RequestMapping("/users/logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.removeAttribute("user");

		return new ModelAndView(new JspView("redirect:/qna/list"));
	}

	@RequestMapping(value = "/users/create", method = RequestMethod.POST)
	public ModelAndView addUser(HttpServletRequest request, HttpServletResponse response) {
		User user = new User(
			request.getParameter("userId"),
			request.getParameter("password"),
			request.getParameter("name"),
			request.getParameter("email"));

		LOGGER.debug("User : {}", user);
		userService.insert(user);

		return new ModelAndView(new JspView("redirect:/"));
	}

	@RequestMapping("/users/updateForm")
	public ModelAndView modifyUserForm(HttpServletRequest request, HttpServletResponse response) {
		User user = userService.findByUserId(request.getParameter("userId"));
		Assert.isTrue(UserSessionUtils.isSameUser(request.getSession(), user), "다른 사용자의 정보를 수정할 수 없습니다.");

		ModelAndView mav = new ModelAndView(new JspView("/user/updateForm.jsp"));
		mav.addObject("user", user);
		return mav;
	}

	@RequestMapping(value = "/users/update", method = RequestMethod.POST)
	public ModelAndView modifyUser(HttpServletRequest request, HttpServletResponse response) {
		User user = userService.findByUserId(request.getParameter("userId"));
		Assert.isTrue(UserSessionUtils.isSameUser(request.getSession(), user), "다른 사용자의 정보를 수정할 수 없습니다.");

		User updateUser = new User(
			request.getParameter("userId"),
			request.getParameter("password"),
			request.getParameter("name"),
			request.getParameter("email"));

		LOGGER.debug("Update User : {}", updateUser);
		user.update(updateUser);
		return new ModelAndView(new JspView("redirect:/"));
	}

}
