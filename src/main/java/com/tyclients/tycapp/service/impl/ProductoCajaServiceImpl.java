																																																																																																									package com.tyclients.tycapp.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tyclients.tycapp.domain.Caja;
import com.tyclients.tycapp.domain.Producto;
import com.tyclients.tycapp.domain.ProductoCaja;
import com.tyclients.tycapp.domain.ProductoVenta;
import com.tyclients.tycapp.domain.Venta;
import com.tyclients.tycapp.repository.CajaRepository;
import com.tyclients.tycapp.repository.ProductoCajaRepository;
import com.tyclients.tycapp.repository.ProductoRepository;
import com.tyclients.tycapp.service.CajaService;
import com.tyclients.tycapp.service.ProductoCajaService;
import com.tyclients.tycapp.service.ProductoService;

import java.time.Instant;
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
 * Service Implementation for managing {@link ProductoCaja}.
 */
@Service
@Transactional
public class ProductoCajaServiceImpl implements ProductoCajaService {

    private final Logger log = LoggerFactory.getLogger(ProductoCajaServiceImpl.class);

    private final ProductoCajaRepository productoCajaRepository;
    
    private final ProductoRepository productoRepository;
    
    private final CajaRepository cajaRepository;

    public ProductoCajaServiceImpl(CajaRepository cajaRepository, ProductoRepository productoRepository, ProductoCajaRepository productoCajaRepository) {
        this.productoCajaRepository = productoCajaRepository;
        this.productoRepository = productoRepository;
        this.cajaRepository = cajaRepository;
    }

    @Override
    public ProductoCaja save(ProductoCaja productoCaja) {
        log.debug("Request to save ProductoCaja : {}", productoCaja);
        return productoCajaRepository.save(productoCaja);
    }

    @Override
    public ProductoCaja update(ProductoCaja productoCaja) {
        log.debug("Request to save ProductoCaja : {}", productoCaja);
        return productoCajaRepository.save(productoCaja);
    }

    @Override
    public Optional<ProductoCaja> partialUpdate(ProductoCaja productoCaja) {
        log.debug("Request to partially update ProductoCaja : {}", productoCaja);

        return productoCajaRepository
            .findById(productoCaja.getId())
            .map(existingProductoCaja -> {
                if (productoCaja.getCantidad() != null) {
                    existingProductoCaja.setCantidad(productoCaja.getCantidad());
                }
                if (productoCaja.getCreatedDate() != null) {
                    existingProductoCaja.setCreatedDate(productoCaja.getCreatedDate());
                }
                if (productoCaja.getUpdatedDate() != null) {
                    existingProductoCaja.setUpdatedDate(productoCaja.getUpdatedDate());
                }

                return existingProductoCaja;
            })
            .map(productoCajaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductoCaja> findAll(Pageable pageable) {
        log.debug("Request to get all ProductoCajas");
        return productoCajaRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductoCaja> findOne(Long id) {
        log.debug("Request to get ProductoCaja : {}", id);
        return productoCajaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductoCaja : {}", id);
        productoCajaRepository.deleteById(id);
    }
    
    @Override
	public List<ProductoCaja> findByIdCaja(Optional<Caja> caja) {
		// TODO Auto-generated method stub
		List<ProductoCaja> productosCaja = productoCajaRepository.findByCaja(caja);
		/*for(ProductoCaja productoCaja : productosCaja) {	
			System.out.println("PRODUCTOCTOS CAJA" + productoCaja.getProducto().getEstado());
		}*/
		return productosCaja;
	}

	@Override
	public ProductoCaja adminCreateProductoCaja(JsonNode jsonNode) {
		ObjectMapper obj = new ObjectMapper();
        obj.registerModule(new JavaTimeModule()); // esto es necesario para evitar un error.
        Long idProducto = obj.convertValue(jsonNode.get("idProducto"), Long.class);
        Long idCaja = obj.convertValue(jsonNode.get("idCaja"), Long.class );
        
        Optional<Producto> producto = productoRepository.findById(idProducto);
        Optional<Caja> caja = cajaRepository.findById(idCaja);
        
        if(producto.get() != null && caja.get() != null) {
        	ProductoCaja productoCaja = new ProductoCaja();        
        	productoCaja.setCaja(caja.get());
        	productoCaja.setProducto(producto.get());
        	productoCajaRepository.save(productoCaja);
        }
        System.out.println("idp->" + idProducto + "    idc-->> " + idCaja);	
		return null;
	}

	@Override
	public Optional<ProductoCaja> findByIdCajaAndIdProducto(JsonNode jsonNode) {
		ObjectMapper obj = new ObjectMapper();
        obj.registerModule(new JavaTimeModule()); // esto es necesario para evitar un error.
        Long idProducto = obj.convertValue(jsonNode.get("idProducto"), Long.class);
        Long idCaja = obj.convertValue(jsonNode.get("idCaja"), Long.class );
        
        Optional<Producto> producto = productoRepository.findByIdAndEstado(idProducto, true);
        System.out.println("PRODUCTOASDASDASDAD-> " + producto);
        Optional<Caja> caja = cajaRepository.findById(idCaja);
        if(producto.get() != null && caja.get() != null) {	
        	Optional<ProductoCaja> productoCaja = productoCajaRepository.findByCajaAndProducto(caja.get(), producto.get());
        	return productoCaja;
        }
        
		return null;
	}

	@Override
	public void deleteProductoCajaByProducto(Producto producto) {
		log.debug("Request to delete ProductoCaja : {}", producto);
        productoCajaRepository.deleteByProducto(producto);
	}
}
