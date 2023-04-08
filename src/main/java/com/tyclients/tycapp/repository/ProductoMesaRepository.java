package com.tyclients.tycapp.repository;

import com.tyclients.tycapp.domain.Mesa;
import com.tyclients.tycapp.domain.Producto;
import com.tyclients.tycapp.domain.ProductoMesa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductoMesa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductoMesaRepository extends JpaRepository<ProductoMesa, Long> {

	List<ProductoMesa> findByMesa(Mesa mesa);

	Object deleteByMesa(Optional<Mesa> mesa);

	Optional<ProductoMesa> findByProductoAndMesa(Producto producto, Mesa mesa);
	
}
