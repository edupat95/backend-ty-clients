package com.tyclients.tycapp.web.rest;

import com.tyclients.tycapp.domain.AdminClub;
import com.tyclients.tycapp.repository.AdminClubRepository;
import com.tyclients.tycapp.service.AdminClubService;
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
 * REST controller for managing {@link com.tyclients.tycapp.domain.AdminClub}.
 */
@RestController
@RequestMapping("/api")
public class AdminClubResource {

    private final Logger log = LoggerFactory.getLogger(AdminClubResource.class);

    private static final String ENTITY_NAME = "adminClub";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdminClubService adminClubService;

    private final AdminClubRepository adminClubRepository;

    public AdminClubResource(AdminClubService adminClubService, AdminClubRepository adminClubRepository) {
        this.adminClubService = adminClubService;
        this.adminClubRepository = adminClubRepository;
    }

    /**
     * {@code POST  /admin-clubs} : Create a new adminClub.
     *
     * @param adminClub the adminClub to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adminClub, or with status {@code 400 (Bad Request)} if the adminClub has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/admin-clubs")
    public ResponseEntity<AdminClub> createAdminClub(@Valid @RequestBody AdminClub adminClub) throws URISyntaxException {
        log.debug("REST request to save AdminClub : {}", adminClub);
        if (adminClub.getId() != null) {
            throw new BadRequestAlertException("A new adminClub cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdminClub result = adminClubService.save(adminClub);
        return ResponseEntity
            .created(new URI("/api/admin-clubs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /admin-clubs/:id} : Updates an existing adminClub.
     *
     * @param id the id of the adminClub to save.
     * @param adminClub the adminClub to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adminClub,
     * or with status {@code 400 (Bad Request)} if the adminClub is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adminClub couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/admin-clubs/{id}")
    public ResponseEntity<AdminClub> updateAdminClub(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AdminClub adminClub
    ) throws URISyntaxException {
        log.debug("REST request to update AdminClub : {}, {}", id, adminClub);
        if (adminClub.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adminClub.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adminClubRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AdminClub result = adminClubService.update(adminClub);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adminClub.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /admin-clubs/:id} : Partial updates given fields of an existing adminClub, field will ignore if it is null
     *
     * @param id the id of the adminClub to save.
     * @param adminClub the adminClub to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adminClub,
     * or with status {@code 400 (Bad Request)} if the adminClub is not valid,
     * or with status {@code 404 (Not Found)} if the adminClub is not found,
     * or with status {@code 500 (Internal Server Error)} if the adminClub couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/admin-clubs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AdminClub> partialUpdateAdminClub(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AdminClub adminClub
    ) throws URISyntaxException {
        log.debug("REST request to partial update AdminClub partially : {}, {}", id, adminClub);
        if (adminClub.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adminClub.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adminClubRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdminClub> result = adminClubService.partialUpdate(adminClub);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adminClub.getId().toString())
        );
    }

    /**
     * {@code GET  /admin-clubs} : get all the adminClubs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adminClubs in body.
     */
    @GetMapping("/admin-clubs")
    public ResponseEntity<List<AdminClub>> getAllAdminClubs(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of AdminClubs");
        Page<AdminClub> page = adminClubService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /admin-clubs/:id} : get the "id" adminClub.
     *
     * @param id the id of the adminClub to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adminClub, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/admin-clubs/{id}")
    public ResponseEntity<AdminClub> getAdminClub(@PathVariable Long id) {
        log.debug("REST request to get AdminClub : {}", id);
        Optional<AdminClub> adminClub = adminClubService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adminClub);
    }

    /**
     * {@code DELETE  /admin-clubs/:id} : delete the "id" adminClub.
     *
     * @param id the id of the adminClub to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/admin-clubs/{id}")
    public ResponseEntity<Void> deleteAdminClub(@PathVariable Long id) {
        log.debug("REST request to delete AdminClub : {}", id);
        adminClubService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
