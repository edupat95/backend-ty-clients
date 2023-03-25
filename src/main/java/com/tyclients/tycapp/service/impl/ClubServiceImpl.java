package com.tyclients.tycapp.service.impl;

import com.tyclients.tycapp.repository.AdminClubRepository;
import com.tyclients.tycapp.domain.AdminClub;
import com.tyclients.tycapp.domain.Club;
import com.tyclients.tycapp.repository.ClubRepository;
import com.tyclients.tycapp.service.ClubService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Club}.
 */
@Service
@Transactional
public class ClubServiceImpl implements ClubService {

    private final Logger log = LoggerFactory.getLogger(ClubServiceImpl.class);

    private final ClubRepository clubRepository;

    private final AdminClubRepository adminClubRepository;
    
    public ClubServiceImpl(ClubRepository clubRepository, AdminClubRepository adminClubRepository) {
        this.clubRepository = clubRepository;
        this.adminClubRepository = adminClubRepository;
    }

    @Override
    public Club save(Club club) {
        log.debug("Request to save Club : {}", club);
        return clubRepository.save(club);
    }

    @Override
    public Club update(Club club) {
        log.debug("Request to save Club : {}", club);
        return clubRepository.save(club);
    }

    @Override
    public Optional<Club> partialUpdate(Club club) {
        log.debug("Request to partially update Club : {}", club);

        return clubRepository
            .findById(club.getId())
            .map(existingClub -> {
                if (club.getNombre() != null) {
                    existingClub.setNombre(club.getNombre());
                }
                if (club.getDireccion() != null) {
                    existingClub.setDireccion(club.getDireccion());
                }
                if (club.getEstado() != null) {
                    existingClub.setEstado(club.getEstado());
                }
                if (club.getCreatedDate() != null) {
                    existingClub.setCreatedDate(club.getCreatedDate());
                }
                if (club.getUpdatedDate() != null) {
                    existingClub.setUpdatedDate(club.getUpdatedDate());
                }

                return existingClub;
            })
            .map(clubRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Club> findAll(Pageable pageable) {
        log.debug("Request to get all Clubs");
        return clubRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Club> findOne(Long id) {
        log.debug("Request to get Club : {}", id);
        return clubRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Club : {}", id);
        clubRepository.deleteById(id);
    }
    
    @Override
	public Optional<Club> getClubByIdUser(Long user_id) {
		// TODO Auto-generated method stub
		//1° FIND ADMINCLUB WITH USERID
		Optional<AdminClub> adminClubResult = adminClubRepository.findByUserId(user_id);
		System.out.println("----------------------------->ADMIN CLUB ENCONTRADO -> " + adminClubResult);
		//2° FIND CLUB WITH ADMINCLUB
		Optional<Club> clubResult = clubRepository.findByAdminClub(adminClubResult);
		return clubResult;
	}
}
