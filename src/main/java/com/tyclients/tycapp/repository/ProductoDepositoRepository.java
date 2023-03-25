package com.tyclients.tycapp.repository;

import com.tyclients.tycapp.domain.ProductoDeposito;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductoDeposito entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductoDepositoRepository extends JpaRepository<ProductoDeposito, Long> {}
