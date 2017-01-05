package project.dbaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.Statement;

@Repository
public class UserTable {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public Long getUserId(String username) {
	String sql = "select id from user where username='" + username + "'";
	
	SqlRowSet rows = jdbcTemplate.queryForRowSet(sql);
	
	Long userId = null;
	
	while(rows.next()) {
	    userId = rows.getLong("id");
	}
	
	return userId;
    }
    
    public boolean isExisting(String userEmail) {
	String sql = "select count(0) as isin from user where username=? or email=?" ;
	
	Object[] args = {userEmail,userEmail};
	
	SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, args);
	
	rows.next();
	int isin = rows.getInt("isin");
	
	if (isin  == 0) {
	    return false;
	} 
	
	return true;
    }
    
    public long insert(String username, String email, String password) {
	String sql = "insert into user (username,email,password,user_role,create_date) values (?,?,?,?,?)";
	
	GeneratedKeyHolder gkey = new GeneratedKeyHolder();
	
	jdbcTemplate.update(new PreparedStatementCreator() {
	    
	    @Override
	    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
		PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, username);
		ps.setString(2, email);
		ps.setString(3, password);
		ps.setString(4, "role_user");
		ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
		return ps;
	    }
	}, gkey);
	
	return gkey.getKey().longValue();
    }
}
