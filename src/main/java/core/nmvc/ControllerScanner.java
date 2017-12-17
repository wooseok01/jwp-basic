package core.nmvc;

import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import core.annotation.Controller;

public class ControllerScanner {
	private Reflections reflections;
	private final Logger LOGGER = LoggerFactory.getLogger(ControllerScanner.class);

	public Map<Class<?>, Object> getControllers(Object basePackage)  {
		this.reflections = new Reflections(basePackage);
		Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(Controller.class);

		Map<Class<?>, Object> controllerMapper = Maps.newHashMap();
		for (Class<?> clazz : controllers) {
			try {
				controllerMapper.put(clazz, clazz.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				LOGGER.error(e.getMessage());
			}
		}
		
		return controllerMapper;
	}
}
