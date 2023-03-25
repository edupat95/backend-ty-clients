package com.tyclients.tycapp.repository;

import com.tyclients.tycapp.domain.Trabajador;
import com.tyclients.tycapp.domain.Registrador;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Registrador entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegistradorRepository extends JpaRepository<Registrador, Long> {
	Optional<Registrador> findByTrabajador(Optional<Trabajador> idTrabajador);
}
