package com.hackathon.mred.service.impl;

import com.hackathon.mred.service.FlightService;
import com.hackathon.mred.domain.Flight;
import com.hackathon.mred.repository.FlightRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Flight.
 */
@Service
@Transactional
public class FlightServiceImpl implements FlightService{

    private final Logger log = LoggerFactory.getLogger(FlightServiceImpl.class);
    
    @Inject
    private FlightRepository flightRepository;

    /**
     * Save a flight.
     *
     * @param flight the entity to save
     * @return the persisted entity
     */
    public Flight save(Flight flight) {
        log.debug("Request to save Flight : {}", flight);
        Flight result = flightRepository.save(flight);
        return result;
    }

    /**
     *  Get all the flights.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Flight> findAll() {
        log.debug("Request to get all Flights");
        List<Flight> result = flightRepository.findAll();

        return result;
    }

    /**
     *  Get one flight by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Flight findOne(Long id) {
        log.debug("Request to get Flight : {}", id);
        Flight flight = flightRepository.findOne(id);
        return flight;
    }

    /**
     *  Delete the  flight by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Flight : {}", id);
        flightRepository.delete(id);
    }
}
