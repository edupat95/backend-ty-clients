package com.tyclients.tycapp.service.impl;

import com.tyclients.tycapp.domain.AdminClub;
import com.tyclients.tycapp.repository.AdminClubRepository;
import com.tyclients.tycapp.service.AdminClubService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AdminClub}.
 */
@Service
@Transactional
public class AdminClubServiceImpl implements AdminClubService {

    private final Logger log = LoggerFactory.getLogger(AdminClubServiceImpl.class);

    private final AdminClubRepository adminClubRepository;

    public AdminClubServiceImpl(AdminClubRepository adminClubRepository) {
        this.adminClubRepository = adminClubRepository;
    }

    @Override
    public AdminClub save(AdminClub adminClub) {
        log.debug("Request to save AdminClub : {}", adminClub);
        return adminClubRepository.save(adminClub);
    }

    @Override
    public AdminClub update(AdminClub adminClub) {
        log.debug("Request to save AdminClub : {}", adminClub);
        return adminClubRepository.save(adminClub);
    }

    @Override
    public Optional<AdminClub> partialUpdate(AdminClub adminClub) {
        log.debug("Request to partially update AdminClub : {}", adminClub);

        return adminClubRepository
            .findById(adminClub.getId())
            .map(existingAdminClub -> {
                if (adminClub.getEstado() != null) {
                    existingAdminClub.setEstado(adminClub.getEstado());
                }
                if (adminClub.getCreatedDate() != null) {
                    existingAdminClub.setCreatedDate(adminClub.getCreatedDate());
                }
                if (adminClub.getUpdatedDate() != null) {
                    existingAdminClub.setUpdatedDate(adminClub.getUpdatedDate());
                }

                return existingAdminClub;
            })
            .map(adminClubRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AdminClub> findAll(Pageable pageable) {
        log.debug("Request to get all AdminClubs");
        return adminClubRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AdminClub> findOne(Long id) {
        log.debug("Request to get AdminClub : {}", id);
        return adminClubRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AdminClub : {}", id);
        adminClubRepository.deleteById(id);
    }
}
