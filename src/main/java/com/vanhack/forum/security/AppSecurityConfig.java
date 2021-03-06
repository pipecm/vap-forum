package com.vanhack.forum.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private DataSource dataSource;
	
	@Value("select email, password, id from vap_forum_user where email = ?")
	private String userQuery;
	
	@Value("select email, 'ADMIN' from vap_forum_user where email = ?")
	private String roleQuery;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.
			jdbcAuthentication()
				.usersByUsernameQuery(userQuery)
				.authoritiesByUsernameQuery(roleQuery)
				.dataSource(dataSource)
				.passwordEncoder(passwordEncoder);
	}
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http
    		.formLogin().loginPage("/login")
    		.failureUrl("/login?error=true")
			.defaultSuccessUrl("/home")
//			.usernameParameter("email")
//			.passwordParameter("password")
    		.and().authorizeRequests()
    		.antMatchers("/login").permitAll()
    		.antMatchers("/signup").permitAll()
    		.antMatchers("/api/**").authenticated()
    		.antMatchers("/admin/**").hasAuthority("ADMIN")
    		.anyRequest().fullyAuthenticated()
    		.and().httpBasic()
    		.and().csrf().disable()
    		.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/").and().exceptionHandling()
			.accessDeniedPage("/access-denied");


    }
    
    @Override
	public void configure(WebSecurity web) throws Exception {
	    web
	       .ignoring()
	       .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}
}