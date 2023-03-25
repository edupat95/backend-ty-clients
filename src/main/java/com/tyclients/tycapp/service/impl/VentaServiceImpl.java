package com.tyclients.tycapp.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tyclients.tycapp.repository.ClubRepository;
import com.tyclients.tycapp.repository.FormaPagoRepository;
import com.tyclients.tycapp.repository.ProductoVentaRepository;
import com.tyclients.tycapp.service.AsociadoClubService;
import com.tyclients.tycapp.service.ClubService;
import com.tyclients.tycapp.service.ProductoService;
import com.tyclients.tycapp.domain.AsociadoClub;
import com.tyclients.tycapp.domain.Cajero;
import com.tyclients.tycapp.domain.Club;
import com.tyclients.tycapp.domain.FormaPago;
import com.tyclients.tycapp.domain.Producto;
import com.tyclients.tycapp.domain.ProductoVenta;
import com.tyclients.tycapp.domain.Venta;
import com.tyclients.tycapp.repository.VentaRepository;
import com.tyclients.tycapp.service.VentaService;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Venta}.
 */
@Service
@Transactional
public class VentaServiceImpl implements VentaService {

    private final Logger log = LoggerFactory.getLogger(VentaServiceImpl.class);

    private final VentaRepository ventaRepository;

    private final ClubRepository clubRepository;
    
    private final AsociadoClubService asociadoClubService;
    
    private final ProductoVentaRepository productoVentaRepository;
    
    private final ProductoService productoService;
    
    private final FormaPagoRepository formaPagoRepository;
        
    public VentaServiceImpl(FormaPagoRepository formaPagoRepository, ProductoService productoService,ProductoVentaRepository productoVentaRepository, AsociadoClubService asociadoClubService, VentaRepository ventaRepository, ClubRepository clubRepository) {
        this.ventaRepository = ventaRepository;
        this.clubRepository = clubRepository;
        this.asociadoClubService = asociadoClubService;
        this.productoVentaRepository = productoVentaRepository;
        this.productoService = productoService;
        this.formaPagoRepository = formaPagoRepository;
    }
    @Override
    public Venta save(Venta venta) {
        log.debug("Request to save Venta : {}", venta);
        return ventaRepository.save(venta);
    }

    @Override
    public Venta update(Venta venta) {
        log.debug("Request to save Venta : {}", venta);
        return ventaRepository.save(venta);
    }

