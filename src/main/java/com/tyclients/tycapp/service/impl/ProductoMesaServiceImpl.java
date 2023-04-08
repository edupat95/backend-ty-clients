package com.tyclients.tycapp.service.impl;

import com.tyclients.tycapp.domain.Mesa;
import com.tyclients.tycapp.domain.Producto;
import com.tyclients.tycapp.domain.ProductoMesa;
import com.tyclients.tycapp.repository.ProductoMesaRepository;
import com.tyclients.tycapp.service.ProductoMesaService;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductoMesa}.
 */
@Service
@Transactional
public class ProductoMesaServiceImpl implements ProductoMesaService {

    private final Logger log = LoggerFactory.getLogger(ProductoMesaServiceImpl.class);

    private final ProductoMesaRepository productoMesaRepository;

    public ProductoMesaServiceImpl(ProductoMesaRepository productoMesaRepository) {
        this.productoMesaRepository = productoMesaRepository;
    }

    @Override
    public ProductoMesa save(ProductoMesa productoMesa) {
        log.debug("Request to save ProductoMesa : {}", productoMesa);
        return productoMesaRepository.save(productoMesa);
    }

    @Override
    public ProductoMesa update(ProductoMesa productoMesa) {
        log.debug("Request to save ProductoMesa : {}", productoMesa);
        return productoMesaRepository.save(productoMesa);
    }

    @Override
    public Optional<ProductoMesa> partialUpdate(ProductoMesa productoMesa) {
        log.debug("Request to partially update ProductoMesa : {}", productoMesa);

        return productoMesaRepository
            .findById(productoMesa.getId())
            .map(existingProductoMesa -> {
                if (productoMesa.getCostoTotal() != null) {
                    existingProductoMesa.setCostoTotal(productoMesa.getCostoTotal());
                }
                if (productoMesa.getCostoTotalPuntos() != null) {
                    existingProductoMesa.setCostoTotalPuntos(productoMesa.getCostoTotalPuntos());
                }
                if (productoMesa.getCantidad() != null) {
                    existingProductoMesa.setCantidad(productoMesa.getCantidad());
                }
                if (productoMesa.getEstado() != null) {
                    existingProductoMesa.setEstado(productoMesa.getEstado());
                }
                if (productoMesa.getCreatedDate() != null) {
                    existingProductoMesa.setCreatedDate(productoMesa.getCreatedDate());
                }
                if (productoMesa.getUpdatedDate() != null) {
                    existingProductoMesa.setUpdatedDate(productoMesa.getUpdatedDate());
                }

                return existingProductoMesa;
            })
            .map(productoMesaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductoMesa> findAll(Pageable pageable) {
        log.debug("Request to get all ProductoMesas");
        return productoMesaRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductoMesa> findOne(Long id) {
        log.debug("Request to get ProductoMesa : {}", id);
        return productoMesaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductoMesa : {}", id);
        productoMesaRepository.deleteById(id);
    }

	@Override
	public List<ProductoMesa> findByMesa(Mesa mesa) {
		// TODO Auto-generated method stub
		
		return productoMesaRepository.findByMesa(mesa);
	}

	@Override
	public void deleteByMesa(Optional<Mesa> mesa) {
		// TODO Auto-generated method stub
		productoMesaRepository.deleteByMesa(mesa);
	}

	@Override
	public Optional<ProductoMesa> findByProductoAndMesa(Producto producto, Mesa mesa) {
		return productoMesaRepository.findByProductoAndMesa(producto, mesa);
	}
}
