package com.tyclients.tycapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tyclients.tycapp.IntegrationTest;
import com.tyclients.tycapp.domain.Cajero;
import com.tyclients.tycapp.domain.Trabajador;
import com.tyclients.tycapp.repository.CajeroRepository;
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
 * Integration tests for the {@link CajeroResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CajeroResourceIT {

    private static final Long DEFAULT_PLATA_DE_CAMBIO = 1L;
    private static final Long UPDATED_PLATA_DE_CAMBIO = 2L;

    private static final Integer DEFAULT_TIPO = 1;
    private static final Integer UPDATED_TIPO = 2;

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    private static final Instant DEFAULT_CREAD_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREAD_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/cajeros";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CajeroRepository cajeroRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCajeroMockMvc;

    private Cajero cajero;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cajero createEntity(EntityManager em) {
        Cajero cajero = new Cajero()
            .plataDeCambio(DEFAULT_PLATA_DE_CAMBIO)
            .tipo(DEFAULT_TIPO)
            .estado(DEFAULT_ESTADO)
            .creadDate(DEFAULT_CREAD_DATE)
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
        cajero.setTrabajador(trabajador);
        return cajero;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cajero createUpdatedEntity(EntityManager em) {
        Cajero cajero = new Cajero()
            .plataDeCambio(UPDATED_PLATA_DE_CAMBIO)
            .tipo(UPDATED_TIPO)
            .estado(UPDATED_ESTADO)
            .creadDate(UPDATED_CREAD_DATE)
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
        cajero.setTrabajador(trabajador);
        return cajero;
    }

    @BeforeEach
    public void initTest() {
        cajero = createEntity(em);
    }

    @Test
    @Transactional
    void createCajero() throws Exception {
        int databaseSizeBeforeCreate = cajeroRepository.findAll().size();
        // Create the Cajero
        restCajeroMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cajero)))
            .andExpect(status().isCreated());

        // Validate the Cajero in the database
        List<Cajero> cajeroList = cajeroRepository.findAll();
        assertThat(cajeroList).hasSize(databaseSizeBeforeCreate + 1);
        Cajero testCajero = cajeroList.get(cajeroList.size() - 1);
        assertThat(testCajero.getPlataDeCambio()).isEqualTo(DEFAULT_PLATA_DE_CAMBIO);
        assertThat(testCajero.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testCajero.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testCajero.getCreadDate()).isEqualTo(DEFAULT_CREAD_DATE);
        assertThat(testCajero.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createCajeroWithExistingId() throws Exception {
        // Create the Cajero with an existing ID
        cajero.setId(1L);

        int databaseSizeBeforeCreate = cajeroRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCajeroMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cajero)))
            .andExpect(status().isBadRequest());

        // Validate the Cajero in the database
        List<Cajero> cajeroList = cajeroRepository.findAll();
        assertThat(cajeroList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cajeroRepository.findAll().size();
        // set the field null
        cajero.setEstado(null);

        // Create the Cajero, which fails.

        restCajeroMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cajero)))
            .andExpect(status().isBadRequest());

        List<Cajero> cajeroList = cajeroRepository.findAll();
        assertThat(cajeroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCajeros() throws Exception {
        // Initialize the database
        cajeroRepository.saveAndFlush(cajero);

        // Get all the cajeroList
        restCajeroMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cajero.getId().intValue())))
            .andExpect(jsonPath("$.[*].plataDeCambio").value(hasItem(DEFAULT_PLATA_DE_CAMBIO.intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())))
            .andExpect(jsonPath("$.[*].creadDate").value(hasItem(DEFAULT_CREAD_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getCajero() throws Exception {
        // Initialize the database
        cajeroRepository.saveAndFlush(cajero);

        // Get the cajero
        restCajeroMockMvc
            .perform(get(ENTITY_API_URL_ID, cajero.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cajero.getId().intValue()))
            .andExpect(jsonPath("$.plataDeCambio").value(DEFAULT_PLATA_DE_CAMBIO.intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()))
            .andExpect(jsonPath("$.creadDate").value(DEFAULT_CREAD_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCajero() throws Exception {
        // Get the cajero
        restCajeroMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCajero() throws Exception {
        // Initialize the database
        cajeroRepository.saveAndFlush(cajero);

        int databaseSizeBeforeUpdate = cajeroRepository.findAll().size();

        // Update the cajero
        Cajero updatedCajero = cajeroRepository.findById(cajero.getId()).get();
        // Disconnect from session so that the updates on updatedCajero are not directly saved in db
        em.detach(updatedCajero);
        updatedCajero
            .plataDeCambio(UPDATED_PLATA_DE_CAMBIO)
            .tipo(UPDATED_TIPO)
            .estado(UPDATED_ESTADO)
            .creadDate(UPDATED_CREAD_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restCajeroMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCajero.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCajero))
            )
            .andExpect(status().isOk());

        // Validate the Cajero in the database
        List<Cajero> cajeroList = cajeroRepository.findAll();
        assertThat(cajeroList).hasSize(databaseSizeBeforeUpdate);
        Cajero testCajero = cajeroList.get(cajeroList.size() - 1);
        assertThat(testCajero.getPlataDeCambio()).isEqualTo(UPDATED_PLATA_DE_CAMBIO);
        assertThat(testCajero.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testCajero.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testCajero.getCreadDate()).isEqualTo(UPDATED_CREAD_DATE);
        assertThat(testCajero.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingCajero() throws Exception {
        int databaseSizeBeforeUpdate = cajeroRepository.findAll().size();
        cajero.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCajeroMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cajero.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cajero))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cajero in the database
        List<Cajero> cajeroList = cajeroRepository.findAll();
        assertThat(cajeroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCajero() throws Exception {
        int databaseSizeBeforeUpdate = cajeroRepository.findAll().size();
        cajero.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCajeroMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cajero))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cajero in the database
        List<Cajero> cajeroList = cajeroRepository.findAll();
        assertThat(cajeroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCajero() throws Exception {
        int databaseSizeBeforeUpdate = cajeroRepository.findAll().size();
        cajero.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCajeroMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cajero)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cajero in the database
        List<Cajero> cajeroList = cajeroRepository.findAll();
        assertThat(cajeroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCajeroWithPatch() throws Exception {
        // Initialize the database
        cajeroRepository.saveAndFlush(cajero);

        int databaseSizeBeforeUpdate = cajeroRepository.findAll().size();

        // Update the cajero using partial update
        Cajero partialUpdatedCajero = new Cajero();
        partialUpdatedCajero.setId(cajero.getId());

        partialUpdatedCajero.tipo(UPDATED_TIPO).estado(UPDATED_ESTADO).updatedDate(UPDATED_UPDATED_DATE);

        restCajeroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCajero.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCajero))
            )
            .andExpect(status().isOk());

        // Validate the Cajero in the database
        List<Cajero> cajeroList = cajeroRepository.findAll();
        assertThat(cajeroList).hasSize(databaseSizeBeforeUpdate);
        Cajero testCajero = cajeroList.get(cajeroList.size() - 1);
        assertThat(testCajero.getPlataDeCambio()).isEqualTo(DEFAULT_PLATA_DE_CAMBIO);
        assertThat(testCajero.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testCajero.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testCajero.getCreadDate()).isEqualTo(DEFAULT_CREAD_DATE);
        assertThat(testCajero.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateCajeroWithPatch() throws Exception {
        // Initialize the database
        cajeroRepository.saveAndFlush(cajero);

        int databaseSizeBeforeUpdate = cajeroRepository.findAll().size();

        // Update the cajero using partial update
        Cajero partialUpdatedCajero = new Cajero();
        partialUpdatedCajero.setId(cajero.getId());

        partialUpdatedCajero
            .plataDeCambio(UPDATED_PLATA_DE_CAMBIO)
            .tipo(UPDATED_TIPO)
            .estado(UPDATED_ESTADO)
            .creadDate(UPDATED_CREAD_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restCajeroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCajero.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCajero))
            )
            .andExpect(status().isOk());

        // Validate the Cajero in the database
        List<Cajero> cajeroList = cajeroRepository.findAll();
        assertThat(cajeroList).hasSize(databaseSizeBeforeUpdate);
        Cajero testCajero = cajeroList.get(cajeroList.size() - 1);
        assertThat(testCajero.getPlataDeCambio()).isEqualTo(UPDATED_PLATA_DE_CAMBIO);
        assertThat(testCajero.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testCajero.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testCajero.getCreadDate()).isEqualTo(UPDATED_CREAD_DATE);
        assertThat(testCajero.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingCajero() throws Exception {
        int databaseSizeBeforeUpdate = cajeroRepository.findAll().size();
        cajero.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCajeroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cajero.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cajero))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cajero in the database
        List<Cajero> cajeroList = cajeroRepository.findAll();
        assertThat(cajeroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCajero() throws Exception {
        int databaseSizeBeforeUpdate = cajeroRepository.findAll().size();
        cajero.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCajeroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cajero))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cajero in the database
        List<Cajero> cajeroList = cajeroRepository.findAll();
        assertThat(cajeroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCajero() throws Exception {
        int databaseSizeBeforeUpdate = cajeroRepository.findAll().size();
        cajero.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCajeroMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cajero)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cajero in the database
        List<Cajero> cajeroList = cajeroRepository.findAll();
        assertThat(cajeroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCajero() throws Exception {
        // Initialize the database
        cajeroRepository.saveAndFlush(cajero);

        int databaseSizeBeforeDelete = cajeroRepository.findAll().size();

        // Delete the cajero
        restCajeroMockMvc
            .perform(delete(ENTITY_API_URL_ID, cajero.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cajero> cajeroList = cajeroRepository.findAll();
        assertThat(cajeroList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
