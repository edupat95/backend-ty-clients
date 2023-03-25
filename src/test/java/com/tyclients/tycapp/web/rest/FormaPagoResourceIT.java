package com.tyclients.tycapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tyclients.tycapp.IntegrationTest;
import com.tyclients.tycapp.domain.Club;
import com.tyclients.tycapp.domain.FormaPago;
import com.tyclients.tycapp.repository.FormaPagoRepository;
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
 * Integration tests for the {@link FormaPagoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FormaPagoResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/forma-pagos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FormaPagoRepository formaPagoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFormaPagoMockMvc;

    private FormaPago formaPago;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormaPago createEntity(EntityManager em) {
        FormaPago formaPago = new FormaPago()
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
        formaPago.setClub(club);
        return formaPago;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormaPago createUpdatedEntity(EntityManager em) {
        FormaPago formaPago = new FormaPago()
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
        formaPago.setClub(club);
        return formaPago;
    }

    @BeforeEach
    public void initTest() {
        formaPago = createEntity(em);
    }

    @Test
    @Transactional
    void createFormaPago() throws Exception {
        int databaseSizeBeforeCreate = formaPagoRepository.findAll().size();
        // Create the FormaPago
        restFormaPagoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(formaPago)))
            .andExpect(status().isCreated());

        // Validate the FormaPago in the database
        List<FormaPago> formaPagoList = formaPagoRepository.findAll();
        assertThat(formaPagoList).hasSize(databaseSizeBeforeCreate + 1);
        FormaPago testFormaPago = formaPagoList.get(formaPagoList.size() - 1);
        assertThat(testFormaPago.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFormaPago.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testFormaPago.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testFormaPago.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createFormaPagoWithExistingId() throws Exception {
        // Create the FormaPago with an existing ID
        formaPago.setId(1L);

        int databaseSizeBeforeCreate = formaPagoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormaPagoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(formaPago)))
            .andExpect(status().isBadRequest());

        // Validate the FormaPago in the database
        List<FormaPago> formaPagoList = formaPagoRepository.findAll();
        assertThat(formaPagoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = formaPagoRepository.findAll().size();
        // set the field null
        formaPago.setEstado(null);

        // Create the FormaPago, which fails.

        restFormaPagoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(formaPago)))
            .andExpect(status().isBadRequest());

        List<FormaPago> formaPagoList = formaPagoRepository.findAll();
        assertThat(formaPagoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFormaPagos() throws Exception {
        // Initialize the database
        formaPagoRepository.saveAndFlush(formaPago);

        // Get all the formaPagoList
        restFormaPagoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formaPago.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getFormaPago() throws Exception {
        // Initialize the database
        formaPagoRepository.saveAndFlush(formaPago);

        // Get the formaPago
        restFormaPagoMockMvc
            .perform(get(ENTITY_API_URL_ID, formaPago.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(formaPago.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFormaPago() throws Exception {
        // Get the formaPago
        restFormaPagoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFormaPago() throws Exception {
        // Initialize the database
        formaPagoRepository.saveAndFlush(formaPago);

        int databaseSizeBeforeUpdate = formaPagoRepository.findAll().size();

        // Update the formaPago
        FormaPago updatedFormaPago = formaPagoRepository.findById(formaPago.getId()).get();
        // Disconnect from session so that the updates on updatedFormaPago are not directly saved in db
        em.detach(updatedFormaPago);
        updatedFormaPago.name(UPDATED_NAME).estado(UPDATED_ESTADO).createdDate(UPDATED_CREATED_DATE).updatedDate(UPDATED_UPDATED_DATE);

        restFormaPagoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFormaPago.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFormaPago))
            )
            .andExpect(status().isOk());

        // Validate the FormaPago in the database
        List<FormaPago> formaPagoList = formaPagoRepository.findAll();
        assertThat(formaPagoList).hasSize(databaseSizeBeforeUpdate);
        FormaPago testFormaPago = formaPagoList.get(formaPagoList.size() - 1);
        assertThat(testFormaPago.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFormaPago.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testFormaPago.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testFormaPago.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingFormaPago() throws Exception {
        int databaseSizeBeforeUpdate = formaPagoRepository.findAll().size();
        formaPago.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormaPagoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, formaPago.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(formaPago))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormaPago in the database
        List<FormaPago> formaPagoList = formaPagoRepository.findAll();
        assertThat(formaPagoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFormaPago() throws Exception {
        int databaseSizeBeforeUpdate = formaPagoRepository.findAll().size();
        formaPago.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormaPagoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(formaPago))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormaPago in the database
        List<FormaPago> formaPagoList = formaPagoRepository.findAll();
        assertThat(formaPagoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFormaPago() throws Exception {
        int databaseSizeBeforeUpdate = formaPagoRepository.findAll().size();
        formaPago.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormaPagoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(formaPago)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FormaPago in the database
        List<FormaPago> formaPagoList = formaPagoRepository.findAll();
        assertThat(formaPagoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFormaPagoWithPatch() throws Exception {
        // Initialize the database
        formaPagoRepository.saveAndFlush(formaPago);

        int databaseSizeBeforeUpdate = formaPagoRepository.findAll().size();

        // Update the formaPago using partial update
        FormaPago partialUpdatedFormaPago = new FormaPago();
        partialUpdatedFormaPago.setId(formaPago.getId());

        partialUpdatedFormaPago.updatedDate(UPDATED_UPDATED_DATE);

        restFormaPagoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormaPago.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFormaPago))
            )
            .andExpect(status().isOk());

        // Validate the FormaPago in the database
        List<FormaPago> formaPagoList = formaPagoRepository.findAll();
        assertThat(formaPagoList).hasSize(databaseSizeBeforeUpdate);
        FormaPago testFormaPago = formaPagoList.get(formaPagoList.size() - 1);
        assertThat(testFormaPago.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFormaPago.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testFormaPago.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testFormaPago.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateFormaPagoWithPatch() throws Exception {
        // Initialize the database
        formaPagoRepository.saveAndFlush(formaPago);

        int databaseSizeBeforeUpdate = formaPagoRepository.findAll().size();

        // Update the formaPago using partial update
        FormaPago partialUpdatedFormaPago = new FormaPago();
        partialUpdatedFormaPago.setId(formaPago.getId());

        partialUpdatedFormaPago
            .name(UPDATED_NAME)
            .estado(UPDATED_ESTADO)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restFormaPagoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormaPago.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFormaPago))
            )
            .andExpect(status().isOk());

        // Validate the FormaPago in the database
        List<FormaPago> formaPagoList = formaPagoRepository.findAll();
        assertThat(formaPagoList).hasSize(databaseSizeBeforeUpdate);
        FormaPago testFormaPago = formaPagoList.get(formaPagoList.size() - 1);
        assertThat(testFormaPago.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFormaPago.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testFormaPago.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testFormaPago.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingFormaPago() throws Exception {
        int databaseSizeBeforeUpdate = formaPagoRepository.findAll().size();
        formaPago.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormaPagoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, formaPago.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(formaPago))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormaPago in the database
        List<FormaPago> formaPagoList = formaPagoRepository.findAll();
        assertThat(formaPagoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFormaPago() throws Exception {
        int databaseSizeBeforeUpdate = formaPagoRepository.findAll().size();
        formaPago.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormaPagoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(formaPago))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormaPago in the database
        List<FormaPago> formaPagoList = formaPagoRepository.findAll();
        assertThat(formaPagoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFormaPago() throws Exception {
        int databaseSizeBeforeUpdate = formaPagoRepository.findAll().size();
        formaPago.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormaPagoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(formaPago))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FormaPago in the database
        List<FormaPago> formaPagoList = formaPagoRepository.findAll();
        assertThat(formaPagoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFormaPago() throws Exception {
        // Initialize the database
        formaPagoRepository.saveAndFlush(formaPago);

        int databaseSizeBeforeDelete = formaPagoRepository.findAll().size();

        // Delete the formaPago
        restFormaPagoMockMvc
            .perform(delete(ENTITY_API_URL_ID, formaPago.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FormaPago> formaPagoList = formaPagoRepository.findAll();
        assertThat(formaPagoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
