package com.tyclients.tycapp.service;

import com.tyclients.tycapp.domain.Entregador;
import com.tyclients.tycapp.domain.Registrador;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Entregador}.
 */
public interface EntregadorService {
    /**
     * Save a entregador.
     *
     * @param entregador the entity to save.
     * @return the persisted entity.
     */
    Entregador save(Entregador entregador);

    /**
     * Updates a entregador.
     *
     * @param entregador the entity to update.
     * @return the persisted entity.
     */
    Entregador update(Entregador entregador);

    /**
     * Partially updates a entregador.
     *
     * @param entregador the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Entregador> partialUpdate(Entregador entregador);

    /**
     * Get all the entregadors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Entregador> findAll(Pageable pageable);

    /**
     * Get the "id" entregador.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Entregador> findOne(Long id);

    /**
     * Delete the "id" entregador.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

	Optional<Entregador> findByTrabajador(Long trabajador_id);
}
