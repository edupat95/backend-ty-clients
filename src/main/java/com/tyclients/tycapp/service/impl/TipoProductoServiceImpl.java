package com.tyclients.tycapp.service.impl;

import com.tyclients.tycapp.domain.TipoProducto;
import com.tyclients.tycapp.repository.TipoProductoRepository;
import com.tyclients.tycapp.service.TipoProductoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TipoProducto}.
 */
@Service
@Transactional
public class TipoProductoServiceImpl implements TipoProductoService {

    private final Logger log = LoggerFactory.getLogger(TipoProductoServiceImpl.class);

    private final TipoProductoRepository tipoProductoRepository;

    public TipoProductoServiceImpl(TipoProductoRepository tipoProductoRepository) {
        this.tipoProductoRepository = tipoProductoRepository;
    }

    @Override
    public TipoProducto save(TipoProducto tipoProducto) {
        log.debug("Request to save TipoProducto : {}", tipoProducto);
        return tipoProductoRepository.save(tipoProducto);
    }

    @Override
    public TipoProducto update(TipoProducto tipoProducto) {
        log.debug("Request to save TipoProducto : {}", tipoProducto);
        return tipoProductoRepository.save(tipoProducto);
    }

    @Override
    public Optional<TipoProducto> partialUpdate(TipoProducto tipoProducto) {
        log.debug("Request to partially update TipoProducto : {}", tipoProducto);

        return tipoProductoRepository
            .findById(tipoProducto.getId())
            .map(existingTipoProducto -> {
                if (tipoProducto.getEstado() != null) {
                    existingTipoProducto.setEstado(tipoProducto.getEstado());
                }
                if (tipoProducto.getName() != null) {
                    existingTipoProducto.setName(tipoProducto.getName());
                }

                return existingTipoProducto;
            })
            .map(tipoProductoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TipoProducto> findAll(Pageable pageable) {
        log.debug("Request to get all TipoProductos");
        return tipoProductoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoProducto> findOne(Long id) {
        log.debug("Request to get TipoProducto : {}", id);
        return tipoProductoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoProducto : {}", id);
        tipoProductoRepository.deleteById(id);
    }
}
