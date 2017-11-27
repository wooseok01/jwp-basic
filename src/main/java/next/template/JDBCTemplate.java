package next.template;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLDataException;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;

import core.jdbc.ConnectionManager;
import next.model.User;

/**
 * Created by wooseokSong on 2017-11-27.
 */
public abstract class JDBCTemplate {
	public void insert(User user) {
		try (Connection con = ConnectionManager.getConnection()){

		}catch(SQLException e) {
			throw new DataAccessResourceFailureException("sql connection fail!", e);
		}
	}

	public abstract void setValues(User user, PreparedStatement pstmt);

	public abstract String createQuery();
}
