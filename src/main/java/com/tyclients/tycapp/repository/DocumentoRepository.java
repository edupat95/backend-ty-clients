package com.tyclients.tycapp.repository;

import com.tyclients.tycapp.domain.Documento;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Documento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {

	Optional<Documento> findByNumeroDni(Long numeroDni);
	}
