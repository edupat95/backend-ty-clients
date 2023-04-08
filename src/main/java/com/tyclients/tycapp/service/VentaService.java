package com.tyclients.tycapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.tyclients.tycapp.domain.Cajero;
import com.tyclients.tycapp.domain.ProductoVenta;
import com.tyclients.tycapp.domain.Venta;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Venta}.
 */
public interface VentaService {
    /**
     * Save a venta.
     *
     * @param venta the entity to save.
     * @return the persisted entity.
     */
    Venta save(Venta venta);

    /**
     * Updates a venta.
     *
     * @param venta the entity to update.
     * @return the persisted entity.
     */
    Venta update(Venta venta);

    /**
     * Partially updates a venta.
     *
     * @param venta the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Venta> partialUpdate(Venta venta);

    /**
     * Get all the ventas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Venta> findAll(Pageable pageable);

    /**
     * Get the "id" venta.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Venta> findOne(Long id);

    /**
     * Delete the "id" venta.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    List<Venta> findByClub(Long idClub);

	List<ProductoVenta> cashierCreateVenta(JsonNode jsonNode);

	List<ProductoVenta> cashierCreateCanje(JsonNode jsonNode);

	List<ProductoVenta> cashierCreateVentaSinIdentificar(JsonNode jsonNode);

	List<Venta> findByFechaBetweenAndCajeros(Instant fechaDesde, Instant fechaHasta, List<Cajero> cajerosDelClub);

	Optional<Venta> findByIdentificador(Long idClub, Long idEntregador, UUID identificador_ticket);

}
