package com.tyclients.tycapp.web.rest;

import com.tyclients.tycapp.domain.ProductoVenta;
import com.tyclients.tycapp.repository.ProductoVentaRepository;
import com.tyclients.tycapp.service.ProductoVentaService;
import com.tyclients.tycapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.tyclients.tycapp.domain.ProductoVenta}.
 */
@RestController
@RequestMapping("/api")
public class ProductoVentaResource {

    private final Logger log = LoggerFactory.getLogger(ProductoVentaResource.class);

    private static final String ENTITY_NAME = "productoVenta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductoVentaService productoVentaService;

    private final ProductoVentaRepository productoVentaRepository;

    public ProductoVentaResource(ProductoVentaService productoVentaService, ProductoVentaRepository productoVentaRepository) {
        this.productoVentaService = productoVentaService;
        this.productoVentaRepository = productoVentaRepository;
    }

    /**
     * {@code POST  /producto-ventas} : Create a new productoVenta.
     *
     * @param productoVenta the productoVenta to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productoVenta, or with status {@code 400 (Bad Request)} if the productoVenta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/producto-ventas")
    public ResponseEntity<ProductoVenta> createProductoVenta(@Valid @RequestBody ProductoVenta productoVenta) throws URISyntaxException {
        log.debug("REST request to save ProductoVenta : {}", productoVenta);
        if (productoVenta.getId() != null) {
            throw new BadRequestAlertException("A new productoVenta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductoVenta result = productoVentaService.save(productoVenta);
        return ResponseEntity
            .created(new URI("/api/producto-ventas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /producto-ventas/:id} : Updates an existing productoVenta.
     *
     * @param id the id of the productoVenta to save.
     * @param productoVenta the productoVenta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productoVenta,
     * or with status {@code 400 (Bad Request)} if the productoVenta is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productoVenta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/producto-ventas/{id}")
    public ResponseEntity<ProductoVenta> updateProductoVenta(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductoVenta productoVenta
    ) throws URISyntaxException {
        log.debug("REST request to update ProductoVenta : {}, {}", id, productoVenta);
        if (productoVenta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productoVenta.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productoVentaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductoVenta result = productoVentaService.update(productoVenta);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productoVenta.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /producto-ventas/:id} : Partial updates given fields of an existing productoVenta, field will ignore if it is null
     *
     * @param id the id of the productoVenta to save.
     * @param productoVenta the productoVenta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productoVenta,
     * or with status {@code 400 (Bad Request)} if the productoVenta is not valid,
     * or with status {@code 404 (Not Found)} if the productoVenta is not found,
     * or with status {@code 500 (Internal Server Error)} if the productoVenta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/producto-ventas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProductoVenta> partialUpdateProductoVenta(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductoVenta productoVenta
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductoVenta partially : {}, {}", id, productoVenta);
        if (productoVenta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productoVenta.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productoVentaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductoVenta> result = productoVentaService.partialUpdate(productoVenta);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productoVenta.getId().toString())
        );
    }

    /**
     * {@code GET  /producto-ventas} : get all the productoVentas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productoVentas in body.
     */
    @GetMapping("/producto-ventas")
    public ResponseEntity<List<ProductoVenta>> getAllProductoVentas(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ProductoVentas");
        Page<ProductoVenta> page = productoVentaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /producto-ventas/:id} : get the "id" productoVenta.
     *
     * @param id the id of the productoVenta to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productoVenta, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/producto-ventas/{id}")
    public ResponseEntity<ProductoVenta> getProductoVenta(@PathVariable Long id) {
        log.debug("REST request to get ProductoVenta : {}", id);
        Optional<ProductoVenta> productoVenta = productoVentaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productoVenta);
    }

    /**
     * {@code DELETE  /producto-ventas/:id} : delete the "id" productoVenta.
     *
     * @param id the id of the productoVenta to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/producto-ventas/{id}")
    public ResponseEntity<Void> deleteProductoVenta(@PathVariable Long id) {
        log.debug("REST request to delete ProductoVenta : {}", id);
        productoVentaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
    
    //GET todos los productos-ventas que pertenecen a un club especifico
    @GetMapping("/producto-ventas/club/{idClub}")
    public List<ProductoVenta> getProductoVentaByIdClub(@PathVariable Long idClub) {
        log.debug("REST request to get ProductoVenta : {}", idClub);
        List<ProductoVenta> productosVenta = productoVentaService.getProductosVentaByIdClub(idClub);
        return productosVenta;
    }
    
    //GET todos los productos-ventas que pertenecen a un club especifico
    @GetMapping("/producto-ventas/venta/{idVenta}")
    public List<ProductoVenta> getProductoVentaByIdVenta(@PathVariable Long idVenta) {
        log.debug("REST request to get ProductoVenta by venta id: {}", idVenta);
        List<ProductoVenta> productosVenta = productoVentaService.getProductosVentaByIdVenta(idVenta);
        return productosVenta;
    }
}
