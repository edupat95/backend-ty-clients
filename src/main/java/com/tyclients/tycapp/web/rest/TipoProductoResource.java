package com.tyclients.tycapp.web.rest;

import com.tyclients.tycapp.domain.TipoProducto;
import com.tyclients.tycapp.repository.TipoProductoRepository;
import com.tyclients.tycapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.tyclients.tycapp.domain.TipoProducto}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TipoProductoResource {

    private final Logger log = LoggerFactory.getLogger(TipoProductoResource.class);

    private static final String ENTITY_NAME = "tipoProducto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoProductoRepository tipoProductoRepository;

    public TipoProductoResource(TipoProductoRepository tipoProductoRepository) {
        this.tipoProductoRepository = tipoProductoRepository;
    }

    /**
     * {@code POST  /tipo-productos} : Create a new tipoProducto.
     *
     * @param tipoProducto the tipoProducto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoProducto, or with status {@code 400 (Bad Request)} if the tipoProducto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-productos")
    public ResponseEntity<TipoProducto> createTipoProducto(@RequestBody TipoProducto tipoProducto) throws URISyntaxException {
        log.debug("REST request to save TipoProducto : {}", tipoProducto);
        if (tipoProducto.getId() != null) {
            throw new BadRequestAlertException("A new tipoProducto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoProducto result = tipoProductoRepository.save(tipoProducto);
        return ResponseEntity
            .created(new URI("/api/tipo-productos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-productos/:id} : Updates an existing tipoProducto.
     *
     * @param id the id of the tipoProducto to save.
     * @param tipoProducto the tipoProducto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoProducto,
     * or with status {@code 400 (Bad Request)} if the tipoProducto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoProducto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-productos/{id}")
    public ResponseEntity<TipoProducto> updateTipoProducto(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TipoProducto tipoProducto
    ) throws URISyntaxException {
        log.debug("REST request to update TipoProducto : {}, {}", id, tipoProducto);
        if (tipoProducto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoProducto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoProductoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TipoProducto result = tipoProductoRepository.save(tipoProducto);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoProducto.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tipo-productos/:id} : Partial updates given fields of an existing tipoProducto, field will ignore if it is null
     *
     * @param id the id of the tipoProducto to save.
     * @param tipoProducto the tipoProducto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoProducto,
     * or with status {@code 400 (Bad Request)} if the tipoProducto is not valid,
     * or with status {@code 404 (Not Found)} if the tipoProducto is not found,
     * or with status {@code 500 (Internal Server Error)} if the tipoProducto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tipo-productos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TipoProducto> partialUpdateTipoProducto(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TipoProducto tipoProducto
    ) throws URISyntaxException {
        log.debug("REST request to partial update TipoProducto partially : {}, {}", id, tipoProducto);
        if (tipoProducto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoProducto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoProductoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TipoProducto> result = tipoProductoRepository
            .findById(tipoProducto.getId())
            .map(existingTipoProducto -> {
                if (tipoProducto.getName() != null) {
                    existingTipoProducto.setName(tipoProducto.getName());
                }

                return existingTipoProducto;
            })
            .map(tipoProductoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoProducto.getId().toString())
        );
    }

    /**
     * {@code GET  /tipo-productos} : get all the tipoProductos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoProductos in body.
     */
    @GetMapping("/tipo-productos")
    public List<TipoProducto> getAllTipoProductos() {
        log.debug("REST request to get all TipoProductos");
        return tipoProductoRepository.findAll();
    }

    /**
     * {@code GET  /tipo-productos/:id} : get the "id" tipoProducto.
     *
     * @param id the id of the tipoProducto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoProducto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-productos/{id}")
    public ResponseEntity<TipoProducto> getTipoProducto(@PathVariable Long id) {
        log.debug("REST request to get TipoProducto : {}", id);
        Optional<TipoProducto> tipoProducto = tipoProductoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tipoProducto);
    }

    /**
     * {@code DELETE  /tipo-productos/:id} : delete the "id" tipoProducto.
     *
     * @param id the id of the tipoProducto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-productos/{id}")
    public ResponseEntity<Void> deleteTipoProducto(@PathVariable Long id) {
        log.debug("REST request to delete TipoProducto : {}", id);
        tipoProductoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
