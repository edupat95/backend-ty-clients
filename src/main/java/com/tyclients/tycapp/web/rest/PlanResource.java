package com.tyclients.tycapp.web.rest;

import com.tyclients.tycapp.domain.Plan;
import com.tyclients.tycapp.repository.PlanRepository;
import com.tyclients.tycapp.service.PlanService;
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
 * REST controller for managing {@link com.tyclients.tycapp.domain.Plan}.
 */
@RestController
@RequestMapping("/api")
public class PlanResource {

    private final Logger log = LoggerFactory.getLogger(PlanResource.class);

    private static final String ENTITY_NAME = "plan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlanService planService;

    private final PlanRepository planRepository;

    public PlanResource(PlanService planService, PlanRepository planRepository) {
        this.planService = planService;
        this.planRepository = planRepository;
    }

    /**
     * {@code POST  /plans} : Create a new plan.
     *
     * @param plan the plan to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new plan, or with status {@code 400 (Bad Request)} if the plan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plans")
    public ResponseEntity<Plan> createPlan(@Valid @RequestBody Plan plan) throws URISyntaxException {
        log.debug("REST request to save Plan : {}", plan);
        if (plan.getId() != null) {
            throw new BadRequestAlertException("A new plan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Plan result = planService.save(plan);
        return ResponseEntity
            .created(new URI("/api/plans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plans/:id} : Updates an existing plan.
     *
     * @param id the id of the plan to save.
     * @param plan the plan to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plan,
     * or with status {@code 400 (Bad Request)} if the plan is not valid,
     * or with status {@code 500 (Internal Server Error)} if the plan couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plans/{id}")
    public ResponseEntity<Plan> updatePlan(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Plan plan)
        throws URISyntaxException {
        log.debug("REST request to update Plan : {}, {}", id, plan);
        if (plan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plan.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Plan result = planService.update(plan);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plan.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /plans/:id} : Partial updates given fields of an existing plan, field will ignore if it is null
     *
     * @param id the id of the plan to save.
     * @param plan the plan to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plan,
     * or with status {@code 400 (Bad Request)} if the plan is not valid,
     * or with status {@code 404 (Not Found)} if the plan is not found,
     * or with status {@code 500 (Internal Server Error)} if the plan couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/plans/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Plan> partialUpdatePlan(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Plan plan
    ) throws URISyntaxException {
        log.debug("REST request to partial update Plan partially : {}, {}", id, plan);
        if (plan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plan.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Plan> result = planService.partialUpdate(plan);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plan.getId().toString())
        );
    }

    /**
     * {@code GET  /plans} : get all the plans.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plans in body.
     */
    @GetMapping("/plans")
    public ResponseEntity<List<Plan>> getAllPlans(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Plans");
        Page<Plan> page = planService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plans/:id} : get the "id" plan.
     *
     * @param id the id of the plan to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the plan, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plans/{id}")
    public ResponseEntity<Plan> getPlan(@PathVariable Long id) {
        log.debug("REST request to get Plan : {}", id);
        Optional<Plan> plan = planService.findOne(id);
        return ResponseUtil.wrapOrNotFound(plan);
    }

    /**
     * {@code DELETE  /plans/:id} : delete the "id" plan.
     *
     * @param id the id of the plan to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plans/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long id) {
        log.debug("REST request to delete Plan : {}", id);
        planService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
