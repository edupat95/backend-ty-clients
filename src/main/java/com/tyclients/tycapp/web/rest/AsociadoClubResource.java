package com.tyclients.tycapp.web.rest;

import com.tyclients.tycapp.domain.Club;
import com.tyclients.tycapp.service.ClubService;
import com.tyclients.tycapp.domain.AsociadoClub;
import com.tyclients.tycapp.repository.AsociadoClubRepository;
import com.tyclients.tycapp.service.AsociadoClubService;
import com.tyclients.tycapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

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
 * REST controller for managing {@link com.tyclients.tycapp.domain.AsociadoClub}.
 */
@RestController
@RequestMapping("/api")
public class AsociadoClubResource {

    private final Logger log = LoggerFactory.getLogger(AsociadoClubResource.class);

    private static final String ENTITY_NAME = "asociadoClub";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AsociadoClubService asociadoClubService;
    
    private final AsociadoClubRepository asociadoClubRepository;
    
    private final ClubService clubService;

    public AsociadoClubResource(AsociadoClubService asociadoClubService, AsociadoClubRepository asociadoClubRepository, ClubService clubService) {
        this.asociadoClubService = asociadoClubService;
        this.asociadoClubRepository = asociadoClubRepository;
        this.clubService = clubService;
    }
    /**
     * {@code POST  /asociado-clubs} : Create a new asociadoClub.
     *
     * @param asociadoClub the asociadoClub to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new asociadoClub, or with status {@code 400 (Bad Request)} if the asociadoClub has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/asociado-clubs")
    public ResponseEntity<AsociadoClub> createAsociadoClub(@Valid @RequestBody AsociadoClub asociadoClub) throws URISyntaxException {
        log.debug("REST request to save AsociadoClub : {}", asociadoClub);
        if (asociadoClub.getId() != null) {
            throw new BadRequestAlertException("A new asociadoClub cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AsociadoClub result = asociadoClubService.save(asociadoClub);
        return ResponseEntity
            .created(new URI("/api/asociado-clubs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /asociado-clubs/:id} : Updates an existing asociadoClub.
     *
     * @param id the id of the asociadoClub to save.
     * @param asociadoClub the asociadoClub to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated asociadoClub,
     * or with status {@code 400 (Bad Request)} if the asociadoClub is not valid,
     * or with status {@code 500 (Internal Server Error)} if the asociadoClub couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/asociado-clubs/{id}")
    public ResponseEntity<AsociadoClub> updateAsociadoClub(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AsociadoClub asociadoClub
    ) throws URISyntaxException {
        log.debug("REST request to update AsociadoClub : {}, {}", id, asociadoClub);
        if (asociadoClub.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, asociadoClub.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!asociadoClubRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AsociadoClub result = asociadoClubService.update(asociadoClub);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, asociadoClub.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /asociado-clubs/:id} : Partial updates given fields of an existing asociadoClub, field will ignore if it is null
     *
     * @param id the id of the asociadoClub to save.
     * @param asociadoClub the asociadoClub to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated asociadoClub,
     * or with status {@code 400 (Bad Request)} if the asociadoClub is not valid,
     * or with status {@code 404 (Not Found)} if the asociadoClub is not found,
     * or with status {@code 500 (Internal Server Error)} if the asociadoClub couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/asociado-clubs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AsociadoClub> partialUpdateAsociadoClub(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AsociadoClub asociadoClub
    ) throws URISyntaxException {
        log.debug("REST request to partial update AsociadoClub partially : {}, {}", id, asociadoClub);
        if (asociadoClub.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, asociadoClub.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!asociadoClubRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AsociadoClub> result = asociadoClubService.partialUpdate(asociadoClub);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, asociadoClub.getId().toString())
        );
    }

    /**
     * {@code GET  /asociado-clubs} : get all the asociadoClubs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of asociadoClubs in body.
     */
    @GetMapping("/asociado-clubs")
    public ResponseEntity<List<AsociadoClub>> getAllAsociadoClubs(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of AsociadoClubs");
        Page<AsociadoClub> page = asociadoClubService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /asociado-clubs/:id} : get the "id" asociadoClub.
     *
     * @param id the id of the asociadoClub to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the asociadoClub, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/asociado-clubs/{id}")
    public ResponseEntity<AsociadoClub> getAsociadoClub(@PathVariable Long id) {
        log.debug("REST request to get AsociadoClub : {}", id);
        Optional<AsociadoClub> asociadoClub = asociadoClubService.findOne(id);
        return ResponseUtil.wrapOrNotFound(asociadoClub);
    }

