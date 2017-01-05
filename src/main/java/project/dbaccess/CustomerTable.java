package project.dbaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
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

import project.entity.Customer;

@Repository
public class CustomerTable {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void remove(long... ids) {
	String sql = "update customer set status=?,modify_date=? where id=?";

	int len = ids.length;

	jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

	    @Override
	    public void setValues(PreparedStatement ps, int i) throws SQLException {
		ps.setString(1, "inactive");
		ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
		ps.setLong(3, ids[i]);
	    }

	    @Override
	    public int getBatchSize() {
		return len;
	    }

	});
    }

    public void edit(Long id, String first_name, String last_name, String phone, String email, String line_one,
	    String line_two, String city, String county, String country) {
	String sql = "update customer set first_name=?,last_name=?,phone=?,email=?,line_one=?,line_two=?,"
		+ "city=?,county=?,country=?,modify_date=? where id=?";

	jdbcTemplate.update(sql, new PreparedStatementSetter() {

	    @Override
	    public void setValues(PreparedStatement ps) throws SQLException {
		ps.setString(1, first_name);
		ps.setString(2, last_name);
		ps.setString(3, phone);
		ps.setString(4, email);
		ps.setString(5, line_one);
		ps.setString(6, line_two);
		ps.setString(7, city);
		ps.setString(8, county);
		ps.setString(9, country);
		ps.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now()));
		ps.setLong(11, id);
	    }

	});
    }

    public List<Customer> getAllCustomer(Long userId, int from) {
	String  sql = "select c.* from user_customer uc "
		+ "join customer c on uc.customer_id=c.id "
		+ "where uc.user_id=" + userId
		+ " and c.status='active' order by c.id desc";
	
	if (from != -1) {
	    sql = "select c.* from user_customer uc "
			+ "join customer c on uc.customer_id=c.id "
			+ "where uc.user_id=" + userId
			+ " and c.status='active' order by c.id desc limit "
			+ from + ", 10 ";
	}

	SqlRowSet rows = jdbcTemplate.queryForRowSet(sql);

	List<Customer> list = new ArrayList<>(10);

	while (rows.next()) {
	    list.add(new Customer(rows.getLong("id"), rows.getString("first_name"), rows.getString("last_name"),
		    rows.getString("phone"), rows.getString("email"), rows.getString("line_one"),
		    rows.getString("line_two"), rows.getString("city"), rows.getString("county"),
		    rows.getString("country")));
	}

	return list;
    }

    // customer has a trigger for address insertion
    public long insert(Long userId, String first_name, String last_name, String phone, String email, String line_one,
	    String line_two, String city, String county, String country) {

	String sql = "insert into customer (first_name,last_name,phone,email,status,line_one,line_two,city,county,country,create_date) "
		+ "values (?,?,?,?,?,?,?,?,?,?,?)";

	GeneratedKeyHolder key = new GeneratedKeyHolder();

	jdbcTemplate.update(new PreparedStatementCreator() {

	    @Override
	    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
		PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, first_name);
		ps.setString(2, last_name);
		ps.setString(3, phone);
		ps.setString(4, email);
		ps.setString(5, "active");
		ps.setString(6, line_one);
		ps.setString(7, line_two);
		ps.setString(8, city);
		ps.setString(9, county);
		ps.setString(10, country);
		ps.setTimestamp(11, Timestamp.valueOf(LocalDateTime.now()));
		return ps;
	    }
	}, key);

	long curCustomerId = key.getKey().longValue();

	String sqluc = "insert into user_customer (user_id, customer_id) values (?,?) ";

	jdbcTemplate.update(sqluc, new PreparedStatementSetter() {

	    @Override
	    public void setValues(PreparedStatement ps) throws SQLException {
		ps.setLong(1, userId);
		ps.setLong(2, curCustomerId);
	    }
	});
	return curCustomerId;
    }

    public void addAddress(Long id, String lineOne, String lineTwo, String city, String county, String country) {
	String sql = "insert into address (customer_id,line_one,line_two,city,county,country,create_date) "
		+ "values (?,?,?,?,?,?,?)";

	jdbcTemplate.update(sql, new PreparedStatementSetter() {

	    @Override
	    public void setValues(PreparedStatement ps) throws SQLException {
		ps.setLong(1, id);
		ps.setString(2, lineOne);
		ps.setString(3, lineTwo);
		ps.setString(4, city);
		ps.setString(5, county);
		ps.setString(6, country);
		ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
	    }
	});
    }
}
