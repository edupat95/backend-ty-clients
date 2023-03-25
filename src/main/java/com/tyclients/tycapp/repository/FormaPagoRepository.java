package com.tyclients.tycapp.repository;

import com.tyclients.tycapp.domain.Club;
import com.tyclients.tycapp.domain.FormaPago;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FormaPago entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormaPagoRepository extends JpaRepository<FormaPago, Long> {

	//List<FormaPago> findByClub(Club club);

	List<FormaPago> findByClubAndEstado(Club club, boolean estado);
	
}
