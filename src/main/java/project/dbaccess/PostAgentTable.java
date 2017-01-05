package project.dbaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.Statement;

import project.entity.PostAgent;

@Repository
public class PostAgentTable {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void remove(long... ids) {
	String sql = "delete from post_agent where id=?";

	int len = ids.length;

	jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

	    @Override
	    public void setValues(PreparedStatement ps, int i) throws SQLException {
		ps.setLong(1, ids[i]);
	    }

	    @Override
	    public int getBatchSize() {
		return len;
	    }
	});
    }

    public void edit(Long id, String name, String website) {
	String sql = "update post_agent set name=?,website=? where id=?";
	jdbcTemplate.update(sql, new PreparedStatementSetter() {

	    @Override
	    public void setValues(PreparedStatement ps) throws SQLException {
		ps.setString(1, name);
		ps.setString(2, website);
		ps.setLong(3, id);
	    }
	});
    }

    public long insert(String name, String website) {

	String sql = "insert into post_agent (name,website,create_date) " + "values (?,?,?) ";

	GeneratedKeyHolder gkh = new GeneratedKeyHolder();

	jdbcTemplate.update(new PreparedStatementCreator() {

	    @Override
	    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
		PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, name);
		ps.setString(2, website);
		ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
		return ps;
	    }
	}, gkh);
	
	return gkh.getKey().longValue();
    }

    public List<PostAgent> getAllPosts() {
	String sql = "select * from post_agent order by id desc";
	SqlRowSet rows = jdbcTemplate.queryForRowSet(sql);

	List<PostAgent> list = new ArrayList<>();

	while (rows.next()) {
	    list.add(new PostAgent(rows.getLong("id"), rows.getString("name"), rows.getString("website")));

	}
	return list;
    }
}
