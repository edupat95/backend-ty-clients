package com.tyclients.tycapp.service;

import com.tyclients.tycapp.domain.PlanContratado;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link PlanContratado}.
 */
public interface PlanContratadoService {
    /**
     * Save a planContratado.
     *
     * @param planContratado the entity to save.
     * @return the persisted entity.
     */
    PlanContratado save(PlanContratado planContratado);

    /**
     * Updates a planContratado.
     *
     * @param planContratado the entity to update.
     * @return the persisted entity.
     */
    PlanContratado update(PlanContratado planContratado);

    /**
     * Partially updates a planContratado.
     *
     * @param planContratado the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PlanContratado> partialUpdate(PlanContratado planContratado);

    /**
     * Get all the planContratados.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PlanContratado> findAll(Pageable pageable);

    /**
     * Get the "id" planContratado.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PlanContratado> findOne(Long id);

    /**
     * Delete the "id" planContratado.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
