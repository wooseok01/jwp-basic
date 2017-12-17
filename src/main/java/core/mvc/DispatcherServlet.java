package core.mvc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import core.nmvc.AnnotationHandlerMapping;
import core.nmvc.HandlerExecution;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

	private AnnotationHandlerMapping annotationHandlerMapping;

	@Override
	public void init() throws ServletException {
		// Controller 어노테이션이 들어간 클래스를 모두 찾는다. -> ControllerScanner 클래스 사용.
		// 어노테이션 기반의 매핑을 담당할 AnnotationHandlerMapping 클래스를 추가한다.
		// AnnotatedHandlerMapping 클래스에 클라이언트 요청정보를 전달하면 요청에 해당 하는 HandlerExcution을 반환한다.
		annotationHandlerMapping = new AnnotationHandlerMapping("next.controller");
		annotationHandlerMapping.initialize();
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		String requestUri = request.getRequestURI();
		logger.debug("Method : {}, Request URI : {}", request.getMethod(), requestUri);

		HandlerExecution handlerExecution = annotationHandlerMapping.getHandler(request);
		
		try {
			Assert.notNull(handlerExecution, "URI is not exist!");
			ModelAndView mav = handlerExecution.handle(request, response);

			View view = mav.getView();
			view.render(mav.getModel(), request, response);
			
		} catch (Throwable e) {
			logger.error("Exception : {}", e);
			throw new ServletException(e.getMessage());
		}
	}
}
