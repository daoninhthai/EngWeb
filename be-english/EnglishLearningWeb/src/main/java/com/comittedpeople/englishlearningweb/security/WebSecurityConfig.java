package com.comittedpeople.englishlearningweb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.comittedpeople.englishlearningweb.jwt.JwtAuthenticationFilter;
import com.comittedpeople.englishlearningweb.services.UserDetailsCustomServiceImpl;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsCustomServiceImpl userDetailsCustomServiceImpl;
	
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter () {
		return new JwtAuthenticationFilter();
	}
	
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean () throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure (AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsCustomServiceImpl).passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.cors()
//		.and().authorizeRequests()
//		.antMatchers("/api/v1/auth/*").permitAll()
//		.anyRequest().authenticated();
		
		//Best config.
		http.cors()
		.and().csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.authorizeRequests().antMatchers("/api/v1/users/**").hasRole("USER")
		.and()
		.authorizeRequests().antMatchers("/api/v1/users/*/ban/**").hasRole("ADMIN")
		.and()
		.authorizeRequests().antMatchers("/api/v1/roomChat/**").hasRole("USER")
		.and()
		.authorizeRequests().antMatchers("/api/v1/admin/**").hasRole("ADMIN")
		.and()
		.authorizeRequests().antMatchers(HttpMethod.POST, "/api/v1/grammar").hasRole("ADMIN")
		.and()
		.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/v1/grammar/*").hasRole("ADMIN")
		.and()
		.authorizeRequests().antMatchers(HttpMethod.PATCH, "/api/v1/grammar/*").hasRole("ADMIN")
		.and()
		.authorizeRequests().antMatchers(HttpMethod.POST, "/api/v1/grammar/*/forms").hasRole("ADMIN")
		.and()
		.authorizeRequests().antMatchers(HttpMethod.POST, "/api/v1/grammarCategories").hasRole("ADMIN")
		.and()
		.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/v1/grammarCategories/*").hasRole("ADMIN")
		.and()
		.authorizeRequests().antMatchers(HttpMethod.PATCH, "/api/v1/grammarCategories/*").hasRole("ADMIN")
		.and()
		.authorizeRequests().antMatchers(HttpMethod.POST, "/api/v1/grammarCategories/*/grammar").hasRole("ADMIN")
		.and()
		.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/v1/grammarForms/*").hasRole("ADMIN")
		.and()
		.authorizeRequests().antMatchers(HttpMethod.PATCH, "/api/v1/grammarForms/*").hasRole("ADMIN")
		.and()
		.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/v1/grammarForms/*/examples").hasRole("ADMIN")
		.and()
		.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/v1/grammarForms/*/notes").hasRole("ADMIN")
		.and()
		.authorizeRequests().antMatchers(HttpMethod.GET, "/api/v1/grammar").permitAll()
		.and()
		.authorizeRequests().antMatchers(HttpMethod.POST, "/api/v1/vocabCategories").hasRole("ADMIN")
		.and()
		.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/v1/vocabCategories/*").hasRole("ADMIN")
		.and()
		.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/v1/vocabCategories/*").hasRole("ADMIN")
		.and()
		.authorizeRequests().antMatchers(HttpMethod.POST, "/api/v1/vocabCategories/*/lessons").hasRole("ADMIN")
		.and()
		.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/v1/vocabLessons/*").hasRole("ADMIN")
		.and()
		.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/v1/vocabLessons/*").hasRole("ADMIN")
		.and()
		.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/v1/vocabLessons/*/content").hasRole("ADMIN")
		.and()
		.authorizeRequests().antMatchers(HttpMethod.POST, "/api/v1/vocabLessons/*/content").hasRole("ADMIN")
		.and()
		.authorizeRequests().antMatchers("h2-console/**").permitAll()
		.anyRequest().permitAll();
		
		//Disable Spring Security for H2.
		http.headers().frameOptions().disable();

		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}
