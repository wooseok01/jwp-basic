package core.ref;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.lang.reflect.Method;

public class Junit3Test {
    public void test1() throws Exception {
        System.out.println("Running Test1");
    }

    public void test2() throws Exception {
        System.out.println("Running Test2");
    }

    public void three() throws Exception {
        System.out.println("Running Test3");
    }

    @Test
    public void refelectionTest() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;
        for (Method method : clazz.getDeclaredMethods()) {
            if (StringUtils.startsWith(method.getName(), "test")) {
                method.invoke(clazz.newInstance());
            }
        }
    }
}
