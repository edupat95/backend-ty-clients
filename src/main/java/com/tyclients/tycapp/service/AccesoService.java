package com.tyclients.tycapp.service;

import com.tyclients.tycapp.domain.Acceso;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Acceso}.
 */
public interface AccesoService {
    /**
     * Save a acceso.
     *
     * @param acceso the entity to save.
     * @return the persisted entity.
     */
    Acceso save(Acceso acceso);

    /**
     * Updates a acceso.
     *
     * @param acceso the entity to update.
     * @return the persisted entity.
     */
    Acceso update(Acceso acceso);

    /**
     * Partially updates a acceso.
     *
     * @param acceso the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Acceso> partialUpdate(Acceso acceso);

    /**
     * Get all the accesos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Acceso> findAll(Pageable pageable);

    /**
     * Get the "id" acceso.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Acceso> findOne(Long id);

    /**
     * Delete the "id" acceso.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
