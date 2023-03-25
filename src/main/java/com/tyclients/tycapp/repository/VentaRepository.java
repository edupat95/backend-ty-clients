package com.tyclients.tycapp.repository;

import com.tyclients.tycapp.domain.Cajero;
import com.tyclients.tycapp.domain.Venta;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Venta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

	Optional<List<Venta>> findByCreatedDateBetweenAndCajero(Instant fechaDesde, Instant fechaHasta, Cajero cajero);
	
}
