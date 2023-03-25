package com.tyclients.tycapp.repository;

import com.tyclients.tycapp.domain.Trabajador;
import com.tyclients.tycapp.domain.Caja;
import com.tyclients.tycapp.domain.Cajero;
import com.tyclients.tycapp.domain.Club;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Cajero entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CajeroRepository extends JpaRepository<Cajero, Long> {
	Optional<Cajero> findByTrabajador(Trabajador trabajador);

	List<Cajero> findByCaja(Caja caja);	
}
