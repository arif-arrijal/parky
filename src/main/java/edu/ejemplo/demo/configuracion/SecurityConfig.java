package edu.ejemplo.demo.configuracion;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		
	  auth.jdbcAuthentication().dataSource(dataSource)
	  
		.usersByUsernameQuery(
			"select email,password , 1 from user where email=?")
		.authoritiesByUsernameQuery(
			"select email, role from user u where u.email=?")
			;
	}	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

	  http.authorizeRequests()
	  	.antMatchers("/login","**/login","/logout").permitAll() // #4
	  	
		.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/admin").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/parking/**").access("hasRole('ROLE_PARKING_OWNER','ADMIN')")
		.antMatchers("/guestor/**").access("hasRole('ROLE_GUESTOR','ADMIN')")
		.and()
		  .formLogin().loginPage("/login").failureUrl("/login?loginerror=true")
		  .usernameParameter("email").passwordParameter("password")
		.and()
		  .logout()
		  .logoutUrl("/logout")
		  .logoutSuccessUrl("/login?logout=true")
		  
		.and()
		  .exceptionHandling().accessDeniedPage("/403")
		.and()
		 .csrf().disable();	  
	}
	
	@Bean(name = "dataSource")
	public DriverManagerDataSource dataSource() {
	    DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
	    driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
	    driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/demo");
	    driverManagerDataSource.setUsername("meetrack_user");
	    driverManagerDataSource.setPassword("123123");
	    return driverManagerDataSource;
	}

    // ...
}
