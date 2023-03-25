package com.tyclients.tycapp.repository;

import com.tyclients.tycapp.domain.AdminClub;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AdminClub entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdminClubRepository extends JpaRepository<AdminClub, Long> {
	Optional<AdminClub> findByUserId(Long user_id);
}
