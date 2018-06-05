package com.techprimers.demo.TechPrimersSecurity.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.techprimers.demo.TechPrimersSecurity.dao.UsersRepository;
import com.techprimers.demo.TechPrimersSecurity.model.CustomUserDetails;
import com.techprimers.demo.TechPrimersSecurity.model.Users;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UsersRepository usersRepository;

	@Override
	// need to authenticate and provide UserDetails (create another model to hold
	// this)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// A container object which may or may not contain a non-null value. If a value
		// is present, isPresent() will return true and get() will return the value.
		Optional<Users> optionalUsers = usersRepository.findByName(username);

		// lambda in the event the user doesn't exist
		// doing this instead of a null check
		optionalUsers.orElseThrow(() -> new UsernameNotFoundException("Username not found"));
		// this will happen if the user is present
		// If a value is present, apply the provided mapping function to it, and if the
		// result is non-null, return an Optional describing the result. Otherwise
		// return an empty Optional.
		// :: applies the function to the given argument and will create a new one and
		// get the value
		// Very simply put, when we are using a method reference – the target reference
		// is placed before the delimiter :: and the name of the method is provided
		// after it.
		return optionalUsers.map(CustomUserDetails::new).get();
	}
}
