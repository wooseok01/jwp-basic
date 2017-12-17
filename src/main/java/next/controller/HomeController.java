package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;

@Controller
public class HomeController {
	private QuestionDao questionDao = QuestionDao.getInstance();

	@RequestMapping("/")
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView(new JspView("home.jsp"))
			.addObject("questions", questionDao.findAll());
	}
}
