package com.tyclients.tycapp.repository;

import com.tyclients.tycapp.domain.Venta;
import com.tyclients.tycapp.domain.ProductoVenta;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductoVenta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductoVentaRepository extends JpaRepository<ProductoVenta, Long> {
	List<ProductoVenta> findByVenta(Venta venta);
}
