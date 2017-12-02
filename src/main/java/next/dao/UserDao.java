package next.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessResourceFailureException;

import next.model.User;
import next.template.JDBCTemplate;

public class UserDao {
	public void insert(User user) throws SQLException {
		JDBCTemplate jdbcTemplate = new JDBCTemplate() {
			@Override
			public void setValues(PreparedStatement pstmt) {
				try {
					pstmt.setString(1, user.getUserId());
					pstmt.setString(2, user.getPassword());
					pstmt.setString(3, user.getName());
					pstmt.setString(4, user.getEmail());

				} catch (SQLException e) {
					throw new RuntimeException("setValues error!", e);
				}
			}

			@Override
			public String createQuery() {
				return "INSERT INTO USERS(userId, password, name, email) VALUES(?,?,?,?)";
			}
		};

		jdbcTemplate.update(jdbcTemplate.createQuery());
	}

	public void update(User user) throws SQLException {
		JDBCTemplate jdbcTemplate = new JDBCTemplate() {
			@Override
			public void setValues(PreparedStatement pstmt) {
				try {
					pstmt.setString(1, user.getPassword());
					pstmt.setString(2, user.getName());
					pstmt.setString(3, user.getEmail());
					pstmt.setString(4, user.getUserId());

				} catch (SQLException e) {
					throw new RuntimeException("setValues error!", e);
				}
			}

			@Override
			public String createQuery() {
				return "UPDATE USERS SET password=?, name=?, email=? WHERE userId=?";
			}
		};

		jdbcTemplate.update(jdbcTemplate.createQuery());
	}

	public List<User> findAll() throws SQLException {
		JDBCTemplate jdbcTemplate = new JDBCTemplate() {
			@Override
			public void setValues(PreparedStatement pstmt) {

			}

			@Override
			public String createQuery() {
				return "SELECT userId, password, name, email FROM USERS";
			}
		};

		return jdbcTemplate.select(jdbcTemplate.createQuery(), (ResultSet resultSet) -> {
			List<User> users = new ArrayList<>();
			while (resultSet.next()) {
				User user = new User(
					resultSet.getString("userId"),
					resultSet.getString("password"),
					resultSet.getString("name"),
					resultSet.getString("email"));

				users.add(user);
			}

			return users;
		});
	}

	public User findByUserId(String userId) throws SQLException {
		JDBCTemplate jdbcTemplate = new JDBCTemplate() {
			@Override
			public void setValues(PreparedStatement pstmt) {
				try {
					pstmt.setString(1, userId);
				} catch (SQLException e) {
					throw new DataAccessResourceFailureException("preparedStatement setString fail", e);
				}
			}

			@Override
			public String createQuery() {
				return "SELECT userId, password, name, email FROM USERS WHERE userId = ?";
			}
		};

		return jdbcTemplate.select(jdbcTemplate.createQuery(), (ResultSet resultSet) -> {
			if (resultSet.next()) {
				return new User(
					resultSet.getString("userId"),
					resultSet.getString("password"),
					resultSet.getString("name"),
					resultSet.getString("email"));
			}

			return null;
		});
	}

}
