package next.http;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import next.controller.Controller;

/**
 * Created by wooseokSong on 2017-11-27.
 */
@WebServlet(name = "dispatcherServlet", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doService(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doService(req, resp);
	}

	private void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		Controller controller = RequestMapping.getController(path);
		if (controller == null) {
			throw new IllegalStateException("request path is wrong!");
		}

		String forwardUrl = controller.excute(request, response);
		if (forwardUrl.startsWith("redirect:")) {
			response.sendRedirect(StringUtils.replace(forwardUrl, "redirect:", ""));
			return;
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(forwardUrl);
		dispatcher.forward(request, response);
	}

}
