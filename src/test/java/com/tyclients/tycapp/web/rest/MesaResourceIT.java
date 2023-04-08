package com.tyclients.tycapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tyclients.tycapp.IntegrationTest;
import com.tyclients.tycapp.domain.Mesa;
import com.tyclients.tycapp.repository.MesaRepository;
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
 * Integration tests for the {@link MesaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MesaResourceIT {

    private static final Integer DEFAULT_NUMERO_MESA = 1;
    private static final Integer UPDATED_NUMERO_MESA = 2;

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/mesas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMesaMockMvc;

    private Mesa mesa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mesa createEntity(EntityManager em) {
        Mesa mesa = new Mesa()
            .numeroMesa(DEFAULT_NUMERO_MESA)
            .estado(DEFAULT_ESTADO)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return mesa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mesa createUpdatedEntity(EntityManager em) {
        Mesa mesa = new Mesa()
            .numeroMesa(UPDATED_NUMERO_MESA)
            .estado(UPDATED_ESTADO)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        return mesa;
    }

    @BeforeEach
    public void initTest() {
        mesa = createEntity(em);
    }

    @Test
    @Transactional
    void createMesa() throws Exception {
        int databaseSizeBeforeCreate = mesaRepository.findAll().size();
        // Create the Mesa
        restMesaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mesa)))
            .andExpect(status().isCreated());

        // Validate the Mesa in the database
        List<Mesa> mesaList = mesaRepository.findAll();
        assertThat(mesaList).hasSize(databaseSizeBeforeCreate + 1);
        Mesa testMesa = mesaList.get(mesaList.size() - 1);
        assertThat(testMesa.getNumeroMesa()).isEqualTo(DEFAULT_NUMERO_MESA);
        assertThat(testMesa.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testMesa.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testMesa.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createMesaWithExistingId() throws Exception {
        // Create the Mesa with an existing ID
        mesa.setId(1L);

        int databaseSizeBeforeCreate = mesaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMesaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mesa)))
            .andExpect(status().isBadRequest());

        // Validate the Mesa in the database
        List<Mesa> mesaList = mesaRepository.findAll();
        assertThat(mesaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = mesaRepository.findAll().size();
        // set the field null
        mesa.setEstado(null);

        // Create the Mesa, which fails.

        restMesaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mesa)))
            .andExpect(status().isBadRequest());

        List<Mesa> mesaList = mesaRepository.findAll();
        assertThat(mesaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMesas() throws Exception {
        // Initialize the database
        mesaRepository.saveAndFlush(mesa);

        // Get all the mesaList
        restMesaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mesa.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroMesa").value(hasItem(DEFAULT_NUMERO_MESA)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getMesa() throws Exception {
        // Initialize the database
        mesaRepository.saveAndFlush(mesa);

        // Get the mesa
        restMesaMockMvc
            .perform(get(ENTITY_API_URL_ID, mesa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mesa.getId().intValue()))
            .andExpect(jsonPath("$.numeroMesa").value(DEFAULT_NUMERO_MESA))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMesa() throws Exception {
        // Get the mesa
        restMesaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMesa() throws Exception {
        // Initialize the database
        mesaRepository.saveAndFlush(mesa);

        int databaseSizeBeforeUpdate = mesaRepository.findAll().size();

        // Update the mesa
        Mesa updatedMesa = mesaRepository.findById(mesa.getId()).get();
        // Disconnect from session so that the updates on updatedMesa are not directly saved in db
        em.detach(updatedMesa);
        updatedMesa
            .numeroMesa(UPDATED_NUMERO_MESA)
            .estado(UPDATED_ESTADO)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restMesaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMesa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMesa))
            )
            .andExpect(status().isOk());

        // Validate the Mesa in the database
        List<Mesa> mesaList = mesaRepository.findAll();
        assertThat(mesaList).hasSize(databaseSizeBeforeUpdate);
        Mesa testMesa = mesaList.get(mesaList.size() - 1);
        assertThat(testMesa.getNumeroMesa()).isEqualTo(UPDATED_NUMERO_MESA);
        assertThat(testMesa.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testMesa.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testMesa.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingMesa() throws Exception {
        int databaseSizeBeforeUpdate = mesaRepository.findAll().size();
        mesa.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMesaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mesa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mesa))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mesa in the database
        List<Mesa> mesaList = mesaRepository.findAll();
        assertThat(mesaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMesa() throws Exception {
        int databaseSizeBeforeUpdate = mesaRepository.findAll().size();
        mesa.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMesaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mesa))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mesa in the database
        List<Mesa> mesaList = mesaRepository.findAll();
        assertThat(mesaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMesa() throws Exception {
        int databaseSizeBeforeUpdate = mesaRepository.findAll().size();
        mesa.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMesaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mesa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Mesa in the database
        List<Mesa> mesaList = mesaRepository.findAll();
        assertThat(mesaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMesaWithPatch() throws Exception {
        // Initialize the database
        mesaRepository.saveAndFlush(mesa);

        int databaseSizeBeforeUpdate = mesaRepository.findAll().size();

        // Update the mesa using partial update
        Mesa partialUpdatedMesa = new Mesa();
        partialUpdatedMesa.setId(mesa.getId());

        partialUpdatedMesa.updatedDate(UPDATED_UPDATED_DATE);

        restMesaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMesa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMesa))
            )
            .andExpect(status().isOk());

        // Validate the Mesa in the database
        List<Mesa> mesaList = mesaRepository.findAll();
        assertThat(mesaList).hasSize(databaseSizeBeforeUpdate);
        Mesa testMesa = mesaList.get(mesaList.size() - 1);
        assertThat(testMesa.getNumeroMesa()).isEqualTo(DEFAULT_NUMERO_MESA);
        assertThat(testMesa.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testMesa.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testMesa.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateMesaWithPatch() throws Exception {
        // Initialize the database
        mesaRepository.saveAndFlush(mesa);

        int databaseSizeBeforeUpdate = mesaRepository.findAll().size();

        // Update the mesa using partial update
        Mesa partialUpdatedMesa = new Mesa();
        partialUpdatedMesa.setId(mesa.getId());

        partialUpdatedMesa
            .numeroMesa(UPDATED_NUMERO_MESA)
            .estado(UPDATED_ESTADO)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restMesaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMesa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMesa))
            )
            .andExpect(status().isOk());

        // Validate the Mesa in the database
        List<Mesa> mesaList = mesaRepository.findAll();
        assertThat(mesaList).hasSize(databaseSizeBeforeUpdate);
        Mesa testMesa = mesaList.get(mesaList.size() - 1);
        assertThat(testMesa.getNumeroMesa()).isEqualTo(UPDATED_NUMERO_MESA);
        assertThat(testMesa.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testMesa.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testMesa.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingMesa() throws Exception {
        int databaseSizeBeforeUpdate = mesaRepository.findAll().size();
        mesa.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMesaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mesa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mesa))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mesa in the database
        List<Mesa> mesaList = mesaRepository.findAll();
        assertThat(mesaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMesa() throws Exception {
        int databaseSizeBeforeUpdate = mesaRepository.findAll().size();
        mesa.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMesaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mesa))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mesa in the database
        List<Mesa> mesaList = mesaRepository.findAll();
        assertThat(mesaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMesa() throws Exception {
        int databaseSizeBeforeUpdate = mesaRepository.findAll().size();
        mesa.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMesaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(mesa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Mesa in the database
        List<Mesa> mesaList = mesaRepository.findAll();
        assertThat(mesaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMesa() throws Exception {
        // Initialize the database
        mesaRepository.saveAndFlush(mesa);

        int databaseSizeBeforeDelete = mesaRepository.findAll().size();

        // Delete the mesa
        restMesaMockMvc
            .perform(delete(ENTITY_API_URL_ID, mesa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Mesa> mesaList = mesaRepository.findAll();
        assertThat(mesaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
