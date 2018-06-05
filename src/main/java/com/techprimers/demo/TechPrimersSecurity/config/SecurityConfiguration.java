package com.techprimers.demo.TechPrimersSecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.techprimers.demo.TechPrimersSecurity.dao.UsersRepository;
import com.techprimers.demo.TechPrimersSecurity.service.CustomUserDetailsService;

@EnableGlobalMethodSecurity(prePostEnabled = true) // this was added for the @PreAuthorize admin role annotation in the home controller
@EnableWebSecurity // enables Spring Security for us
@EnableJpaRepositories(basePackageClasses = UsersRepository.class) // will inject all of the classes for the jpa
																	// repository
@Configuration
// extending WebSecurityConf... to override the configure method
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	// started with UserDetailService that is an interface (needs to be implemented in child class)
	private CustomUserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// for use with the database 
		auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
	}



	// created security for http endpoint
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*
		 * Cross-Site Request Forgery (CSRF) is an attack that forces an end user to
		 * execute unwanted actions on a web application in which they're currently
		 * authenticated. CSRF attacks specifically target state-changing requests, not
		 * theft of data, since the attacker has no way to see the response to the
		 * forged request.
		 */
		http.csrf().disable();
		// authorizes all the secure resuests
		http.authorizeRequests().antMatchers("/secured/**").authenticated().anyRequest().permitAll().and()
		// using spring security default login page
		.formLogin()// here can use a custom login page by adding .loginPage("/loginpage")
				.permitAll();
	}

	// generic password encoder created here 
	// this is an interface which is why we are overriding in the method return
	// like creating an anonymous class 
	// BCrypt can replace this 
	private PasswordEncoder getPasswordEncoder() {
		return new PasswordEncoder() {
			@Override
			public String encode(CharSequence charSequence) {
				return charSequence.toString();
			}

			@Override
			public boolean matches(CharSequence charSequence, String s) {
				return true;
			}
		};
	}

	// may want to use this for encryption
	// @Bean
	// public BCryptPasswordEncoder passwordEncoder() {
	// BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	// return bCryptPasswordEncoder;
	// }
}
