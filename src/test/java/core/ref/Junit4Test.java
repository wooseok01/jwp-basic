package core.ref;

import org.junit.Test;

import java.lang.reflect.Method;

public class Junit4Test {
	@MyTest
	public void one() throws Exception {
		System.out.println("Running Test1");
	}

	@MyTest
	public void two() throws Exception {
		System.out.println("Running Test2");
	}

	public void testThree() throws Exception {
		System.out.println("Running Test3");
	}

	@Test
	public void 리플랙션_어노테이션_테스트() throws Exception {
		Class<Junit4Test> clazz = Junit4Test.class;
		for (Method method : clazz.getMethods()) {
			if (method.getAnnotation(MyTest.class) != null) {
				method.invoke(clazz.newInstance());
			}
		}
	}
}
