package com.tyclients.tycapp.service.impl;

import com.tyclients.tycapp.domain.Entregador;
import com.tyclients.tycapp.domain.Registrador;
import com.tyclients.tycapp.domain.Trabajador;
import com.tyclients.tycapp.repository.EntregadorRepository;
import com.tyclients.tycapp.repository.TrabajadorRepository;
import com.tyclients.tycapp.service.EntregadorService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Entregador}.
 */
@Service
@Transactional
public class EntregadorServiceImpl implements EntregadorService {

    private final Logger log = LoggerFactory.getLogger(EntregadorServiceImpl.class);

    private final EntregadorRepository entregadorRepository;
    
    private final TrabajadorRepository trabajadorRepository;

    public EntregadorServiceImpl(TrabajadorRepository trabajadorRepository, EntregadorRepository entregadorRepository) {
        this.entregadorRepository = entregadorRepository;
        this.trabajadorRepository = trabajadorRepository;
    }

    @Override
    public Entregador save(Entregador entregador) {
        log.debug("Request to save Entregador : {}", entregador);
        return entregadorRepository.save(entregador);
    }

    @Override
    public Entregador update(Entregador entregador) {
        log.debug("Request to save Entregador : {}", entregador);
        return entregadorRepository.save(entregador);
    }

    @Override
    public Optional<Entregador> partialUpdate(Entregador entregador) {
        log.debug("Request to partially update Entregador : {}", entregador);

        return entregadorRepository
            .findById(entregador.getId())
            .map(existingEntregador -> {
                if (entregador.getEstado() != null) {
                    existingEntregador.setEstado(entregador.getEstado());
                }
                if (entregador.getCreatedDate() != null) {
                    existingEntregador.setCreatedDate(entregador.getCreatedDate());
                }
                if (entregador.getUpdatedDate() != null) {
                    existingEntregador.setUpdatedDate(entregador.getUpdatedDate());
                }

                return existingEntregador;
            })
            .map(entregadorRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Entregador> findAll(Pageable pageable) {
        log.debug("Request to get all Entregadors");
        return entregadorRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Entregador> findOne(Long id) {
        log.debug("Request to get Entregador : {}", id);
        return entregadorRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Entregador : {}", id);
        entregadorRepository.deleteById(id);
    }

	@Override
	public Optional<Entregador> findByTrabajador(Long trabajador_id) {
		Optional<Trabajador> trabajador = trabajadorRepository.findById(trabajador_id);
		System.out.println("Pregunto si existe el trabajador");
		if(trabajador.isPresent()) {
			System.out.println("Existe el trabajador");
			Optional<Entregador> entregador = entregadorRepository.findByTrabajador(trabajador.get());
			return entregador;
		}
		return null;
	}
}
