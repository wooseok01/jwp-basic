package next.http;

import java.util.HashMap;
import java.util.Map;

import next.controller.Controller;
import next.controller.CreateUserController;
import next.controller.ForwardController;
import next.controller.ListUserController;
import next.controller.LoginController;
import next.controller.LogoutController;
import next.controller.ProfileController;
import next.controller.UpdateUserController;
import next.controller.UpdateUserFormController;

/**
 * Created by wooseokSong on 2017-11-27.
 */
public class RequestMapping {
	private static Map<String, Controller> controllerMap = new HashMap<>();
	static {
		controllerMap.put("/", new ForwardController("/index.jsp"));
		controllerMap.put("/users/form", new ForwardController("/user/form.jsp"));
		controllerMap.put("/users/create", new CreateUserController());
		controllerMap.put("/users/loginForm", new ForwardController("/user/login.jsp"));
		controllerMap.put("/users/login", new LoginController());
		controllerMap.put("/users", new ListUserController());
		controllerMap.put("/users/logout", new LogoutController());
		controllerMap.put("/users/profile", new ProfileController());
		controllerMap.put("/users/updateForm", new UpdateUserFormController());
		controllerMap.put("/users/update", new UpdateUserController());

	}

	public static Controller getController(String name) {
		return controllerMap.get(name);
	}
}
