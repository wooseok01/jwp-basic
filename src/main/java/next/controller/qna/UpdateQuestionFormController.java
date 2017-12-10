package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;

public class UpdateQuestionFormController extends AbstractController {
	private QuestionDao questionDao = new QuestionDao();

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long questionId = Long.parseLong(request.getParameter("questionId"));

		try {
			if (UpdateQuestionConfirmController.isWriter(request, questionDao)) {
				ModelAndView modelAndView = jspView("/qna/form.jsp");
				modelAndView.addObject("question", questionDao.findById(questionId));
				return modelAndView;
			}
		} catch (IllegalArgumentException e) {
			return jspView("redirect:/");
		}

		return jspView("redirect:/");
	}

}
