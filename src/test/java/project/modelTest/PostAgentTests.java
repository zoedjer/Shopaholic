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

import project.dbaccess.PostAgentTable;
import project.service.PostAgentService;
import project.testConfig.TestingConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestingConfig.class)
public class PostAgentTests {

    private String[] sample = { "abc", "abc@abc.com" };

    @Autowired
    private PostAgentTable postAgentTable;
    
    @Autowired
    private PostAgentService postAgentService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String CREATE_TABLES = "sql/test_create_tables.sql";
    private static final String DROP_TABLES = "sql/test_drop_tables.sql";

    @Before
    public void before() throws ScriptException, SQLException {
	ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(),
		new ClassPathResource(CREATE_TABLES));
    }

    @After
    public void after() throws ScriptException, SQLException {
	ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(), new ClassPathResource(DROP_TABLES));
    }

    @Test
    public void testInsert() {
	assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "post_agent", "name='" + sample[0] + "'"));
	postAgentTable.insert(sample[0],sample[1]);
	assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "post_agent", "name='" + sample[0] + "'"));
    }

    @Test
    public void testEdit() {
	assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "post_agent", "name='" + sample[0] + "'"));
	long id = postAgentTable.insert(sample[0], sample[1]);
	assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "post_agent", "name='" + sample[0] + "'"));
	String tmpName = "aaa";
	postAgentTable.edit(id, tmpName, sample[1]);
	assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "post_agent", "name='"+tmpName+"'"));
    }

    @Test
    public void testRemove() {
	assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "post_agent", "name='" + sample[0] + "'"));
	long id = postAgentTable.insert(sample[0], sample[1]);
	assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "post_agent", "name='" + sample[0] + "'"));
	postAgentTable.remove(id);
	assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "post_agent", "name='" + sample[0] + "'"));
    }
    
    @Test
    public void testGetAll() {
	assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "post_agent", "name='" + sample[0] + "'"));
	postAgentTable.insert(sample[0], sample[1]);
	assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "post_agent", "name='" + sample[0] + "'"));
	assertEquals(1, postAgentTable.getAllPosts().size());
    }
    
    @Test
    public void testServiceAllName() {
	assertEquals(0, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "post_agent", "name='" + sample[0] + "'"));
	postAgentTable.insert(sample[0], sample[1]);
	assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "post_agent", "name='" + sample[0] + "'"));
	assertEquals(1, postAgentService.getAllPostNames().size());
    }
}
