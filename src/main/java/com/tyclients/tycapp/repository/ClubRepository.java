package com.tyclients.tycapp.repository;

import com.tyclients.tycapp.domain.AdminClub;
import com.tyclients.tycapp.domain.Club;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Club entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
	Optional<Club> findByAdminClub(Optional<AdminClub> adminClubResult);
}
