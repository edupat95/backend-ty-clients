package com.tyclients.tycapp.service.impl;

import com.tyclients.tycapp.domain.Deposito;
import com.tyclients.tycapp.repository.DepositoRepository;
import com.tyclients.tycapp.service.DepositoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Deposito}.
 */
@Service
@Transactional
public class DepositoServiceImpl implements DepositoService {

    private final Logger log = LoggerFactory.getLogger(DepositoServiceImpl.class);

    private final DepositoRepository depositoRepository;

    public DepositoServiceImpl(DepositoRepository depositoRepository) {
        this.depositoRepository = depositoRepository;
    }

    @Override
    public Deposito save(Deposito deposito) {
        log.debug("Request to save Deposito : {}", deposito);
        return depositoRepository.save(deposito);
    }

    @Override
    public Deposito update(Deposito deposito) {
        log.debug("Request to save Deposito : {}", deposito);
        return depositoRepository.save(deposito);
    }

    @Override
    public Optional<Deposito> partialUpdate(Deposito deposito) {
        log.debug("Request to partially update Deposito : {}", deposito);

        return depositoRepository
            .findById(deposito.getId())
            .map(existingDeposito -> {
                if (deposito.getName() != null) {
                    existingDeposito.setName(deposito.getName());
                }
                if (deposito.getEstado() != null) {
                    existingDeposito.setEstado(deposito.getEstado());
                }
                if (deposito.getCreatedDate() != null) {
                    existingDeposito.setCreatedDate(deposito.getCreatedDate());
                }
                if (deposito.getUpdatedDate() != null) {
                    existingDeposito.setUpdatedDate(deposito.getUpdatedDate());
                }

                return existingDeposito;
            })
            .map(depositoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Deposito> findAll(Pageable pageable) {
        log.debug("Request to get all Depositos");
        return depositoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Deposito> findOne(Long id) {
        log.debug("Request to get Deposito : {}", id);
        return depositoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Deposito : {}", id);
        depositoRepository.deleteById(id);
    }
}
