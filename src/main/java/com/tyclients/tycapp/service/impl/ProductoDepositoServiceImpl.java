package com.tyclients.tycapp.service.impl;

import com.tyclients.tycapp.domain.ProductoDeposito;
import com.tyclients.tycapp.repository.ProductoDepositoRepository;
import com.tyclients.tycapp.service.ProductoDepositoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductoDeposito}.
 */
@Service
@Transactional
public class ProductoDepositoServiceImpl implements ProductoDepositoService {

    private final Logger log = LoggerFactory.getLogger(ProductoDepositoServiceImpl.class);

    private final ProductoDepositoRepository productoDepositoRepository;

    public ProductoDepositoServiceImpl(ProductoDepositoRepository productoDepositoRepository) {
        this.productoDepositoRepository = productoDepositoRepository;
    }

    @Override
    public ProductoDeposito save(ProductoDeposito productoDeposito) {
        log.debug("Request to save ProductoDeposito : {}", productoDeposito);
        return productoDepositoRepository.save(productoDeposito);
    }

    @Override
    public ProductoDeposito update(ProductoDeposito productoDeposito) {
        log.debug("Request to save ProductoDeposito : {}", productoDeposito);
        return productoDepositoRepository.save(productoDeposito);
    }

    @Override
    public Optional<ProductoDeposito> partialUpdate(ProductoDeposito productoDeposito) {
        log.debug("Request to partially update ProductoDeposito : {}", productoDeposito);

        return productoDepositoRepository
            .findById(productoDeposito.getId())
            .map(existingProductoDeposito -> {
                if (productoDeposito.getCantidad() != null) {
                    existingProductoDeposito.setCantidad(productoDeposito.getCantidad());
                }
                if (productoDeposito.getCreatedDate() != null) {
                    existingProductoDeposito.setCreatedDate(productoDeposito.getCreatedDate());
                }
                if (productoDeposito.getUpdatedDate() != null) {
                    existingProductoDeposito.setUpdatedDate(productoDeposito.getUpdatedDate());
                }

                return existingProductoDeposito;
            })
            .map(productoDepositoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductoDeposito> findAll(Pageable pageable) {
        log.debug("Request to get all ProductoDepositos");
        return productoDepositoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductoDeposito> findOne(Long id) {
        log.debug("Request to get ProductoDeposito : {}", id);
        return productoDepositoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductoDeposito : {}", id);
        productoDepositoRepository.deleteById(id);
    }
}
