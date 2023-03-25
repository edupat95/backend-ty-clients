package com.tyclients.tycapp.repository;

import com.tyclients.tycapp.domain.Acceso;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Acceso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccesoRepository extends JpaRepository<Acceso, Long> {}
