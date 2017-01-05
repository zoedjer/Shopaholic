package project.testConfig;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import project.dbaccess.CustomerTable;
import project.dbaccess.ItemTable;
import project.dbaccess.OrderBoxTable;
import project.dbaccess.PostAgentTable;
import project.dbaccess.UserTable;
import project.service.CustomerService;
import project.service.ItemService;
import project.service.OrderBoxService;
import project.service.PostAgentService;
import project.service.UserService;

@Configuration
public class TestingConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
	return new EmbeddedDatabaseBuilder().generateUniqueName(true).setType(EmbeddedDatabaseType.H2)
		.setName("shopaholic").setScriptEncoding("UTF-8").build();
    }

    @Bean
    @Primary
    public JdbcTemplate JdbcTemplate() {
	return new JdbcTemplate(dataSource());
    }

    @Bean
    public PostAgentService postAgentService() {
	return new PostAgentService();
    }
    
    @Bean
    public PostAgentTable postAgentTable() {
	return new PostAgentTable();
    }
    
    @Bean
    public UserTable userTable() {
	return new UserTable();
    }
    
    @Bean
    public UserService userService() {
	return new UserService();
    }
    
    @Bean
    public ItemTable itemTable() {
	return new ItemTable();
    }
    
    @Bean
    public ItemService itemService() {
	return new ItemService();
    }
    
    @Bean
    public OrderBoxTable orderBoxTable() {
	return new OrderBoxTable();
    }
    
    @Bean
    public OrderBoxService orderBoxService() {
	return new OrderBoxService();
    }
    
    @Bean
    public CustomerTable customerTable() {
	return new CustomerTable();
    }
    
    @Bean
    public CustomerService customerService() {
	return new CustomerService();
    }
}
