package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;

public class AddAnswerController extends AbstractController {
	private static final Logger log = LoggerFactory.getLogger(AddAnswerController.class);

	private AnswerDao answerDao = new AnswerDao();
	private QuestionDao questionDao = new QuestionDao();

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long questionId = Long.parseLong(request.getParameter("questionId"));
		Answer answer = new Answer(
			request.getParameter("writer"),
			request.getParameter("contents"),
			questionId);
		log.debug("answer : {}", answer);

		Answer savedAnswer = answerDao.insert(answer);

		Question question = questionDao.findById(questionId);
		questionDao.updateAnswerCount(questionId, question.getCountOfComment() + 1);
		return jsonView().addObject("answer", savedAnswer);
	}
}
