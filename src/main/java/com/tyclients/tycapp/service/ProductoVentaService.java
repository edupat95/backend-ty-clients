package com.tyclients.tycapp.service;

import com.tyclients.tycapp.domain.ProductoVenta;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ProductoVenta}.
 */
public interface ProductoVentaService {
    /**
     * Save a productoVenta.
     *
     * @param productoVenta the entity to save.
     * @return the persisted entity.
     */
    ProductoVenta save(ProductoVenta productoVenta);

    /**
     * Updates a productoVenta.
     *
     * @param productoVenta the entity to update.
     * @return the persisted entity.
     */
    ProductoVenta update(ProductoVenta productoVenta);

    /**
     * Partially updates a productoVenta.
     *
     * @param productoVenta the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProductoVenta> partialUpdate(ProductoVenta productoVenta);

    /**
     * Get all the productoVentas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductoVenta> findAll(Pageable pageable);

    /**
     * Get the "id" productoVenta.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductoVenta> findOne(Long id);

    /**
     * Delete the "id" productoVenta.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    List<ProductoVenta> getProductosVentaByIdClub(Long idClub);

	List<ProductoVenta> getProductosVentaByIdVenta(Long idVenta);
}
