package com.hackathon.mred.web.rest;

import com.hackathon.mred.MredApp;

import com.hackathon.mred.domain.Flight;
import com.hackathon.mred.repository.FlightRepository;
import com.hackathon.mred.service.FlightService;

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
 * Test class for the FlightResource REST controller.
 *
 * @see FlightResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MredApp.class)
public class FlightResourceIntTest {

    private static final String DEFAULT_TAILNO = "AAAAAAAAAA";
    private static final String UPDATED_TAILNO = "BBBBBBBBBB";

    private static final String DEFAULT_PILOTNAME = "AAAAAAAAAA";
    private static final String UPDATED_PILOTNAME = "BBBBBBBBBB";

    private static final Float DEFAULT_STARTLOCX = 1F;
    private static final Float UPDATED_STARTLOCX = 2F;

    private static final Float DEFAULT_STARTLOCY = 1F;
    private static final Float UPDATED_STARTLOCY = 2F;

    private static final Float DEFAULT_ENDLOCX = 1F;
    private static final Float UPDATED_ENDLOCX = 2F;

    private static final Float DEFAULT_ENDLOCY = 1F;
    private static final Float UPDATED_ENDLOCY = 2F;

    private static final ZonedDateTime DEFAULT_STARTTIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_STARTTIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_ENDTIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ENDTIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    @Inject
    private FlightRepository flightRepository;

    @Inject
    private FlightService flightService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restFlightMockMvc;

