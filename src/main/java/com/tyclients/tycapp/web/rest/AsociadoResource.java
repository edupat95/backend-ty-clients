package com.tyclients.tycapp.web.rest;

import com.tyclients.tycapp.domain.Asociado;
import com.tyclients.tycapp.repository.AsociadoRepository;
import com.tyclients.tycapp.service.AsociadoService;
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
 * REST controller for managing {@link com.tyclients.tycapp.domain.Asociado}.
 */
@RestController
@RequestMapping("/api")
public class AsociadoResource {

    private final Logger log = LoggerFactory.getLogger(AsociadoResource.class);

    private static final String ENTITY_NAME = "asociado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AsociadoService asociadoService;

    private final AsociadoRepository asociadoRepository;

    public AsociadoResource(AsociadoService asociadoService, AsociadoRepository asociadoRepository) {
        this.asociadoService = asociadoService;
        this.asociadoRepository = asociadoRepository;
    }

    /**
     * {@code POST  /asociados} : Create a new asociado.
     *
     * @param asociado the asociado to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new asociado, or with status {@code 400 (Bad Request)} if the asociado has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/asociados")
    public ResponseEntity<Asociado> createAsociado(@Valid @RequestBody Asociado asociado) throws URISyntaxException {
        log.debug("REST request to save Asociado : {}", asociado);
        if (asociado.getId() != null) {
            throw new BadRequestAlertException("A new asociado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Asociado result = asociadoService.save(asociado);
        return ResponseEntity
            .created(new URI("/api/asociados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /asociados/:id} : Updates an existing asociado.
     *
     * @param id the id of the asociado to save.
     * @param asociado the asociado to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated asociado,
     * or with status {@code 400 (Bad Request)} if the asociado is not valid,
     * or with status {@code 500 (Internal Server Error)} if the asociado couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/asociados/{id}")
    public ResponseEntity<Asociado> updateAsociado(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Asociado asociado
    ) throws URISyntaxException {
        log.debug("REST request to update Asociado : {}, {}", id, asociado);
        if (asociado.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, asociado.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!asociadoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Asociado result = asociadoService.update(asociado);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, asociado.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /asociados/:id} : Partial updates given fields of an existing asociado, field will ignore if it is null
     *
     * @param id the id of the asociado to save.
     * @param asociado the asociado to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated asociado,
     * or with status {@code 400 (Bad Request)} if the asociado is not valid,
     * or with status {@code 404 (Not Found)} if the asociado is not found,
     * or with status {@code 500 (Internal Server Error)} if the asociado couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/asociados/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Asociado> partialUpdateAsociado(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Asociado asociado
    ) throws URISyntaxException {
        log.debug("REST request to partial update Asociado partially : {}, {}", id, asociado);
        if (asociado.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, asociado.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!asociadoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Asociado> result = asociadoService.partialUpdate(asociado);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, asociado.getId().toString())
        );
    }

    /**
     * {@code GET  /asociados} : get all the asociados.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of asociados in body.
     */
    @GetMapping("/asociados")
    public ResponseEntity<List<Asociado>> getAllAsociados(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Asociados");
        Page<Asociado> page = asociadoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /asociados/:id} : get the "id" asociado.
     *
     * @param id the id of the asociado to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the asociado, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/asociados/{id}")
    public ResponseEntity<Asociado> getAsociado(@PathVariable Long id) {
        log.debug("REST request to get Asociado : {}", id);
        Optional<Asociado> asociado = asociadoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(asociado);
    }

    /**
     * {@code DELETE  /asociados/:id} : delete the "id" asociado.
     *
     * @param id the id of the asociado to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/asociados/{id}")
    public ResponseEntity<Void> deleteAsociado(@PathVariable Long id) {
        log.debug("REST request to delete Asociado : {}", id);
        asociadoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
