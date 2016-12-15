package com.hackathon.mred.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hackathon.mred.domain.Horse;
import com.hackathon.mred.service.HorseService;
import com.hackathon.mred.web.rest.util.HeaderUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Horse.
 */
@RestController
@RequestMapping("/api")
public class HorseResource {

    private final Logger log = LoggerFactory.getLogger(HorseResource.class);
        
    @Inject
    private HorseService horseService;

    /**
     * POST  /horses : Create a new horse.
     *
     * @param horse the horse to create
     * @return the ResponseEntity with status 201 (Created) and with body the new horse, or with status 400 (Bad Request) if the horse has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/horses")
    @Timed
    public ResponseEntity<Horse> createHorse(@Valid @RequestBody Horse horse) throws URISyntaxException {
        log.debug("REST request to save Horse : {}", horse);
        if (horse.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("horse", "idexists", "A new horse cannot already have an ID")).body(null);
        }
        Horse result = horseService.save(horse);
        return ResponseEntity.created(new URI("/api/horses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("horse", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /horses : Updates an existing horse.
     *
     * @param horse the horse to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated horse,
     * or with status 400 (Bad Request) if the horse is not valid,
     * or with status 500 (Internal Server Error) if the horse couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/horses")
    @Timed
    public ResponseEntity<Horse> updateHorse(@Valid @RequestBody Horse horse) throws URISyntaxException {
        log.debug("REST request to update Horse : {}", horse);
        if (horse.getId() == null) {
            return createHorse(horse);
        }
        Horse result = horseService.save(horse);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("horse", horse.getId().toString()))
            .body(result);
    }

    /**
     * GET  /horses : get all the horses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of horses in body
     */
    @GetMapping("/horses")
    @Timed
    public List<Horse> getAllHorses() {
        log.debug("REST request to get all Horses");
        return horseService.findAll();
    }

    /**
     * GET  /horses/:id : get the "id" horse.
     *
     * @param id the id of the horse to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the horse, or with status 404 (Not Found)
     */
    @GetMapping("/horses/{id}")
    @Timed
    public ResponseEntity<Horse> getHorse(@PathVariable Long id) {
        log.debug("REST request to get Horse : {}", id);
        Horse horse = horseService.findOne(id);
        return Optional.ofNullable(horse)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /horses/:id : delete the "id" horse.
     *
     * @param id the id of the horse to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/horses/{id}")
    @Timed
    public ResponseEntity<Void> deleteHorse(@PathVariable Long id) {
        log.debug("REST request to delete Horse : {}", id);
        horseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("horse", id.toString())).build();
    }

}
