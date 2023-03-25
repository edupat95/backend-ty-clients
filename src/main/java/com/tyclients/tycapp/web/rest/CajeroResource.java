package com.tyclients.tycapp.web.rest;

import com.tyclients.tycapp.service.TrabajadorService;
import com.tyclients.tycapp.domain.Trabajador;
import com.tyclients.tycapp.domain.Venta;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tyclients.tycapp.domain.Caja;
import com.tyclients.tycapp.domain.Cajero;
import com.tyclients.tycapp.domain.ProductoVenta;
import com.tyclients.tycapp.repository.CajeroRepository;
import com.tyclients.tycapp.service.CajaService;
import com.tyclients.tycapp.service.CajeroService;
import com.tyclients.tycapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

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
 * REST controller for managing {@link com.tyclients.tycapp.domain.Cajero}.
 */
@RestController
@RequestMapping("/api")
public class CajeroResource {

    private final Logger log = LoggerFactory.getLogger(CajeroResource.class);

    private static final String ENTITY_NAME = "cajero";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CajeroService cajeroService;

    private final CajeroRepository cajeroRepository;

    private final TrabajadorService trabajadorService;
    
    private final CajaService cajaService;

    public CajeroResource(CajaService cajaService, CajeroService cajeroService, CajeroRepository cajeroRepository, TrabajadorService trabajadorService) {
        this.cajeroService = cajeroService;
        this.cajeroRepository = cajeroRepository;
        this.trabajadorService = trabajadorService;
        this.cajaService = cajaService;
    }

    /**
     * {@code POST  /cajeros} : Create a new cajero.
     *
     * @param cajero the cajero to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cajero, or with status {@code 400 (Bad Request)} if the cajero has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cajeros")
    public ResponseEntity<Cajero> createCajero(@Valid @RequestBody Cajero cajero) throws URISyntaxException {
        log.debug("REST request to save Cajero : {}", cajero);
        if (cajero.getId() != null) {
            throw new BadRequestAlertException("A new cajero cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Cajero result = cajeroService.save(cajero);
        return ResponseEntity
            .created(new URI("/api/cajeros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cajeros/:id} : Updates an existing cajero.
     *
     * @param id the id of the cajero to save.
     * @param cajero the cajero to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cajero,
     * or with status {@code 400 (Bad Request)} if the cajero is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cajero couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cajeros/{id}")
    public ResponseEntity<Cajero> updateCajero(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Cajero cajero
    ) throws URISyntaxException {
        log.debug("REST request to update Cajero : {}, {}", id, cajero);
        if (cajero.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cajero.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cajeroRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Cajero result = cajeroService.update(cajero);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cajero.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cajeros/:id} : Partial updates given fields of an existing cajero, field will ignore if it is null
     *
     * @param id the id of the cajero to save.
     * @param cajero the cajero to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cajero,
     * or with status {@code 400 (Bad Request)} if the cajero is not valid,
     * or with status {@code 404 (Not Found)} if the cajero is not found,
     * or with status {@code 500 (Internal Server Error)} if the cajero couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cajeros/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Cajero> partialUpdateCajero(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Cajero cajero
    ) throws URISyntaxException {
        log.debug("REST request to partial update Cajero partially : {}, {}", id, cajero);
        if (cajero.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cajero.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cajeroRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Cajero> result = cajeroService.partialUpdate(cajero);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cajero.getId().toString())
        );
    }

    /**
     * {@code GET  /cajeros} : get all the cajeros.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cajeros in body.
     */
    @GetMapping("/cajeros")
    public ResponseEntity<List<Cajero>> getAllCajeros(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Cajeros");
        Page<Cajero> page = cajeroService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cajeros/:id} : get the "id" cajero.
     *
     * @param id the id of the cajero to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cajero, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cajeros/{id}")
    public ResponseEntity<Cajero> getCajero(@PathVariable Long id) {
        log.debug("REST request to get Cajero : {}", id);
        Optional<Cajero> cajero = cajeroService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cajero);
    }

    /**
     * {@code DELETE  /cajeros/:id} : delete the "id" cajero.
     *
     * @param id the id of the cajero to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cajeros/{id}")
    public ResponseEntity<Void> deleteCajero(@PathVariable Long id) {
        log.debug("REST request to delete Cajero : {}", id);
        cajeroService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
    
    //ENDPOINTS AGREGADO
    //BUSCAR CAJERO POR ID DE TRABAJADOR
    @GetMapping("/cajeros/trabajador/{trabajador_id}")
    public ResponseEntity<Cajero> getCajeroByIdTrabajador(@PathVariable Long trabajador_id) {
        log.debug("REST request to get Trabajador : {}", trabajador_id);
        Optional<Trabajador> idTrabajador = trabajadorService.findOne(trabajador_id);
        Optional<Cajero> cajero = cajeroService.findByIdTrabajador(idTrabajador);
        return ResponseUtil.wrapOrNotFound(cajero);
    }
    
    //BUSCAR CAJERO POR ID DE CAJA.
    @GetMapping("/cajeros/caja/{idCaja}")
    public List<Cajero> getCajeroByIdCaja(@PathVariable Long idCaja) {
        log.debug("REST request to get Trabajador : {}", idCaja);
        List<Cajero> cajeros = cajeroService.findByIdCaja(idCaja);
        return cajeros;
    }
    
    //BUSCAR CAJERO POR ID DE CLUB. TRAER TODOS LOS TRABAJADORES CAJEROS DE UN CLUB
    @GetMapping("/cajeros/club/{club_id}")
    public List<Cajero> getCajeroByIdClub(@PathVariable Long club_id) {
        log.debug("REST request to get Trabajador : {}", club_id);
        List<Cajero> cajeros = cajeroService.findByIdClub(club_id);
        return cajeros;
    }
    
    //VINCULAR CAJERO A UNA CAJA CON EL ID DE CAJERO Y EL ID DE CAJA
    @PostMapping("/cajeros/link/caja")
    public boolean linkCajeroToCaja(@Valid @RequestBody JsonNode jsonNode) throws URISyntaxException {
        log.debug("REST request to save Cajero : {}", jsonNode);
        ObjectMapper obj = new ObjectMapper();
        obj.registerModule(new JavaTimeModule()); // esto es necesario para evitar un error.
        
        Long idCaja = obj.convertValue(jsonNode.get("idCaja"),Long.class);
        Long idCajero = obj.convertValue(jsonNode.get("idCajero"),Long.class);
        
        Optional<Cajero> cajero = cajeroService.findOne(idCajero);
        Optional<Caja> caja = cajaService.findOne(idCaja);
        
        if (cajero.isPresent() && caja.isPresent()) {
        	cajero.get().setCaja(caja.get());
        	this.updateCajero(cajero.get().getId(),cajero.get());
        	return true;
        } else {
        	return false;
        }
        
        
    }
        

}
