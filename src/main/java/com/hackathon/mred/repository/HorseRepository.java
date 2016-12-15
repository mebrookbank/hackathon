package com.hackathon.mred.repository;

import com.hackathon.mred.domain.Horse;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Horse entity.
 */
@SuppressWarnings("unused")
public interface HorseRepository extends JpaRepository<Horse,Long> {

}
