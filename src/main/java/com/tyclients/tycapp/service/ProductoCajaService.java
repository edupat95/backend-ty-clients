package com.tyclients.tycapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.tyclients.tycapp.domain.Caja;
import com.tyclients.tycapp.domain.Producto;
import com.tyclients.tycapp.domain.ProductoCaja;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ProductoCaja}.
 */
public interface ProductoCajaService {
    /**
     * Save a productoCaja.
     *
     * @param productoCaja the entity to save.
     * @return the persisted entity.
     */
    ProductoCaja save(ProductoCaja productoCaja);

    /**
     * Updates a productoCaja.
     *
     * @param productoCaja the entity to update.
     * @return the persisted entity.
     */
    ProductoCaja update(ProductoCaja productoCaja);

    /**
     * Partially updates a productoCaja.
     *
     * @param productoCaja the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProductoCaja> partialUpdate(ProductoCaja productoCaja);

    /**
     * Get all the productoCajas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductoCaja> findAll(Pageable pageable);

    /**
     * Get the "id" productoCaja.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductoCaja> findOne(Long id);

    /**
     * Delete the "id" productoCaja.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    List<ProductoCaja> findByIdCaja(Optional<Caja> caja);
    
    ProductoCaja adminCreateProductoCaja(JsonNode jsonNode);
    
    Optional<ProductoCaja> findByIdCajaAndIdProducto(JsonNode jsonNode);

	void deleteProductoCajaByProducto(Producto producto);
    
}
