package com.tyclients.tycapp.service.impl;

import com.tyclients.tycapp.domain.Club;
import com.tyclients.tycapp.domain.FormaPago;
import com.tyclients.tycapp.repository.FormaPagoRepository;
import com.tyclients.tycapp.service.FormaPagoService;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FormaPago}.
 */
@Service
@Transactional
public class FormaPagoServiceImpl implements FormaPagoService {

    private final Logger log = LoggerFactory.getLogger(FormaPagoServiceImpl.class);

    private final FormaPagoRepository formaPagoRepository;

    public FormaPagoServiceImpl(FormaPagoRepository formaPagoRepository) {
        this.formaPagoRepository = formaPagoRepository;
    }

    @Override
    public FormaPago save(FormaPago formaPago) {
        log.debug("Request to save FormaPago : {}", formaPago);
        return formaPagoRepository.save(formaPago);
    }

    @Override
    public FormaPago update(FormaPago formaPago) {
        log.debug("Request to save FormaPago : {}", formaPago);
        return formaPagoRepository.save(formaPago);
    }

    @Override
    public Optional<FormaPago> partialUpdate(FormaPago formaPago) {
        log.debug("Request to partially update FormaPago : {}", formaPago);

        return formaPagoRepository
            .findById(formaPago.getId())
            .map(existingFormaPago -> {
                if (formaPago.getName() != null) {
                    existingFormaPago.setName(formaPago.getName());
                }
                if (formaPago.getCreatedDate() != null) {
                    existingFormaPago.setCreatedDate(formaPago.getCreatedDate());
                }
                if (formaPago.getUpdatedDate() != null) {
                    existingFormaPago.setUpdatedDate(formaPago.getUpdatedDate());
                }

                return existingFormaPago;
            })
            .map(formaPagoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FormaPago> findAll(Pageable pageable) {
        log.debug("Request to get all FormaPagos");
        return formaPagoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FormaPago> findOne(Long id) {
        log.debug("Request to get FormaPago : {}", id);
        return formaPagoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FormaPago : {}", id);
        formaPagoRepository.deleteById(id);
    }

	@Override
	public List<FormaPago> findByClub(Club club) {
		log.debug("Request to get FormaPago By Club: {}", club);
        return formaPagoRepository.findByClubAndEstado(club, true);
	}
}
