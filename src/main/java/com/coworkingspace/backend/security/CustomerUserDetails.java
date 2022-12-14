package com.coworkingspace.backend.security;

import com.coworkingspace.backend.dao.entity.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomerUserDetails implements UserDetails {
	private final String usename;
	private final String password;
	private final List<GrantedAuthority> authorities;
	private final Customer customer;

	public CustomerUserDetails(Customer customer) {
		this.usename = customer.getEmail();
		this.password = customer.getPassword();
		this.authorities = List.of(new SimpleGrantedAuthority(customer.getRole().getRoleName().toUpperCase()));
		this.customer = customer;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return usename;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
