package com.tyclients.tycapp.service;

import com.tyclients.tycapp.domain.Mesa;
import com.tyclients.tycapp.domain.Producto;
import com.tyclients.tycapp.domain.ProductoMesa;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ProductoMesa}.
 */
public interface ProductoMesaService {
    /**
     * Save a productoMesa.
     *
     * @param productoMesa the entity to save.
     * @return the persisted entity.
     */
    ProductoMesa save(ProductoMesa productoMesa);

    /**
     * Updates a productoMesa.
     *
     * @param productoMesa the entity to update.
     * @return the persisted entity.
     */
    ProductoMesa update(ProductoMesa productoMesa);

    /**
     * Partially updates a productoMesa.
     *
     * @param productoMesa the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProductoMesa> partialUpdate(ProductoMesa productoMesa);

    /**
     * Get all the productoMesas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductoMesa> findAll(Pageable pageable);

    /**
     * Get the "id" productoMesa.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductoMesa> findOne(Long id);

    /**
     * Delete the "id" productoMesa.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

	List<ProductoMesa> findByMesa(Mesa mesa);

	void deleteByMesa(Optional<Mesa> mesa);

	Optional<ProductoMesa> findByProductoAndMesa(Producto producto, Mesa mesa);

}
