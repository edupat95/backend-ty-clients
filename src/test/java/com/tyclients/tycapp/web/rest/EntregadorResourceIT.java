package com.tyclients.tycapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tyclients.tycapp.IntegrationTest;
import com.tyclients.tycapp.domain.Entregador;
import com.tyclients.tycapp.domain.Trabajador;
import com.tyclients.tycapp.repository.EntregadorRepository;
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
 * Integration tests for the {@link EntregadorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EntregadorResourceIT {

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/entregadors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EntregadorRepository entregadorRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEntregadorMockMvc;

    private Entregador entregador;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entregador createEntity(EntityManager em) {
        Entregador entregador = new Entregador().estado(DEFAULT_ESTADO).createdDate(DEFAULT_CREATED_DATE).updatedDate(DEFAULT_UPDATED_DATE);
        // Add required entity
        Trabajador trabajador;
        if (TestUtil.findAll(em, Trabajador.class).isEmpty()) {
            trabajador = TrabajadorResourceIT.createEntity(em);
            em.persist(trabajador);
            em.flush();
        } else {
            trabajador = TestUtil.findAll(em, Trabajador.class).get(0);
        }
        entregador.setTrabajador(trabajador);
        return entregador;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entregador createUpdatedEntity(EntityManager em) {
        Entregador entregador = new Entregador().estado(UPDATED_ESTADO).createdDate(UPDATED_CREATED_DATE).updatedDate(UPDATED_UPDATED_DATE);
        // Add required entity
        Trabajador trabajador;
        if (TestUtil.findAll(em, Trabajador.class).isEmpty()) {
            trabajador = TrabajadorResourceIT.createUpdatedEntity(em);
            em.persist(trabajador);
            em.flush();
        } else {
            trabajador = TestUtil.findAll(em, Trabajador.class).get(0);
        }
        entregador.setTrabajador(trabajador);
        return entregador;
    }

    @BeforeEach
    public void initTest() {
        entregador = createEntity(em);
    }

    @Test
    @Transactional
    void createEntregador() throws Exception {
        int databaseSizeBeforeCreate = entregadorRepository.findAll().size();
        // Create the Entregador
        restEntregadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entregador)))
            .andExpect(status().isCreated());

        // Validate the Entregador in the database
        List<Entregador> entregadorList = entregadorRepository.findAll();
        assertThat(entregadorList).hasSize(databaseSizeBeforeCreate + 1);
        Entregador testEntregador = entregadorList.get(entregadorList.size() - 1);
        assertThat(testEntregador.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testEntregador.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testEntregador.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createEntregadorWithExistingId() throws Exception {
        // Create the Entregador with an existing ID
        entregador.setId(1L);

        int databaseSizeBeforeCreate = entregadorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntregadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entregador)))
            .andExpect(status().isBadRequest());

        // Validate the Entregador in the database
        List<Entregador> entregadorList = entregadorRepository.findAll();
        assertThat(entregadorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = entregadorRepository.findAll().size();
        // set the field null
        entregador.setEstado(null);

        // Create the Entregador, which fails.

        restEntregadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entregador)))
            .andExpect(status().isBadRequest());

        List<Entregador> entregadorList = entregadorRepository.findAll();
        assertThat(entregadorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEntregadors() throws Exception {
        // Initialize the database
        entregadorRepository.saveAndFlush(entregador);

        // Get all the entregadorList
        restEntregadorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entregador.getId().intValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getEntregador() throws Exception {
        // Initialize the database
        entregadorRepository.saveAndFlush(entregador);

        // Get the entregador
        restEntregadorMockMvc
            .perform(get(ENTITY_API_URL_ID, entregador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(entregador.getId().intValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingEntregador() throws Exception {
        // Get the entregador
        restEntregadorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEntregador() throws Exception {
        // Initialize the database
        entregadorRepository.saveAndFlush(entregador);

        int databaseSizeBeforeUpdate = entregadorRepository.findAll().size();

        // Update the entregador
        Entregador updatedEntregador = entregadorRepository.findById(entregador.getId()).get();
        // Disconnect from session so that the updates on updatedEntregador are not directly saved in db
        em.detach(updatedEntregador);
        updatedEntregador.estado(UPDATED_ESTADO).createdDate(UPDATED_CREATED_DATE).updatedDate(UPDATED_UPDATED_DATE);

        restEntregadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEntregador.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEntregador))
            )
            .andExpect(status().isOk());

        // Validate the Entregador in the database
        List<Entregador> entregadorList = entregadorRepository.findAll();
        assertThat(entregadorList).hasSize(databaseSizeBeforeUpdate);
        Entregador testEntregador = entregadorList.get(entregadorList.size() - 1);
        assertThat(testEntregador.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testEntregador.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEntregador.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingEntregador() throws Exception {
        int databaseSizeBeforeUpdate = entregadorRepository.findAll().size();
        entregador.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntregadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, entregador.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(entregador))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entregador in the database
        List<Entregador> entregadorList = entregadorRepository.findAll();
        assertThat(entregadorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEntregador() throws Exception {
        int databaseSizeBeforeUpdate = entregadorRepository.findAll().size();
        entregador.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntregadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(entregador))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entregador in the database
        List<Entregador> entregadorList = entregadorRepository.findAll();
        assertThat(entregadorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEntregador() throws Exception {
        int databaseSizeBeforeUpdate = entregadorRepository.findAll().size();
        entregador.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntregadorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entregador)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Entregador in the database
        List<Entregador> entregadorList = entregadorRepository.findAll();
        assertThat(entregadorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEntregadorWithPatch() throws Exception {
        // Initialize the database
        entregadorRepository.saveAndFlush(entregador);

        int databaseSizeBeforeUpdate = entregadorRepository.findAll().size();

        // Update the entregador using partial update
        Entregador partialUpdatedEntregador = new Entregador();
        partialUpdatedEntregador.setId(entregador.getId());

        restEntregadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntregador.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEntregador))
            )
            .andExpect(status().isOk());

        // Validate the Entregador in the database
        List<Entregador> entregadorList = entregadorRepository.findAll();
        assertThat(entregadorList).hasSize(databaseSizeBeforeUpdate);
        Entregador testEntregador = entregadorList.get(entregadorList.size() - 1);
        assertThat(testEntregador.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testEntregador.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testEntregador.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateEntregadorWithPatch() throws Exception {
        // Initialize the database
        entregadorRepository.saveAndFlush(entregador);

        int databaseSizeBeforeUpdate = entregadorRepository.findAll().size();

        // Update the entregador using partial update
        Entregador partialUpdatedEntregador = new Entregador();
        partialUpdatedEntregador.setId(entregador.getId());

        partialUpdatedEntregador.estado(UPDATED_ESTADO).createdDate(UPDATED_CREATED_DATE).updatedDate(UPDATED_UPDATED_DATE);

        restEntregadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntregador.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEntregador))
            )
            .andExpect(status().isOk());

        // Validate the Entregador in the database
        List<Entregador> entregadorList = entregadorRepository.findAll();
        assertThat(entregadorList).hasSize(databaseSizeBeforeUpdate);
        Entregador testEntregador = entregadorList.get(entregadorList.size() - 1);
        assertThat(testEntregador.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testEntregador.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEntregador.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingEntregador() throws Exception {
        int databaseSizeBeforeUpdate = entregadorRepository.findAll().size();
        entregador.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntregadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, entregador.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(entregador))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entregador in the database
        List<Entregador> entregadorList = entregadorRepository.findAll();
        assertThat(entregadorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEntregador() throws Exception {
        int databaseSizeBeforeUpdate = entregadorRepository.findAll().size();
        entregador.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntregadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(entregador))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entregador in the database
        List<Entregador> entregadorList = entregadorRepository.findAll();
        assertThat(entregadorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEntregador() throws Exception {
        int databaseSizeBeforeUpdate = entregadorRepository.findAll().size();
        entregador.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntregadorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(entregador))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Entregador in the database
        List<Entregador> entregadorList = entregadorRepository.findAll();
        assertThat(entregadorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEntregador() throws Exception {
        // Initialize the database
        entregadorRepository.saveAndFlush(entregador);

        int databaseSizeBeforeDelete = entregadorRepository.findAll().size();

        // Delete the entregador
        restEntregadorMockMvc
            .perform(delete(ENTITY_API_URL_ID, entregador.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Entregador> entregadorList = entregadorRepository.findAll();
        assertThat(entregadorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
