package next.template;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessResourceFailureException;

import core.jdbc.ConnectionManager;

/**
 * Created by wooseokSong on 2017-11-27.
 */
public abstract class JDBCTemplate {
	public void update(String query) {
		try (Connection con = ConnectionManager.getConnection();
			 PreparedStatement pstmt = con.prepareStatement(query)) {
			setValues(pstmt);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new DataAccessResourceFailureException("sql connection fail!", e);
		}
	}

	public <T> T select(String query, RowMapper<T> rowMapper) {
		try (Connection con = ConnectionManager.getConnection();
			 PreparedStatement pstmt = con.prepareStatement(query)) {
			setValues(pstmt);

			return rowMapper.mapRow(pstmt.executeQuery());

		} catch (SQLException e) {
			throw new DataAccessResourceFailureException("sql connection fail!", e);
		}
	}

	public void closeResultSet(ResultSet resultSet) {
		try {
			resultSet.close();
		} catch (SQLException e) {
			throw new DataAccessResourceFailureException("resultSet close fail", e);
		}
	}

	public abstract void setValues(PreparedStatement pstmt);

	public abstract String createQuery();
}
