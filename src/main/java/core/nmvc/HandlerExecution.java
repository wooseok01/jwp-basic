package core.nmvc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.ModelAndView;

public class HandlerExecution {
	private Object declaedObject; //Class<?>
	private Method method;
	private final Logger LOGGER = LoggerFactory.getLogger(HandlerExecution.class);

	public HandlerExecution(Object declaedObject, Method method) {
		this.declaedObject = declaedObject;
		this.method = method;
	}

	public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) {
		try {
			return (ModelAndView)method.invoke(declaedObject, request, response);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			LOGGER.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}
}
