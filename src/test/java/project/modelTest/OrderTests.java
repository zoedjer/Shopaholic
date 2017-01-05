package project.modelTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import project.entity.OrderedItem;
import project.service.OrderBoxService;
import project.testConfig.TestingConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestingConfig.class)
public class OrderTests {
    
    private long[] ids = {1L, 2L, 3L};
    private String[] data = {"1111", "25.25"};
    
    @Autowired
    private OrderBoxService orderboxService;

    @Autowired
    private JdbcTemplate jdbc;

    private static final String CREATE_TABLES = "sql/test_create_tables.sql";
    private static final String DROP_TABLES = "sql/test_drop_tables.sql";
    private static final String ORDER_DATA = "sql/test_order_data.sql";

    @Before
    public void before() throws ScriptException, SQLException {
	ScriptUtils.executeSqlScript(jdbc.getDataSource().getConnection(), new ClassPathResource(CREATE_TABLES));
	ScriptUtils.executeSqlScript(jdbc.getDataSource().getConnection(), new ClassPathResource(ORDER_DATA));
    }

    @After
    public void after() throws ScriptException, SQLException {
	ScriptUtils.executeSqlScript(jdbc.getDataSource().getConnection(), new ClassPathResource(DROP_TABLES));
    }

    @Test
    public void testInsert() {
	assertEquals(0, JdbcTestUtils.countRowsInTable(jdbc, "orderbox"));
	List<OrderedItem> items = new ArrayList<>();
	for (long i=1L; i< 4L; i++) {
	    items.add(new OrderedItem(i, (int) i, "100.00", "80.00"));
	}
	orderboxService.createOrder(ids[0], ids[0], ids[0], data[0], data[1], items);
	assertEquals(1, JdbcTestUtils.countRowsInTable(jdbc, "orderbox"));
    }

    @Test
    public void testRemove() {
	assertEquals(0, JdbcTestUtils.countRowsInTable(jdbc, "orderbox"));
	List<OrderedItem> items = new ArrayList<>();
	for (long i=1L; i< 4L; i++) {
	    items.add(new OrderedItem(i, (int) i, "100.00", "80.00"));
	}
	long id = orderboxService.createOrder(ids[0], ids[0], ids[0], data[0], data[1], items);
	assertEquals(1, JdbcTestUtils.countRowsInTable(jdbc, "orderbox"));
	orderboxService.removeOrder(id);
	assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbc, "orderbox","status='inactive'"));
	assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbc, "orderbox","status='active'"));
    }

    @Test
    public void testGetItemsWithinOrder() {
	assertEquals(0, JdbcTestUtils.countRowsInTable(jdbc, "orderbox"));
	List<OrderedItem> items = new ArrayList<>();
	for (long i=1L; i< 4L; i++) {
	    items.add(new OrderedItem(i, (int) i, "100.00", "80.00"));
	}
	long id = orderboxService.createOrder(ids[0], ids[0], ids[0], data[0], data[1], items);
	assertEquals(1, JdbcTestUtils.countRowsInTable(jdbc, "orderbox"));
	assertEquals(items.size(), orderboxService.getOrderItems(id).size());
    }

    @Test
    public void testGetOrderDetail() {
	assertEquals(0, JdbcTestUtils.countRowsInTable(jdbc, "orderbox"));
	List<OrderedItem> items = new ArrayList<>();
	for (long i=1L; i< 4L; i++) {
	    items.add(new OrderedItem(i, (int) i, "100.00", "80.00"));
	}
	orderboxService.createOrder(ids[0], ids[0], ids[0], data[0], data[1], items);
	assertEquals(1, JdbcTestUtils.countRowsInTable(jdbc, "orderbox"));
	// user_id = ids[0]
	assertTrue(orderboxService.getOrderDetail(ids[0]).size() > 0);
    }

    @Test
    public void testProfitByDate() {
	assertEquals(0, JdbcTestUtils.countRowsInTable(jdbc, "orderbox"));
	List<OrderedItem> items = new ArrayList<>();
	for (long i=1L; i< 4L; i++) {
	    items.add(new OrderedItem(i, (int) i, "100.00", "80.00"));
	}
	orderboxService.createOrder(ids[0], ids[0], ids[0], data[0], data[1], items);
	assertEquals(1, JdbcTestUtils.countRowsInTable(jdbc, "orderbox")); 
	assertTrue(orderboxService.getProfitByDate(ids[0], null, null).size() > 0);
    }

}
