package com.tyclients.tycapp.repository;

import com.tyclients.tycapp.domain.Asociado;
import com.tyclients.tycapp.domain.Club;
import com.tyclients.tycapp.domain.AsociadoClub;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AsociadoClub entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AsociadoClubRepository extends JpaRepository<AsociadoClub, Long> {
	Optional<AsociadoClub> findAllByClubAndIdentificador(Optional<Club> club, UUID identificador);

	Optional<AsociadoClub> findByAsociado(Asociado idAsociado)
	
	;

	Optional<AsociadoClub> findByAsociadoAndClub(Asociado asociado, Club club);

	Optional<AsociadoClub> findByIdentificadorAndClub(UUID identificador, Club club);
}
