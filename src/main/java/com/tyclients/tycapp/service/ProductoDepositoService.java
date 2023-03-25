package com.tyclients.tycapp.service;

import com.tyclients.tycapp.domain.ProductoDeposito;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ProductoDeposito}.
 */
public interface ProductoDepositoService {
    /**
     * Save a productoDeposito.
     *
     * @param productoDeposito the entity to save.
     * @return the persisted entity.
     */
    ProductoDeposito save(ProductoDeposito productoDeposito);

    /**
     * Updates a productoDeposito.
     *
     * @param productoDeposito the entity to update.
     * @return the persisted entity.
     */
    ProductoDeposito update(ProductoDeposito productoDeposito);

    /**
     * Partially updates a productoDeposito.
     *
     * @param productoDeposito the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProductoDeposito> partialUpdate(ProductoDeposito productoDeposito);

    /**
     * Get all the productoDepositos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductoDeposito> findAll(Pageable pageable);

    /**
     * Get the "id" productoDeposito.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductoDeposito> findOne(Long id);

    /**
     * Delete the "id" productoDeposito.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
