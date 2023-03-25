package com.tyclients.tycapp.service;

import com.tyclients.tycapp.domain.TipoProducto;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link TipoProducto}.
 */
public interface TipoProductoService {
    /**
     * Save a tipoProducto.
     *
     * @param tipoProducto the entity to save.
     * @return the persisted entity.
     */
    TipoProducto save(TipoProducto tipoProducto);

    /**
     * Updates a tipoProducto.
     *
     * @param tipoProducto the entity to update.
     * @return the persisted entity.
     */
    TipoProducto update(TipoProducto tipoProducto);

    /**
     * Partially updates a tipoProducto.
     *
     * @param tipoProducto the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TipoProducto> partialUpdate(TipoProducto tipoProducto);

    /**
     * Get all the tipoProductos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TipoProducto> findAll(Pageable pageable);

    /**
     * Get the "id" tipoProducto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoProducto> findOne(Long id);

    /**
     * Delete the "id" tipoProducto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
