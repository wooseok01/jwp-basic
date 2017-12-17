package next.service;

import java.sql.SQLException;
import java.util.List;

import next.dao.UserDao;
import next.model.User;

public class UserService {
	private static UserService userService = new UserService();
	private UserDao userDao = UserDao.getInstance();

	private UserService() {}

	public static UserService getInstance() {
		return userService;
	}

	public List<User> findAll() throws SQLException {
		return userDao.findAll();
	}

	public User findByUserId(String userId) {
		return userDao.findByUserId(userId);
	}

	public void insert(User user) {
		userDao.insert(user);
	}
}
