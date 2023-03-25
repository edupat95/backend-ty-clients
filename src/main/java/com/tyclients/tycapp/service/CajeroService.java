package com.tyclients.tycapp.service;

import com.tyclients.tycapp.domain.Trabajador;
import com.tyclients.tycapp.domain.Cajero;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Cajero}.
 */
public interface CajeroService {
    /**
     * Save a cajero.
     *
     * @param cajero the entity to save.
     * @return the persisted entity.
     */
    Cajero save(Cajero cajero);

    /**
     * Updates a cajero.
     *
     * @param cajero the entity to update.
     * @return the persisted entity.
     */
    Cajero update(Cajero cajero);

    /**
     * Partially updates a cajero.
     *
     * @param cajero the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Cajero> partialUpdate(Cajero cajero);

    /**
     * Get all the cajeros.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Cajero> findAll(Pageable pageable);

    /**
     * Get the "id" cajero.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Cajero> findOne(Long id);

    /**
     * Delete the "id" cajero.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    Optional<Cajero> findByIdTrabajador(Optional<Trabajador> idTrabajador);

    List<Cajero> findByIdClub(Long idClub);

	List<Cajero> findByIdCaja(Long idCaja);
}
