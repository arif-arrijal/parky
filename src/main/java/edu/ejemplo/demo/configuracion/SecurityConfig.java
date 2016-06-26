package edu.ejemplo.demo.configuracion;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
	  auth
	  	.userDetailsService(userDetailsService)
	  	.passwordEncoder(new PlaintextPasswordEncoder());
	}	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

	  http.authorizeRequests()
	  	.antMatchers("/login","**/login","/logout").permitAll() // #4
	  	//forget about this, it wasnt working properly, but could be fixed?
		.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/admin").access("hasRole('ROLE_ADMIN')")
	//	.antMatchers("/parking/**").access("hasRole('ROLE_PARKING','ADMIN')")
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
}
