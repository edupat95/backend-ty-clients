package com.tyclients.tycapp.web.rest;

import com.tyclients.tycapp.domain.ProductoDeposito;
import com.tyclients.tycapp.repository.ProductoDepositoRepository;
import com.tyclients.tycapp.service.ProductoDepositoService;
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
 * REST controller for managing {@link com.tyclients.tycapp.domain.ProductoDeposito}.
 */
@RestController
@RequestMapping("/api")
public class ProductoDepositoResource {

    private final Logger log = LoggerFactory.getLogger(ProductoDepositoResource.class);

    private static final String ENTITY_NAME = "productoDeposito";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductoDepositoService productoDepositoService;

    private final ProductoDepositoRepository productoDepositoRepository;

    public ProductoDepositoResource(
        ProductoDepositoService productoDepositoService,
        ProductoDepositoRepository productoDepositoRepository
    ) {
        this.productoDepositoService = productoDepositoService;
        this.productoDepositoRepository = productoDepositoRepository;
    }

    /**
     * {@code POST  /producto-depositos} : Create a new productoDeposito.
     *
     * @param productoDeposito the productoDeposito to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productoDeposito, or with status {@code 400 (Bad Request)} if the productoDeposito has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/producto-depositos")
    public ResponseEntity<ProductoDeposito> createProductoDeposito(@Valid @RequestBody ProductoDeposito productoDeposito)
        throws URISyntaxException {
        log.debug("REST request to save ProductoDeposito : {}", productoDeposito);
        if (productoDeposito.getId() != null) {
            throw new BadRequestAlertException("A new productoDeposito cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductoDeposito result = productoDepositoService.save(productoDeposito);
        return ResponseEntity
            .created(new URI("/api/producto-depositos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /producto-depositos/:id} : Updates an existing productoDeposito.
     *
     * @param id the id of the productoDeposito to save.
     * @param productoDeposito the productoDeposito to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productoDeposito,
     * or with status {@code 400 (Bad Request)} if the productoDeposito is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productoDeposito couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/producto-depositos/{id}")
    public ResponseEntity<ProductoDeposito> updateProductoDeposito(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductoDeposito productoDeposito
    ) throws URISyntaxException {
        log.debug("REST request to update ProductoDeposito : {}, {}", id, productoDeposito);
        if (productoDeposito.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productoDeposito.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productoDepositoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductoDeposito result = productoDepositoService.update(productoDeposito);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productoDeposito.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /producto-depositos/:id} : Partial updates given fields of an existing productoDeposito, field will ignore if it is null
     *
     * @param id the id of the productoDeposito to save.
     * @param productoDeposito the productoDeposito to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productoDeposito,
     * or with status {@code 400 (Bad Request)} if the productoDeposito is not valid,
     * or with status {@code 404 (Not Found)} if the productoDeposito is not found,
     * or with status {@code 500 (Internal Server Error)} if the productoDeposito couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/producto-depositos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProductoDeposito> partialUpdateProductoDeposito(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductoDeposito productoDeposito
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductoDeposito partially : {}, {}", id, productoDeposito);
        if (productoDeposito.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productoDeposito.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productoDepositoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductoDeposito> result = productoDepositoService.partialUpdate(productoDeposito);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productoDeposito.getId().toString())
        );
    }

    /**
     * {@code GET  /producto-depositos} : get all the productoDepositos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productoDepositos in body.
     */
    @GetMapping("/producto-depositos")
    public ResponseEntity<List<ProductoDeposito>> getAllProductoDepositos(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ProductoDepositos");
        Page<ProductoDeposito> page = productoDepositoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /producto-depositos/:id} : get the "id" productoDeposito.
     *
     * @param id the id of the productoDeposito to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productoDeposito, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/producto-depositos/{id}")
    public ResponseEntity<ProductoDeposito> getProductoDeposito(@PathVariable Long id) {
        log.debug("REST request to get ProductoDeposito : {}", id);
        Optional<ProductoDeposito> productoDeposito = productoDepositoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productoDeposito);
    }

    /**
     * {@code DELETE  /producto-depositos/:id} : delete the "id" productoDeposito.
     *
     * @param id the id of the productoDeposito to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/producto-depositos/{id}")
    public ResponseEntity<Void> deleteProductoDeposito(@PathVariable Long id) {
        log.debug("REST request to delete ProductoDeposito : {}", id);
        productoDepositoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
