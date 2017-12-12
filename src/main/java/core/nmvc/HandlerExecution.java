package core.nmvc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.ModelAndView;

public class HandlerExecution {
	private final Logger LOGGER = LoggerFactory.getLogger(HandlerExecution.class);
	private Method method;
	private Object object;

	public HandlerExecution(Object object, Method method) {
		this.object = object;
		this.method = method;
	}

	public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			return (ModelAndView)method.invoke(object, request, response);
		} catch (IllegalAccessException | InvocationTargetException e) {
			LOGGER.error("handle error!!", e);
			throw new RuntimeException("Handler error!!", e);
		}
	}
}
