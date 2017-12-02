package next.template;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by wooseokSong on 2017-12-02.
 */
@FunctionalInterface
public interface RowMapper<T> {
	T mapRow(ResultSet resultSet) throws SQLException;
}
