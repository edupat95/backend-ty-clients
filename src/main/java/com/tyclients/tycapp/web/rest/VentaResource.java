package com.tyclients.tycapp.web.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tyclients.tycapp.domain.Cajero;
import com.tyclients.tycapp.domain.Mesa;
import com.tyclients.tycapp.domain.ProductoVenta;
import com.tyclients.tycapp.domain.Venta;
import com.tyclients.tycapp.repository.VentaRepository;
import com.tyclients.tycapp.service.CajeroService;
import com.tyclients.tycapp.service.MesaService;
import com.tyclients.tycapp.service.ProductoMesaService;
import com.tyclients.tycapp.service.VentaService;
import com.tyclients.tycapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
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
 * REST controller for managing {@link com.tyclients.tycapp.domain.Venta}.
 */
@RestController
@RequestMapping("/api")
public class VentaResource {

    private final Logger log = LoggerFactory.getLogger(VentaResource.class);

    private static final String ENTITY_NAME = "venta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VentaService ventaService;

    private final VentaRepository ventaRepository;
    
    private final CajeroService cajeroService;
    
    private final MesaService mesaService;
    
    private final ProductoMesaService productoMesaService;
    
    public VentaResource(ProductoMesaService productoMesaService, MesaService mesaService, VentaService ventaService, VentaRepository ventaRepository, CajeroService cajeroService) {
        this.ventaService = ventaService;
        this.ventaRepository = ventaRepository;
        this.cajeroService = cajeroService;
        this.mesaService = mesaService;
        this.productoMesaService = productoMesaService;
    }

    /**
     * {@code POST  /ventas} : Create a new venta.
     *
     * @param venta the venta to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new venta, or with status {@code 400 (Bad Request)} if the venta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ventas")
    public ResponseEntity<Venta> createVenta(@Valid @RequestBody Venta venta) throws URISyntaxException {
        log.debug("REST request to save Venta : {}", venta);
        if (venta.getId() != null) {
            throw new BadRequestAlertException("A new venta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Venta result = ventaService.save(venta);
        return ResponseEntity
            .created(new URI("/api/ventas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ventas/:id} : Updates an existing venta.
     *
     * @param id the id of the venta to save.
     * @param venta the venta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated venta,
     * or with status {@code 400 (Bad Request)} if the venta is not valid,
     * or with status {@code 500 (Internal Server Error)} if the venta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ventas/{id}")
    public ResponseEntity<Venta> updateVenta(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Venta venta)
        throws URISyntaxException {
        log.debug("REST request to update Venta : {}, {}", id, venta);
        if (venta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, venta.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ventaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Venta result = ventaService.update(venta);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, venta.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ventas/:id} : Partial updates given fields of an existing venta, field will ignore if it is null
     *
     * @param id the id of the venta to save.
     * @param venta the venta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated venta,
     * or with status {@code 400 (Bad Request)} if the venta is not valid,
     * or with status {@code 404 (Not Found)} if the venta is not found,
     * or with status {@code 500 (Internal Server Error)} if the venta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ventas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Venta> partialUpdateVenta(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Venta venta
    ) throws URISyntaxException {
        log.debug("REST request to partial update Venta partially : {}, {}", id, venta);
        if (venta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, venta.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ventaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Venta> result = ventaService.partialUpdate(venta);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, venta.getId().toString())
        );
    }

    /**
     * {@code GET  /ventas} : get all the ventas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ventas in body.
     */
    @GetMapping("/ventas")
    public ResponseEntity<List<Venta>> getAllVentas(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Ventas");
        Page<Venta> page = ventaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ventas/:id} : get the "id" venta.
     *
     * @param id the id of the venta to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the venta, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ventas/{id}")
    public ResponseEntity<Venta> getVenta(@PathVariable Long id) {
        log.debug("REST request to get Venta : {}", id);
        Optional<Venta> venta = ventaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(venta);
    }

