package com.tyclients.tycapp.service;

import com.tyclients.tycapp.domain.Club;
import com.tyclients.tycapp.domain.Mesa;
import com.tyclients.tycapp.domain.ProductoMesa;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Mesa}.
 */
public interface MesaService {
    /**
     * Save a mesa.
     *
     * @param mesa the entity to save.
     * @return the persisted entity.
     */
    Mesa save(Mesa mesa);

    /**
     * Updates a mesa.
     *
     * @param mesa the entity to update.
     * @return the persisted entity.
     */
    Mesa update(Mesa mesa);

    /**
     * Partially updates a mesa.
     *
     * @param mesa the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Mesa> partialUpdate(Mesa mesa);

    /**
     * Get all the mesas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Mesa> findAll(Pageable pageable);

    /**
     * Get the "id" mesa.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Mesa> findOne(Long id);

    /**
     * Delete the "id" mesa.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    void createMesas (Club club, Long cantidad);

	List<Mesa> findByClub(Club club);

}
