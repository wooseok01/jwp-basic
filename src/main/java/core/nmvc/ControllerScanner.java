package core.nmvc;

import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import core.annotation.Controller;

public class ControllerScanner {
	private final Logger LOGGER = LoggerFactory.getLogger(ControllerScanner.class);

	public Map<Class<?>, Object> getControllers() {
		Reflections reflections = new Reflections("next.controller");
		Set<Class<?>> annotatedWith = reflections.getTypesAnnotatedWith(Controller.class);

		Map<Class<?>, Object> controllerClasses = Maps.newHashMap();

		try {
			for (Class<?> clazz : annotatedWith) {
				controllerClasses.put(clazz, clazz.newInstance());
			}
		} catch (InstantiationException | IllegalAccessException e) {
			LOGGER.error("getController occur error!", e);
		}

		return controllerClasses;
	}
}