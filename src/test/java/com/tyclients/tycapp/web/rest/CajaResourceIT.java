package com.tyclients.tycapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tyclients.tycapp.IntegrationTest;
import com.tyclients.tycapp.domain.Caja;
import com.tyclients.tycapp.domain.Club;
import com.tyclients.tycapp.repository.CajaRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CajaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CajaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/cajas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CajaRepository cajaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCajaMockMvc;

    private Caja caja;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Caja createEntity(EntityManager em) {
        Caja caja = new Caja()
            .nombre(DEFAULT_NOMBRE)
            .estado(DEFAULT_ESTADO)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        // Add required entity
        Club club;
        if (TestUtil.findAll(em, Club.class).isEmpty()) {
            club = ClubResourceIT.createEntity(em);
            em.persist(club);
            em.flush();
        } else {
            club = TestUtil.findAll(em, Club.class).get(0);
        }
        caja.setClub(club);
        return caja;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Caja createUpdatedEntity(EntityManager em) {
        Caja caja = new Caja()
            .nombre(UPDATED_NOMBRE)
            .estado(UPDATED_ESTADO)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        // Add required entity
        Club club;
        if (TestUtil.findAll(em, Club.class).isEmpty()) {
            club = ClubResourceIT.createUpdatedEntity(em);
            em.persist(club);
            em.flush();
        } else {
            club = TestUtil.findAll(em, Club.class).get(0);
        }
        caja.setClub(club);
        return caja;
    }

    @BeforeEach
    public void initTest() {
        caja = createEntity(em);
    }

    @Test
    @Transactional
    void createCaja() throws Exception {
        int databaseSizeBeforeCreate = cajaRepository.findAll().size();
        // Create the Caja
        restCajaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(caja)))
            .andExpect(status().isCreated());

        // Validate the Caja in the database
        List<Caja> cajaList = cajaRepository.findAll();
        assertThat(cajaList).hasSize(databaseSizeBeforeCreate + 1);
        Caja testCaja = cajaList.get(cajaList.size() - 1);
        assertThat(testCaja.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testCaja.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testCaja.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCaja.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createCajaWithExistingId() throws Exception {
        // Create the Caja with an existing ID
        caja.setId(1L);

        int databaseSizeBeforeCreate = cajaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCajaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(caja)))
            .andExpect(status().isBadRequest());

        // Validate the Caja in the database
        List<Caja> cajaList = cajaRepository.findAll();
        assertThat(cajaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cajaRepository.findAll().size();
        // set the field null
        caja.setEstado(null);

        // Create the Caja, which fails.

        restCajaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(caja)))
            .andExpect(status().isBadRequest());

        List<Caja> cajaList = cajaRepository.findAll();
        assertThat(cajaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCajas() throws Exception {
        // Initialize the database
        cajaRepository.saveAndFlush(caja);

        // Get all the cajaList
        restCajaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caja.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getCaja() throws Exception {
        // Initialize the database
        cajaRepository.saveAndFlush(caja);

        // Get the caja
        restCajaMockMvc
            .perform(get(ENTITY_API_URL_ID, caja.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(caja.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCaja() throws Exception {
        // Get the caja
        restCajaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCaja() throws Exception {
        // Initialize the database
        cajaRepository.saveAndFlush(caja);

        int databaseSizeBeforeUpdate = cajaRepository.findAll().size();

        // Update the caja
        Caja updatedCaja = cajaRepository.findById(caja.getId()).get();
        // Disconnect from session so that the updates on updatedCaja are not directly saved in db
        em.detach(updatedCaja);
        updatedCaja.nombre(UPDATED_NOMBRE).estado(UPDATED_ESTADO).createdDate(UPDATED_CREATED_DATE).updatedDate(UPDATED_UPDATED_DATE);

        restCajaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCaja.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCaja))
            )
            .andExpect(status().isOk());

        // Validate the Caja in the database
        List<Caja> cajaList = cajaRepository.findAll();
        assertThat(cajaList).hasSize(databaseSizeBeforeUpdate);
        Caja testCaja = cajaList.get(cajaList.size() - 1);
        assertThat(testCaja.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCaja.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testCaja.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCaja.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingCaja() throws Exception {
        int databaseSizeBeforeUpdate = cajaRepository.findAll().size();
        caja.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCajaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, caja.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(caja))
            )
            .andExpect(status().isBadRequest());

        // Validate the Caja in the database
        List<Caja> cajaList = cajaRepository.findAll();
        assertThat(cajaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCaja() throws Exception {
        int databaseSizeBeforeUpdate = cajaRepository.findAll().size();
        caja.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCajaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(caja))
            )
            .andExpect(status().isBadRequest());

        // Validate the Caja in the database
        List<Caja> cajaList = cajaRepository.findAll();
        assertThat(cajaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCaja() throws Exception {
        int databaseSizeBeforeUpdate = cajaRepository.findAll().size();
        caja.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCajaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(caja)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Caja in the database
        List<Caja> cajaList = cajaRepository.findAll();
        assertThat(cajaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCajaWithPatch() throws Exception {
        // Initialize the database
        cajaRepository.saveAndFlush(caja);

        int databaseSizeBeforeUpdate = cajaRepository.findAll().size();

        // Update the caja using partial update
        Caja partialUpdatedCaja = new Caja();
        partialUpdatedCaja.setId(caja.getId());

        partialUpdatedCaja.nombre(UPDATED_NOMBRE);

        restCajaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCaja.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCaja))
            )
            .andExpect(status().isOk());

        // Validate the Caja in the database
        List<Caja> cajaList = cajaRepository.findAll();
        assertThat(cajaList).hasSize(databaseSizeBeforeUpdate);
        Caja testCaja = cajaList.get(cajaList.size() - 1);
        assertThat(testCaja.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCaja.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testCaja.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCaja.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateCajaWithPatch() throws Exception {
        // Initialize the database
        cajaRepository.saveAndFlush(caja);

        int databaseSizeBeforeUpdate = cajaRepository.findAll().size();

        // Update the caja using partial update
        Caja partialUpdatedCaja = new Caja();
        partialUpdatedCaja.setId(caja.getId());

        partialUpdatedCaja
            .nombre(UPDATED_NOMBRE)
            .estado(UPDATED_ESTADO)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restCajaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCaja.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCaja))
            )
            .andExpect(status().isOk());

        // Validate the Caja in the database
        List<Caja> cajaList = cajaRepository.findAll();
        assertThat(cajaList).hasSize(databaseSizeBeforeUpdate);
        Caja testCaja = cajaList.get(cajaList.size() - 1);
        assertThat(testCaja.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCaja.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testCaja.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCaja.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingCaja() throws Exception {
        int databaseSizeBeforeUpdate = cajaRepository.findAll().size();
        caja.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCajaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, caja.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(caja))
            )
            .andExpect(status().isBadRequest());

        // Validate the Caja in the database
        List<Caja> cajaList = cajaRepository.findAll();
        assertThat(cajaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCaja() throws Exception {
        int databaseSizeBeforeUpdate = cajaRepository.findAll().size();
        caja.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCajaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(caja))
            )
            .andExpect(status().isBadRequest());

        // Validate the Caja in the database
        List<Caja> cajaList = cajaRepository.findAll();
        assertThat(cajaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCaja() throws Exception {
        int databaseSizeBeforeUpdate = cajaRepository.findAll().size();
        caja.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCajaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(caja)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Caja in the database
        List<Caja> cajaList = cajaRepository.findAll();
        assertThat(cajaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCaja() throws Exception {
        // Initialize the database
        cajaRepository.saveAndFlush(caja);

        int databaseSizeBeforeDelete = cajaRepository.findAll().size();

        // Delete the caja
        restCajaMockMvc
            .perform(delete(ENTITY_API_URL_ID, caja.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Caja> cajaList = cajaRepository.findAll();
        assertThat(cajaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
