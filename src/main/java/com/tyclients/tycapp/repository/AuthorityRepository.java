package com.tyclients.tycapp.repository;

import com.tyclients.tycapp.domain.Authority;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
	
	Optional<Authority> findByName(String name);
}
