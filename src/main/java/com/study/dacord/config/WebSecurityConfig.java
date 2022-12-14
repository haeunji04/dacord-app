package com.study.dacord.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/", "/css/**").permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
			.logout()
				.permitAll();
	}
	
	/**
	 * Authentication 로그인
	 * Authorization  권한
	 * 
	 * < 노트 >
	 * @OneToOne 
	 * ex) user - user_detail
	 * 
	 * @OneToMany
	 * ex) user - board
	 * 
	 * @ManyToOne
	 * ex) board - user
	 * 
	 * @ManyToMany
	 * ex) user - role
	 */
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) 
			throws Exception {
	    auth.jdbcAuthentication()
	      .dataSource(dataSource)
	      .passwordEncoder(passwordEncoder())
	      .usersByUsernameQuery("select username,password,enabled "
	        + "from user "
	        + "where username = ?")
	      .authoritiesByUsernameQuery("select username,authority "
	        + "from user_role ur inner join user u on ur.user_id = u.id "
	        + "inner join role r on ur.role_id = r.id "
	        + "where email = ?");
	}	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}

}