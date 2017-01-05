package project.beanconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@Component
public class BeanConfig {

    @Value("${spring.datasource.driver-class-name}")
    private String driver;

    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.url}")
    private String url;

    // @Bean
    // public DriverManagerDataSource dataSource() {
    // DriverManagerDataSource dmd = new DriverManagerDataSource();
    // dmd.setDriverClassName(driver);
    // dmd.setUsername(user);
    // dmd.setPassword(password);
    // dmd.setUrl(url);
    // return dmd;
    // }

    @Bean
    public DriverManagerDataSource dataSource() {
	String dbName = System.getProperty("RDS_DB_NAME");
	String userName = System.getProperty("RDS_USERNAME");
	String password = System.getProperty("RDS_PASSWORD");
	String hostname = System.getProperty("RDS_HOSTNAME");
	String port = System.getProperty("RDS_PORT");
	String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password="
		+ password;

	DriverManagerDataSource dmd = new DriverManagerDataSource();
	dmd.setDriverClassName(driver);
	dmd.setUsername(userName);
	dmd.setPassword(password);
	dmd.setUrl(jdbcUrl);
	dmd.setSchema(dbName);
	return dmd;
    }

    @Bean
    public Gson gson() {
	return new Gson();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
	return new MyLoginSuccessHandler();
    }

}
