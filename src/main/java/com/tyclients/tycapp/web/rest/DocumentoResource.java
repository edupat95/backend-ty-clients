package com.tyclients.tycapp.web.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tyclients.tycapp.domain.AsociadoClub;
import com.tyclients.tycapp.domain.Club;
import com.tyclients.tycapp.domain.Registrador;
import com.tyclients.tycapp.domain.Documento;
import com.tyclients.tycapp.repository.DocumentoRepository;
import com.tyclients.tycapp.service.ClubService;
import com.tyclients.tycapp.service.DocumentoService;
import com.tyclients.tycapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
import java.util.UUID;
/**
 * REST controller for managing {@link com.tyclients.tycapp.domain.Documento}.
 */
@RestController
@RequestMapping("/api")
public class DocumentoResource {

    private final Logger log = LoggerFactory.getLogger(DocumentoResource.class);

    private static final String ENTITY_NAME = "documento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentoService documentoService;

    private final DocumentoRepository documentoRepository;
    
    private final ClubService clubService;

    public DocumentoResource(ClubService clubService, DocumentoService documentoService, DocumentoRepository documentoRepository) {
        this.documentoService = documentoService;
        this.documentoRepository = documentoRepository;
        this.clubService = clubService;
    }

    /**
     * {@code POST  /documentos} : Create a new documento.
     *
     * @param documento the documento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documento, or with status {@code 400 (Bad Request)} if the documento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/documentos")
    public ResponseEntity<Documento> createDocumento(@RequestBody Documento documento) throws URISyntaxException {
        log.debug("REST request to save Documento : {}", documento);
        if (documento.getId() != null) {
            throw new BadRequestAlertException("A new documento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Documento result = documentoService.save(documento);
        return ResponseEntity
            .created(new URI("/api/documentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /documentos/:id} : Updates an existing documento.
     *
     * @param id the id of the documento to save.
     * @param documento the documento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documento,
     * or with status {@code 400 (Bad Request)} if the documento is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/documentos/{id}")
    public ResponseEntity<Documento> updateDocumento(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Documento documento
    ) throws URISyntaxException {
        log.debug("REST request to update Documento : {}, {}", id, documento);
        if (documento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documento.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Documento result = documentoService.update(documento);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documento.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /documentos/:id} : Partial updates given fields of an existing documento, field will ignore if it is null
     *
     * @param id the id of the documento to save.
     * @param documento the documento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documento,
     * or with status {@code 400 (Bad Request)} if the documento is not valid,
     * or with status {@code 404 (Not Found)} if the documento is not found,
     * or with status {@code 500 (Internal Server Error)} if the documento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/documentos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Documento> partialUpdateDocumento(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Documento documento
    ) throws URISyntaxException {
        log.debug("REST request to partial update Documento partially : {}, {}", id, documento);
        if (documento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documento.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Documento> result = documentoService.partialUpdate(documento);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documento.getId().toString())
        );
    }

    /**
     * {@code GET  /documentos} : get all the documentos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentos in body.
     */
    @GetMapping("/documentos")
    public ResponseEntity<List<Documento>> getAllDocumentos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Documentos");
        Page<Documento> page = documentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /documentos/:id} : get the "id" documento.
     *
     * @param id the id of the documento to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documento, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/documentos/{id}")
    public ResponseEntity<Documento> getDocumento(@PathVariable Long id) {
        log.debug("REST request to get Documento : {}", id);
        Optional<Documento> documento = documentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documento);
    }

    /**
     * {@code DELETE  /documentos/:id} : delete the "id" documento.
     *
     * @param id the id of the documento to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/documentos/{id}")
    public ResponseEntity<Void> deleteDocumento(@PathVariable Long id) {
        log.debug("REST request to delete Documento : {}", id);
        documentoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
    
    //ASOCIAR CLIENTE A UN CLUB CON SU DNI
    @PostMapping("/documentos/create/asociado-club")
    public AsociadoClub RegistradorCreateAsociadoClub(@RequestBody JsonNode jsonNode) throws URISyntaxException {
        log.debug("REST request to save Documento and AsociadoClub with Registrador: {}", jsonNode);
        
        ObjectMapper obj = new ObjectMapper();
        obj.registerModule(new JavaTimeModule()); // esto es necesario para evitar un error.
        Documento documento = obj.convertValue(jsonNode.get("Documento"),Documento.class); 
        Registrador registrador = obj.convertValue(jsonNode.get("Registrador"),Registrador.class);
        Long idClub = obj.convertValue(jsonNode.get("idClub"),Long.class); 
        Optional<Club> club = clubService.findOne(idClub); 
        if(club.isPresent()) {
        	AsociadoClub asociadoClub = documentoService.createAsociadoClubByDoducmento(documento,registrador, club.get());
        	return asociadoClub;
        }
        return null;   
    }
    
    //VINCULAR AL CLIENTE A UN TARJETA
    @PostMapping("/documentos/link/asociado-club")
    public AsociadoClub RegistradorLinkAsociadoClub(@RequestBody JsonNode jsonNode) throws URISyntaxException {
        log.debug("REST request to save Documento and AsociadoClub with Registrador: {}", jsonNode);
        
        ObjectMapper obj = new ObjectMapper();
        obj.registerModule(new JavaTimeModule()); // esto es necesario para evitar un error.
        Documento documento = obj.convertValue(jsonNode.get("Documento"),Documento.class); 
        Registrador registrador = obj.convertValue(jsonNode.get("Registrador"),Registrador.class);
        Long idClub = obj.convertValue(jsonNode.get("idClub"),Long.class); 
        UUID identificador = obj.convertValue(jsonNode.get("identificador"), UUID.class);
        
        
        Optional<Club> club = clubService.findOne(idClub);
        
        if(club.isPresent()) {
        	AsociadoClub asociadoClub = documentoService.linkAsociadoClubByDoducmento(documento,registrador, club.get(), identificador);
        	return asociadoClub;
        }
        return null;   
    }
}
