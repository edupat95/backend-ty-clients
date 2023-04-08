package com.tyclients.tycapp.web.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tyclients.tycapp.domain.Club;
import com.tyclients.tycapp.domain.Documento;
import com.tyclients.tycapp.domain.Mesa;
import com.tyclients.tycapp.domain.Producto;
import com.tyclients.tycapp.domain.ProductoMesa;
import com.tyclients.tycapp.domain.Registrador;
import com.tyclients.tycapp.repository.ProductoMesaRepository;
import com.tyclients.tycapp.service.MesaService;
import com.tyclients.tycapp.service.ProductoMesaService;
import com.tyclients.tycapp.service.ProductoService;
import com.tyclients.tycapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
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
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.tyclients.tycapp.domain.ProductoMesa}.
 */
@RestController
@RequestMapping("/api")
public class ProductoMesaResource {

    private final Logger log = LoggerFactory.getLogger(ProductoMesaResource.class);

    private static final String ENTITY_NAME = "productoMesa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductoMesaService productoMesaService;

    private final ProductoMesaRepository productoMesaRepository;
    
    private final MesaService mesaService;
    
    private final ProductoService productoService;
    public ProductoMesaResource(ProductoService productoService, MesaService mesaService, ProductoMesaService productoMesaService, ProductoMesaRepository productoMesaRepository) {
        this.productoMesaService = productoMesaService;
        this.productoMesaRepository = productoMesaRepository;
        this.mesaService = mesaService;
        this.productoService = productoService;
    }

