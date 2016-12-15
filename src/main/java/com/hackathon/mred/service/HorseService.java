package com.hackathon.mred.service;

import com.hackathon.mred.domain.Horse;
import java.util.List;

/**
 * Service Interface for managing Horse.
 */
public interface HorseService {

    /**
     * Save a horse.
     *
     * @param horse the entity to save
     * @return the persisted entity
     */
    Horse save(Horse horse);

    /**
     *  Get all the horses.
     *  
     *  @return the list of entities
     */
    List<Horse> findAll();

    /**
     *  Get the "id" horse.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Horse findOne(Long id);

    /**
     *  Delete the "id" horse.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
