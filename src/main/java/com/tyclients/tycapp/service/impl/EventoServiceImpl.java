package com.tyclients.tycapp.service.impl;

import com.tyclients.tycapp.domain.Evento;
import com.tyclients.tycapp.repository.EventoRepository;
import com.tyclients.tycapp.service.EventoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Evento}.
 */
@Service
@Transactional
public class EventoServiceImpl implements EventoService {

    private final Logger log = LoggerFactory.getLogger(EventoServiceImpl.class);

    private final EventoRepository eventoRepository;

    public EventoServiceImpl(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    @Override
    public Evento save(Evento evento) {
        log.debug("Request to save Evento : {}", evento);
        return eventoRepository.save(evento);
    }

    @Override
    public Evento update(Evento evento) {
        log.debug("Request to save Evento : {}", evento);
        return eventoRepository.save(evento);
    }

    @Override
    public Optional<Evento> partialUpdate(Evento evento) {
        log.debug("Request to partially update Evento : {}", evento);

        return eventoRepository
            .findById(evento.getId())
            .map(existingEvento -> {
                if (evento.getFechaEvento() != null) {
                    existingEvento.setFechaEvento(evento.getFechaEvento());
                }
                if (evento.getFechaCreacion() != null) {
                    existingEvento.setFechaCreacion(evento.getFechaCreacion());
                }
                if (evento.getEstado() != null) {
                    existingEvento.setEstado(evento.getEstado());
                }
                if (evento.getDireccion() != null) {
                    existingEvento.setDireccion(evento.getDireccion());
                }
                if (evento.getCreatedDate() != null) {
                    existingEvento.setCreatedDate(evento.getCreatedDate());
                }
                if (evento.getUpdatedDate() != null) {
                    existingEvento.setUpdatedDate(evento.getUpdatedDate());
                }

                return existingEvento;
            })
            .map(eventoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Evento> findAll(Pageable pageable) {
        log.debug("Request to get all Eventos");
        return eventoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Evento> findOne(Long id) {
        log.debug("Request to get Evento : {}", id);
        return eventoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Evento : {}", id);
        eventoRepository.deleteById(id);
    }
}
