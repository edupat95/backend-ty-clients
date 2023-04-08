package com.tyclients.tycapp.repository;

import com.tyclients.tycapp.domain.PlanContratado;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PlanContratado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanContratadoRepository extends JpaRepository<PlanContratado, Long> {}
