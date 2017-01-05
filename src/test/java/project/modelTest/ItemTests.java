package project.modelTest;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

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

import project.service.ItemService;
import project.testConfig.TestingConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestingConfig.class)
public class ItemTests {

    private String[] item = { "narya", "ring", "199.88", "elvish ring" };

    @Autowired
    private ItemService itemService;

    @Autowired
    private JdbcTemplate jdbc;

    private long curUserId = 2;

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
	assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbc, "item", "name='" + item[0] + "'"));
	itemService.createItem(curUserId, item[0], item[1], item[2], item[3]);
	assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbc, "item", "name='" + item[0] + "'"));
    }

    @Test
    public void testEdit() {
	assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbc, "item", "name='" + item[0] + "'"));
	long id = itemService.createItem(curUserId, item[0], item[1], item[2], item[3]);
	assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbc, "item", "name='" + item[0] + "'"));
	String name = "barahir";
	itemService.editItem(id, name, item[1], item[2], item[3]);
	assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbc, "item", "name='" + name + "'"));
    }

    @Test
    public void testRemove() {
	assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbc, "item", "name='" + item[0] + "'"));
	long id = itemService.createItem(curUserId, item[0], item[1], item[2], item[3]);
	assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbc, "item", "name='" + item[0] + "'"));
	itemService.removeItem(id);
	assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbc, "item", "name='" + item[0] + "'"));
    }

    @Test
    public void testGetAll() {
	assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbc, "item", "name='" + item[0] + "'"));
	itemService.createItem(curUserId, item[0], item[1], item[2], item[3]);
	assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbc, "item", "name='" + item[0] + "'"));
	assertEquals(1, itemService.getAll(curUserId).size());
    }

}
