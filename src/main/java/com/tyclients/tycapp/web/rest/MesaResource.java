package com.tyclients.tycapp.web.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tyclients.tycapp.domain.AsociadoClub;
import com.tyclients.tycapp.domain.Club;
import com.tyclients.tycapp.domain.Documento;
import com.tyclients.tycapp.domain.Mesa;
import com.tyclients.tycapp.domain.Producto;
import com.tyclients.tycapp.domain.Registrador;
import com.tyclients.tycapp.repository.MesaRepository;
import com.tyclients.tycapp.service.ClubService;
import com.tyclients.tycapp.service.MesaService;
import com.tyclients.tycapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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
 * REST controller for managing {@link com.tyclients.tycapp.domain.Mesa}.
 */
@RestController
@RequestMapping("/api")
public class MesaResource {

    private final Logger log = LoggerFactory.getLogger(MesaResource.class);

    private static final String ENTITY_NAME = "mesa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MesaService mesaService;

    private final MesaRepository mesaRepository;
    
    private final ClubService clubService;

    public MesaResource(ClubService clubService, MesaService mesaService, MesaRepository mesaRepository) {
        this.mesaService = mesaService;
        this.mesaRepository = mesaRepository;
        this.clubService = clubService;
    }

    /**
     * {@code POST  /mesas} : Create a new mesa.
     *
     * @param mesa the mesa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mesa, or with status {@code 400 (Bad Request)} if the mesa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mesas")
    public ResponseEntity<Mesa> createMesa(@Valid @RequestBody Mesa mesa) throws URISyntaxException {
        log.debug("REST request to save Mesa : {}", mesa);
        if (mesa.getId() != null) {
            throw new BadRequestAlertException("A new mesa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Mesa result = mesaService.save(mesa);
        return ResponseEntity
            .created(new URI("/api/mesas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mesas/:id} : Updates an existing mesa.
     *
     * @param id the id of the mesa to save.
     * @param mesa the mesa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mesa,
     * or with status {@code 400 (Bad Request)} if the mesa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mesa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mesas/{id}")
    public ResponseEntity<Mesa> updateMesa(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Mesa mesa)
        throws URISyntaxException {
        log.debug("REST request to update Mesa : {}, {}", id, mesa);
        if (mesa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mesa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mesaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Mesa result = mesaService.update(mesa);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mesa.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /mesas/:id} : Partial updates given fields of an existing mesa, field will ignore if it is null
     *
     * @param id the id of the mesa to save.
     * @param mesa the mesa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mesa,
     * or with status {@code 400 (Bad Request)} if the mesa is not valid,
     * or with status {@code 404 (Not Found)} if the mesa is not found,
     * or with status {@code 500 (Internal Server Error)} if the mesa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/mesas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Mesa> partialUpdateMesa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Mesa mesa
    ) throws URISyntaxException {
        log.debug("REST request to partial update Mesa partially : {}, {}", id, mesa);
        if (mesa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mesa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mesaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Mesa> result = mesaService.partialUpdate(mesa);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mesa.getId().toString())
        );
    }

    /**
     * {@code GET  /mesas} : get all the mesas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mesas in body.
     */
    @GetMapping("/mesas")
    public ResponseEntity<List<Mesa>> getAllMesas(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Mesas");
        Page<Mesa> page = mesaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mesas/:id} : get the "id" mesa.
     *
     * @param id the id of the mesa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mesa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mesas/{id}")
    public ResponseEntity<Mesa> getMesa(@PathVariable Long id) {
        log.debug("REST request to get Mesa : {}", id);
        Optional<Mesa> mesa = mesaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mesa);
    }

    /**
     * {@code DELETE  /mesas/:id} : delete the "id" mesa.
     *
     * @param id the id of the mesa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mesas/{id}")
    public ResponseEntity<Void> deleteMesa(@PathVariable Long id) {
        log.debug("REST request to delete Mesa : {}", id);
        mesaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
    
    //CREAR MUCHAS MESAS PARA UN CLUB
    @PostMapping("/mesas/crear")
    public ResponseEntity<String> createMesas(@RequestBody JsonNode jsonNode) throws URISyntaxException {
        //log.debug("REST request to save Documento and AsociadoClub with Registrador: {}", jsonNode);
        
        ObjectMapper obj = new ObjectMapper();
        obj.registerModule(new JavaTimeModule()); // esto es necesario para evitar un error.
        
        Long idClub = obj.convertValue(jsonNode.get("idClub"),Long.class); 
        Optional<Club> club = clubService.findOne(idClub); 
        Long cantidad = obj.convertValue(jsonNode.get("cantidad"),Long.class); 
        System.out.println("idClub: " + idClub);
        System.out.println("cantidad: " + cantidad);
        if(club.isPresent()) {
            mesaService.createMesas(club.get(), cantidad);
        	return ResponseEntity.badRequest().body("Mesas creadas");//return ResponseEntity.ok(mesas);
        } else {
            return ResponseEntity.badRequest().body("El club no existe");
        }
    }
    
    //LISTAR PRODUCTOS DE UN CLUB ESPECIFICO
    @GetMapping("/mesas/club/{idClub}")
    public ResponseEntity<List<Mesa>> getAllMesasByClub(@PathVariable Long idClub) {
        log.debug("REST request to get a page of Productos");
        
        Optional<Club> club = clubService.findOne(idClub);
        if(club.isPresent()) {
        	List<Mesa> listMesas = mesaService.findByClub(club.get());
        	return new ResponseEntity<List<Mesa>>( listMesas , HttpStatus.OK);
        }{
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}
