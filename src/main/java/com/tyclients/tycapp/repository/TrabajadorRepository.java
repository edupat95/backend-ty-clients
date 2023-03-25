package com.tyclients.tycapp.repository;

import com.tyclients.tycapp.domain.Club;
import com.tyclients.tycapp.domain.Trabajador;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Trabajador entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrabajadorRepository extends JpaRepository<Trabajador, Long> {
	Optional<Trabajador> findByUserId(Long user_id);

	Optional<List<Trabajador>> findByClub(Optional<Club> club);
}
