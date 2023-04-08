package com.tyclients.tycapp.service.impl;

import com.tyclients.tycapp.service.CajaService;
import com.tyclients.tycapp.service.CajeroService;
import com.tyclients.tycapp.service.ClubService;
import com.tyclients.tycapp.service.ProductoCajaService;
import com.tyclients.tycapp.domain.Caja;
import com.tyclients.tycapp.domain.Cajero;
import com.tyclients.tycapp.domain.Club;
import com.tyclients.tycapp.domain.ProductoCaja;
import com.tyclients.tycapp.domain.Producto;
import com.tyclients.tycapp.repository.ProductoRepository;
import com.tyclients.tycapp.service.ProductoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Producto}.
 */
@Service
@Transactional
public class ProductoServiceImpl implements ProductoService {

    private final Logger log = LoggerFactory.getLogger(ProductoServiceImpl.class);

    private final ProductoRepository productoRepository;
    
    private final CajaService cajaService;
    
    private final CajeroService cajeroService;
    
    private final ProductoCajaService productoCajaService;
    
    private final ClubService clubService;

    public ProductoServiceImpl(ClubService clubService, ProductoRepository productoRepository, CajaService cajaService, CajeroService cajeroService, ProductoCajaService productoCajaService) {
        this.productoRepository = productoRepository;
        this.cajaService = cajaService;
        this.cajeroService = cajeroService;
        this.productoCajaService = productoCajaService;
        this.clubService = clubService;
    }

    @Override
    public Producto save(Producto producto) {
        log.debug("Request to save Producto : {}", producto);
        return productoRepository.save(producto);
    }

    @Override
    public Producto update(Producto producto) {
        log.debug("Request to save Producto : {}", producto);
        return productoRepository.save(producto);
    }

    @Override
    public Optional<Producto> partialUpdate(Producto producto) {
        log.debug("Request to partially update Producto : {}", producto);

        return productoRepository
            .findById(producto.getId())
            .map(existingProducto -> {
                if (producto.getNombre() != null) {
                    existingProducto.setNombre(producto.getNombre());
                }
                if (producto.getPrecio() != null) {
                    existingProducto.setPrecio(producto.getPrecio());
                }
                if (producto.getPrecioPuntos() != null) {
                    existingProducto.setPrecioPuntos(producto.getPrecioPuntos());
                }
                if (producto.getPuntosRecompensa() != null) {
                    existingProducto.setPuntosRecompensa(producto.getPuntosRecompensa());
                }
                if (producto.getDescripcion() != null) {
                    existingProducto.setDescripcion(producto.getDescripcion());
                }
                if (producto.getEstado() != null) {
                    existingProducto.setEstado(producto.getEstado());
                }
                if (producto.getCreatedDate() != null) {
                    existingProducto.setCreatedDate(producto.getCreatedDate());
                }
                if (producto.getUpdatedDate() != null) {
                    existingProducto.setUpdatedDate(producto.getUpdatedDate());
                }

                return existingProducto;
            })
            .map(productoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Producto> findAll(Pageable pageable) {
        log.debug("Request to get all Productos");
        return productoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Producto> findOne(Long id) {
        log.debug("Request to get Producto : {}", id);
        return productoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Producto : {}", id);
        productoRepository.deleteById(id);
    }
    
    @Override
	public List<Producto> findByEstado(boolean estado) {
		log.debug("Request to get Producto by estado: {}", estado);
        return productoRepository.findByEstado(estado);
	}

	@Override
	public List<Producto> findByIdCajero(Long idCajero) {
		// TODO Auto-generated method stub
		//1° Traemos el objeto cajero.
		Optional<Cajero> cajero = cajeroService.findOne(idCajero);
		//2° Buscamos la caja con el id de caja.
		Optional<Caja> caja = cajaService.findOne(cajero.get().getCaja().getId());
		//3° Buscamos los productos dentro de la caja.
		List<ProductoCaja> productosCaja = productoCajaService.findByIdCaja(caja);
		//4° Creamos la lista de productos
		List<Producto> productos = new ArrayList<Producto>();
		 for(ProductoCaja productoCaja : productosCaja) {
	        //System.out.println("Producto en producto caja------------------->" + productoCaja.getIdProducto());
	        if(productoCaja.getProducto().getEstado() == true) {
	        	productos.add(productoCaja.getProducto());
	        }
		 }
		
		return productos;
	}

    @Override
    public List<Producto> findByIdClub(Long idClub) {
        // TODO Auto-generated method stub
    	
    	Optional<Club> club = clubService.findOne(idClub);
    	if(club != null) {
    		List<Producto> productosClub = productoRepository.findByClubAndEstado(club.get(), true);
            return productosClub;
    	} else {
    		return null;
    	}
        
    }
}
