package com.techprimers.demo.TechPrimersSecurity.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

// extends users to give us access to user info
public class CustomUserDetails extends Users implements UserDetails {

	// custom constructor for copying constructor from Users
	public CustomUserDetails(final Users users) {
		super(users);
	}

	// finish this last
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// this is assigning the roles for Authorization to certain pages
		// simple granted authority built into Spring security using role 
		// using stream api to obtain a role
		return getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole()))
				// Performs a mutable reduction operation on the elements of this stream using a
				// Collector. A Collector encapsulates the functions used as arguments to
				// collect(Supplier, BiConsumer, BiConsumer), allowing for reuse of collection
				// strategies and composition of collect operations such as multiple-level
				// grouping or partitioning.
				.collect(Collectors.toList()); // the roles are a list
	}

	@Override
	public String getPassword() {
		return super.getPassword(); // changed from null
	}

	@Override
	public String getUsername() {
		return super.getName();
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