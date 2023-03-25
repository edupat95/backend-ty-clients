package com.tyclients.tycapp.service;

import com.tyclients.tycapp.domain.AdminClub;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link AdminClub}.
 */
public interface AdminClubService {
    /**
     * Save a adminClub.
     *
     * @param adminClub the entity to save.
     * @return the persisted entity.
     */
    AdminClub save(AdminClub adminClub);

    /**
     * Updates a adminClub.
     *
     * @param adminClub the entity to update.
     * @return the persisted entity.
     */
    AdminClub update(AdminClub adminClub);

    /**
     * Partially updates a adminClub.
     *
     * @param adminClub the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AdminClub> partialUpdate(AdminClub adminClub);

    /**
     * Get all the adminClubs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AdminClub> findAll(Pageable pageable);

    /**
     * Get the "id" adminClub.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AdminClub> findOne(Long id);

    /**
     * Delete the "id" adminClub.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
