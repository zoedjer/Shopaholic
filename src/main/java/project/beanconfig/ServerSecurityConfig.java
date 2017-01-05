package project.beanconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class ServerSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DriverManagerDataSource dataSource;

    @Autowired
    private AuthenticationSuccessHandler successHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery(
			"select username, from_base64(password) as password,true from user where username=?")
		.authoritiesByUsernameQuery("select username, user_role from user where username=?");
    }

    /**
     * if any path is not working, try to see blocked!!!!!!!!
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
	http.authorizeRequests().antMatchers("/login", "/register", "/registration", "/verifyUser").permitAll()
		.anyRequest().authenticated().and().formLogin().loginPage("/login").loginProcessingUrl("/login")
		.successHandler(successHandler).permitAll().and().csrf().disable();
    }

}
