package com.tyclients.tycapp.service.impl;

import com.tyclients.tycapp.domain.Trabajador;
import com.tyclients.tycapp.repository.TrabajadorRepository;
import com.tyclients.tycapp.service.TrabajadorService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Trabajador}.
 */
@Service
@Transactional
public class TrabajadorServiceImpl implements TrabajadorService {

    private final Logger log = LoggerFactory.getLogger(TrabajadorServiceImpl.class);

    private final TrabajadorRepository trabajadorRepository;

    public TrabajadorServiceImpl(TrabajadorRepository trabajadorRepository) {
        this.trabajadorRepository = trabajadorRepository;
    }

    @Override
    public Trabajador save(Trabajador trabajador) {
        log.debug("Request to save Trabajador : {}", trabajador);
        return trabajadorRepository.save(trabajador);
    }

    @Override
    public Trabajador update(Trabajador trabajador) {
        log.debug("Request to save Trabajador : {}", trabajador);
        return trabajadorRepository.save(trabajador);
    }

    @Override
    public Optional<Trabajador> partialUpdate(Trabajador trabajador) {
        log.debug("Request to partially update Trabajador : {}", trabajador);

        return trabajadorRepository
            .findById(trabajador.getId())
            .map(existingTrabajador -> {
                if (trabajador.getSueldo() != null) {
                    existingTrabajador.setSueldo(trabajador.getSueldo());
                }
                if (trabajador.getDescripcion() != null) {
                    existingTrabajador.setDescripcion(trabajador.getDescripcion());
                }
                if (trabajador.getFechaIngreso() != null) {
                    existingTrabajador.setFechaIngreso(trabajador.getFechaIngreso());
                }
                if (trabajador.getEstado() != null) {
                    existingTrabajador.setEstado(trabajador.getEstado());
                }
                if (trabajador.getCreatedDate() != null) {
                    existingTrabajador.setCreatedDate(trabajador.getCreatedDate());
                }
                if (trabajador.getUpdatedDate() != null) {
                    existingTrabajador.setUpdatedDate(trabajador.getUpdatedDate());
                }

                return existingTrabajador;
            })
            .map(trabajadorRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Trabajador> findAll(Pageable pageable) {
        log.debug("Request to get all Trabajadors");
        return trabajadorRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Trabajador> findOne(Long id) {
        log.debug("Request to get Trabajador : {}", id);
        return trabajadorRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Trabajador : {}", id);
        trabajadorRepository.deleteById(id);
    }
    
    @Override
	public Optional<Trabajador> findByUserId(Long user_id) {
		log.debug("Request to get Trabajador by user_id: {}", user_id);
        return trabajadorRepository.findByUserId(user_id);
	}

}
