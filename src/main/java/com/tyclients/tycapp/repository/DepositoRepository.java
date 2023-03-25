package com.tyclients.tycapp.repository;

import com.tyclients.tycapp.domain.Deposito;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Deposito entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepositoRepository extends JpaRepository<Deposito, Long> {}
