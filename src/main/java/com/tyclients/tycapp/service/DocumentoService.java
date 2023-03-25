package com.tyclients.tycapp.service;

import com.tyclients.tycapp.domain.AsociadoClub;
import com.tyclients.tycapp.domain.Registrador;
import com.tyclients.tycapp.domain.Documento;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Documento}.
 */
public interface DocumentoService {
    /**
     * Save a documento.
     *
     * @param documento the entity to save.
     * @return the persisted entity.
     */
    Documento save(Documento documento);

    /**
     * Updates a documento.
     *
     * @param documento the entity to update.
     * @return the persisted entity.
     */
    Documento update(Documento documento);

    /**
     * Partially updates a documento.
     *
     * @param documento the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Documento> partialUpdate(Documento documento);

    /**
     * Get all the documentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Documento> findAll(Pageable pageable);

    /**
     * Get the "id" documento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Documento> findOne(Long id);

    /**
     * Delete the "id" documento.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    AsociadoClub createAsociadoClubByDoducmento(Documento documento, Registrador registrador);

	Optional<Documento> findByNumeroDni(Long numeroDni);
}