    /**
     * {@code DELETE  /asociado-clubs/:id} : delete the "id" asociadoClub.
     *
     * @param id the id of the asociadoClub to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/asociado-clubs/{id}")
    public ResponseEntity<Void> deleteAsociadoClub(@PathVariable Long id) {
        log.debug("REST request to delete AsociadoClub : {}", id);
        asociadoClubService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
    
  //Obtener un asociado especifico de un club especifico
    @GetMapping("/asociado-clubs/club/{idClub}/ident/{identificador}")
    public ResponseEntity<Optional<AsociadoClub>> getAsociadoClubByIdClubAndIdentificador(@PathVariable Long idClub, @PathVariable UUID identificador) {
    	log.debug("REST request to get AsociadoClub by idClub: {}", idClub);

    	Optional<Club> club = this.clubService.findOne(idClub); 
    	
    	Optional<AsociadoClub> asociado = asociadoClubService.findAllByIdClubAndIdentificador(club, identificador);

    	return new ResponseEntity<Optional<AsociadoClub>>( asociado , HttpStatus.OK);
    }
    
  //Sumar puntos a un asociado especifico por comprar producto.
    @PatchMapping(value = "/asociado-clubs/venta/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AsociadoClub> partialUpdateAsociadoClubVenta(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AsociadoClub asociadoClub
    ) throws URISyntaxException {
        log.debug("REST request to partial update AsociadoClub partially : {}, {}", id, asociadoClub);
        if (asociadoClub.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, asociadoClub.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!asociadoClubRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        	
        Optional<AsociadoClub> asociadoAntiguo = asociadoClubService.findOne(id); //traemos al objeto que vamos a actualizar
        // sumamos la cantidad del objeto viejo con la nueva cantidad del objeto nuevo
        Long puntosActualizados = asociadoAntiguo.get().getPuntosClub() + asociadoClub.getPuntosClub(); 
        //actualizamos la cantidad del objeto nuevo
        asociadoClub.setPuntosClub(puntosActualizados);
        //modificamos al objeto en la base de datos.
        
        Optional<AsociadoClub> result = asociadoClubService.partialUpdate(asociadoClub);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, asociadoClub.getId().toString())
        );
    }
    
    //Restar puntos a un asociado especifico por canjear un producto.
    @PatchMapping(value = "/asociado-clubs/canje/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AsociadoClub> partialUpdateAsociadoClubCanje(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AsociadoClub asociadoClub
    ) throws URISyntaxException {
        log.debug("REST request to partial update AsociadoClub partially : {}, {}", id, asociadoClub);
        if (asociadoClub.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, asociadoClub.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!asociadoClubRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        
        Optional<AsociadoClub> asociadoAntiguo = asociadoClubService.findOne(id); //traemos al obejto que vamos a actualizar
        // sumamos la cantidad del objeto viejo con la nueva cantidad del objeto nuevo
        Long puntosActualizados = asociadoAntiguo.get().getPuntosClub() - asociadoClub.getPuntosClub(); 
        //actualizamos la cantidad del objeto nuevo 
        asociadoClub.setPuntosClub(puntosActualizados);
        //modificamos al objeto en la base de datos.
        
        Optional<AsociadoClub> result = asociadoClubService.partialUpdate(asociadoClub);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, asociadoClub.getId().toString())
        );
        
    }
}
