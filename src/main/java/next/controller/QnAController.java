package next.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.CannotDeleteException;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.User;
import next.service.QnaService;

@Controller
public class QnAController {
	private QuestionDao questionDao = QuestionDao.getInstance();
	private AnswerDao answerDao = AnswerDao.getInstance();
	private QnaService qnaService = QnaService.getInstance();

	@RequestMapping("/qna/show")
	public ModelAndView showQuestion(HttpServletRequest request, HttpServletResponse response) {
		long questionId = Long.parseLong(request.getParameter("questionId"));

		Question question = questionDao.findById(questionId);
		List<Answer> answers = answerDao.findAllByQuestionId(questionId);

		ModelAndView mav = new ModelAndView(new JspView("/qna/show.jsp"));
		mav.addObject("question", question);
		mav.addObject("answers", answers);
		return mav;
	}

	@RequestMapping("/qna/form")
	public ModelAndView questionForm(HttpServletRequest request, HttpServletResponse response) {
		if (!UserSessionUtils.isLogined(request.getSession())) {
			return new ModelAndView(new JspView("redirect:/users/loginForm"));
		}

		return new ModelAndView(new JspView("/qna/form.jsp"));
	}

	@RequestMapping(value = "/qna/create", method = RequestMethod.POST)
	public ModelAndView addQuestion(HttpServletRequest request, HttpServletResponse response) {
		if (!UserSessionUtils.isLogined(request.getSession())) {
			return new ModelAndView(new JspView("redirect:/users/loginForm"));
		}

		User user = UserSessionUtils.getUserFromSession(request.getSession());
		Question question = new Question(
			user.getUserId(),
			request.getParameter("title"),
			request.getParameter("contents"));

		questionDao.insert(question);
		return new ModelAndView(new JspView("redirect:/"));
	}

	@RequestMapping("/qna/updateForm")
	public ModelAndView modifyForm(HttpServletRequest request, HttpServletResponse response) {
		if (!UserSessionUtils.isLogined(request.getSession())) {
			return new ModelAndView(new JspView("redirect:/users/loginForm"));
		}

		long questionId = Long.parseLong(request.getParameter("questionId"));
		Question question = questionDao.findById(questionId);

		if (!question.isSameUser(UserSessionUtils.getUserFromSession(request.getSession()))) {
			throw new IllegalStateException("다른 사용자가 쓴 글을 수정할 수 없습니다.");
		}

		return new ModelAndView(new JspView("/qna/update.jsp")).addObject("question", question);
	}

	@RequestMapping(value = "/qna/update", method = RequestMethod.POST)
	public ModelAndView modifyQuestion(HttpServletRequest request, HttpServletResponse response) {
		if (!UserSessionUtils.isLogined(request.getSession())) {
			return new ModelAndView(new JspView("redirect:/users/loginForm"));
		}

		long questionId = Long.parseLong(request.getParameter("questionId"));
		Question question = questionDao.findById(questionId);

		if (!question.isSameUser(UserSessionUtils.getUserFromSession(request.getSession()))) {
			throw new IllegalStateException("다른 사용자가 쓴 글을 수정할 수 없습니다.");
		}

		Question newQuestion = new Question(
			question.getWriter(),
			request.getParameter("title"),
			request.getParameter("contents"));

		question.update(newQuestion);
		questionDao.update(question);
		return new ModelAndView(new JspView("redirect:/"));
	}

	@RequestMapping("/qna/delete")
	public ModelAndView removeQuestion(HttpServletRequest request, HttpServletResponse response) {
		if (!UserSessionUtils.isLogined(request.getSession())) {
			return new ModelAndView(new JspView("redirect:/users/loginForm"));
		}

		long questionId = Long.parseLong(request.getParameter("questionId"));
		try {
			qnaService.deleteQuestion(questionId, UserSessionUtils.getUserFromSession(request.getSession()));
			return new ModelAndView(new JspView("redirect:/"));

		} catch (CannotDeleteException e) {
			return new ModelAndView(new JspView("show.jsp"))
				.addObject("question", qnaService.findById(questionId))
				.addObject("answers", qnaService.findAllByQuestionId(questionId))
				.addObject("errorMessage", e.getMessage());
		}
	}
}
