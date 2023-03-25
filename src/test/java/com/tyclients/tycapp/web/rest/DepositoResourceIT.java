package com.tyclients.tycapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tyclients.tycapp.IntegrationTest;
import com.tyclients.tycapp.domain.Club;
import com.tyclients.tycapp.domain.Deposito;
import com.tyclients.tycapp.repository.DepositoRepository;
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
 * Integration tests for the {@link DepositoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DepositoResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/depositos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DepositoRepository depositoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDepositoMockMvc;

    private Deposito deposito;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deposito createEntity(EntityManager em) {
        Deposito deposito = new Deposito()
            .name(DEFAULT_NAME)
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
        deposito.setClub(club);
        return deposito;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deposito createUpdatedEntity(EntityManager em) {
        Deposito deposito = new Deposito()
            .name(UPDATED_NAME)
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
        deposito.setClub(club);
        return deposito;
    }

    @BeforeEach
    public void initTest() {
        deposito = createEntity(em);
    }

    @Test
    @Transactional
    void createDeposito() throws Exception {
        int databaseSizeBeforeCreate = depositoRepository.findAll().size();
        // Create the Deposito
        restDepositoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deposito)))
            .andExpect(status().isCreated());

        // Validate the Deposito in the database
        List<Deposito> depositoList = depositoRepository.findAll();
        assertThat(depositoList).hasSize(databaseSizeBeforeCreate + 1);
        Deposito testDeposito = depositoList.get(depositoList.size() - 1);
        assertThat(testDeposito.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDeposito.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testDeposito.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDeposito.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createDepositoWithExistingId() throws Exception {
        // Create the Deposito with an existing ID
        deposito.setId(1L);

        int databaseSizeBeforeCreate = depositoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepositoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deposito)))
            .andExpect(status().isBadRequest());

        // Validate the Deposito in the database
        List<Deposito> depositoList = depositoRepository.findAll();
        assertThat(depositoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDepositos() throws Exception {
        // Initialize the database
        depositoRepository.saveAndFlush(deposito);

        // Get all the depositoList
        restDepositoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deposito.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getDeposito() throws Exception {
        // Initialize the database
        depositoRepository.saveAndFlush(deposito);

        // Get the deposito
        restDepositoMockMvc
            .perform(get(ENTITY_API_URL_ID, deposito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deposito.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDeposito() throws Exception {
        // Get the deposito
        restDepositoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDeposito() throws Exception {
        // Initialize the database
        depositoRepository.saveAndFlush(deposito);

        int databaseSizeBeforeUpdate = depositoRepository.findAll().size();

        // Update the deposito
        Deposito updatedDeposito = depositoRepository.findById(deposito.getId()).get();
        // Disconnect from session so that the updates on updatedDeposito are not directly saved in db
        em.detach(updatedDeposito);
        updatedDeposito.name(UPDATED_NAME).estado(UPDATED_ESTADO).createdDate(UPDATED_CREATED_DATE).updatedDate(UPDATED_UPDATED_DATE);

        restDepositoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDeposito.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDeposito))
            )
            .andExpect(status().isOk());

        // Validate the Deposito in the database
        List<Deposito> depositoList = depositoRepository.findAll();
        assertThat(depositoList).hasSize(databaseSizeBeforeUpdate);
        Deposito testDeposito = depositoList.get(depositoList.size() - 1);
        assertThat(testDeposito.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDeposito.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testDeposito.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDeposito.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingDeposito() throws Exception {
        int databaseSizeBeforeUpdate = depositoRepository.findAll().size();
        deposito.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepositoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, deposito.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deposito))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deposito in the database
        List<Deposito> depositoList = depositoRepository.findAll();
        assertThat(depositoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDeposito() throws Exception {
        int databaseSizeBeforeUpdate = depositoRepository.findAll().size();
        deposito.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepositoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(deposito))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deposito in the database
        List<Deposito> depositoList = depositoRepository.findAll();
        assertThat(depositoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDeposito() throws Exception {
        int databaseSizeBeforeUpdate = depositoRepository.findAll().size();
        deposito.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepositoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(deposito)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Deposito in the database
        List<Deposito> depositoList = depositoRepository.findAll();
        assertThat(depositoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDepositoWithPatch() throws Exception {
        // Initialize the database
        depositoRepository.saveAndFlush(deposito);

        int databaseSizeBeforeUpdate = depositoRepository.findAll().size();

        // Update the deposito using partial update
        Deposito partialUpdatedDeposito = new Deposito();
        partialUpdatedDeposito.setId(deposito.getId());

        partialUpdatedDeposito.estado(UPDATED_ESTADO);

        restDepositoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeposito.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeposito))
            )
            .andExpect(status().isOk());

        // Validate the Deposito in the database
        List<Deposito> depositoList = depositoRepository.findAll();
        assertThat(depositoList).hasSize(databaseSizeBeforeUpdate);
        Deposito testDeposito = depositoList.get(depositoList.size() - 1);
        assertThat(testDeposito.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDeposito.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testDeposito.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDeposito.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateDepositoWithPatch() throws Exception {
        // Initialize the database
        depositoRepository.saveAndFlush(deposito);

        int databaseSizeBeforeUpdate = depositoRepository.findAll().size();

        // Update the deposito using partial update
        Deposito partialUpdatedDeposito = new Deposito();
        partialUpdatedDeposito.setId(deposito.getId());

        partialUpdatedDeposito
            .name(UPDATED_NAME)
            .estado(UPDATED_ESTADO)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restDepositoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeposito.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeposito))
            )
            .andExpect(status().isOk());

        // Validate the Deposito in the database
        List<Deposito> depositoList = depositoRepository.findAll();
        assertThat(depositoList).hasSize(databaseSizeBeforeUpdate);
        Deposito testDeposito = depositoList.get(depositoList.size() - 1);
        assertThat(testDeposito.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDeposito.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testDeposito.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDeposito.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingDeposito() throws Exception {
        int databaseSizeBeforeUpdate = depositoRepository.findAll().size();
        deposito.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepositoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, deposito.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deposito))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deposito in the database
        List<Deposito> depositoList = depositoRepository.findAll();
        assertThat(depositoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDeposito() throws Exception {
        int databaseSizeBeforeUpdate = depositoRepository.findAll().size();
        deposito.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepositoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(deposito))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deposito in the database
        List<Deposito> depositoList = depositoRepository.findAll();
        assertThat(depositoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDeposito() throws Exception {
        int databaseSizeBeforeUpdate = depositoRepository.findAll().size();
        deposito.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepositoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(deposito)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Deposito in the database
        List<Deposito> depositoList = depositoRepository.findAll();
        assertThat(depositoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDeposito() throws Exception {
        // Initialize the database
        depositoRepository.saveAndFlush(deposito);

        int databaseSizeBeforeDelete = depositoRepository.findAll().size();

        // Delete the deposito
        restDepositoMockMvc
            .perform(delete(ENTITY_API_URL_ID, deposito.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Deposito> depositoList = depositoRepository.findAll();
        assertThat(depositoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
