package com.hackathon.mred.service.impl;

import com.hackathon.mred.service.HorseService;
import com.hackathon.mred.domain.Horse;
import com.hackathon.mred.repository.HorseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Horse.
 */
@Service
@Transactional
public class HorseServiceImpl implements HorseService{

    private final Logger log = LoggerFactory.getLogger(HorseServiceImpl.class);
    
    @Inject
    private HorseRepository horseRepository;

    /**
     * Save a horse.
     *
     * @param horse the entity to save
     * @return the persisted entity
     */
    public Horse save(Horse horse) {
        log.debug("Request to save Horse : {}", horse);
        Horse result = horseRepository.save(horse);
        return result;
    }

    /**
     *  Get all the horses.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Horse> findAll() {
        log.debug("Request to get all Horses");
        List<Horse> result = horseRepository.findAll();

        return result;
    }

    /**
     *  Get one horse by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Horse findOne(Long id) {
        log.debug("Request to get Horse : {}", id);
        Horse horse = horseRepository.findOne(id);
        return horse;
    }

    /**
     *  Delete the  horse by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Horse : {}", id);
        horseRepository.delete(id);
    }
}
