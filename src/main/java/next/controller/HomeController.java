package next.controller;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.mvc.JspView;
import core.mvc.ModelAndView;

@Controller("HomeController")
public class HomeController {
	@RequestMapping("/")
	public ModelAndView welcome() {
		return new ModelAndView(new JspView("/user/form.jsp"));
	}
}
