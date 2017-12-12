package core.ref;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.model.Question;
import next.model.User;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;

public class ReflectionTest {
	private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

	@Test
	public void showClass() {
		Class<Question> clazz = Question.class;
		logger.debug("멤버 변수");
		for (Field field : clazz.getDeclaredFields()) {
			logger.debug(field.getName());
		}

	}

	@Test
	public void newInstanceWithConstructorArgs() {
		Class<User> clazz = User.class;
		logger.debug(clazz.getName());
	}

	@Test
	public void privateFieldAccess() throws Exception {
		Class<Student> clazz = Student.class;
		Student student = new Student();

		for(Field field : clazz.getDeclaredFields()) {
			field.setAccessible(true);
			System.out.println(field.getName());

			if(field.getType().equals(String.class)) {
				field.set(student, "주한");
			} else if(field.getType().equals(int.class)) {
				field.setInt(student, 100);
			}
		}

		System.out.println(student.getName());
		System.out.println(student.getAge());
	}
}
