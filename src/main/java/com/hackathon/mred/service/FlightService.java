package com.hackathon.mred.service;

import com.hackathon.mred.domain.Flight;
import java.util.List;

/**
 * Service Interface for managing Flight.
 */
public interface FlightService {

    /**
     * Save a flight.
     *
     * @param flight the entity to save
     * @return the persisted entity
     */
    Flight save(Flight flight);

    /**
     *  Get all the flights.
     *  
     *  @return the list of entities
     */
    List<Flight> findAll();

    /**
     *  Get the "id" flight.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Flight findOne(Long id);

    /**
     *  Delete the "id" flight.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
