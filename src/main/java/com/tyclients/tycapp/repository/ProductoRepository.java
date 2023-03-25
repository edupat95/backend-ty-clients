package com.tyclients.tycapp.repository;

import com.tyclients.tycapp.domain.Club;
import com.tyclients.tycapp.domain.Producto;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Producto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
	List<Producto> findByEstado(boolean estado);
	Optional<Producto> findByIdAndEstado(Long id, boolean estado);
	List<Producto> findByClubAndEstado(Club club, Boolean estado);
}
