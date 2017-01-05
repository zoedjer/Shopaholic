package project.modelTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.apache.tomcat.util.codec.binary.Base64;
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

import project.dbaccess.UserTable;
import project.service.UserService;
import project.testConfig.TestingConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestingConfig.class)
public class UserTests {

    private String[] user = { "user@user.com", "user" };

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private UserTable userTable;

    private static final String CREATE_TABLES = "sql/test_create_tables.sql";
    private static final String DROP_TABLES = "sql/test_drop_tables.sql";

    @Before
    public void before() throws ScriptException, SQLException {
	ScriptUtils.executeSqlScript(jdbc.getDataSource().getConnection(), new ClassPathResource(CREATE_TABLES));
    }

    @After
    public void after() throws ScriptException, SQLException {
	ScriptUtils.executeSqlScript(jdbc.getDataSource().getConnection(), new ClassPathResource(DROP_TABLES));
    }

    @Test
    public void testInsert() {
	assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbc, "user", "email='" + user[0] + "'"));
	userTable.insert(user[0], user[0], user[1]);
	assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbc, "user", "email='" + user[0] + "'"));
    }

    @Test
    public void testGetId() {
	assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbc, "user", "email='" + user[0] + "'"));
	long expected_id = userTable.insert(user[0], user[0], user[1]);
	assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbc, "user", "email='" + user[0] + "'"));
	long actual_id = userTable.getUserId(user[0]);
	assertEquals(expected_id, actual_id);
    }

    @Test
    public void testCheckExistence() {
	assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbc, "user", "email='" + user[0] + "'"));
	userTable.insert(user[0], user[0], user[1]);
	assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbc, "user", "email='" + user[0] + "'"));
	assertTrue(userTable.isExisting(user[0]));
    }
    
    @Test
    public void testUserPasswordEncode() {
	String decoded_str = new String(Base64.decodeBase64(UserService.encode(user[1])));
	assertEquals(user[1], decoded_str);
    }

}
