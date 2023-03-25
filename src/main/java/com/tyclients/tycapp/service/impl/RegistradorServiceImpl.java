package com.tyclients.tycapp.service.impl;

import com.tyclients.tycapp.domain.Trabajador;
import com.tyclients.tycapp.service.TrabajadorService;
import com.tyclients.tycapp.domain.Registrador;
import com.tyclients.tycapp.repository.RegistradorRepository;
import com.tyclients.tycapp.service.RegistradorService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Registrador}.
 */
@Service
@Transactional
public class RegistradorServiceImpl implements RegistradorService {

    private final Logger log = LoggerFactory.getLogger(RegistradorServiceImpl.class);

    private final RegistradorRepository registradorRepository;
    
    private final TrabajadorService trabajadorService;

    public RegistradorServiceImpl(RegistradorRepository registradorRepository, TrabajadorService  trabajadorService) {
        this.registradorRepository = registradorRepository;
        this.trabajadorService = trabajadorService;
    }
    @Override
    public Registrador save(Registrador registrador) {
        log.debug("Request to save Registrador : {}", registrador);
        return registradorRepository.save(registrador);
    }

    @Override
    public Registrador update(Registrador registrador) {
        log.debug("Request to save Registrador : {}", registrador);
        return registradorRepository.save(registrador);
    }

    @Override
    public Optional<Registrador> partialUpdate(Registrador registrador) {
        log.debug("Request to partially update Registrador : {}", registrador);

        return registradorRepository
            .findById(registrador.getId())
            .map(existingRegistrador -> {
                if (registrador.getEstado() != null) {
                    existingRegistrador.setEstado(registrador.getEstado());
                }
                if (registrador.getCreatedDate() != null) {
                    existingRegistrador.setCreatedDate(registrador.getCreatedDate());
                }
                if (registrador.getUpdatedDate() != null) {
                    existingRegistrador.setUpdatedDate(registrador.getUpdatedDate());
                }

                return existingRegistrador;
            })
            .map(registradorRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Registrador> findAll(Pageable pageable) {
        log.debug("Request to get all Registradors");
        return registradorRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Registrador> findOne(Long id) {
        log.debug("Request to get Registrador : {}", id);
        return registradorRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Registrador : {}", id);
        registradorRepository.deleteById(id);
    }
    
    @Override
	public Optional<Registrador> findByTrabajador(Long trabajador_id) {
		// TODO Auto-generated method stub
		log.debug("Request to get Registrador By idTrabajador: {}", trabajador_id);
		Optional<Trabajador> idTrabajador = trabajadorService.findOne(trabajador_id);
		return registradorRepository.findByTrabajador(idTrabajador);
	}
}
