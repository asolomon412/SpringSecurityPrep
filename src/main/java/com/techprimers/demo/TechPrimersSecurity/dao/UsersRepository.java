package com.techprimers.demo.TechPrimersSecurity.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techprimers.demo.TechPrimersSecurity.model.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {
	// made this optional in case a user does not exist
	Optional<Users> findByName(String username);

}
