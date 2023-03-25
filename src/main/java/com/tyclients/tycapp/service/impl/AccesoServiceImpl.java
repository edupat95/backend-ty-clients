package com.tyclients.tycapp.service.impl;

import com.tyclients.tycapp.domain.Acceso;
import com.tyclients.tycapp.repository.AccesoRepository;
import com.tyclients.tycapp.service.AccesoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Acceso}.
 */
@Service
@Transactional
public class AccesoServiceImpl implements AccesoService {

    private final Logger log = LoggerFactory.getLogger(AccesoServiceImpl.class);

    private final AccesoRepository accesoRepository;

    public AccesoServiceImpl(AccesoRepository accesoRepository) {
        this.accesoRepository = accesoRepository;
    }

    @Override
    public Acceso save(Acceso acceso) {
        log.debug("Request to save Acceso : {}", acceso);
        return accesoRepository.save(acceso);
    }

    @Override
    public Acceso update(Acceso acceso) {
        log.debug("Request to save Acceso : {}", acceso);
        return accesoRepository.save(acceso);
    }

    @Override
    public Optional<Acceso> partialUpdate(Acceso acceso) {
        log.debug("Request to partially update Acceso : {}", acceso);

        return accesoRepository
            .findById(acceso.getId())
            .map(existingAcceso -> {
                if (acceso.getCostoPuntos() != null) {
                    existingAcceso.setCostoPuntos(acceso.getCostoPuntos());
                }
                if (acceso.getFechaCanje() != null) {
                    existingAcceso.setFechaCanje(acceso.getFechaCanje());
                }
                if (acceso.getEstado() != null) {
                    existingAcceso.setEstado(acceso.getEstado());
                }
                if (acceso.getCreatedDate() != null) {
                    existingAcceso.setCreatedDate(acceso.getCreatedDate());
                }
                if (acceso.getUpdatedDate() != null) {
                    existingAcceso.setUpdatedDate(acceso.getUpdatedDate());
                }

                return existingAcceso;
            })
            .map(accesoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Acceso> findAll(Pageable pageable) {
        log.debug("Request to get all Accesos");
        return accesoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Acceso> findOne(Long id) {
        log.debug("Request to get Acceso : {}", id);
        return accesoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Acceso : {}", id);
        accesoRepository.deleteById(id);
    }
}
