package com.hackathon.mred.web.rest;

import com.hackathon.mred.MredApp;

import com.hackathon.mred.domain.Horse;
import com.hackathon.mred.repository.HorseRepository;
import com.hackathon.mred.service.HorseService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.hackathon.mred.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the HorseResource REST controller.
 *
 * @see HorseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MredApp.class)
public class HorseResourceIntTest {

    private static final String DEFAULT_HORSENAME = "AAAAAAAAAA";
    private static final String UPDATED_HORSENAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_OWNERNAME = "AAAAAAAAAA";
    private static final String UPDATED_OWNERNAME = "BBBBBBBBBB";

    private static final Double DEFAULT_LOCATIONX = -180D;
    private static final Double UPDATED_LOCATIONX = -179D;

    private static final Double DEFAULT_LOCATIONY = -90D;
    private static final Double UPDATED_LOCATIONY = -89D;

    private static final ZonedDateTime DEFAULT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_POINT = "AAAAAAAAAA";
    private static final String UPDATED_POINT = "BBBBBBBBBB";

    @Inject
    private HorseRepository horseRepository;

    @Inject
    private HorseService horseService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restHorseMockMvc;

    private Horse horse;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HorseResource horseResource = new HorseResource();
        ReflectionTestUtils.setField(horseResource, "horseService", horseService);
        this.restHorseMockMvc = MockMvcBuilders.standaloneSetup(horseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Horse createEntity(EntityManager em) {
        Horse horse = new Horse()
                .horsename(DEFAULT_HORSENAME)
                .phone(DEFAULT_PHONE)
                .ownername(DEFAULT_OWNERNAME)
                .locationx(DEFAULT_LOCATIONX)
                .locationy(DEFAULT_LOCATIONY)
                .time(DEFAULT_TIME)
                .point(DEFAULT_POINT);
        return horse;
    }

    @Before
    public void initTest() {
        horse = createEntity(em);
    }

    @Test
    @Transactional
    public void createHorse() throws Exception {
        int databaseSizeBeforeCreate = horseRepository.findAll().size();

        // Create the Horse

        restHorseMockMvc.perform(post("/api/horses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horse)))
            .andExpect(status().isCreated());

        // Validate the Horse in the database
        List<Horse> horseList = horseRepository.findAll();
        assertThat(horseList).hasSize(databaseSizeBeforeCreate + 1);
        Horse testHorse = horseList.get(horseList.size() - 1);
        assertThat(testHorse.getHorsename()).isEqualTo(DEFAULT_HORSENAME);
        assertThat(testHorse.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testHorse.getOwnername()).isEqualTo(DEFAULT_OWNERNAME);
        assertThat(testHorse.getLocationx()).isEqualTo(DEFAULT_LOCATIONX);
        assertThat(testHorse.getLocationy()).isEqualTo(DEFAULT_LOCATIONY);
        assertThat(testHorse.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testHorse.getPoint()).isEqualTo(DEFAULT_POINT);
    }

    @Test
    @Transactional
    public void createHorseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = horseRepository.findAll().size();

        // Create the Horse with an existing ID
        Horse existingHorse = new Horse();
        existingHorse.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHorseMockMvc.perform(post("/api/horses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingHorse)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Horse> horseList = horseRepository.findAll();
        assertThat(horseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLocationxIsRequired() throws Exception {
        int databaseSizeBeforeTest = horseRepository.findAll().size();
        // set the field null
        horse.setLocationx(null);

        // Create the Horse, which fails.

        restHorseMockMvc.perform(post("/api/horses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horse)))
            .andExpect(status().isBadRequest());

        List<Horse> horseList = horseRepository.findAll();
        assertThat(horseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocationyIsRequired() throws Exception {
        int databaseSizeBeforeTest = horseRepository.findAll().size();
        // set the field null
        horse.setLocationy(null);

        // Create the Horse, which fails.

        restHorseMockMvc.perform(post("/api/horses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horse)))
            .andExpect(status().isBadRequest());

        List<Horse> horseList = horseRepository.findAll();
        assertThat(horseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHorses() throws Exception {
        // Initialize the database
        horseRepository.saveAndFlush(horse);

        // Get all the horseList
        restHorseMockMvc.perform(get("/api/horses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(horse.getId().intValue())))
            .andExpect(jsonPath("$.[*].horsename").value(hasItem(DEFAULT_HORSENAME.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].ownername").value(hasItem(DEFAULT_OWNERNAME.toString())))
            .andExpect(jsonPath("$.[*].locationx").value(hasItem(DEFAULT_LOCATIONX.doubleValue())))
            .andExpect(jsonPath("$.[*].locationy").value(hasItem(DEFAULT_LOCATIONY.doubleValue())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(sameInstant(DEFAULT_TIME))))
            .andExpect(jsonPath("$.[*].point").value(hasItem(DEFAULT_POINT.toString())));
    }

    @Test
    @Transactional
    public void getHorse() throws Exception {
        // Initialize the database
        horseRepository.saveAndFlush(horse);

        // Get the horse
        restHorseMockMvc.perform(get("/api/horses/{id}", horse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(horse.getId().intValue()))
            .andExpect(jsonPath("$.horsename").value(DEFAULT_HORSENAME.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.ownername").value(DEFAULT_OWNERNAME.toString()))
            .andExpect(jsonPath("$.locationx").value(DEFAULT_LOCATIONX.doubleValue()))
            .andExpect(jsonPath("$.locationy").value(DEFAULT_LOCATIONY.doubleValue()))
            .andExpect(jsonPath("$.time").value(sameInstant(DEFAULT_TIME)))
            .andExpect(jsonPath("$.point").value(DEFAULT_POINT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHorse() throws Exception {
        // Get the horse
        restHorseMockMvc.perform(get("/api/horses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHorse() throws Exception {
        // Initialize the database
        horseService.save(horse);

        int databaseSizeBeforeUpdate = horseRepository.findAll().size();

        // Update the horse
        Horse updatedHorse = horseRepository.findOne(horse.getId());
        updatedHorse
                .horsename(UPDATED_HORSENAME)
                .phone(UPDATED_PHONE)
                .ownername(UPDATED_OWNERNAME)
                .locationx(UPDATED_LOCATIONX)
                .locationy(UPDATED_LOCATIONY)
                .time(UPDATED_TIME)
                .point(UPDATED_POINT);

        restHorseMockMvc.perform(put("/api/horses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHorse)))
            .andExpect(status().isOk());

        // Validate the Horse in the database
        List<Horse> horseList = horseRepository.findAll();
        assertThat(horseList).hasSize(databaseSizeBeforeUpdate);
        Horse testHorse = horseList.get(horseList.size() - 1);
        assertThat(testHorse.getHorsename()).isEqualTo(UPDATED_HORSENAME);
        assertThat(testHorse.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testHorse.getOwnername()).isEqualTo(UPDATED_OWNERNAME);
        assertThat(testHorse.getLocationx()).isEqualTo(UPDATED_LOCATIONX);
        assertThat(testHorse.getLocationy()).isEqualTo(UPDATED_LOCATIONY);
        assertThat(testHorse.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testHorse.getPoint()).isEqualTo(UPDATED_POINT);
    }

    @Test
    @Transactional
    public void updateNonExistingHorse() throws Exception {
        int databaseSizeBeforeUpdate = horseRepository.findAll().size();

        // Create the Horse

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHorseMockMvc.perform(put("/api/horses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horse)))
            .andExpect(status().isCreated());

        // Validate the Horse in the database
        List<Horse> horseList = horseRepository.findAll();
        assertThat(horseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHorse() throws Exception {
        // Initialize the database
        horseService.save(horse);

        int databaseSizeBeforeDelete = horseRepository.findAll().size();

        // Get the horse
        restHorseMockMvc.perform(delete("/api/horses/{id}", horse.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Horse> horseList = horseRepository.findAll();
        assertThat(horseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