    /**
     * {@code POST  /producto-mesas} : Create a new productoMesa.
     *
     * @param productoMesa the productoMesa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productoMesa, or with status {@code 400 (Bad Request)} if the productoMesa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/producto-mesas")
    public ResponseEntity<ProductoMesa> createProductoMesa(@Valid @RequestBody ProductoMesa productoMesa) throws URISyntaxException {
        log.debug("REST request to save ProductoMesa : {}", productoMesa);
        if (productoMesa.getId() != null) {
            throw new BadRequestAlertException("A new productoMesa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductoMesa result = productoMesaService.save(productoMesa);
        return ResponseEntity
            .created(new URI("/api/producto-mesas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /producto-mesas/:id} : Updates an existing productoMesa.
     *
     * @param id the id of the productoMesa to save.
     * @param productoMesa the productoMesa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productoMesa,
     * or with status {@code 400 (Bad Request)} if the productoMesa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productoMesa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/producto-mesas/{id}")
    public ResponseEntity<ProductoMesa> updateProductoMesa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProductoMesa productoMesa
    ) throws URISyntaxException {
        log.debug("REST request to update ProductoMesa : {}, {}", id, productoMesa);
        if (productoMesa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productoMesa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productoMesaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductoMesa result = productoMesaService.update(productoMesa);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productoMesa.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /producto-mesas/:id} : Partial updates given fields of an existing productoMesa, field will ignore if it is null
     *
     * @param id the id of the productoMesa to save.
     * @param productoMesa the productoMesa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productoMesa,
     * or with status {@code 400 (Bad Request)} if the productoMesa is not valid,
     * or with status {@code 404 (Not Found)} if the productoMesa is not found,
     * or with status {@code 500 (Internal Server Error)} if the productoMesa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/producto-mesas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProductoMesa> partialUpdateProductoMesa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProductoMesa productoMesa
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductoMesa partially : {}, {}", id, productoMesa);
        if (productoMesa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productoMesa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productoMesaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductoMesa> result = productoMesaService.partialUpdate(productoMesa);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productoMesa.getId().toString())
        );
    }

    /**
     * {@code GET  /producto-mesas} : get all the productoMesas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productoMesas in body.
     */
    @GetMapping("/producto-mesas")
    public ResponseEntity<List<ProductoMesa>> getAllProductoMesas(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ProductoMesas");
        Page<ProductoMesa> page = productoMesaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /producto-mesas/:id} : get the "id" productoMesa.
     *
     * @param id the id of the productoMesa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productoMesa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/producto-mesas/{id}")
    public ResponseEntity<ProductoMesa> getProductoMesa(@PathVariable Long id) {
        log.debug("REST request to get ProductoMesa : {}", id);
        Optional<ProductoMesa> productoMesa = productoMesaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productoMesa);
    }

    /**
     * {@code DELETE  /producto-mesas/:id} : delete the "id" productoMesa.
     *
     * @param id the id of the productoMesa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/producto-mesas/{id}")
    public ResponseEntity<Void> deleteProductoMesa(@PathVariable Long id) {
        log.debug("REST request to delete ProductoMesa : {}", id);
        productoMesaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
    
    @DeleteMapping("/producto-mesas/mesa/{idMesa}")
    public ResponseEntity<Void> deleteProductoMesaInMesa(@PathVariable Long idMesa) {
        log.debug("REST request to delete ProductoMesa : {}", idMesa);
        Optional<Mesa> mesa = mesaService.findOne(idMesa);
        if(mesa.isPresent()){
        	productoMesaService.deleteByMesa(mesa);
        	return ResponseEntity
                    .noContent()
                    .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, idMesa.toString()))
                    .build();
        } else {
        	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Mesa no encontrada");
        }
    }
    //LISTAR PRODUCTOSMESA DE UN CLUB ESPECIFICO
    @GetMapping("/producto-mesas/mesa/{idMesa}")
    public ResponseEntity<List<ProductoMesa>> getAllMesasByClub(@PathVariable Long idMesa) {
        log.debug("REST request to get a page of Productos");
        Optional<Mesa> mesa = mesaService.findOne(idMesa);
        if(mesa.isPresent()) {
        	List<ProductoMesa> listProductoMesas = productoMesaService.findByMesa(mesa.get());
        	return new ResponseEntity<List<ProductoMesa>>( listProductoMesas, HttpStatus.OK);
        }{
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/producto-mesas/agregar")
    public ResponseEntity<ProductoMesa> createProductoMesaByMesaAndProducto(@RequestBody JsonNode jsonNode) throws URISyntaxException {
        log.debug("REST request to save ProductoMesa : {}", jsonNode);
        System.out.println("DATOS RECIBIDOS: " + jsonNode);
        
        ObjectMapper obj = new ObjectMapper();
        obj.registerModule(new JavaTimeModule()); // esto es necesario para evitar un error.
        
        Long idProducto = obj.convertValue(jsonNode.get("idProducto"),Long.class); 
        Long idMesa = obj.convertValue(jsonNode.get("idMesa"),Long.class);
        
        Optional<Producto> producto = productoService.findOne(idProducto);
        Optional<Mesa> mesa = mesaService.findOne(idMesa);
        
        if(producto.isPresent() && mesa.isPresent()) {
        	//primero buscamos si ya existe un productoMesa con el producto y la mesa
        	Optional<ProductoMesa> productoMesaExist = productoMesaService.findByProductoAndMesa(producto.get(), mesa.get());
        	if(productoMesaExist.isPresent()) {
        		//System.out.println("Existe el producto mesa, hay que sumarle 1 a la cantidad y editat el totalgasto");
        		Long nuevaCantidad = productoMesaExist.get().getCantidad() + 1;
        		productoMesaExist.get().setCostoTotal(nuevaCantidad * producto.get().getPrecio());
        		productoMesaExist.get().setCostoTotalPuntos(nuevaCantidad * producto.get().getPrecioPuntos());
        		productoMesaExist.get().setCantidad(nuevaCantidad);
        		productoMesaExist.get().setEstado(true);
        		productoMesaExist.get().setUpdatedDate(Instant.now());
            	productoMesaExist.get().setMesa(mesa.get());
            	productoMesaExist.get().setProducto(producto.get());
            	productoMesaService.update(productoMesaExist.get());
            	return ResponseEntity.ok().body(productoMesaExist.get());
        	} else {
        		ProductoMesa productoMesa = new ProductoMesa();
        		//System.out.println("No existe entonces lo creamos");
            	productoMesa.setCostoTotal(producto.get().getPrecio());
            	productoMesa.setCostoTotalPuntos(producto.get().getPrecioPuntos());
            	productoMesa.setCantidad(1L);
            	productoMesa.setEstado(true);
            	productoMesa.setCreatedDate(Instant.now());
            	productoMesa.setUpdatedDate(Instant.now());
            	productoMesa.setMesa(mesa.get());
            	productoMesa.setProducto(producto.get());
            	productoMesaService.save(productoMesa);
            	 return ResponseEntity.created(new URI("/producto-mesas/" + productoMesa.getId())).body(productoMesa);        	}
        } else {
        	System.out.println("NO EXISTE LA MESA O EL PRODUCTO");
        	return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/producto-mesas/quitar")
    public ResponseEntity<ProductoMesa> removeProductoMesaByMesaAndProducto(@RequestBody JsonNode jsonNode) throws URISyntaxException {
        log.debug("REST request to save ProductoMesa : {}", jsonNode);
        System.out.println("DATOS RECIBIDOS: " + jsonNode);
        
        ObjectMapper obj = new ObjectMapper();
        obj.registerModule(new JavaTimeModule()); // esto es necesario para evitar un error.
        
        Long idProducto = obj.convertValue(jsonNode.get("idProducto"),Long.class); 
        Long idMesa = obj.convertValue(jsonNode.get("idMesa"),Long.class);
        
        Optional<Producto> producto = productoService.findOne(idProducto);
        Optional<Mesa> mesa = mesaService.findOne(idMesa);
        
        if(producto.isPresent() && mesa.isPresent()) {
        	//primero buscamos si ya existe un productoMesa con el producto y la mesa
        	Optional<ProductoMesa> productoMesaExist = productoMesaService.findByProductoAndMesa(producto.get(), mesa.get());
        	if(productoMesaExist.isPresent()) {
        		//System.out.println("Existe el producto mesa, hay que sumarle 1 a la cantidad y editat el totalgasto");
        		if(productoMesaExist.get().getCantidad() > 1) {
        			Long nuevaCantidad = productoMesaExist.get().getCantidad() - 1;
        			productoMesaExist.get().setCostoTotal(nuevaCantidad * producto.get().getPrecio());
        			productoMesaExist.get().setCostoTotalPuntos(nuevaCantidad * producto.get().getPrecioPuntos());
        			productoMesaExist.get().setCantidad(nuevaCantidad);
        			productoMesaExist.get().setEstado(true);
        			productoMesaExist.get().setUpdatedDate(Instant.now());
            		productoMesaExist.get().setMesa(mesa.get());
            		productoMesaExist.get().setProducto(producto.get());
            		productoMesaService.update(productoMesaExist.get());
            		return ResponseEntity.ok().body(productoMesaExist.get());
            	} else {
            		productoMesaService.delete(productoMesaExist.get().getId());
            		return ResponseEntity.ok().body(productoMesaExist.get());
            	}
        	} else {
        		ProductoMesa productoMesa = new ProductoMesa();
        		//System.out.println("No existe entonces lo creamos");
            	productoMesa.setCostoTotal(producto.get().getPrecio());
            	productoMesa.setCostoTotalPuntos(producto.get().getPrecioPuntos());
            	productoMesa.setCantidad(1L);
            	productoMesa.setEstado(true);
            	productoMesa.setCreatedDate(Instant.now());
            	productoMesa.setUpdatedDate(Instant.now());
            	productoMesa.setMesa(mesa.get());
            	productoMesa.setProducto(producto.get());
            	productoMesaService.save(productoMesa);
            	 return ResponseEntity.created(new URI("/producto-mesas/" + productoMesa.getId())).body(productoMesa);        	}
        } else {
        	System.out.println("NO EXISTE LA MESA O EL PRODUCTO");
        	return ResponseEntity.notFound().build();
        }
    }
}
