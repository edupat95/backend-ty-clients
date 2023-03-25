package com.tyclients.tycapp.web.rest;

import com.tyclients.tycapp.domain.Deposito;
import com.tyclients.tycapp.repository.DepositoRepository;
import com.tyclients.tycapp.service.DepositoService;
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
 * REST controller for managing {@link com.tyclients.tycapp.domain.Deposito}.
 */
@RestController
@RequestMapping("/api")
public class DepositoResource {

    private final Logger log = LoggerFactory.getLogger(DepositoResource.class);

    private static final String ENTITY_NAME = "deposito";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DepositoService depositoService;

    private final DepositoRepository depositoRepository;

    public DepositoResource(DepositoService depositoService, DepositoRepository depositoRepository) {
        this.depositoService = depositoService;
        this.depositoRepository = depositoRepository;
    }

    /**
     * {@code POST  /depositos} : Create a new deposito.
     *
     * @param deposito the deposito to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deposito, or with status {@code 400 (Bad Request)} if the deposito has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/depositos")
    public ResponseEntity<Deposito> createDeposito(@Valid @RequestBody Deposito deposito) throws URISyntaxException {
        log.debug("REST request to save Deposito : {}", deposito);
        if (deposito.getId() != null) {
            throw new BadRequestAlertException("A new deposito cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Deposito result = depositoService.save(deposito);
        return ResponseEntity
            .created(new URI("/api/depositos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /depositos/:id} : Updates an existing deposito.
     *
     * @param id the id of the deposito to save.
     * @param deposito the deposito to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deposito,
     * or with status {@code 400 (Bad Request)} if the deposito is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deposito couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/depositos/{id}")
    public ResponseEntity<Deposito> updateDeposito(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Deposito deposito
    ) throws URISyntaxException {
        log.debug("REST request to update Deposito : {}, {}", id, deposito);
        if (deposito.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deposito.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!depositoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Deposito result = depositoService.update(deposito);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deposito.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /depositos/:id} : Partial updates given fields of an existing deposito, field will ignore if it is null
     *
     * @param id the id of the deposito to save.
     * @param deposito the deposito to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deposito,
     * or with status {@code 400 (Bad Request)} if the deposito is not valid,
     * or with status {@code 404 (Not Found)} if the deposito is not found,
     * or with status {@code 500 (Internal Server Error)} if the deposito couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/depositos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Deposito> partialUpdateDeposito(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Deposito deposito
    ) throws URISyntaxException {
        log.debug("REST request to partial update Deposito partially : {}, {}", id, deposito);
        if (deposito.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deposito.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!depositoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Deposito> result = depositoService.partialUpdate(deposito);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deposito.getId().toString())
        );
    }

    /**
     * {@code GET  /depositos} : get all the depositos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of depositos in body.
     */
    @GetMapping("/depositos")
    public ResponseEntity<List<Deposito>> getAllDepositos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Depositos");
        Page<Deposito> page = depositoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /depositos/:id} : get the "id" deposito.
     *
     * @param id the id of the deposito to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deposito, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/depositos/{id}")
    public ResponseEntity<Deposito> getDeposito(@PathVariable Long id) {
        log.debug("REST request to get Deposito : {}", id);
        Optional<Deposito> deposito = depositoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(deposito);
    }

    /**
     * {@code DELETE  /depositos/:id} : delete the "id" deposito.
     *
     * @param id the id of the deposito to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/depositos/{id}")
    public ResponseEntity<Void> deleteDeposito(@PathVariable Long id) {
        log.debug("REST request to delete Deposito : {}", id);
        depositoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
