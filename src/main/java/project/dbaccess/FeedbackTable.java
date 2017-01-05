package project.dbaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class FeedbackTable {

    @Autowired
    private JdbcTemplate jdbc;

    public long insert(Long userId, String message) {
	String sql = "insert into feedback (user_id,message,create_date) values (?,?,?)";

	GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

	jdbc.update(new PreparedStatementCreator() {

	    @Override
	    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
		PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		ps.setLong(1, userId);
		ps.setString(2, message);
		ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
		return ps;
	    }
	}, keyHolder);

	return keyHolder.getKey().longValue();
    }
}
