package com.coworkingspace.backend.filter;

import com.coworkingspace.backend.common.utils.JwtUtil;
import com.coworkingspace.backend.service.CustomerUserDetailsService;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.coworkingspace.backend.common.constant.SecurityConstant.*;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private CustomerUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if (request.getServletPath().equals(LOGIN_PATH)) {
			log.info("Skipping JwtAuthenticationFilter for login endpoint");
			filterChain.doFilter(request, response);
			return;
		}

		try {
			log.info("Going through JwtAuthenticationFilter");
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = getAuthentication(request);
			if (usernamePasswordAuthenticationToken != null) {
				log.info("Adding user to SecurityContextHolder");
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
			filterChain.doFilter(request, response);
		} catch (AuthenticationException e) {
			log.error("Can't set user authentication -> Message: {}", e);
		}
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String authorizationHeader = request.getHeader(HEADER_NAME);
		String token = null;
		String username = null;

		try {
			if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
				token = authorizationHeader.substring(TOKEN_PREFIX.length());
				username = jwtUtil.getUsernameFromToken(token);
			}

			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
				Boolean hasValidToken = jwtUtil.validateToken(token, userDetails);
				if (Boolean.TRUE.equals(hasValidToken)) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
							new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken.setDetails(
							new WebAuthenticationDetailsSource().buildDetails(request)
					);
					return usernamePasswordAuthenticationToken;
				}
			}
		} catch (JwtException e) {
			log.error("Throwing JwtException");
			throw new AuthenticationCredentialsNotFoundException("Incorrect token", e);
		}
		return null;
	}
}