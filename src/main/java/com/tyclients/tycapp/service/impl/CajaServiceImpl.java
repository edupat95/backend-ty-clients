package com.tyclients.tycapp.service.impl;

import com.tyclients.tycapp.domain.Caja;
import com.tyclients.tycapp.domain.Club;
import com.tyclients.tycapp.repository.CajaRepository;
import com.tyclients.tycapp.service.CajaService;
import com.tyclients.tycapp.service.ClubService;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Caja}.
 */
@Service
@Transactional
public class CajaServiceImpl implements CajaService {

    private final Logger log = LoggerFactory.getLogger(CajaServiceImpl.class);

    private final CajaRepository cajaRepository;
    
    private final ClubService clubService;
    
    public CajaServiceImpl(ClubService clubService, CajaRepository cajaRepository) {
        this.cajaRepository = cajaRepository;
        this.clubService = clubService;
    }

    @Override
    public Caja save(Caja caja) {
        log.debug("Request to save Caja : {}", caja);
        return cajaRepository.save(caja);
    }

    @Override
    public Caja update(Caja caja) {
        log.debug("Request to save Caja : {}", caja);
        return cajaRepository.save(caja);
    }

    @Override
    public Optional<Caja> partialUpdate(Caja caja) {
        log.debug("Request to partially update Caja : {}", caja);

        return cajaRepository
            .findById(caja.getId())
            .map(existingCaja -> {
                if (caja.getNombre() != null) {
                    existingCaja.setNombre(caja.getNombre());
                }
                if (caja.getEstado() != null) {
                    existingCaja.setEstado(caja.getEstado());
                }
                if (caja.getCreatedDate() != null) {
                    existingCaja.setCreatedDate(caja.getCreatedDate());
                }
                if (caja.getUpdatedDate() != null) {
                    existingCaja.setUpdatedDate(caja.getUpdatedDate());
                }

                return existingCaja;
            })
            .map(cajaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Caja> findAll(Pageable pageable) {
        log.debug("Request to get all Cajas");
        return cajaRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Caja> findOne(Long id) {
        log.debug("Request to get Caja : {}", id);
        return cajaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Caja : {}", id);
        cajaRepository.deleteById(id);
    }

	@Override
	public List<Caja> findByIdClub(Long idClub) {
		log.debug("Request to get Caja by Club: {}", idClub);
		Optional<Club> club = clubService.findOne(idClub);
		if(club.get() != null) {
			List<Caja> cajas = cajaRepository.findByClub(club.get());
			return cajas;
		}
		return null;
	}
}
