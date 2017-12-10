package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.Result;

public class DeleteQuestionController extends AbstractController {
	private QuestionDao questionDao = new QuestionDao();
	private AnswerDao answerDao = new AnswerDao();
	
	private final String DELETE_FAIL = "delete_fail";

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long questionId = Long.parseLong(request.getParameter("questionId"));
		if(isOkToDelete(questionId)) {
			questionDao.deleteQuestion(questionId);
			return jsonView().addObject("result", Result.ok());
		}
		
		return jsonView().addObject("result", Result.fail(DELETE_FAIL));
	}
	
	private boolean isOkToDelete(long questionId) {
		Question question = questionDao.findById(questionId);
		if(question.getCountOfComment() == 0) {
			questionDao.deleteQuestion(questionId);
			return true;
		}
		if(answerDao.findAnswerByWriter(questionId, question.getWriter()) == question.getCountOfComment()) {
			return true;
		}
		
		return false;
	}

}
