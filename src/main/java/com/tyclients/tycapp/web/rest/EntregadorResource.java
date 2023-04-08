package com.tyclients.tycapp.web.rest;

import com.tyclients.tycapp.domain.Entregador;
import com.tyclients.tycapp.domain.Registrador;
import com.tyclients.tycapp.repository.EntregadorRepository;
import com.tyclients.tycapp.service.EntregadorService;
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
 * REST controller for managing {@link com.tyclients.tycapp.domain.Entregador}.
 */
@RestController
@RequestMapping("/api")
public class EntregadorResource {

    private final Logger log = LoggerFactory.getLogger(EntregadorResource.class);

    private static final String ENTITY_NAME = "entregador";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntregadorService entregadorService;

    private final EntregadorRepository entregadorRepository;

    public EntregadorResource(EntregadorService entregadorService, EntregadorRepository entregadorRepository) {
        this.entregadorService = entregadorService;
        this.entregadorRepository = entregadorRepository;
    }

    /**
     * {@code POST  /entregadors} : Create a new entregador.
     *
     * @param entregador the entregador to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entregador, or with status {@code 400 (Bad Request)} if the entregador has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/entregadors")
    public ResponseEntity<Entregador> createEntregador(@Valid @RequestBody Entregador entregador) throws URISyntaxException {
        log.debug("REST request to save Entregador : {}", entregador);
        if (entregador.getId() != null) {
            throw new BadRequestAlertException("A new entregador cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Entregador result = entregadorService.save(entregador);
        return ResponseEntity
            .created(new URI("/api/entregadors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /entregadors/:id} : Updates an existing entregador.
     *
     * @param id the id of the entregador to save.
     * @param entregador the entregador to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entregador,
     * or with status {@code 400 (Bad Request)} if the entregador is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entregador couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/entregadors/{id}")
    public ResponseEntity<Entregador> updateEntregador(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Entregador entregador
    ) throws URISyntaxException {
        log.debug("REST request to update Entregador : {}, {}", id, entregador);
        if (entregador.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entregador.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entregadorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Entregador result = entregadorService.update(entregador);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entregador.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /entregadors/:id} : Partial updates given fields of an existing entregador, field will ignore if it is null
     *
     * @param id the id of the entregador to save.
     * @param entregador the entregador to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entregador,
     * or with status {@code 400 (Bad Request)} if the entregador is not valid,
     * or with status {@code 404 (Not Found)} if the entregador is not found,
     * or with status {@code 500 (Internal Server Error)} if the entregador couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/entregadors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Entregador> partialUpdateEntregador(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Entregador entregador
    ) throws URISyntaxException {
        log.debug("REST request to partial update Entregador partially : {}, {}", id, entregador);
        if (entregador.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entregador.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entregadorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Entregador> result = entregadorService.partialUpdate(entregador);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entregador.getId().toString())
        );
    }

    /**
     * {@code GET  /entregadors} : get all the entregadors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entregadors in body.
     */
    @GetMapping("/entregadors")
    public ResponseEntity<List<Entregador>> getAllEntregadors(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Entregadors");
        Page<Entregador> page = entregadorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /entregadors/:id} : get the "id" entregador.
     *
     * @param id the id of the entregador to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entregador, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/entregadors/{id}")
    public ResponseEntity<Entregador> getEntregador(@PathVariable Long id) {
        log.debug("REST request to get Entregador : {}", id);
        Optional<Entregador> entregador = entregadorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entregador);
    }

    /**
     * {@code DELETE  /entregadors/:id} : delete the "id" entregador.
     *
     * @param id the id of the entregador to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/entregadors/{id}")
    public ResponseEntity<Void> deleteEntregador(@PathVariable Long id) {
        log.debug("REST request to delete Entregador : {}", id);
        entregadorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
    
    //Buscar registrador por trabajador_id 
    @GetMapping("/entregadors/trabajador/{trabajador_id}")
    public ResponseEntity<Entregador> getRegistradorByIdTrabajador(@PathVariable Long trabajador_id) {
        log.debug("REST request to get Registrador : {}", trabajador_id);
        Optional<Entregador> entregador = entregadorService.findByTrabajador(trabajador_id);
        return ResponseUtil.wrapOrNotFound(entregador);
    }
    
}
