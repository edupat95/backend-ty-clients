package com.tyclients.tycapp.service.impl;

import com.tyclients.tycapp.domain.Trabajador;
import com.fasterxml.jackson.databind.JsonNode;
import com.tyclients.tycapp.domain.Caja;
import com.tyclients.tycapp.domain.Cajero;
import com.tyclients.tycapp.domain.Club;
import com.tyclients.tycapp.domain.Producto;
import com.tyclients.tycapp.domain.ProductoVenta;
import com.tyclients.tycapp.repository.CajaRepository;
import com.tyclients.tycapp.repository.CajeroRepository;
import com.tyclients.tycapp.repository.ClubRepository;
import com.tyclients.tycapp.repository.TrabajadorRepository;
import com.tyclients.tycapp.service.CajeroService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Cajero}.
 */
@Service
@Transactional
public class CajeroServiceImpl implements CajeroService {

    private final Logger log = LoggerFactory.getLogger(CajeroServiceImpl.class);

    private final CajeroRepository cajeroRepository;
    
    private final ClubRepository clubRepository;
    
    private final TrabajadorRepository trabajadorRepository;
    
    private final CajaRepository cajaRepository;
    
    public CajeroServiceImpl(CajaRepository cajaRepository, TrabajadorRepository trabajadorRepository, ClubRepository clubRepository, CajeroRepository cajeroRepository) {
        this.cajeroRepository = cajeroRepository;
        this.clubRepository = clubRepository;
        this.trabajadorRepository = trabajadorRepository;
        this.cajaRepository = cajaRepository;
    }

    @Override
    public Cajero save(Cajero cajero) {
        log.debug("Request to save Cajero : {}", cajero);
        return cajeroRepository.save(cajero);
    }

    @Override
    public Cajero update(Cajero cajero) {
        log.debug("Request to save Cajero : {}", cajero);
        return cajeroRepository.save(cajero);
    }

    @Override
    public Optional<Cajero> partialUpdate(Cajero cajero) {
        log.debug("Request to partially update Cajero : {}", cajero);

        return cajeroRepository
            .findById(cajero.getId())
            .map(existingCajero -> {
                if (cajero.getPlataDeCambio() != null) {
                    existingCajero.setPlataDeCambio(cajero.getPlataDeCambio());
                }
                if (cajero.getEstado() != null) {
                    existingCajero.setEstado(cajero.getEstado());
                }
                if (cajero.getCreadDate() != null) {
                    existingCajero.setCreadDate(cajero.getCreadDate());
                }
                if (cajero.getUpdatedDate() != null) {
                    existingCajero.setUpdatedDate(cajero.getUpdatedDate());
                }

                return existingCajero;
            })
            .map(cajeroRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Cajero> findAll(Pageable pageable) {
        log.debug("Request to get all Cajeros");
        return cajeroRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cajero> findOne(Long id) {
        log.debug("Request to get Cajero : {}", id);
        return cajeroRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cajero : {}", id);
        cajeroRepository.deleteById(id);
    }
    
    @Override
	public Optional<Cajero> findByIdTrabajador(Optional<Trabajador> trabajador) {
		log.debug("Request to get Cajero : {}", trabajador);
        return cajeroRepository.findByTrabajador(trabajador.get());
	}

	@Override
	public List<Cajero> findByIdClub(Long idClub) {
		log.debug("Request to get Cajero by idClub : {}", idClub);

	    Optional<Club> club = clubRepository.findById(idClub);

	    List<Trabajador> trabajadores = trabajadorRepository.findByClub(club).orElse(Collections.emptyList());

	    List<Cajero> cajeros = new ArrayList<>();

	    for (Trabajador trabajador : trabajadores) {
	        Optional<Cajero> cajero = cajeroRepository.findByTrabajador(trabajador);
	        if (cajero.isPresent()) {
	            cajeros.add(cajero.get());
	        }
	    }

	    return cajeros;
	}

	@Override
	public List<Cajero> findByIdCaja(Long idCaja) {
		
		Optional<Caja> caja = cajaRepository.findById(idCaja);
		
	    List<Cajero> cajeros = cajeroRepository.findByCaja(caja.get());
		
		return cajeros;
	}

}
