package com.tyclients.tycapp.web.rest;

import com.tyclients.tycapp.domain.Registrador;
import com.tyclients.tycapp.repository.RegistradorRepository;
import com.tyclients.tycapp.service.RegistradorService;
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
 * REST controller for managing {@link com.tyclients.tycapp.domain.Registrador}.
 */
@RestController
@RequestMapping("/api")
public class RegistradorResource {

    private final Logger log = LoggerFactory.getLogger(RegistradorResource.class);

    private static final String ENTITY_NAME = "registrador";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegistradorService registradorService;

    private final RegistradorRepository registradorRepository;

    public RegistradorResource(RegistradorService registradorService, RegistradorRepository registradorRepository) {
        this.registradorService = registradorService;
        this.registradorRepository = registradorRepository;
    }

    /**
     * {@code POST  /registradors} : Create a new registrador.
     *
     * @param registrador the registrador to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new registrador, or with status {@code 400 (Bad Request)} if the registrador has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/registradors")
    public ResponseEntity<Registrador> createRegistrador(@Valid @RequestBody Registrador registrador) throws URISyntaxException {
        log.debug("REST request to save Registrador : {}", registrador);
        if (registrador.getId() != null) {
            throw new BadRequestAlertException("A new registrador cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Registrador result = registradorService.save(registrador);
        return ResponseEntity
            .created(new URI("/api/registradors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /registradors/:id} : Updates an existing registrador.
     *
     * @param id the id of the registrador to save.
     * @param registrador the registrador to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated registrador,
     * or with status {@code 400 (Bad Request)} if the registrador is not valid,
     * or with status {@code 500 (Internal Server Error)} if the registrador couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/registradors/{id}")
    public ResponseEntity<Registrador> updateRegistrador(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Registrador registrador
    ) throws URISyntaxException {
        log.debug("REST request to update Registrador : {}, {}", id, registrador);
        if (registrador.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, registrador.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!registradorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Registrador result = registradorService.update(registrador);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, registrador.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /registradors/:id} : Partial updates given fields of an existing registrador, field will ignore if it is null
     *
     * @param id the id of the registrador to save.
     * @param registrador the registrador to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated registrador,
     * or with status {@code 400 (Bad Request)} if the registrador is not valid,
     * or with status {@code 404 (Not Found)} if the registrador is not found,
     * or with status {@code 500 (Internal Server Error)} if the registrador couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/registradors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Registrador> partialUpdateRegistrador(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Registrador registrador
    ) throws URISyntaxException {
        log.debug("REST request to partial update Registrador partially : {}, {}", id, registrador);
        if (registrador.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, registrador.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!registradorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Registrador> result = registradorService.partialUpdate(registrador);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, registrador.getId().toString())
        );
    }

    /**
     * {@code GET  /registradors} : get all the registradors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of registradors in body.
     */
    @GetMapping("/registradors")
    public ResponseEntity<List<Registrador>> getAllRegistradors(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Registradors");
        Page<Registrador> page = registradorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /registradors/:id} : get the "id" registrador.
     *
     * @param id the id of the registrador to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the registrador, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/registradors/{id}")
    public ResponseEntity<Registrador> getRegistrador(@PathVariable Long id) {
        log.debug("REST request to get Registrador : {}", id);
        Optional<Registrador> registrador = registradorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(registrador);
    }

    /**
     * {@code DELETE  /registradors/:id} : delete the "id" registrador.
     *
     * @param id the id of the registrador to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/registradors/{id}")
    public ResponseEntity<Void> deleteRegistrador(@PathVariable Long id) {
        log.debug("REST request to delete Registrador : {}", id);
        registradorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
    
    //Buscar registrador por trabajador_id 
    @GetMapping("/registradors/trabajador/{trabajador_id}")
    public ResponseEntity<Registrador> getRegistradorByIdTrabajador(@PathVariable Long trabajador_id) {
        log.debug("REST request to get Registrador : {}", trabajador_id);

        Optional<Registrador> registrador = registradorService.findByTrabajador(trabajador_id);
        return ResponseUtil.wrapOrNotFound(registrador);
    }
}
