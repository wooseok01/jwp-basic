package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import core.jdbc.DataAccessException;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;
import next.model.Result;
import next.model.User;

public class UpdateQuestionConfirmController extends AbstractController {
	private QuestionDao questionDao = new QuestionDao();

	private static final String NOT_LOGIN = "not_login";
	private static final String DATA_ACCESS_FAIL = "data_access_fail";
	private static final String ACCESS_DENIED = "access_denied";

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = jsonView();
		try {
			if (isWriter(request, questionDao)) {
				return modelAndView.addObject("result", Result.ok());
			}

		} catch (IllegalArgumentException e) {
			return modelAndView.addObject("result", Result.fail(NOT_LOGIN));

		} catch (DataAccessException e2) {
			return modelAndView.addObject("result", Result.fail(DATA_ACCESS_FAIL));
		}

		return modelAndView.addObject("result", Result.fail(ACCESS_DENIED));
	}

	protected static boolean isWriter(HttpServletRequest request, QuestionDao questionDao) {
		User user = (User)request.getSession().getAttribute("user");
		long questionId = Long.parseLong(request.getParameter("questionId"));

		Assert.notNull(user, NOT_LOGIN);

		if (StringUtils.equals(questionDao.findById(questionId).getWriter(), user.getName())) {
			return true;
		}

		return false;
	}

}
