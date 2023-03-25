package com.tyclients.tycapp.repository;

import com.tyclients.tycapp.domain.Asociado;
import com.tyclients.tycapp.domain.Documento;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Asociado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AsociadoRepository extends JpaRepository<Asociado, Long> {

	Optional<Asociado> findByDocumento(Optional<Documento> documentoExist);}
