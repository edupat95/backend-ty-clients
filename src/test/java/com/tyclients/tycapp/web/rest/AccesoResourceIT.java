package com.tyclients.tycapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tyclients.tycapp.IntegrationTest;
import com.tyclients.tycapp.domain.Acceso;
import com.tyclients.tycapp.domain.Asociado;
import com.tyclients.tycapp.domain.Evento;
import com.tyclients.tycapp.repository.AccesoRepository;
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
 * Integration tests for the {@link AccesoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AccesoResourceIT {

    private static final Long DEFAULT_COSTO_PUNTOS = 1L;
    private static final Long UPDATED_COSTO_PUNTOS = 2L;

    private static final Instant DEFAULT_FECHA_CANJE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_CANJE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/accesos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AccesoRepository accesoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccesoMockMvc;

    private Acceso acceso;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Acceso createEntity(EntityManager em) {
        Acceso acceso = new Acceso()
            .costoPuntos(DEFAULT_COSTO_PUNTOS)
            .fechaCanje(DEFAULT_FECHA_CANJE)
            .estado(DEFAULT_ESTADO)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        // Add required entity
        Asociado asociado;
        if (TestUtil.findAll(em, Asociado.class).isEmpty()) {
            asociado = AsociadoResourceIT.createEntity(em);
            em.persist(asociado);
            em.flush();
        } else {
            asociado = TestUtil.findAll(em, Asociado.class).get(0);
        }
        acceso.setAsociado(asociado);
        // Add required entity
        Evento evento;
        if (TestUtil.findAll(em, Evento.class).isEmpty()) {
            evento = EventoResourceIT.createEntity(em);
            em.persist(evento);
            em.flush();
        } else {
            evento = TestUtil.findAll(em, Evento.class).get(0);
        }
        acceso.setEvento(evento);
        return acceso;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Acceso createUpdatedEntity(EntityManager em) {
        Acceso acceso = new Acceso()
            .costoPuntos(UPDATED_COSTO_PUNTOS)
            .fechaCanje(UPDATED_FECHA_CANJE)
            .estado(UPDATED_ESTADO)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        // Add required entity
        Asociado asociado;
        if (TestUtil.findAll(em, Asociado.class).isEmpty()) {
            asociado = AsociadoResourceIT.createUpdatedEntity(em);
            em.persist(asociado);
            em.flush();
        } else {
            asociado = TestUtil.findAll(em, Asociado.class).get(0);
        }
        acceso.setAsociado(asociado);
        // Add required entity
        Evento evento;
        if (TestUtil.findAll(em, Evento.class).isEmpty()) {
            evento = EventoResourceIT.createUpdatedEntity(em);
            em.persist(evento);
            em.flush();
        } else {
            evento = TestUtil.findAll(em, Evento.class).get(0);
        }
        acceso.setEvento(evento);
        return acceso;
    }

    @BeforeEach
    public void initTest() {
        acceso = createEntity(em);
    }

    @Test
    @Transactional
    void createAcceso() throws Exception {
        int databaseSizeBeforeCreate = accesoRepository.findAll().size();
        // Create the Acceso
        restAccesoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(acceso)))
            .andExpect(status().isCreated());

        // Validate the Acceso in the database
        List<Acceso> accesoList = accesoRepository.findAll();
        assertThat(accesoList).hasSize(databaseSizeBeforeCreate + 1);
        Acceso testAcceso = accesoList.get(accesoList.size() - 1);
        assertThat(testAcceso.getCostoPuntos()).isEqualTo(DEFAULT_COSTO_PUNTOS);
        assertThat(testAcceso.getFechaCanje()).isEqualTo(DEFAULT_FECHA_CANJE);
        assertThat(testAcceso.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testAcceso.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testAcceso.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createAccesoWithExistingId() throws Exception {
        // Create the Acceso with an existing ID
        acceso.setId(1L);

        int databaseSizeBeforeCreate = accesoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccesoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(acceso)))
            .andExpect(status().isBadRequest());

        // Validate the Acceso in the database
        List<Acceso> accesoList = accesoRepository.findAll();
        assertThat(accesoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = accesoRepository.findAll().size();
        // set the field null
        acceso.setEstado(null);

        // Create the Acceso, which fails.

        restAccesoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(acceso)))
            .andExpect(status().isBadRequest());

        List<Acceso> accesoList = accesoRepository.findAll();
        assertThat(accesoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAccesos() throws Exception {
        // Initialize the database
        accesoRepository.saveAndFlush(acceso);

        // Get all the accesoList
        restAccesoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(acceso.getId().intValue())))
            .andExpect(jsonPath("$.[*].costoPuntos").value(hasItem(DEFAULT_COSTO_PUNTOS.intValue())))
            .andExpect(jsonPath("$.[*].fechaCanje").value(hasItem(DEFAULT_FECHA_CANJE.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getAcceso() throws Exception {
        // Initialize the database
        accesoRepository.saveAndFlush(acceso);

        // Get the acceso
        restAccesoMockMvc
            .perform(get(ENTITY_API_URL_ID, acceso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(acceso.getId().intValue()))
            .andExpect(jsonPath("$.costoPuntos").value(DEFAULT_COSTO_PUNTOS.intValue()))
            .andExpect(jsonPath("$.fechaCanje").value(DEFAULT_FECHA_CANJE.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAcceso() throws Exception {
        // Get the acceso
        restAccesoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAcceso() throws Exception {
        // Initialize the database
        accesoRepository.saveAndFlush(acceso);

        int databaseSizeBeforeUpdate = accesoRepository.findAll().size();

        // Update the acceso
        Acceso updatedAcceso = accesoRepository.findById(acceso.getId()).get();
        // Disconnect from session so that the updates on updatedAcceso are not directly saved in db
        em.detach(updatedAcceso);
        updatedAcceso
            .costoPuntos(UPDATED_COSTO_PUNTOS)
            .fechaCanje(UPDATED_FECHA_CANJE)
            .estado(UPDATED_ESTADO)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restAccesoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAcceso.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAcceso))
            )
            .andExpect(status().isOk());

        // Validate the Acceso in the database
        List<Acceso> accesoList = accesoRepository.findAll();
        assertThat(accesoList).hasSize(databaseSizeBeforeUpdate);
        Acceso testAcceso = accesoList.get(accesoList.size() - 1);
        assertThat(testAcceso.getCostoPuntos()).isEqualTo(UPDATED_COSTO_PUNTOS);
        assertThat(testAcceso.getFechaCanje()).isEqualTo(UPDATED_FECHA_CANJE);
        assertThat(testAcceso.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testAcceso.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAcceso.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingAcceso() throws Exception {
        int databaseSizeBeforeUpdate = accesoRepository.findAll().size();
        acceso.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccesoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, acceso.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(acceso))
            )
            .andExpect(status().isBadRequest());

        // Validate the Acceso in the database
        List<Acceso> accesoList = accesoRepository.findAll();
        assertThat(accesoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAcceso() throws Exception {
        int databaseSizeBeforeUpdate = accesoRepository.findAll().size();
        acceso.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccesoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(acceso))
            )
            .andExpect(status().isBadRequest());

        // Validate the Acceso in the database
        List<Acceso> accesoList = accesoRepository.findAll();
        assertThat(accesoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAcceso() throws Exception {
        int databaseSizeBeforeUpdate = accesoRepository.findAll().size();
        acceso.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccesoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(acceso)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Acceso in the database
        List<Acceso> accesoList = accesoRepository.findAll();
        assertThat(accesoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAccesoWithPatch() throws Exception {
        // Initialize the database
        accesoRepository.saveAndFlush(acceso);

        int databaseSizeBeforeUpdate = accesoRepository.findAll().size();

        // Update the acceso using partial update
        Acceso partialUpdatedAcceso = new Acceso();
        partialUpdatedAcceso.setId(acceso.getId());

        partialUpdatedAcceso.costoPuntos(UPDATED_COSTO_PUNTOS).fechaCanje(UPDATED_FECHA_CANJE);

        restAccesoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAcceso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAcceso))
            )
            .andExpect(status().isOk());

        // Validate the Acceso in the database
        List<Acceso> accesoList = accesoRepository.findAll();
        assertThat(accesoList).hasSize(databaseSizeBeforeUpdate);
        Acceso testAcceso = accesoList.get(accesoList.size() - 1);
        assertThat(testAcceso.getCostoPuntos()).isEqualTo(UPDATED_COSTO_PUNTOS);
        assertThat(testAcceso.getFechaCanje()).isEqualTo(UPDATED_FECHA_CANJE);
        assertThat(testAcceso.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testAcceso.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testAcceso.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateAccesoWithPatch() throws Exception {
        // Initialize the database
        accesoRepository.saveAndFlush(acceso);

        int databaseSizeBeforeUpdate = accesoRepository.findAll().size();

        // Update the acceso using partial update
        Acceso partialUpdatedAcceso = new Acceso();
        partialUpdatedAcceso.setId(acceso.getId());

        partialUpdatedAcceso
            .costoPuntos(UPDATED_COSTO_PUNTOS)
            .fechaCanje(UPDATED_FECHA_CANJE)
            .estado(UPDATED_ESTADO)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restAccesoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAcceso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAcceso))
            )
            .andExpect(status().isOk());

        // Validate the Acceso in the database
        List<Acceso> accesoList = accesoRepository.findAll();
        assertThat(accesoList).hasSize(databaseSizeBeforeUpdate);
        Acceso testAcceso = accesoList.get(accesoList.size() - 1);
        assertThat(testAcceso.getCostoPuntos()).isEqualTo(UPDATED_COSTO_PUNTOS);
        assertThat(testAcceso.getFechaCanje()).isEqualTo(UPDATED_FECHA_CANJE);
        assertThat(testAcceso.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testAcceso.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAcceso.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingAcceso() throws Exception {
        int databaseSizeBeforeUpdate = accesoRepository.findAll().size();
        acceso.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccesoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, acceso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(acceso))
            )
            .andExpect(status().isBadRequest());

        // Validate the Acceso in the database
        List<Acceso> accesoList = accesoRepository.findAll();
        assertThat(accesoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAcceso() throws Exception {
        int databaseSizeBeforeUpdate = accesoRepository.findAll().size();
        acceso.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccesoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(acceso))
            )
            .andExpect(status().isBadRequest());

        // Validate the Acceso in the database
        List<Acceso> accesoList = accesoRepository.findAll();
        assertThat(accesoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAcceso() throws Exception {
        int databaseSizeBeforeUpdate = accesoRepository.findAll().size();
        acceso.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccesoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(acceso)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Acceso in the database
        List<Acceso> accesoList = accesoRepository.findAll();
        assertThat(accesoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAcceso() throws Exception {
        // Initialize the database
        accesoRepository.saveAndFlush(acceso);

        int databaseSizeBeforeDelete = accesoRepository.findAll().size();

        // Delete the acceso
        restAccesoMockMvc
            .perform(delete(ENTITY_API_URL_ID, acceso.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Acceso> accesoList = accesoRepository.findAll();
        assertThat(accesoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
