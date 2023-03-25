package com.tyclients.tycapp.service;

import com.tyclients.tycapp.domain.Evento;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Evento}.
 */
public interface EventoService {
    /**
     * Save a evento.
     *
     * @param evento the entity to save.
     * @return the persisted entity.
     */
    Evento save(Evento evento);

    /**
     * Updates a evento.
     *
     * @param evento the entity to update.
     * @return the persisted entity.
     */
    Evento update(Evento evento);

    /**
     * Partially updates a evento.
     *
     * @param evento the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Evento> partialUpdate(Evento evento);

    /**
     * Get all the eventos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Evento> findAll(Pageable pageable);

    /**
     * Get the "id" evento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Evento> findOne(Long id);

    /**
     * Delete the "id" evento.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
