package project.modelTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.Random;

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

import project.service.CustomerService;
import project.service.UserService;
import project.testConfig.TestingConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestingConfig.class)
public class CustomerTests {

    private long curUserId;

    private String[] detail = { "first", "last", "phone", "email", "lineOne", "lineTwo", "city", "county", "country" };

    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private UserService userService;

    @Autowired
    private JdbcTemplate jdbc;

    private static final String CREATE_TABLES = "sql/test_create_tables.sql";
    private static final String DROP_TABLES = "sql/test_drop_tables.sql";

    @Before
    public void before() throws ScriptException, SQLException {
	ScriptUtils.executeSqlScript(jdbc.getDataSource().getConnection(), new ClassPathResource(CREATE_TABLES));
	curUserId = userService.createUser("user@user.com", "user@user.com", "user@user.com");
    }

    @After
    public void after() throws ScriptException, SQLException {
	ScriptUtils.executeSqlScript(jdbc.getDataSource().getConnection(), new ClassPathResource(DROP_TABLES));
    }

    @Test
    public void testInsert() {
	assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbc, "customer", "first_name='" + detail[0] + "'"));
	customerService.createCustomer(curUserId, detail[0], detail[1], detail[2], detail[3], detail[4], detail[5],
		detail[6], detail[7], detail[8]);
	assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbc, "customer", "first_name='" + detail[0] + "'"));
    }

    @Test
    public void testEdit() {
	assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbc, "customer", "first_name='" + detail[0] + "'"));
	long id = customerService.createCustomer(curUserId, detail[0], detail[1], detail[2], detail[3], detail[4],
		detail[5], detail[6], detail[7], detail[8]);
	assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbc, "customer", "first_name='" + detail[0] + "'"));
	customerService.editCustomer(id, detail[1], detail[1], detail[2], detail[3], detail[4], detail[5], detail[6],
		detail[7], detail[8]);
	assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbc, "customer", "first_name='" + detail[1] + "'"));
    }

    @Test
    public void testRemove() {
	assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbc, "customer", "first_name='" + detail[0] + "'"));
	long id = customerService.createCustomer(curUserId, detail[0], detail[1], detail[2], detail[3], detail[4],
		detail[5], detail[6], detail[7], detail[8]);
	assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbc, "customer", "first_name='" + detail[0] + "' and status='active'"));
	customerService.removeCustomer(id);
	assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbc, "customer", "first_name='" + detail[0] + "' and status='active'"));
	assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbc, "customer", "first_name='" + detail[0] + "' and status='inactive'"));
    }

    @Test
    public void testGetAllWithAllCustomers() {
	int num = 15;
	assertEquals(0, JdbcTestUtils.countRowsInTable(jdbc, "customer"));
	for (int i = 0; i < num; i++) {
	    customerService.createCustomer(curUserId, detail[0] + i, detail[1] + i, detail[2] + i, detail[3] + i,
		    detail[4] + i, detail[5] + i, detail[6] + i, detail[7] + i, detail[8] + i);
	}
	assertEquals(num, JdbcTestUtils.countRowsInTable(jdbc, "customer"));
	assertEquals(num, customerService.getAll(curUserId, -1).size());
    }

    @Test
    public void testGetAllWithTenCustomerFrom() {
	int num = 15;
	assertEquals(0, JdbcTestUtils.countRowsInTable(jdbc, "customer"));
	for (int i = 0; i < num; i++) {
	    customerService.createCustomer(curUserId, detail[0] + i, detail[1] + i, detail[2] + i, detail[3] + i,
		    detail[4] + i, detail[5] + i, detail[6] + i, detail[7] + i, detail[8] + i);
	}
	assertEquals(num, JdbcTestUtils.countRowsInTable(jdbc, "customer"));
	Random random = new Random();
	assertTrue(customerService.getAll(curUserId, random.nextInt(14)).size() <= 10);
    }

    @Test
    public void testAddAddress() {
	String county = "kildare";
	long id = customerService.createCustomer(curUserId, detail[0], detail[1], detail[2], detail[3], detail[4],
		detail[5], detail[6], detail[7], detail[8]);
	assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbc, "address","county='"+county+"'"));
	customerService.addAddress(id, "lineOne", "lineTwo", "city", county, "country");
	assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbc, "address","county='"+county+"'"));
    }

}
