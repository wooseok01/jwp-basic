package next.service;

import next.dao.UserDao;
import next.model.User;

public class UserService {
	private UserDao userDao = UserDao.getInstance();
	private static UserService service;

	private UserService(){

	}

	public static UserService getInstance() {
		if(service == null) {
			service = new UserService();
		}

		return service;
	}

	public boolean isSuccessLogin(String userId, String password) {
		User user = userDao.findByUserId(userId);

		if(user == null) {
			throw new NullPointerException("사용자를 찾을 수 없습니다.");
		}

		if(user.matchPassword(password)) {
			return true;
		}

		throw new IllegalStateException("비밀번호가 틀립니다!");
	}
}