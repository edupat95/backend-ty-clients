package com.tyclients.tycapp.service;

import com.tyclients.tycapp.domain.Registrador;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Registrador}.
 */
public interface RegistradorService {
    /**
     * Save a registrador.
     *
     * @param registrador the entity to save.
     * @return the persisted entity.
     */
    Registrador save(Registrador registrador);

    /**
     * Updates a registrador.
     *
     * @param registrador the entity to update.
     * @return the persisted entity.
     */
    Registrador update(Registrador registrador);

    /**
     * Partially updates a registrador.
     *
     * @param registrador the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Registrador> partialUpdate(Registrador registrador);

    /**
     * Get all the registradors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Registrador> findAll(Pageable pageable);

    /**
     * Get the "id" registrador.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Registrador> findOne(Long id);

    /**
     * Delete the "id" registrador.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
	Optional<Registrador> findByTrabajador(Long trabajador_id);
}