    private Flight flight;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FlightResource flightResource = new FlightResource();
        ReflectionTestUtils.setField(flightResource, "flightService", flightService);
        this.restFlightMockMvc = MockMvcBuilders.standaloneSetup(flightResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Flight createEntity(EntityManager em) {
        Flight flight = new Flight()
                .tailno(DEFAULT_TAILNO)
                .pilotname(DEFAULT_PILOTNAME)
                .startlocx(DEFAULT_STARTLOCX)
                .startlocy(DEFAULT_STARTLOCY)
                .endlocx(DEFAULT_ENDLOCX)
                .endlocy(DEFAULT_ENDLOCY)
                .starttime(DEFAULT_STARTTIME)
                .endtime(DEFAULT_ENDTIME)
                .phone(DEFAULT_PHONE);
        return flight;
    }

    @Before
    public void initTest() {
        flight = createEntity(em);
    }

    @Test
    @Transactional
    public void createFlight() throws Exception {
        int databaseSizeBeforeCreate = flightRepository.findAll().size();

        // Create the Flight

        restFlightMockMvc.perform(post("/api/flights")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(flight)))
            .andExpect(status().isCreated());

        // Validate the Flight in the database
        List<Flight> flightList = flightRepository.findAll();
        assertThat(flightList).hasSize(databaseSizeBeforeCreate + 1);
        Flight testFlight = flightList.get(flightList.size() - 1);
        assertThat(testFlight.getTailno()).isEqualTo(DEFAULT_TAILNO);
        assertThat(testFlight.getPilotname()).isEqualTo(DEFAULT_PILOTNAME);
        assertThat(testFlight.getStartlocx()).isEqualTo(DEFAULT_STARTLOCX);
        assertThat(testFlight.getStartlocy()).isEqualTo(DEFAULT_STARTLOCY);
        assertThat(testFlight.getEndlocx()).isEqualTo(DEFAULT_ENDLOCX);
        assertThat(testFlight.getEndlocy()).isEqualTo(DEFAULT_ENDLOCY);
        assertThat(testFlight.getStarttime()).isEqualTo(DEFAULT_STARTTIME);
        assertThat(testFlight.getEndtime()).isEqualTo(DEFAULT_ENDTIME);
        assertThat(testFlight.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    public void createFlightWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = flightRepository.findAll().size();

        // Create the Flight with an existing ID
        Flight existingFlight = new Flight();
        existingFlight.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFlightMockMvc.perform(post("/api/flights")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingFlight)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Flight> flightList = flightRepository.findAll();
        assertThat(flightList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFlights() throws Exception {
        // Initialize the database
        flightRepository.saveAndFlush(flight);

        // Get all the flightList
        restFlightMockMvc.perform(get("/api/flights?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(flight.getId().intValue())))
            .andExpect(jsonPath("$.[*].tailno").value(hasItem(DEFAULT_TAILNO.toString())))
            .andExpect(jsonPath("$.[*].pilotname").value(hasItem(DEFAULT_PILOTNAME.toString())))
            .andExpect(jsonPath("$.[*].startlocx").value(hasItem(DEFAULT_STARTLOCX.doubleValue())))
            .andExpect(jsonPath("$.[*].startlocy").value(hasItem(DEFAULT_STARTLOCY.doubleValue())))
            .andExpect(jsonPath("$.[*].endlocx").value(hasItem(DEFAULT_ENDLOCX.doubleValue())))
            .andExpect(jsonPath("$.[*].endlocy").value(hasItem(DEFAULT_ENDLOCY.doubleValue())))
            .andExpect(jsonPath("$.[*].starttime").value(hasItem(sameInstant(DEFAULT_STARTTIME))))
            .andExpect(jsonPath("$.[*].endtime").value(hasItem(sameInstant(DEFAULT_ENDTIME))))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())));
    }

    @Test
    @Transactional
    public void getFlight() throws Exception {
        // Initialize the database
        flightRepository.saveAndFlush(flight);

        // Get the flight
        restFlightMockMvc.perform(get("/api/flights/{id}", flight.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(flight.getId().intValue()))
            .andExpect(jsonPath("$.tailno").value(DEFAULT_TAILNO.toString()))
            .andExpect(jsonPath("$.pilotname").value(DEFAULT_PILOTNAME.toString()))
            .andExpect(jsonPath("$.startlocx").value(DEFAULT_STARTLOCX.doubleValue()))
            .andExpect(jsonPath("$.startlocy").value(DEFAULT_STARTLOCY.doubleValue()))
            .andExpect(jsonPath("$.endlocx").value(DEFAULT_ENDLOCX.doubleValue()))
            .andExpect(jsonPath("$.endlocy").value(DEFAULT_ENDLOCY.doubleValue()))
            .andExpect(jsonPath("$.starttime").value(sameInstant(DEFAULT_STARTTIME)))
            .andExpect(jsonPath("$.endtime").value(sameInstant(DEFAULT_ENDTIME)))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFlight() throws Exception {
        // Get the flight
        restFlightMockMvc.perform(get("/api/flights/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFlight() throws Exception {
        // Initialize the database
        flightService.save(flight);

        int databaseSizeBeforeUpdate = flightRepository.findAll().size();

        // Update the flight
        Flight updatedFlight = flightRepository.findOne(flight.getId());
        updatedFlight
                .tailno(UPDATED_TAILNO)
                .pilotname(UPDATED_PILOTNAME)
                .startlocx(UPDATED_STARTLOCX)
                .startlocy(UPDATED_STARTLOCY)
                .endlocx(UPDATED_ENDLOCX)
                .endlocy(UPDATED_ENDLOCY)
                .starttime(UPDATED_STARTTIME)
                .endtime(UPDATED_ENDTIME)
                .phone(UPDATED_PHONE);

        restFlightMockMvc.perform(put("/api/flights")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFlight)))
            .andExpect(status().isOk());

        // Validate the Flight in the database
        List<Flight> flightList = flightRepository.findAll();
        assertThat(flightList).hasSize(databaseSizeBeforeUpdate);
        Flight testFlight = flightList.get(flightList.size() - 1);
        assertThat(testFlight.getTailno()).isEqualTo(UPDATED_TAILNO);
        assertThat(testFlight.getPilotname()).isEqualTo(UPDATED_PILOTNAME);
        assertThat(testFlight.getStartlocx()).isEqualTo(UPDATED_STARTLOCX);
        assertThat(testFlight.getStartlocy()).isEqualTo(UPDATED_STARTLOCY);
        assertThat(testFlight.getEndlocx()).isEqualTo(UPDATED_ENDLOCX);
        assertThat(testFlight.getEndlocy()).isEqualTo(UPDATED_ENDLOCY);
        assertThat(testFlight.getStarttime()).isEqualTo(UPDATED_STARTTIME);
        assertThat(testFlight.getEndtime()).isEqualTo(UPDATED_ENDTIME);
        assertThat(testFlight.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingFlight() throws Exception {
        int databaseSizeBeforeUpdate = flightRepository.findAll().size();

        // Create the Flight

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFlightMockMvc.perform(put("/api/flights")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(flight)))
            .andExpect(status().isCreated());

        // Validate the Flight in the database
        List<Flight> flightList = flightRepository.findAll();
        assertThat(flightList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFlight() throws Exception {
        // Initialize the database
        flightService.save(flight);

        int databaseSizeBeforeDelete = flightRepository.findAll().size();

        // Get the flight
        restFlightMockMvc.perform(delete("/api/flights/{id}", flight.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Flight> flightList = flightRepository.findAll();
        assertThat(flightList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
