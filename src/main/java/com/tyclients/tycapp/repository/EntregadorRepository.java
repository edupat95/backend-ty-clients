package com.tyclients.tycapp.repository;

import com.tyclients.tycapp.domain.Entregador;
import com.tyclients.tycapp.domain.Trabajador;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Entregador entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntregadorRepository extends JpaRepository<Entregador, Long> {

	Optional<Entregador> findByTrabajador(Trabajador trabajador);}
