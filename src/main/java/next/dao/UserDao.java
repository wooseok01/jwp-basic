package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import next.model.User;
import next.template.JDBCTemplate;

public class UserDao {
	public void insert(User user) throws SQLException {
		JDBCTemplate jdbcTemplate = new JDBCTemplate() {
			@Override
			public void setValues(User user, PreparedStatement pstmt) {
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
				return "INSERT INTO USERS(userId, password, name, email) VALUE(?,?,?,?)";
			}
		};

		excuteQuery(user, jdbcTemplate);
	}

	public void update(User user) throws SQLException {
		JDBCTemplate jdbcTemplate = new JDBCTemplate() {
			@Override
			public void setValues(User user, PreparedStatement pstmt) {
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

		excuteQuery(user, jdbcTemplate);
	}

	public List<User> findAll() throws SQLException {
		String sql = "SELECT userId, password, name, email FROM USERS";

		try (Connection con = ConnectionManager.getConnection();
			 PreparedStatement pstmt = con.prepareStatement(sql);
			 ResultSet rs = pstmt.executeQuery()) {

			List<User> users = new ArrayList<>();

			while (rs.next()) {
				users.add(new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
					rs.getString("email")));
			}

			return users;
		}
	}

	public User findByUserId(String userId) throws SQLException {
		String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
		ResultSet rs = null;
		try (Connection con = ConnectionManager.getConnection();
			 PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();

			User user = null;
			if (rs.next()) {
				user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
					rs.getString("email"));
			}

			return user;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	private void excuteQuery(User user, JDBCTemplate jdbcTemplate) {
		try (Connection con = ConnectionManager.getConnection()) {
			String sql = jdbcTemplate.createQuery();
			PreparedStatement pstmt = con.prepareStatement(sql);
			jdbcTemplate.setValues(user, pstmt);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("excuteQuery occur error!", e);
		}
	}
}
