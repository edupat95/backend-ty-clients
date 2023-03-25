package com.tyclients.tycapp.repository;

import com.tyclients.tycapp.domain.Caja;
import com.tyclients.tycapp.domain.Club;
import com.tyclients.tycapp.domain.Producto;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Caja entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CajaRepository extends JpaRepository<Caja, Long> {
	
	List<Caja> findByClub(Club club);
}
