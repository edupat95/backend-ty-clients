package com.tyclients.tycapp.web.rest;

import com.tyclients.tycapp.domain.Club;
import com.tyclients.tycapp.domain.FormaPago;
import com.tyclients.tycapp.repository.FormaPagoRepository;
import com.tyclients.tycapp.service.ClubService;
import com.tyclients.tycapp.service.FormaPagoService;
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
 * REST controller for managing {@link com.tyclients.tycapp.domain.FormaPago}.
 */
@RestController
@RequestMapping("/api")
public class FormaPagoResource {

    private final Logger log = LoggerFactory.getLogger(FormaPagoResource.class);

    private static final String ENTITY_NAME = "formaPago";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormaPagoService formaPagoService;

    private final FormaPagoRepository formaPagoRepository;
    
    private final ClubService clubService;

    public FormaPagoResource(ClubService clubService, FormaPagoService formaPagoService, FormaPagoRepository formaPagoRepository) {
        this.formaPagoService = formaPagoService;
        this.formaPagoRepository = formaPagoRepository;
        this.clubService = clubService;
    }

    /**
     * {@code POST  /forma-pagos} : Create a new formaPago.
     *
     * @param formaPago the formaPago to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new formaPago, or with status {@code 400 (Bad Request)} if the formaPago has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/forma-pagos")
    public ResponseEntity<FormaPago> createFormaPago(@Valid @RequestBody FormaPago formaPago) throws URISyntaxException {
        log.debug("REST request to save FormaPago : {}", formaPago);
        if (formaPago.getId() != null) {
            throw new BadRequestAlertException("A new formaPago cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FormaPago result = formaPagoService.save(formaPago);
        return ResponseEntity
            .created(new URI("/api/forma-pagos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /forma-pagos/:id} : Updates an existing formaPago.
     *
     * @param id the id of the formaPago to save.
     * @param formaPago the formaPago to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formaPago,
     * or with status {@code 400 (Bad Request)} if the formaPago is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formaPago couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/forma-pagos/{id}")
    public ResponseEntity<FormaPago> updateFormaPago(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FormaPago formaPago
    ) throws URISyntaxException {
        log.debug("REST request to update FormaPago : {}, {}", id, formaPago);
        if (formaPago.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formaPago.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formaPagoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FormaPago result = formaPagoService.update(formaPago);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, formaPago.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /forma-pagos/:id} : Partial updates given fields of an existing formaPago, field will ignore if it is null
     *
     * @param id the id of the formaPago to save.
     * @param formaPago the formaPago to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formaPago,
     * or with status {@code 400 (Bad Request)} if the formaPago is not valid,
     * or with status {@code 404 (Not Found)} if the formaPago is not found,
     * or with status {@code 500 (Internal Server Error)} if the formaPago couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/forma-pagos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FormaPago> partialUpdateFormaPago(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FormaPago formaPago
    ) throws URISyntaxException {
        log.debug("REST request to partial update FormaPago partially : {}, {}", id, formaPago);
        if (formaPago.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, formaPago.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!formaPagoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FormaPago> result = formaPagoService.partialUpdate(formaPago);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, formaPago.getId().toString())
        );
    }

    /**
     * {@code GET  /forma-pagos} : get all the formaPagos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of formaPagos in body.
     */
    @GetMapping("/forma-pagos")
    public ResponseEntity<List<FormaPago>> getAllFormaPagos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of FormaPagos");
        Page<FormaPago> page = formaPagoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /forma-pagos/:id} : get the "id" formaPago.
     *
     * @param id the id of the formaPago to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the formaPago, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/forma-pagos/{id}")
    public ResponseEntity<FormaPago> getFormaPago(@PathVariable Long id) {
        log.debug("REST request to get FormaPago : {}", id);
        Optional<FormaPago> formaPago = formaPagoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(formaPago);
    }

    /**
     * {@code DELETE  /forma-pagos/:id} : delete the "id" formaPago.
     *
     * @param id the id of the formaPago to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/forma-pagos/{id}")
    public ResponseEntity<Void> deleteFormaPago(@PathVariable Long id) {
        log.debug("REST request to delete FormaPago : {}", id);
        formaPagoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
    
    //OBTENER LOS METODOS DE PAGO DE UN CLUB ESPECIFICO
    @GetMapping("/forma-pagos/club/{idClub}")
    public List<FormaPago> getFormaPagoByIdClub(@PathVariable Long idClub) {
        log.debug("REST request to get FormaPago : {}", idClub);
        
        Optional<Club> club = clubService.findOne(idClub);
        
        if(club.isPresent()) {
        	List<FormaPago> formasPago = formaPagoService.findByClub(club.get());
            return formasPago;
        }
        
        return null;

    }

}
