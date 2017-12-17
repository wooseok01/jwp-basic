package core.nmvc;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import core.annotation.RequestMapping;
import core.annotation.RequestMethod;

public class AnnotationHandlerMapping {
	private Object[] basePackage;
	private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();
	private final Logger LOGGER = LoggerFactory.getLogger(AnnotationHandlerMapping.class);
	private ControllerScanner cs = new ControllerScanner();

	public AnnotationHandlerMapping(Object... basePackage) {
		this.basePackage = basePackage;
	}

	public void initialize() {
		Map<Class<?>, Object> controllerMap = Maps.newHashMap();
		for (Object base : basePackage) {
			controllerMap.putAll(cs.getControllers(base));
		}

		Set<Method> methods = getMethods(controllerMap.keySet());
		for (Method method : methods) {
			RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
			HandlerKey handlerKey = new HandlerKey(requestMapping.value(), requestMapping.method());
			HandlerExecution handlerExecution = new HandlerExecution(controllerMap.get(method.getDeclaringClass()),
				method);

			handlerExecutions.put(handlerKey, handlerExecution);
			LOGGER.debug("handerExcutions add {}", handlerKey.toString());
		}

	}

	@SuppressWarnings("unchecked")
	private Set<Method> getMethods(Set<Class<?>> classes) {
		Set<Method> methods = Sets.newHashSet();
		for (Class<?> clazz : classes) {
			methods.addAll(ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withAnnotation(RequestMapping.class)));
		}

		return methods;
	}

	public HandlerExecution getHandler(HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
		return handlerExecutions.get(new HandlerKey(requestUri, rm));
	}
}
