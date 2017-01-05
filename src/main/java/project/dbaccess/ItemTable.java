package project.dbaccess;

import java.math.BigDecimal;
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

import project.entity.Item;

@Repository
public class ItemTable {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void edit(Long id, String name, String category, String price, String brand) {
	String sql = "update item set name=?,category=?,price=?,brand=?,modify_date=? where id=?";

	jdbcTemplate.update(sql, new PreparedStatementSetter() {

	    @Override
	    public void setValues(PreparedStatement ps) throws SQLException {
		ps.setString(1, name);
		ps.setString(2, category);
		ps.setBigDecimal(3, new BigDecimal(price));
		ps.setString(4, brand);
		ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
		ps.setLong(6, id);
	    }

	});
    }

    public void remove(long... ids) {
	String sql = "delete from item where id=?";

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

    public long insert(Long userId, String name, String category, String price, String brand) {
	String sql = "insert into item (name,category,price,brand,create_date) " + "values (?,?,?,?,?)";

	GeneratedKeyHolder holder = new GeneratedKeyHolder();

	jdbcTemplate.update(new PreparedStatementCreator() {

	    @Override
	    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
		PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, name);
		ps.setString(2, category);
		ps.setBigDecimal(3, new BigDecimal(price));
		ps.setString(4, brand);
		ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
		return ps;
	    }
	}, holder);

	Long curItemId = holder.getKey().longValue();

	String sqlui = "insert into user_item (user_id, item_id) values (?,?)";

	jdbcTemplate.update(sqlui, new PreparedStatementSetter() {

	    @Override
	    public void setValues(PreparedStatement ps) throws SQLException {
		ps.setLong(1, userId);
		ps.setLong(2, curItemId);
	    }
	});
	
	return curItemId;
    }

    public List<Item> getAll(Long userId) {
	String sql = "select it.* from user_item ui " + "join item it on ui.item_id=it.id " + "where ui.user_id="
		+ userId + " order by it.id desc";

	List<Item> list = new ArrayList<>();

	SqlRowSet rows = jdbcTemplate.queryForRowSet(sql);

	while (rows.next()) {
	    list.add(new Item(rows.getLong("id"), rows.getString("name"), rows.getString("category"),
		    rows.getString("price"), rows.getString("brand")));
	}

	return list;
    }

}
