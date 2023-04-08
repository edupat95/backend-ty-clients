package com.tyclients.tycapp.service.impl;

import com.tyclients.tycapp.domain.Club;
import com.tyclients.tycapp.domain.Mesa;
import com.tyclients.tycapp.domain.ProductoMesa;
import com.tyclients.tycapp.repository.MesaRepository;
import com.tyclients.tycapp.service.MesaService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Mesa}.
 */
@Service
@Transactional
public class MesaServiceImpl implements MesaService {

    private final Logger log = LoggerFactory.getLogger(MesaServiceImpl.class);

    private final MesaRepository mesaRepository;

    public MesaServiceImpl(MesaRepository mesaRepository) {
        this.mesaRepository = mesaRepository;
    }

    @Override
    public Mesa save(Mesa mesa) {
        log.debug("Request to save Mesa : {}", mesa);
        return mesaRepository.save(mesa);
    }

    @Override
    public Mesa update(Mesa mesa) {
        log.debug("Request to save Mesa : {}", mesa);
        return mesaRepository.save(mesa);
    }

    @Override
    public Optional<Mesa> partialUpdate(Mesa mesa) {
        log.debug("Request to partially update Mesa : {}", mesa);

        return mesaRepository
            .findById(mesa.getId())
            .map(existingMesa -> {
                if (mesa.getNumeroMesa() != null) {
                    existingMesa.setNumeroMesa(mesa.getNumeroMesa());
                }
                if (mesa.getEstado() != null) {
                    existingMesa.setEstado(mesa.getEstado());
                }
                if (mesa.getCreatedDate() != null) {
                    existingMesa.setCreatedDate(mesa.getCreatedDate());
                }
                if (mesa.getUpdatedDate() != null) {
                    existingMesa.setUpdatedDate(mesa.getUpdatedDate());
                }

                return existingMesa;
            })
            .map(mesaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Mesa> findAll(Pageable pageable) {
        log.debug("Request to get all Mesas");
        return mesaRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Mesa> findOne(Long id) {
        log.debug("Request to get Mesa : {}", id);
        return mesaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Mesa : {}", id);
        mesaRepository.deleteById(id);
    }

	@Override
	public void createMesas(Club club, Long cantidad) {
		// TODO Auto-generated method stub
		for(int i = 1; i <= cantidad; i++ ) {
			Mesa mesa = new Mesa();
			mesa.setNumeroMesa(i);
			mesa.setEstado(true);
			mesa.setClub(club);
			mesa.setCreatedDate(LocalDateTime.now().toInstant(ZoneOffset.UTC));
			mesaRepository.save(mesa);
		}
	}

	@Override
	public List<Mesa> findByClub(Club club) {
		log.debug("Request to get all Mesas by club");
	    return mesaRepository.findByClub(club);
	}

}
