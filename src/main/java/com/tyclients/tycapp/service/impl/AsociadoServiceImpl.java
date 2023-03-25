package com.tyclients.tycapp.service.impl;

import com.tyclients.tycapp.domain.Asociado;
import com.tyclients.tycapp.repository.AsociadoRepository;
import com.tyclients.tycapp.service.AsociadoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Asociado}.
 */
@Service
@Transactional
public class AsociadoServiceImpl implements AsociadoService {

    private final Logger log = LoggerFactory.getLogger(AsociadoServiceImpl.class);

    private final AsociadoRepository asociadoRepository;

    public AsociadoServiceImpl(AsociadoRepository asociadoRepository) {
        this.asociadoRepository = asociadoRepository;
    }

    @Override
    public Asociado save(Asociado asociado) {
        log.debug("Request to save Asociado : {}", asociado);
        return asociadoRepository.save(asociado);
    }

    @Override
    public Asociado update(Asociado asociado) {
        log.debug("Request to save Asociado : {}", asociado);
        return asociadoRepository.save(asociado);
    }

    @Override
    public Optional<Asociado> partialUpdate(Asociado asociado) {
        log.debug("Request to partially update Asociado : {}", asociado);

        return asociadoRepository
            .findById(asociado.getId())
            .map(existingAsociado -> {
                if (asociado.getIdentificadorGeneral() != null) {
                    existingAsociado.setIdentificadorGeneral(asociado.getIdentificadorGeneral());
                }
                if (asociado.getEstado() != null) {
                    existingAsociado.setEstado(asociado.getEstado());
                }
                if (asociado.getCreatedDate() != null) {
                    existingAsociado.setCreatedDate(asociado.getCreatedDate());
                }
                if (asociado.getUpdatedDate() != null) {
                    existingAsociado.setUpdatedDate(asociado.getUpdatedDate());
                }

                return existingAsociado;
            })
            .map(asociadoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Asociado> findAll(Pageable pageable) {
        log.debug("Request to get all Asociados");
        return asociadoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Asociado> findOne(Long id) {
        log.debug("Request to get Asociado : {}", id);
        return asociadoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Asociado : {}", id);
        asociadoRepository.deleteById(id);
    }
}
