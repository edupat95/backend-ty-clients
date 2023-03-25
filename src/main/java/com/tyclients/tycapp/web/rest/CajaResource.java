package com.tyclients.tycapp.web.rest;

import com.tyclients.tycapp.domain.Caja;
import com.tyclients.tycapp.repository.CajaRepository;
import com.tyclients.tycapp.service.CajaService;
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
 * REST controller for managing {@link com.tyclients.tycapp.domain.Caja}.
 */
@RestController
@RequestMapping("/api")
public class CajaResource {

    private final Logger log = LoggerFactory.getLogger(CajaResource.class);

    private static final String ENTITY_NAME = "caja";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CajaService cajaService;

    private final CajaRepository cajaRepository;

    public CajaResource(CajaService cajaService, CajaRepository cajaRepository) {
        this.cajaService = cajaService;
        this.cajaRepository = cajaRepository;
    }

    /**
     * {@code POST  /cajas} : Create a new caja.
     *
     * @param caja the caja to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new caja, or with status {@code 400 (Bad Request)} if the caja has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cajas")
    public ResponseEntity<Caja> createCaja(@Valid @RequestBody Caja caja) throws URISyntaxException {
        log.debug("REST request to save Caja : {}", caja);
        if (caja.getId() != null) {
            throw new BadRequestAlertException("A new caja cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Caja result = cajaService.save(caja);
        return ResponseEntity
            .created(new URI("/api/cajas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cajas/:id} : Updates an existing caja.
     *
     * @param id the id of the caja to save.
     * @param caja the caja to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated caja,
     * or with status {@code 400 (Bad Request)} if the caja is not valid,
     * or with status {@code 500 (Internal Server Error)} if the caja couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cajas/{id}")
    public ResponseEntity<Caja> updateCaja(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Caja caja)
        throws URISyntaxException {
        log.debug("REST request to update Caja : {}, {}", id, caja);
        if (caja.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, caja.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cajaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Caja result = cajaService.update(caja);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, caja.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cajas/:id} : Partial updates given fields of an existing caja, field will ignore if it is null
     *
     * @param id the id of the caja to save.
     * @param caja the caja to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated caja,
     * or with status {@code 400 (Bad Request)} if the caja is not valid,
     * or with status {@code 404 (Not Found)} if the caja is not found,
     * or with status {@code 500 (Internal Server Error)} if the caja couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cajas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Caja> partialUpdateCaja(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Caja caja
    ) throws URISyntaxException {
        log.debug("REST request to partial update Caja partially : {}, {}", id, caja);
        if (caja.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, caja.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cajaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Caja> result = cajaService.partialUpdate(caja);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, caja.getId().toString())
        );
    }

    /**
     * {@code GET  /cajas} : get all the cajas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cajas in body.
     */
    @GetMapping("/cajas")
    public ResponseEntity<List<Caja>> getAllCajas(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Cajas");
        Page<Caja> page = cajaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cajas/:id} : get the "id" caja.
     *
     * @param id the id of the caja to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the caja, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cajas/{id}")
    public ResponseEntity<Caja> getCaja(@PathVariable Long id) {
        log.debug("REST request to get Caja : {}", id);
        Optional<Caja> caja = cajaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(caja);
    }

    /**
     * {@code DELETE  /cajas/:id} : delete the "id" caja.
     *
     * @param id the id of the caja to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cajas/{id}")
    public ResponseEntity<Void> deleteCaja(@PathVariable Long id) {
        log.debug("REST request to delete Caja : {}", id);
        cajaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
    
    //BUSCAR CAJAS POR ID DEL CLUB
    @GetMapping("/cajas/club/{idClub}")
    public List<Caja> getCajaByIdClub(@PathVariable Long idClub) {
        log.debug("REST request to get Caja by Club : {}", idClub);
        List<Caja> cajas = cajaService.findByIdClub(idClub);
        return cajas;
    }
    
}
