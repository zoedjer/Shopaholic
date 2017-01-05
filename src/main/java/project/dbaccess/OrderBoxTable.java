package project.dbaccess;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import project.entity.OrderedItem;
import project.entity.ProfitNode;

@Repository
public class OrderBoxTable {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<OrderedItem> getOrderItems(long id) {
	List<OrderedItem> items = new ArrayList<>();

	String sql = "select oi.*,it.name,it.category,it.price,it.brand from order_item oi "
		+ "join item it on oi.item_id=it.id where oi.order_id=" + id;

	SqlRowSet rows = jdbcTemplate.queryForRowSet(sql);

	while (rows.next()) {
	    items.add(new OrderedItem(rows.getLong("item_id"), rows.getString("name"), rows.getInt("quantity"),
		    rows.getString("price"), rows.getString("price_sold")));
	}

	return items;
    }

    public List<ProfitNode> getProfitByDate(Long id, String start, String end) {

	String sql = "select fff.create_date,sum(fff.profit) as profit "
		+ "from (select uo.order_id,date(ob.create_date) as create_date,(ff.total-ob.shipping_fee) "
		+ "as profit from user_order uo join orderbox ob join customer c join post_agent pa "
		+ "join (select f.order_id,sum(f.total) as total "
		+ "from (select oi.order_id, (oi.quantity*(it.price-oi.price_sold)) as total from order_item oi "
		+ "join item it on oi.item_id=it.id) as f group by f.order_id) as ff on uo.order_id=ob.id "
		+ "and ob.customer_id=c.id and ob.post_id=pa.id and uo.order_id=ff.order_id " + "where uo.user_id=" + id
		+ " and ob.status='active') as fff " + "where fff.create_date >= ' " + start + " ' "
		+ " and fff.create_date <= ' " + end + " ' " + " group by fff.create_date asc";

	SqlRowSet rows = jdbcTemplate.queryForRowSet(sql);

	List<ProfitNode> nodes = new ArrayList<>();

	while (rows.next()) {
	    nodes.add(new ProfitNode(rows.getString("create_date"), rows.getString("profit")));
	}

	return nodes;
    }

    public void removeOrder(long... ids) {
	String sql = "update orderbox set status='inactive' where id=?";

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

    public List<Map<String, String>> getOrderDetail(Long id) {
	String sql = "select uo.order_id,ob.tracking_number,ob.shipping_fee,ob.create_date,concat(c.first_name,' ',c.last_name) as customerName, pa.name,(ff.total-ob.shipping_fee) as profit "
		+ "from user_order uo " + "join orderbox ob " + "join customer c " + "join post_agent pa "
		+ "join (select f.order_id,sum(f.total) as total from (select oi.order_id, (oi.quantity*(it.price-oi.price_sold)) as total from order_item oi join item it on oi.item_id=it.id) as f group by f.order_id) as ff"
		+ " on uo.order_id=ob.id " + "and ob.customer_id=c.id " + "and ob.post_id=pa.id "
		+ "and uo.order_id=ff.order_id " + "where uo.user_id=" + id + " and ob.status='active' ";

	SqlRowSet rows = jdbcTemplate.queryForRowSet(sql);

	List<Map<String, String>> list = new ArrayList<>();

	Map<String, String> map;
	while (rows.next()) {
	    map = new HashMap<>();

	    map.put("order_id", rows.getString("order_id"));
	    map.put("customerName", rows.getString("customerName"));
	    map.put("tracking_number", rows.getString("tracking_number"));
	    map.put("shipping_fee", rows.getString("shipping_fee"));
	    map.put("postName", rows.getString("name"));
	    map.put("create_date", rows.getString("create_date"));
	    map.put("profit", rows.getString("profit"));

	    list.add(map);
	}
	return list;
    }

    public long newOrder(Long userId, Long customerId, Long postId, String tracking_number, String shipping_fee,
	    List<OrderedItem> items) {
	String sql_orderbox = "insert into orderbox (customer_id, post_id,tracking_number,status,shipping_fee,create_date) "
		+ "values (?,?,?,?,?,?)";

	GeneratedKeyHolder gkey = new GeneratedKeyHolder();

	jdbcTemplate.update(new PreparedStatementCreator() {

	    @Override
	    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
		PreparedStatement ps = con.prepareStatement(sql_orderbox, Statement.RETURN_GENERATED_KEYS);

		ps.setLong(1, customerId);
		ps.setLong(2, postId);
		ps.setString(3, tracking_number);
		ps.setString(4, "active");
		ps.setBigDecimal(5, new BigDecimal(shipping_fee));
		ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
		return ps;
	    }
	}, gkey);

	long curOrderId = gkey.getKey().longValue();

	int itemSize = items.size();

	String sql_order_item = "insert into order_item (order_id,item_id,price_sold,quantity) " + "values (?,?,?,?)";

	jdbcTemplate.batchUpdate(sql_order_item, new BatchPreparedStatementSetter() {

	    @Override
	    public void setValues(PreparedStatement ps, int i) throws SQLException {
		ps.setLong(1, curOrderId);
		ps.setLong(2, items.get(i).getId());
		ps.setBigDecimal(3, new BigDecimal(items.get(i).getPrice_sold()));
		ps.setInt(4, items.get(i).getQuantity());
	    }

	    @Override
	    public int getBatchSize() {
		return itemSize;
	    }
	});

	String sql_user_order = "insert into user_order (user_id,order_id) values (?,?)";

	jdbcTemplate.update(new PreparedStatementCreator() {

	    @Override
	    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
		PreparedStatement ps = con.prepareStatement(sql_user_order);

		ps.setLong(1, userId);
		ps.setLong(2, curOrderId);

		return ps;
	    }
	});
	
	return curOrderId;
    }

}
