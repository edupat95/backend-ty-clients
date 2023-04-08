package com.tyclients.tycapp.service;

import com.tyclients.tycapp.domain.Plan;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Plan}.
 */
public interface PlanService {
    /**
     * Save a plan.
     *
     * @param plan the entity to save.
     * @return the persisted entity.
     */
    Plan save(Plan plan);

    /**
     * Updates a plan.
     *
     * @param plan the entity to update.
     * @return the persisted entity.
     */
    Plan update(Plan plan);

    /**
     * Partially updates a plan.
     *
     * @param plan the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Plan> partialUpdate(Plan plan);

    /**
     * Get all the plans.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Plan> findAll(Pageable pageable);

    /**
     * Get the "id" plan.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Plan> findOne(Long id);

    /**
     * Delete the "id" plan.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
