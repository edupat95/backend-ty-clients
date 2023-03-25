package com.tyclients.tycapp.service;

import com.tyclients.tycapp.domain.Trabajador;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Trabajador}.
 */
public interface TrabajadorService {
    /**
     * Save a trabajador.
     *
     * @param trabajador the entity to save.
     * @return the persisted entity.
     */
    Trabajador save(Trabajador trabajador);

    /**
     * Updates a trabajador.
     *
     * @param trabajador the entity to update.
     * @return the persisted entity.
     */
    Trabajador update(Trabajador trabajador);

    /**
     * Partially updates a trabajador.
     *
     * @param trabajador the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Trabajador> partialUpdate(Trabajador trabajador);

    /**
     * Get all the trabajadors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Trabajador> findAll(Pageable pageable);

    /**
     * Get the "id" trabajador.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Trabajador> findOne(Long id);

    /**
     * Delete the "id" trabajador.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    Optional<Trabajador> findByUserId(Long user_id);
}
