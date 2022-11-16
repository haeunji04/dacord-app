package com.study.dacord.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class WebSecurityConfig {

	@Autowired
	private DataSource dataSource;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((requests) -> requests
				.antMatchers("/", "/home").permitAll()
				.anyRequest().authenticated()
			)
			.formLogin((form) -> form
				.loginPage("/login")
				.permitAll()
			)
			.logout((logout) -> logout.permitAll());

		return http.build();
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
	        + "from authorities "
	        + "where email = ?");
	}	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}

}