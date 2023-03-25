package com.tyclients.tycapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tyclients.tycapp.IntegrationTest;
import com.tyclients.tycapp.domain.Registrador;
import com.tyclients.tycapp.domain.Trabajador;
import com.tyclients.tycapp.repository.RegistradorRepository;
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
 * Integration tests for the {@link RegistradorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RegistradorResourceIT {

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/registradors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RegistradorRepository registradorRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRegistradorMockMvc;

    private Registrador registrador;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Registrador createEntity(EntityManager em) {
        Registrador registrador = new Registrador()
            .estado(DEFAULT_ESTADO)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        // Add required entity
        Trabajador trabajador;
        if (TestUtil.findAll(em, Trabajador.class).isEmpty()) {
            trabajador = TrabajadorResourceIT.createEntity(em);
            em.persist(trabajador);
            em.flush();
        } else {
            trabajador = TestUtil.findAll(em, Trabajador.class).get(0);
        }
        registrador.setTrabajador(trabajador);
        return registrador;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Registrador createUpdatedEntity(EntityManager em) {
        Registrador registrador = new Registrador()
            .estado(UPDATED_ESTADO)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        // Add required entity
        Trabajador trabajador;
        if (TestUtil.findAll(em, Trabajador.class).isEmpty()) {
            trabajador = TrabajadorResourceIT.createUpdatedEntity(em);
            em.persist(trabajador);
            em.flush();
        } else {
            trabajador = TestUtil.findAll(em, Trabajador.class).get(0);
        }
        registrador.setTrabajador(trabajador);
        return registrador;
    }

    @BeforeEach
    public void initTest() {
        registrador = createEntity(em);
    }

    @Test
    @Transactional
    void createRegistrador() throws Exception {
        int databaseSizeBeforeCreate = registradorRepository.findAll().size();
        // Create the Registrador
        restRegistradorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(registrador)))
            .andExpect(status().isCreated());

        // Validate the Registrador in the database
        List<Registrador> registradorList = registradorRepository.findAll();
        assertThat(registradorList).hasSize(databaseSizeBeforeCreate + 1);
        Registrador testRegistrador = registradorList.get(registradorList.size() - 1);
        assertThat(testRegistrador.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testRegistrador.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testRegistrador.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createRegistradorWithExistingId() throws Exception {
        // Create the Registrador with an existing ID
        registrador.setId(1L);

        int databaseSizeBeforeCreate = registradorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegistradorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(registrador)))
            .andExpect(status().isBadRequest());

        // Validate the Registrador in the database
        List<Registrador> registradorList = registradorRepository.findAll();
        assertThat(registradorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = registradorRepository.findAll().size();
        // set the field null
        registrador.setEstado(null);

        // Create the Registrador, which fails.

        restRegistradorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(registrador)))
            .andExpect(status().isBadRequest());

        List<Registrador> registradorList = registradorRepository.findAll();
        assertThat(registradorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRegistradors() throws Exception {
        // Initialize the database
        registradorRepository.saveAndFlush(registrador);

        // Get all the registradorList
        restRegistradorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(registrador.getId().intValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getRegistrador() throws Exception {
        // Initialize the database
        registradorRepository.saveAndFlush(registrador);

        // Get the registrador
        restRegistradorMockMvc
            .perform(get(ENTITY_API_URL_ID, registrador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(registrador.getId().intValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingRegistrador() throws Exception {
        // Get the registrador
        restRegistradorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRegistrador() throws Exception {
        // Initialize the database
        registradorRepository.saveAndFlush(registrador);

        int databaseSizeBeforeUpdate = registradorRepository.findAll().size();

        // Update the registrador
        Registrador updatedRegistrador = registradorRepository.findById(registrador.getId()).get();
        // Disconnect from session so that the updates on updatedRegistrador are not directly saved in db
        em.detach(updatedRegistrador);
        updatedRegistrador.estado(UPDATED_ESTADO).createdDate(UPDATED_CREATED_DATE).updatedDate(UPDATED_UPDATED_DATE);

        restRegistradorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRegistrador.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRegistrador))
            )
            .andExpect(status().isOk());

        // Validate the Registrador in the database
        List<Registrador> registradorList = registradorRepository.findAll();
        assertThat(registradorList).hasSize(databaseSizeBeforeUpdate);
        Registrador testRegistrador = registradorList.get(registradorList.size() - 1);
        assertThat(testRegistrador.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testRegistrador.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testRegistrador.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingRegistrador() throws Exception {
        int databaseSizeBeforeUpdate = registradorRepository.findAll().size();
        registrador.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegistradorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, registrador.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(registrador))
            )
            .andExpect(status().isBadRequest());

        // Validate the Registrador in the database
        List<Registrador> registradorList = registradorRepository.findAll();
        assertThat(registradorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRegistrador() throws Exception {
        int databaseSizeBeforeUpdate = registradorRepository.findAll().size();
        registrador.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegistradorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(registrador))
            )
            .andExpect(status().isBadRequest());

        // Validate the Registrador in the database
        List<Registrador> registradorList = registradorRepository.findAll();
        assertThat(registradorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRegistrador() throws Exception {
        int databaseSizeBeforeUpdate = registradorRepository.findAll().size();
        registrador.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegistradorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(registrador)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Registrador in the database
        List<Registrador> registradorList = registradorRepository.findAll();
        assertThat(registradorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRegistradorWithPatch() throws Exception {
        // Initialize the database
        registradorRepository.saveAndFlush(registrador);

        int databaseSizeBeforeUpdate = registradorRepository.findAll().size();

        // Update the registrador using partial update
        Registrador partialUpdatedRegistrador = new Registrador();
        partialUpdatedRegistrador.setId(registrador.getId());

        partialUpdatedRegistrador.estado(UPDATED_ESTADO).createdDate(UPDATED_CREATED_DATE);

        restRegistradorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegistrador.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRegistrador))
            )
            .andExpect(status().isOk());

        // Validate the Registrador in the database
        List<Registrador> registradorList = registradorRepository.findAll();
        assertThat(registradorList).hasSize(databaseSizeBeforeUpdate);
        Registrador testRegistrador = registradorList.get(registradorList.size() - 1);
        assertThat(testRegistrador.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testRegistrador.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testRegistrador.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateRegistradorWithPatch() throws Exception {
        // Initialize the database
        registradorRepository.saveAndFlush(registrador);

        int databaseSizeBeforeUpdate = registradorRepository.findAll().size();

        // Update the registrador using partial update
        Registrador partialUpdatedRegistrador = new Registrador();
        partialUpdatedRegistrador.setId(registrador.getId());

        partialUpdatedRegistrador.estado(UPDATED_ESTADO).createdDate(UPDATED_CREATED_DATE).updatedDate(UPDATED_UPDATED_DATE);

        restRegistradorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegistrador.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRegistrador))
            )
            .andExpect(status().isOk());

        // Validate the Registrador in the database
        List<Registrador> registradorList = registradorRepository.findAll();
        assertThat(registradorList).hasSize(databaseSizeBeforeUpdate);
        Registrador testRegistrador = registradorList.get(registradorList.size() - 1);
        assertThat(testRegistrador.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testRegistrador.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testRegistrador.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingRegistrador() throws Exception {
        int databaseSizeBeforeUpdate = registradorRepository.findAll().size();
        registrador.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegistradorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, registrador.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(registrador))
            )
            .andExpect(status().isBadRequest());

        // Validate the Registrador in the database
        List<Registrador> registradorList = registradorRepository.findAll();
        assertThat(registradorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRegistrador() throws Exception {
        int databaseSizeBeforeUpdate = registradorRepository.findAll().size();
        registrador.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegistradorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(registrador))
            )
            .andExpect(status().isBadRequest());

        // Validate the Registrador in the database
        List<Registrador> registradorList = registradorRepository.findAll();
        assertThat(registradorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRegistrador() throws Exception {
        int databaseSizeBeforeUpdate = registradorRepository.findAll().size();
        registrador.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegistradorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(registrador))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Registrador in the database
        List<Registrador> registradorList = registradorRepository.findAll();
        assertThat(registradorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRegistrador() throws Exception {
        // Initialize the database
        registradorRepository.saveAndFlush(registrador);

        int databaseSizeBeforeDelete = registradorRepository.findAll().size();

        // Delete the registrador
        restRegistradorMockMvc
            .perform(delete(ENTITY_API_URL_ID, registrador.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Registrador> registradorList = registradorRepository.findAll();
        assertThat(registradorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
