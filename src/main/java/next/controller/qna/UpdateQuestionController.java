package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;
import next.model.Question;

public class UpdateQuestionController extends AbstractController {
	private QuestionDao questionDao = new QuestionDao();

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long questionId = Long.parseLong(request.getParameter("questionId"));
		Question question = new Question(
			request.getParameter("writer"),
			request.getParameter("title"),
			request.getParameter("contents"));

		question.setQuestionId(questionId);

		questionDao.updateQuestion(question);
		return jspView("redirect:/qna/show?questionId=" + questionId);
	}

}
