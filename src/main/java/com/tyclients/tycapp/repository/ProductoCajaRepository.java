package com.tyclients.tycapp.repository;

import com.tyclients.tycapp.domain.Caja;
import com.tyclients.tycapp.domain.Producto;
import com.tyclients.tycapp.domain.ProductoCaja;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductoCaja entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductoCajaRepository extends JpaRepository<ProductoCaja, Long> {
	List<ProductoCaja> findByCaja(Optional<Caja> caja);
	
	Optional<ProductoCaja> findByCajaAndProducto(Caja caja, Producto producto);

	void deleteByProducto(Producto producto);
}
