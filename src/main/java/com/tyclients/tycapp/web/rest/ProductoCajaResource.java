package com.tyclients.tycapp.web.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.tyclients.tycapp.domain.Caja;
import com.tyclients.tycapp.domain.ProductoCaja;
import com.tyclients.tycapp.domain.ProductoVenta;
import com.tyclients.tycapp.repository.ProductoCajaRepository;
import com.tyclients.tycapp.repository.CajaRepository;
import com.tyclients.tycapp.service.ProductoCajaService;
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
 * REST controller for managing {@link com.tyclients.tycapp.domain.ProductoCaja}.
 */
@RestController
@RequestMapping("/api")
public class ProductoCajaResource {

    private final Logger log = LoggerFactory.getLogger(ProductoCajaResource.class);

    private static final String ENTITY_NAME = "productoCaja";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductoCajaService productoCajaService;

    private final ProductoCajaRepository productoCajaRepository;
    
    private final CajaRepository cajaRepository;
    
    public ProductoCajaResource(CajaRepository cajaRepository, ProductoCajaService productoCajaService, ProductoCajaRepository productoCajaRepository) {
        this.productoCajaService = productoCajaService;
        this.productoCajaRepository = productoCajaRepository;
        this.cajaRepository = cajaRepository;
    }

    /**
     * {@code POST  /producto-cajas} : Create a new productoCaja.
     *
     * @param productoCaja the productoCaja to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productoCaja, or with status {@code 400 (Bad Request)} if the productoCaja has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/producto-cajas")
    public ResponseEntity<ProductoCaja> createProductoCaja(@Valid @RequestBody ProductoCaja productoCaja) throws URISyntaxException {
        log.debug("REST request to save ProductoCaja : {}", productoCaja);
        if (productoCaja.getId() != null) {
            throw new BadRequestAlertException("A new productoCaja cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductoCaja result = productoCajaService.save(productoCaja);
        return ResponseEntity
            .created(new URI("/api/producto-cajas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /producto-cajas/:id} : Updates an existing productoCaja.
     *
     * @param id the id of the productoCaja to save.
     * @param productoCaja the productoCaja to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productoCaja,
     * or with status {@code 400 (Bad Request)} if the productoCaja is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productoCaja couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/producto-cajas/{id}")
    public ResponseEntity<ProductoCaja> updateProductoCaja(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductoCaja productoCaja
    ) throws URISyntaxException {
        log.debug("REST request to update ProductoCaja : {}, {}", id, productoCaja);
        if (productoCaja.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productoCaja.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productoCajaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductoCaja result = productoCajaService.update(productoCaja);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productoCaja.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /producto-cajas/:id} : Partial updates given fields of an existing productoCaja, field will ignore if it is null
     *
     * @param id the id of the productoCaja to save.
     * @param productoCaja the productoCaja to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productoCaja,
     * or with status {@code 400 (Bad Request)} if the productoCaja is not valid,
     * or with status {@code 404 (Not Found)} if the productoCaja is not found,
     * or with status {@code 500 (Internal Server Error)} if the productoCaja couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/producto-cajas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProductoCaja> partialUpdateProductoCaja(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductoCaja productoCaja
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductoCaja partially : {}, {}", id, productoCaja);
        if (productoCaja.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productoCaja.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productoCajaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductoCaja> result = productoCajaService.partialUpdate(productoCaja);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productoCaja.getId().toString())
        );
    }

    /**
     * {@code GET  /producto-cajas} : get all the productoCajas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productoCajas in body.
     */
    @GetMapping("/producto-cajas")
    public ResponseEntity<List<ProductoCaja>> getAllProductoCajas(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ProductoCajas");
        Page<ProductoCaja> page = productoCajaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /producto-cajas/:id} : get the "id" productoCaja.
     *
     * @param id the id of the productoCaja to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productoCaja, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/producto-cajas/{id}")
    public ResponseEntity<ProductoCaja> getProductoCaja(@PathVariable Long id) {
        log.debug("REST request to get ProductoCaja : {}", id);
        Optional<ProductoCaja> productoCaja = productoCajaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productoCaja);
    }

    /**
     * {@code DELETE  /producto-cajas/:id} : delete the "id" productoCaja.
     *
     * @param id the id of the productoCaja to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/producto-cajas/{id}")
    public ResponseEntity<Void> deleteProductoCaja(@PathVariable Long id) {
        log.debug("REST request to delete ProductoCaja : {}", id);
        productoCajaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
    
    // CREAR PRODUCTO CAJA CON ID CAJA Y ID PRODUCTO
    @PostMapping("/producto-cajas/create")
    public ProductoCaja AdminCreateProductoCaja(@RequestBody JsonNode jsonNode) throws URISyntaxException {
        log.debug("REST request to save CachierCreateVenta : {}", jsonNode);
        ProductoCaja productosVenta = productoCajaService.adminCreateProductoCaja(jsonNode);
        return productosVenta;
    }
    //OBTENER LOS PRODUCTOS EN UNA CAJA CON ID CAJA
    @GetMapping("/producto-cajas/caja/{idCaja}")
    public List<ProductoCaja> getProductoCajaByIdCaja(@PathVariable Long idCaja) {
        log.debug("REST request to get ProductoCaja : {}", idCaja);
        Optional<Caja> caja = cajaRepository.findById(idCaja);
        List<ProductoCaja> productosCaja = productoCajaService.findByIdCaja(caja);
        return productosCaja;
    }
    
    //ELIMINAR UN PRODUCTO CAJA CON ID CAJA E ID 
    @PostMapping("/producto-cajas/quitar")
    public ResponseEntity<Void> deleteProductoCaja(@RequestBody JsonNode jsonNode) {
        log.debug("REST request to delete ProductoCaja with ID Caja And ID Prudcto: {}", jsonNode);
        
        Optional<ProductoCaja> productoCaja = productoCajaService.findByIdCajaAndIdProducto(jsonNode);
        
        System.out.println("El productoCaja eliminado es ----------->" + productoCaja.toString());
        
        productoCajaService.delete(productoCaja.get().getId());
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, productoCaja.toString()))
            .build();
    }
}
