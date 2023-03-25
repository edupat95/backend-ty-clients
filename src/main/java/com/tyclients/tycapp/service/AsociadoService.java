package com.tyclients.tycapp.service;

import com.tyclients.tycapp.domain.Asociado;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Asociado}.
 */
public interface AsociadoService {
    /**
     * Save a asociado.
     *
     * @param asociado the entity to save.
     * @return the persisted entity.
     */
    Asociado save(Asociado asociado);

    /**
     * Updates a asociado.
     *
     * @param asociado the entity to update.
     * @return the persisted entity.
     */
    Asociado update(Asociado asociado);

    /**
     * Partially updates a asociado.
     *
     * @param asociado the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Asociado> partialUpdate(Asociado asociado);

    /**
     * Get all the asociados.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Asociado> findAll(Pageable pageable);

    /**
     * Get the "id" asociado.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Asociado> findOne(Long id);

    /**
     * Delete the "id" asociado.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
