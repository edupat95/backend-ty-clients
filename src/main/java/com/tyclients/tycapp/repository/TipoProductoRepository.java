package com.tyclients.tycapp.repository;

import com.tyclients.tycapp.domain.TipoProducto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TipoProducto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoProductoRepository extends JpaRepository<TipoProducto, Long> {}
