package com.tyclients.tycapp.web.rest;

import com.tyclients.tycapp.domain.PlanContratado;
import com.tyclients.tycapp.repository.PlanContratadoRepository;
import com.tyclients.tycapp.service.PlanContratadoService;
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
 * REST controller for managing {@link com.tyclients.tycapp.domain.PlanContratado}.
 */
@RestController
@RequestMapping("/api")
public class PlanContratadoResource {

    private final Logger log = LoggerFactory.getLogger(PlanContratadoResource.class);

    private static final String ENTITY_NAME = "planContratado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlanContratadoService planContratadoService;

    private final PlanContratadoRepository planContratadoRepository;

    public PlanContratadoResource(PlanContratadoService planContratadoService, PlanContratadoRepository planContratadoRepository) {
        this.planContratadoService = planContratadoService;
        this.planContratadoRepository = planContratadoRepository;
    }

    /**
     * {@code POST  /plan-contratados} : Create a new planContratado.
     *
     * @param planContratado the planContratado to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new planContratado, or with status {@code 400 (Bad Request)} if the planContratado has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plan-contratados")
    public ResponseEntity<PlanContratado> createPlanContratado(@Valid @RequestBody PlanContratado planContratado)
        throws URISyntaxException {
        log.debug("REST request to save PlanContratado : {}", planContratado);
        if (planContratado.getId() != null) {
            throw new BadRequestAlertException("A new planContratado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlanContratado result = planContratadoService.save(planContratado);
        return ResponseEntity
            .created(new URI("/api/plan-contratados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plan-contratados/:id} : Updates an existing planContratado.
     *
     * @param id the id of the planContratado to save.
     * @param planContratado the planContratado to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planContratado,
     * or with status {@code 400 (Bad Request)} if the planContratado is not valid,
     * or with status {@code 500 (Internal Server Error)} if the planContratado couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plan-contratados/{id}")
    public ResponseEntity<PlanContratado> updatePlanContratado(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PlanContratado planContratado
    ) throws URISyntaxException {
        log.debug("REST request to update PlanContratado : {}, {}", id, planContratado);
        if (planContratado.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, planContratado.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planContratadoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PlanContratado result = planContratadoService.update(planContratado);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planContratado.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /plan-contratados/:id} : Partial updates given fields of an existing planContratado, field will ignore if it is null
     *
     * @param id the id of the planContratado to save.
     * @param planContratado the planContratado to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planContratado,
     * or with status {@code 400 (Bad Request)} if the planContratado is not valid,
     * or with status {@code 404 (Not Found)} if the planContratado is not found,
     * or with status {@code 500 (Internal Server Error)} if the planContratado couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/plan-contratados/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlanContratado> partialUpdatePlanContratado(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PlanContratado planContratado
    ) throws URISyntaxException {
        log.debug("REST request to partial update PlanContratado partially : {}, {}", id, planContratado);
        if (planContratado.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, planContratado.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planContratadoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PlanContratado> result = planContratadoService.partialUpdate(planContratado);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planContratado.getId().toString())
        );
    }

    /**
     * {@code GET  /plan-contratados} : get all the planContratados.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of planContratados in body.
     */
    @GetMapping("/plan-contratados")
    public ResponseEntity<List<PlanContratado>> getAllPlanContratados(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of PlanContratados");
        Page<PlanContratado> page = planContratadoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plan-contratados/:id} : get the "id" planContratado.
     *
     * @param id the id of the planContratado to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the planContratado, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plan-contratados/{id}")
    public ResponseEntity<PlanContratado> getPlanContratado(@PathVariable Long id) {
        log.debug("REST request to get PlanContratado : {}", id);
        Optional<PlanContratado> planContratado = planContratadoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(planContratado);
    }

    /**
     * {@code DELETE  /plan-contratados/:id} : delete the "id" planContratado.
     *
     * @param id the id of the planContratado to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plan-contratados/{id}")
    public ResponseEntity<Void> deletePlanContratado(@PathVariable Long id) {
        log.debug("REST request to delete PlanContratado : {}", id);
        planContratadoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
