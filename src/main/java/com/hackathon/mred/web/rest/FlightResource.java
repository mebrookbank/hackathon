package com.hackathon.mred.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hackathon.mred.domain.Flight;
import com.hackathon.mred.service.FlightService;
import com.hackathon.mred.web.rest.util.HeaderUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Flight.
 */
@RestController
@RequestMapping("/api")
public class FlightResource {

    private final Logger log = LoggerFactory.getLogger(FlightResource.class);
        
    @Inject
    private FlightService flightService;

    /**
     * POST  /flights : Create a new flight.
     *
     * @param flight the flight to create
     * @return the ResponseEntity with status 201 (Created) and with body the new flight, or with status 400 (Bad Request) if the flight has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/flights")
    @Timed
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight) throws URISyntaxException {
        log.debug("REST request to save Flight : {}", flight);
        if (flight.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("flight", "idexists", "A new flight cannot already have an ID")).body(null);
        }
        Flight result = flightService.save(flight);
        return ResponseEntity.created(new URI("/api/flights/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("flight", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /flights : Updates an existing flight.
     *
     * @param flight the flight to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated flight,
     * or with status 400 (Bad Request) if the flight is not valid,
     * or with status 500 (Internal Server Error) if the flight couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/flights")
    @Timed
    public ResponseEntity<Flight> updateFlight(@RequestBody Flight flight) throws URISyntaxException {
        log.debug("REST request to update Flight : {}", flight);
        if (flight.getId() == null) {
            return createFlight(flight);
        }
        Flight result = flightService.save(flight);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("flight", flight.getId().toString()))
            .body(result);
    }

    /**
     * GET  /flights : get all the flights.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of flights in body
     */
    @GetMapping("/flights")
    @Timed
    public List<Flight> getAllFlights() {
        log.debug("REST request to get all Flights");
        return flightService.findAll();
    }

    /**
     * GET  /flights/:id : get the "id" flight.
     *
     * @param id the id of the flight to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the flight, or with status 404 (Not Found)
     */
    @GetMapping("/flights/{id}")
    @Timed
    public ResponseEntity<Flight> getFlight(@PathVariable Long id) {
        log.debug("REST request to get Flight : {}", id);
        Flight flight = flightService.findOne(id);
        return Optional.ofNullable(flight)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /flights/:id : delete the "id" flight.
     *
     * @param id the id of the flight to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/flights/{id}")
    @Timed
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        log.debug("REST request to delete Flight : {}", id);
        flightService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("flight", id.toString())).build();
    }

}
