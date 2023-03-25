package com.tyclients.tycapp.service.impl;

import com.tyclients.tycapp.domain.Venta;
import com.tyclients.tycapp.service.VentaService;
import com.tyclients.tycapp.domain.ProductoVenta;
import com.tyclients.tycapp.repository.ProductoVentaRepository;
import com.tyclients.tycapp.service.ProductoVentaService;

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
 * Service Implementation for managing {@link ProductoVenta}.
 */
@Service
@Transactional
public class ProductoVentaServiceImpl implements ProductoVentaService {

    private final Logger log = LoggerFactory.getLogger(ProductoVentaServiceImpl.class);

    private final ProductoVentaRepository productoVentaRepository;

    private final VentaService ventaService;
    	
    public ProductoVentaServiceImpl(ProductoVentaRepository productoVentaRepository, VentaService ventaService) {
        this.productoVentaRepository = productoVentaRepository;
        this.ventaService = ventaService;
    }

    @Override
    public ProductoVenta save(ProductoVenta productoVenta) {
        log.debug("Request to save ProductoVenta : {}", productoVenta);
        return productoVentaRepository.save(productoVenta);
    }

    @Override
    public ProductoVenta update(ProductoVenta productoVenta) {
        log.debug("Request to save ProductoVenta : {}", productoVenta);
        return productoVentaRepository.save(productoVenta);
    }

    @Override
    public Optional<ProductoVenta> partialUpdate(ProductoVenta productoVenta) {
        log.debug("Request to partially update ProductoVenta : {}", productoVenta);

        return productoVentaRepository
            .findById(productoVenta.getId())
            .map(existingProductoVenta -> {
                if (productoVenta.getCostoTotal() != null) {
                    existingProductoVenta.setCostoTotal(productoVenta.getCostoTotal());
                }
                if (productoVenta.getCostoTotalPuntos() != null) {
                    existingProductoVenta.setCostoTotalPuntos(productoVenta.getCostoTotalPuntos());
                }
                if (productoVenta.getCantidad() != null) {
                    existingProductoVenta.setCantidad(productoVenta.getCantidad());
                }
                if (productoVenta.getCreatedDate() != null) {
                    existingProductoVenta.setCreatedDate(productoVenta.getCreatedDate());
                }
                if (productoVenta.getUpdatedDate() != null) {
                    existingProductoVenta.setUpdatedDate(productoVenta.getUpdatedDate());
                }

                return existingProductoVenta;
            })
            .map(productoVentaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductoVenta> findAll(Pageable pageable) {
        log.debug("Request to get all ProductoVentas");
        return productoVentaRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductoVenta> findOne(Long id) {
        log.debug("Request to get ProductoVenta : {}", id);
        return productoVentaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductoVenta : {}", id);
        productoVentaRepository.deleteById(id);
    }
    
    @Override
	public List<ProductoVenta> getProductosVentaByIdClub(Long idClub) {
		//1° Busco todas la ventas que pertenecen al club
		List<Venta> ventas = ventaService.findByClub(idClub);
		//Busco todos los productos que se incluyen en esas ventas
		List<ProductoVenta> AllProductosVentas = new ArrayList<ProductoVenta>();
		List<ProductoVenta> productosEnLaVenta = new ArrayList<ProductoVenta>();
		System.out.println("VENTAS ENCONTRADAS--------------->" + ventas);
		for(Venta venta : ventas) {
			productosEnLaVenta = productoVentaRepository.findByVenta(venta);
			System.out.println("PRODUCTOS EN LA VENTA " + venta.getId() + " ---------------> " + productosEnLaVenta + "\n\n");
			//colocamos los productos de la venta en AllProductosVentas
			for(ProductoVenta productoVenta : productosEnLaVenta) {	
				AllProductosVentas.add(productoVenta);
			}
		}
		return AllProductosVentas;
	}

	@Override
	public List<ProductoVenta> getProductosVentaByIdVenta(Long idVenta) {
		Optional<Venta> venta = ventaService.findOne(idVenta);
		
		if(venta.isPresent()) {
			List<ProductoVenta> productosVenta = productoVentaRepository.findByVenta(venta.get());
			return productosVenta;
		}
		
		return null;
	}
}
