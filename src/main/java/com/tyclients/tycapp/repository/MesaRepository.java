package com.tyclients.tycapp.repository;

import com.tyclients.tycapp.domain.Club;
import com.tyclients.tycapp.domain.Mesa;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Mesa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {

	List<Mesa> findByClub(Club club);
	
}
