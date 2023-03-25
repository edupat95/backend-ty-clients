package com.tyclients.tycapp.service;

import com.tyclients.tycapp.domain.Deposito;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Deposito}.
 */
public interface DepositoService {
    /**
     * Save a deposito.
     *
     * @param deposito the entity to save.
     * @return the persisted entity.
     */
    Deposito save(Deposito deposito);

    /**
     * Updates a deposito.
     *
     * @param deposito the entity to update.
     * @return the persisted entity.
     */
    Deposito update(Deposito deposito);

    /**
     * Partially updates a deposito.
     *
     * @param deposito the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Deposito> partialUpdate(Deposito deposito);

    /**
     * Get all the depositos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Deposito> findAll(Pageable pageable);

    /**
     * Get the "id" deposito.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Deposito> findOne(Long id);

    /**
     * Delete the "id" deposito.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
