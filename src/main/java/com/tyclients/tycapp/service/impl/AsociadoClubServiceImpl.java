package com.tyclients.tycapp.service.impl;

import com.tyclients.tycapp.domain.Asociado;
import com.tyclients.tycapp.domain.AsociadoClub;
import com.tyclients.tycapp.domain.Club;
import com.tyclients.tycapp.repository.AsociadoClubRepository;
import com.tyclients.tycapp.service.AsociadoClubService;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AsociadoClub}.
 */
@Service
@Transactional
public class AsociadoClubServiceImpl implements AsociadoClubService {

    private final Logger log = LoggerFactory.getLogger(AsociadoClubServiceImpl.class);

    private final AsociadoClubRepository asociadoClubRepository;

    public AsociadoClubServiceImpl(AsociadoClubRepository asociadoClubRepository) {
        this.asociadoClubRepository = asociadoClubRepository;
    }

    @Override
    public AsociadoClub save(AsociadoClub asociadoClub) {
        log.debug("Request to save AsociadoClub : {}", asociadoClub);
        return asociadoClubRepository.save(asociadoClub);
    }

    @Override
    public AsociadoClub update(AsociadoClub asociadoClub) {
        log.debug("Request to save AsociadoClub : {}", asociadoClub);
        return asociadoClubRepository.save(asociadoClub);
    }

    @Override
    public Optional<AsociadoClub> partialUpdate(AsociadoClub asociadoClub) {
        log.debug("Request to partially update AsociadoClub : {}", asociadoClub);

        return asociadoClubRepository
            .findById(asociadoClub.getId())
            .map(existingAsociadoClub -> {
                if (asociadoClub.getIdentificador() != null) {
                    existingAsociadoClub.setIdentificador(asociadoClub.getIdentificador());
                }
                if (asociadoClub.getFechaAsociacion() != null) {
                    existingAsociadoClub.setFechaAsociacion(asociadoClub.getFechaAsociacion());
                }
                if (asociadoClub.getPuntosClub() != null) {
                    existingAsociadoClub.setPuntosClub(asociadoClub.getPuntosClub());
                }
                if (asociadoClub.getEstado() != null) {
                    existingAsociadoClub.setEstado(asociadoClub.getEstado());
                }
                if (asociadoClub.getCreatedDate() != null) {
                    existingAsociadoClub.setCreatedDate(asociadoClub.getCreatedDate());
                }
                if (asociadoClub.getUpdatedDate() != null) {
                    existingAsociadoClub.setUpdatedDate(asociadoClub.getUpdatedDate());
                }

                return existingAsociadoClub;
            })
            .map(asociadoClubRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AsociadoClub> findAll(Pageable pageable) {
        log.debug("Request to get all AsociadoClubs");
        return asociadoClubRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AsociadoClub> findOne(Long id) {
        log.debug("Request to get AsociadoClub : {}", id);
        return asociadoClubRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AsociadoClub : {}", id);
        asociadoClubRepository.deleteById(id);
    }

    @Override
	public Optional<AsociadoClub> findAllByIdClubAndIdentificador(Optional<Club> club, UUID identificador) {
		log.debug("Request to get findByIdClubAndIdentificador: {}", club, " " , identificador);
        return asociadoClubRepository.findAllByClubAndIdentificador(club, identificador);
	}

	@Override
	public Optional<AsociadoClub> findByIdAsociado(Asociado idAsociado) {
		log.debug("Request to get AsociadoClub : {}", idAsociado);
        return asociadoClubRepository.findByAsociado(idAsociado);
	}

	@Override
	public Optional<AsociadoClub> findByIdentificadorAndClub(UUID identificador, Club club) {
		// TODO Auto-generated method stub
		return asociadoClubRepository.findByIdentificadorAndClub(identificador, club);
	}
}
