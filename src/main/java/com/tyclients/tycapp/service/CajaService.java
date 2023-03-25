package com.tyclients.tycapp.service;

import com.tyclients.tycapp.domain.Caja;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Caja}.
 */
public interface CajaService {
    /**
     * Save a caja.
     *
     * @param caja the entity to save.
     * @return the persisted entity.
     */
    Caja save(Caja caja);

    /**
     * Updates a caja.
     *
     * @param caja the entity to update.
     * @return the persisted entity.
     */
    Caja update(Caja caja);

    /**
     * Partially updates a caja.
     *
     * @param caja the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Caja> partialUpdate(Caja caja);

    /**
     * Get all the cajas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Caja> findAll(Pageable pageable);

    /**
     * Get the "id" caja.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Caja> findOne(Long id);

    /**
     * Delete the "id" caja.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    List<Caja> findByIdClub(Long idClub);
}
