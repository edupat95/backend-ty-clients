package com.tyclients.tycapp.service;

import com.tyclients.tycapp.domain.Asociado;
import com.tyclients.tycapp.domain.Club;
import com.tyclients.tycapp.domain.AsociadoClub;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link AsociadoClub}.
 */
public interface AsociadoClubService {
    /**
     * Save a asociadoClub.
     *
     * @param asociadoClub the entity to save.
     * @return the persisted entity.
     */
    AsociadoClub save(AsociadoClub asociadoClub);

    /**
     * Updates a asociadoClub.
     *
     * @param asociadoClub the entity to update.
     * @return the persisted entity.
     */
    AsociadoClub update(AsociadoClub asociadoClub);

    /**
     * Partially updates a asociadoClub.
     *
     * @param asociadoClub the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AsociadoClub> partialUpdate(AsociadoClub asociadoClub);

    /**
     * Get all the asociadoClubs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AsociadoClub> findAll(Pageable pageable);

    /**
     * Get the "id" asociadoClub.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AsociadoClub> findOne(Long id);

    /**
     * Delete the "id" asociadoClub.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    Optional<AsociadoClub> findAllByIdClubAndIdentificador(Optional<Club> club, UUID identificador);
	
	Optional<AsociadoClub> findByIdAsociado(Asociado idAsociado);

	Optional<AsociadoClub> findByIdentificadorAndClub(UUID identificador, Club club);

}
