package com.tyclients.tycapp.service.impl;

import com.tyclients.tycapp.domain.PlanContratado;
import com.tyclients.tycapp.repository.PlanContratadoRepository;
import com.tyclients.tycapp.service.PlanContratadoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PlanContratado}.
 */
@Service
@Transactional
public class PlanContratadoServiceImpl implements PlanContratadoService {

    private final Logger log = LoggerFactory.getLogger(PlanContratadoServiceImpl.class);

    private final PlanContratadoRepository planContratadoRepository;

    public PlanContratadoServiceImpl(PlanContratadoRepository planContratadoRepository) {
        this.planContratadoRepository = planContratadoRepository;
    }

    @Override
    public PlanContratado save(PlanContratado planContratado) {
        log.debug("Request to save PlanContratado : {}", planContratado);
        return planContratadoRepository.save(planContratado);
    }

    @Override
    public PlanContratado update(PlanContratado planContratado) {
        log.debug("Request to save PlanContratado : {}", planContratado);
        return planContratadoRepository.save(planContratado);
    }

    @Override
    public Optional<PlanContratado> partialUpdate(PlanContratado planContratado) {
        log.debug("Request to partially update PlanContratado : {}", planContratado);

        return planContratadoRepository
            .findById(planContratado.getId())
            .map(existingPlanContratado -> {
                if (planContratado.getTiempoContratado() != null) {
                    existingPlanContratado.setTiempoContratado(planContratado.getTiempoContratado());
                }
                if (planContratado.getFechaVencimiento() != null) {
                    existingPlanContratado.setFechaVencimiento(planContratado.getFechaVencimiento());
                }
                if (planContratado.getEstado() != null) {
                    existingPlanContratado.setEstado(planContratado.getEstado());
                }
                if (planContratado.getCreatedDate() != null) {
                    existingPlanContratado.setCreatedDate(planContratado.getCreatedDate());
                }
                if (planContratado.getUpdatedDate() != null) {
                    existingPlanContratado.setUpdatedDate(planContratado.getUpdatedDate());
                }

                return existingPlanContratado;
            })
            .map(planContratadoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlanContratado> findAll(Pageable pageable) {
        log.debug("Request to get all PlanContratados");
        return planContratadoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PlanContratado> findOne(Long id) {
        log.debug("Request to get PlanContratado : {}", id);
        return planContratadoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PlanContratado : {}", id);
        planContratadoRepository.deleteById(id);
    }
}
