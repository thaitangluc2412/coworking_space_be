package com.coworkingspace.backend.configuration;

import com.coworkingspace.backend.filter.JwtAuthenticationFilter;
import com.coworkingspace.backend.security.CustomAuthenticationEntryPoint;
import com.coworkingspace.backend.service.CustomerUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static com.coworkingspace.backend.common.constant.SecurityConstant.PUBLIC_MATCHERS;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final CustomerUserDetailsService customerUserDetailsService;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(customerUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and()
				.csrf().disable()
				.authorizeRequests()
				.antMatchers(PUBLIC_MATCHERS).permitAll()
				.anyRequest().permitAll()
				.and()
				.exceptionHandling()
				.authenticationEntryPoint(customAuthenticationEntryPoint)
				.and()
				.sessionManagement()
				.sessionCreationPolicy(STATELESS);

		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
		corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
		corsConfiguration.addAllowedMethod(HttpMethod.PATCH);
		corsConfiguration.addAllowedMethod(HttpMethod.PUT);
		source.registerCorsConfiguration("/**", corsConfiguration);

		return source;
	}
}
