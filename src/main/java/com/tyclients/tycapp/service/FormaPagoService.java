package com.tyclients.tycapp.service;

import com.tyclients.tycapp.domain.Club;
import com.tyclients.tycapp.domain.FormaPago;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link FormaPago}.
 */
public interface FormaPagoService {
    /**
     * Save a formaPago.
     *
     * @param formaPago the entity to save.
     * @return the persisted entity.
     */
    FormaPago save(FormaPago formaPago);

    /**
     * Updates a formaPago.
     *
     * @param formaPago the entity to update.
     * @return the persisted entity.
     */
    FormaPago update(FormaPago formaPago);

    /**
     * Partially updates a formaPago.
     *
     * @param formaPago the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FormaPago> partialUpdate(FormaPago formaPago);

    /**
     * Get all the formaPagos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FormaPago> findAll(Pageable pageable);

    /**
     * Get the "id" formaPago.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FormaPago> findOne(Long id);

    /**
     * Delete the "id" formaPago.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

	List<FormaPago> findByClub(Club club);
}