    @Override
    public Optional<Venta> partialUpdate(Venta venta) {
        log.debug("Request to partially update Venta : {}", venta);

        return ventaRepository
            .findById(venta.getId())
            .map(existingVenta -> {
                if (venta.getCostoTotal() != null) {
                    existingVenta.setCostoTotal(venta.getCostoTotal());
                }
                if (venta.getCostoTotalPuntos() != null) {
                    existingVenta.setCostoTotalPuntos(venta.getCostoTotalPuntos());
                }
                if (venta.getIdentificadorTicket() != null) {
                    existingVenta.setIdentificadorTicket(venta.getIdentificadorTicket());
                }
                if (venta.getEntregado() != null) {
                    existingVenta.setEntregado(venta.getEntregado());
                }
                if (venta.getCreatedDate() != null) {
                    existingVenta.setCreatedDate(venta.getCreatedDate());
                }
                if (venta.getUpdatedDate() != null) {
                    existingVenta.setUpdatedDate(venta.getUpdatedDate());
                }

                return existingVenta;
            })
            .map(ventaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Venta> findAll(Pageable pageable) {
        log.debug("Request to get all Ventas");
        return ventaRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Venta> findOne(Long id) {
        log.debug("Request to get Venta : {}", id);
        return ventaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Venta : {}", id);
        ventaRepository.deleteById(id);
    }

	@Override
	public List<Venta> findByClub(Long idClub) {
		// TODO Auto-generated method stub
		//1° Conseguir el objeto club
		Optional<Club> club = clubRepository.findById(idClub);
		//2° Traer las ventas 
		List<Venta> allVentas = ventaRepository.findAll();
		List<Venta> ventas = new ArrayList<Venta>();
		for(Venta venta : allVentas) {
			if(venta.getCajero().getTrabajador().getClub().getId() == club.get().getId()) {
				ventas.add(venta);
			}
		}
		
		return ventas;
	}

	@Override
	public List<ProductoVenta> cashierCreateVenta(JsonNode jsonNode) {
		log.debug("REST request to save CachierCreateVenta : {}", jsonNode);
        ObjectMapper obj = new ObjectMapper();
        obj.registerModule(new JavaTimeModule()); // esto es necesario para evitar un error.
        Venta venta = obj.convertValue(jsonNode.get("Venta"),Venta.class);
        venta.setCreatedDate(Instant.now());
        venta.setEntregado(false);
        venta.setIdentificadorTicket(UUID.randomUUID());
        Venta ventaResult = this.save(venta);
       
        Optional<AsociadoClub> asociadoClub = asociadoClubService.findByIdAsociado(venta.getAsociado());
        List<JsonNode> productos = jsonNode.get("Productos").findParents("id");         
        List<ProductoVenta> productosVenta = new ArrayList<ProductoVenta>();

        Long costoTotalVentaAux = 0L;
        Long recompensaTotalAux = 0L;
        
        for(JsonNode json : productos) {
        	Long id = Long.parseLong(json.get("id").toString());
        	Long cantidad = Long.parseLong(json.get("cantidad").toString());
        	Optional<Producto>producto = productoService.findOne(id);
        	ProductoVenta pv = new ProductoVenta();
        	recompensaTotalAux = recompensaTotalAux  + (producto.get().getPuntosRecompensa() * cantidad);
        	costoTotalVentaAux = costoTotalVentaAux + (producto.get().getPrecio()*cantidad);
        	
        	pv.setCostoTotal(producto.get().getPrecio()*cantidad);
        	pv.setCostoTotalPuntos(null);
        	pv.setVenta(ventaResult);
        	pv.setProducto(producto.get());
        	pv.setCantidad(cantidad);
        	productoVentaRepository.save(pv);
        	productosVenta.add(pv);        	
        	//System.out.println("----------------------->" + producto.toString());
        }
        venta.setCostoTotal(costoTotalVentaAux);
        this.update(venta);
        asociadoClub.get().setPuntosClub(asociadoClub.get().getPuntosClub() + recompensaTotalAux);
        asociadoClubService.update(asociadoClub.get());
        return productosVenta;
	}
	
	@Override
	public List<ProductoVenta> cashierCreateCanje(JsonNode jsonNode) {
		ObjectMapper obj = new ObjectMapper();
        obj.registerModule(new JavaTimeModule()); // esto es necesario para evitar un error.
        Venta venta = obj.convertValue(jsonNode.get("Venta"),Venta.class);
        venta.setCreatedDate(Instant.now());
        venta.setEntregado(false);
        venta.setIdentificadorTicket(UUID.randomUUID());
        Venta ventaResult = this.save(venta);
       
        Optional<AsociadoClub> asociadoClub = asociadoClubService.findByIdAsociado(venta.getAsociado());
        //System.out.println("-------------------------idAsociado->" + asociadoClub);
        List<JsonNode> productos = jsonNode.get("Productos").findParents("id");         
        List<ProductoVenta> productosVenta = new ArrayList<ProductoVenta>();

        Long costoTotalPuntosVentaAux = 0L;
        
        for(JsonNode json : productos) {
        	Long id = Long.parseLong(json.get("id").toString());
        	Long cantidad = Long.parseLong(json.get("cantidad").toString());
        	Optional<Producto>producto = productoService.findOne(id);
        	ProductoVenta pv = new ProductoVenta();
        	
        	costoTotalPuntosVentaAux = costoTotalPuntosVentaAux + (producto.get().getCostoPuntos()*cantidad);
        	
        	pv.setCostoTotal(null);
        	pv.setCostoTotalPuntos(producto.get().getCostoPuntos()*cantidad);
        	pv.setVenta(ventaResult);
        	pv.setProducto(producto.get());
        	pv.setCantidad(cantidad);
        	productoVentaRepository.save(pv);
        	productosVenta.add(pv);
        }
        venta.setCostoTotalPuntos(costoTotalPuntosVentaAux);
        this.update(venta);
        asociadoClub.get().setPuntosClub(asociadoClub.get().getPuntosClub() - costoTotalPuntosVentaAux);
        asociadoClubService.update(asociadoClub.get());
        return productosVenta;
	}
	@Override
	public List<ProductoVenta> cashierCreateVentaSinIdentificar(JsonNode jsonNode) {
		ObjectMapper obj = new ObjectMapper();
        obj.registerModule(new JavaTimeModule()); // esto es necesario para evitar un error.
        System.out.println("EL OBJETO RECIBIDO ES: " + jsonNode.toString());
        Venta venta = obj.convertValue(jsonNode.get("Venta"),Venta.class);
        Long formaPagoId = obj.convertValue(jsonNode.get("FormaPagoId"),Long.class);
        Optional<FormaPago> formaPago = formaPagoRepository.findById(formaPagoId);
        System.out.println("VENTAAAAAA QUE RECIVEEE -> " + venta.toString());
        venta.setFormaPago(formaPago.get());
        venta.setCreatedDate(Instant.now());
        venta.setEntregado(false);
        venta.setIdentificadorTicket(UUID.randomUUID());
        
        Venta ventaResult = this.save(venta);
       
        System.out.println("OBJETOOOOOOO --->> " + ventaResult.toString());
        
        List<JsonNode> productos = jsonNode.get("Productos").findParents("id");         
        List<ProductoVenta> productosVenta = new ArrayList<ProductoVenta>();
        
        System.out.println("-------------------------PRODUCTOS->" + productos);
        
        Long costoTotalVentaAux = 0L;
        
        for(JsonNode json : productos) {
        	Long id = Long.parseLong(json.get("id").toString());
        	Long cantidad = Long.parseLong(json.get("cantidad").toString());
        	Optional<Producto>producto = productoService.findOne(id);
        	ProductoVenta pv = new ProductoVenta();
        	
        	costoTotalVentaAux = costoTotalVentaAux + (producto.get().getPrecio()*cantidad);
        	
        	pv.setCostoTotal(producto.get().getPrecio()*cantidad);
        	pv.setCostoTotalPuntos(null);
        	pv.setVenta(ventaResult);
        	pv.setProducto(producto.get());
        	pv.setCantidad(cantidad);
        	productoVentaRepository.save(pv);
        	productosVenta.add(pv);
        	//System.out.println("----------------------->" + producto.toString());
        }
        venta.setCostoTotal(costoTotalVentaAux);
        this.update(venta);
        /*
        asociadoClub.get().setPuntosClub(asociadoClub.get().getPuntosClub() - costoTotalPuntosVentaAux);
        asociadoClubService.update(asociadoClub.get());
        return productosVenta;
        */
        return productosVenta;
	}
	@Override
	public List<Venta> findByFechaBetweenAndCajeros(Instant fechaDesde, Instant fechaHasta, List<Cajero> cajerosDelClub) {
		
		List<Venta> ventas = new ArrayList<Venta>();
		
		for(Cajero cajero : cajerosDelClub) {
			Optional<List<Venta>> ventasDelCajero = ventaRepository.findByCreatedDateBetweenAndCajero(fechaDesde, fechaHasta, cajero);
			if(ventasDelCajero.isPresent()) {
				ventas.addAll(ventasDelCajero.get());
			}
		}
		
		// TODO Auto-generated method stub
		return ventas;
	}
	
}
