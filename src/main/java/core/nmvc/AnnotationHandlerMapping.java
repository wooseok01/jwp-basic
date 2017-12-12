package core.nmvc;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.reflections.ReflectionUtils;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;

public class AnnotationHandlerMapping {
	private Object[] basePackage;

	private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

	public AnnotationHandlerMapping(Object... basePackage) {
		this.basePackage = basePackage;
	}

	public void initialize() {
		// @Controller 클래스를 찾는다.
		ControllerScanner controllerScanner = new ControllerScanner();
		Map<Class<?>, Object> controllerClassess = controllerScanner.getControllers();

		// @RequestMapping 메소드를 찾는다.
		Set<Method> methods = getRequestMappingClasses(controllerClassess.keySet());

		// @RequestMapping을 기점으로 클래스와 메소드를 handlerExcutions에 넣는다.
		for (Method method : methods) {
			RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
			HandlerKey handlerKey = new HandlerKey(requestMapping.value(), requestMapping.method());

			HandlerExecution handlerExecution
				= new HandlerExecution(controllerClassess.get(method.getDeclaringClass()), method);

			handlerExecutions.put(handlerKey, handlerExecution);
		}
	}

	public HandlerExecution getHandler(HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
		return handlerExecutions.get(new HandlerKey(requestUri, rm));
	}

	private Set<Method> getRequestMappingClasses(Set<Class<?>> classes) {
		Set<Method> methods = Sets.newHashSet();

		for (Class<?> clazz : classes) {
			methods.addAll(ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withAnnotation(RequestMapping.class)));
		}

		return methods;
	}

}