    /**
     * {@code DELETE  /ventas/:id} : delete the "id" venta.
     *
     * @param id the id of the venta to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ventas/{id}")
    public ResponseEntity<Void> deleteVenta(@PathVariable Long id) {
        log.debug("REST request to delete Venta : {}", id);
        ventaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
    
    // CREACION DE VENTA CANGE EN UN CLUB ESPECIFICO CON UN CAJERO ESPECIFICO Y CON UN ASOCIADOCLUB ESPECIFICO 
    @PostMapping("/ventas/create/canje")
    public List<ProductoVenta> CashierCreateCanje(@RequestBody JsonNode jsonNode) throws URISyntaxException {
        log.debug("REST request to save CachierCreateVenta Canje : {}", jsonNode);
        List<ProductoVenta> productosVenta = ventaService.cashierCreateCanje(jsonNode); 
        return productosVenta;
    }
    // CREACION DE VENTA EN UN CLUB ESPECIFICO CON UN CAJERO ESPECIFICO SIN ASOCIADO ESPECIFICO
    @PostMapping("/ventas/create")
    public List<ProductoVenta> CashierCreateVenta(@RequestBody JsonNode jsonNode) throws URISyntaxException {
        log.debug("REST request to save CachierCreateVenta : {}", jsonNode);
        List<ProductoVenta> productosVenta = ventaService.cashierCreateVenta(jsonNode);
        return productosVenta;
    }
    
    // CREATE VENTA A CLIENTE NO IDENTIFICADO
    @PostMapping("/ventas/create/sin_identificar")
    public List<ProductoVenta> CashierCreateVentaSinIdentificar(@RequestBody JsonNode jsonNode) throws URISyntaxException {
        log.debug("REST request to save CachierCreateVenta : {}", jsonNode);
        List<ProductoVenta> productosVenta = ventaService.cashierCreateVentaSinIdentificar(jsonNode);
        return productosVenta;
    }
    
    //CREAR UNA VENTA DE UNA MESA. ES DECIR CERRAR UNA MESA PORQUE FUE COBRADA.
    @PostMapping("/ventas/mesa/create")
    public List<ProductoVenta> CashierCreateVentaMesa(@RequestBody JsonNode jsonNode) throws URISyntaxException {
        log.debug("REST request to save CachierCreateVenta : {}", jsonNode);
        System.out.println("DATOS RECIBIDOS: " + jsonNode);
        List<ProductoVenta> productosVenta = ventaService.cashierCreateVentaSinIdentificar(jsonNode);
        System.out.println("LISTA DE PRODUCTOS: " + productosVenta);
        if(productosVenta != null) {
        	ObjectMapper obj = new ObjectMapper();
            obj.registerModule(new JavaTimeModule()); // esto es necesario para evitar un error.
        	Long mesaId = obj.convertValue(jsonNode.get("MesaId"),Long.class);
        	Optional<Mesa> mesa = mesaService.findOne(mesaId);
        	if(mesa.isPresent()) {
        		productoMesaService.deleteByMesa(mesa);
        	} else {
                return null;
        	}
        } else {
        	return null;
        }
        return productosVenta;
    }
    @PostMapping("/ventas/mesa/create/identificado")
    public List<ProductoVenta> CashierCreateVentaIdentificado(@RequestBody JsonNode jsonNode) throws URISyntaxException {
    	log.debug("REST request to save CachierCreateVenta : {}", jsonNode);
        System.out.println("DATOS RECIBIDOS: " + jsonNode);
        List<ProductoVenta> productosVenta = ventaService.cashierCreateVenta(jsonNode);
        System.out.println("LISTA DE PRODUCTOS: " + productosVenta);
        if(productosVenta != null) {
        	ObjectMapper obj = new ObjectMapper();
            obj.registerModule(new JavaTimeModule()); // esto es necesario para evitar un error.
        	Long mesaId = obj.convertValue(jsonNode.get("MesaId"),Long.class);
        	Optional<Mesa> mesa = mesaService.findOne(mesaId);
        	if(mesa.isPresent()) {
        		productoMesaService.deleteByMesa(mesa);
        	} else {
                return null;
        	}
        } else {
        	return null;
        }
        return productosVenta;
    }
    @PostMapping("/ventas/mesa/create/canje")
    public List<ProductoVenta> CashierCreateCanjeMesa(@RequestBody JsonNode jsonNode) throws URISyntaxException {
        log.debug("REST request to save CachierCreateVenta : {}", jsonNode);
        System.out.println("DATOS RECIBIDOS: " + jsonNode);
        List<ProductoVenta> productosVenta = ventaService.cashierCreateCanje(jsonNode);
        System.out.println("LISTA DE PRODUCTOS: " + productosVenta);
        if(productosVenta != null) {
        	ObjectMapper obj = new ObjectMapper();
            obj.registerModule(new JavaTimeModule()); // esto es necesario para evitar un error.
        	Long mesaId = obj.convertValue(jsonNode.get("MesaId"),Long.class);
        	Optional<Mesa> mesa = mesaService.findOne(mesaId);
        	if(mesa.isPresent()) {
        		productoMesaService.deleteByMesa(mesa);
        	} else {
                return null;
        	}
        } else {
        	return null;
        }
        return productosVenta;
    }
    
    
    
    //Get ventas de un club especifico
    @GetMapping("/ventas/club/{idClub}")
    public List<Venta> getVentaByIdClub(@PathVariable Long idClub) {
        log.debug("REST request to get Venta by idClub: {}", idClub); 
        
        List<Venta> ventas = ventaService.findByClub(idClub);
        
        return ventas;
    }
    
    @GetMapping("/ventas/club/{idClub}/desde/{fechaDesde}/hasta/{fechaHasta}")
    public List<Venta> getVentasByClubAndFechaBetween(@PathVariable Long idClub,@PathVariable Instant fechaDesde,@PathVariable Instant fechaHasta) {
        log.debug("REST request to get ventas by Club And FechaBetween : {}", idClub, fechaDesde, fechaHasta );
        
        List<Cajero> cajerosDelClub = cajeroService.findByIdClub(idClub);
        
        List<Venta> ventas = ventaService.findByFechaBetweenAndCajeros(fechaDesde, fechaHasta, cajerosDelClub); //ventaService.cashierCreateVentaSinIdentificar(jsonNode);
        
        return ventas;
    }

    @PostMapping("/ventas/buscar")
    public ResponseEntity<Venta> buscarVentaConIdentificador(@RequestBody JsonNode jsonNode) throws URISyntaxException {
        log.debug("REST request to save CachierCreateVenta : {}", jsonNode);
        System.out.println("DATOS RECIBIDOSSSSSSSSSSSSSSSS" + jsonNode);
    	ObjectMapper obj = new ObjectMapper();
        obj.registerModule(new JavaTimeModule()); // esto es necesario para evitar un error.
        UUID identificador_ticket = obj.convertValue(jsonNode.get("identificador_ticket"),UUID.class);
        Long idEntregador = obj.convertValue(jsonNode.get("idEntregador"),Long.class);
        System.out.println("IDENTIFICADOR RECIBIDO: " + identificador_ticket);
        System.out.println("idEntregador: " + idEntregador);
        
        Optional<Venta> venta = ventaService.findByIdentificador(idEntregador,identificador_ticket);
        
        return ResponseUtil.wrapOrNotFound(venta);
    }
    @PutMapping("/ventas/entregar")
    public ResponseEntity<Venta> entregarVenta(@RequestBody JsonNode jsonNode) throws URISyntaxException {
        log.debug("REST request to save CachierCreateVenta : {}", jsonNode);
        System.out.println("DATOS RECIBIDOSSSSSSSSSSSSSSSS" + jsonNode);
    	ObjectMapper obj = new ObjectMapper();
        obj.registerModule(new JavaTimeModule()); // esto es necesario para evitar un error.
        Venta venta= obj.convertValue(jsonNode.get("Venta"),Venta.class);
        Long idEntregador = obj.convertValue(jsonNode.get("idEntregador"),Long.class);
        
        Optional<Venta> ventaNewState = ventaService.entregarVenta(venta,idEntregador);
        return ResponseUtil.wrapOrNotFound(ventaNewState);
    }
}
