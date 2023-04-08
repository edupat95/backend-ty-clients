package com.tyclients.tycapp.web.rest;

import static com.tyclients.tycapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tyclients.tycapp.IntegrationTest;
import com.tyclients.tycapp.domain.AdminClub;
import com.tyclients.tycapp.domain.Club;
import com.tyclients.tycapp.domain.Trabajador;
import com.tyclients.tycapp.domain.User;
import com.tyclients.tycapp.repository.TrabajadorRepository;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
 * Integration tests for the {@link TrabajadorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TrabajadorResourceIT {

    private static final Long DEFAULT_SUELDO = 1L;
    private static final Long UPDATED_SUELDO = 2L;

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_FECHA_INGRESO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_INGRESO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/trabajadors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TrabajadorRepository trabajadorRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrabajadorMockMvc;

    private Trabajador trabajador;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trabajador createEntity(EntityManager em) {
        Trabajador trabajador = new Trabajador()
            .sueldo(DEFAULT_SUELDO)
            .descripcion(DEFAULT_DESCRIPCION)
            .fechaIngreso(DEFAULT_FECHA_INGRESO)
            .estado(DEFAULT_ESTADO)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        trabajador.setUser(user);
        // Add required entity
        Club club;
        if (TestUtil.findAll(em, Club.class).isEmpty()) {
            club = ClubResourceIT.createEntity(em);
            em.persist(club);
            em.flush();
        } else {
            club = TestUtil.findAll(em, Club.class).get(0);
        }
        trabajador.setClub(club);
        // Add required entity
        AdminClub adminClub;
        if (TestUtil.findAll(em, AdminClub.class).isEmpty()) {
            adminClub = AdminClubResourceIT.createEntity(em);
            em.persist(adminClub);
            em.flush();
        } else {
            adminClub = TestUtil.findAll(em, AdminClub.class).get(0);
        }
        trabajador.setAdminClub(adminClub);
        return trabajador;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trabajador createUpdatedEntity(EntityManager em) {
        Trabajador trabajador = new Trabajador()
            .sueldo(UPDATED_SUELDO)
            .descripcion(UPDATED_DESCRIPCION)
            .fechaIngreso(UPDATED_FECHA_INGRESO)
            .estado(UPDATED_ESTADO)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        trabajador.setUser(user);
        // Add required entity
        Club club;
        if (TestUtil.findAll(em, Club.class).isEmpty()) {
            club = ClubResourceIT.createUpdatedEntity(em);
            em.persist(club);
            em.flush();
        } else {
            club = TestUtil.findAll(em, Club.class).get(0);
        }
        trabajador.setClub(club);
        // Add required entity
        AdminClub adminClub;
        if (TestUtil.findAll(em, AdminClub.class).isEmpty()) {
            adminClub = AdminClubResourceIT.createUpdatedEntity(em);
            em.persist(adminClub);
            em.flush();
        } else {
            adminClub = TestUtil.findAll(em, AdminClub.class).get(0);
        }
        trabajador.setAdminClub(adminClub);
        return trabajador;
    }

    @BeforeEach
    public void initTest() {
        trabajador = createEntity(em);
    }

    @Test
    @Transactional
    void createTrabajador() throws Exception {
        int databaseSizeBeforeCreate = trabajadorRepository.findAll().size();
        // Create the Trabajador
        restTrabajadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trabajador)))
            .andExpect(status().isCreated());

        // Validate the Trabajador in the database
        List<Trabajador> trabajadorList = trabajadorRepository.findAll();
        assertThat(trabajadorList).hasSize(databaseSizeBeforeCreate + 1);
        Trabajador testTrabajador = trabajadorList.get(trabajadorList.size() - 1);
        assertThat(testTrabajador.getSueldo()).isEqualTo(DEFAULT_SUELDO);
        assertThat(testTrabajador.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testTrabajador.getFechaIngreso()).isEqualTo(DEFAULT_FECHA_INGRESO);
        assertThat(testTrabajador.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testTrabajador.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testTrabajador.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createTrabajadorWithExistingId() throws Exception {
        // Create the Trabajador with an existing ID
        trabajador.setId(1L);

        int databaseSizeBeforeCreate = trabajadorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrabajadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trabajador)))
            .andExpect(status().isBadRequest());

        // Validate the Trabajador in the database
        List<Trabajador> trabajadorList = trabajadorRepository.findAll();
        assertThat(trabajadorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFechaIngresoIsRequired() throws Exception {
        int databaseSizeBeforeTest = trabajadorRepository.findAll().size();
        // set the field null
        trabajador.setFechaIngreso(null);

        // Create the Trabajador, which fails.

        restTrabajadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trabajador)))
            .andExpect(status().isBadRequest());

        List<Trabajador> trabajadorList = trabajadorRepository.findAll();
        assertThat(trabajadorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = trabajadorRepository.findAll().size();
        // set the field null
        trabajador.setEstado(null);

        // Create the Trabajador, which fails.

        restTrabajadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trabajador)))
            .andExpect(status().isBadRequest());

        List<Trabajador> trabajadorList = trabajadorRepository.findAll();
        assertThat(trabajadorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTrabajadors() throws Exception {
        // Initialize the database
        trabajadorRepository.saveAndFlush(trabajador);

        // Get all the trabajadorList
        restTrabajadorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trabajador.getId().intValue())))
            .andExpect(jsonPath("$.[*].sueldo").value(hasItem(DEFAULT_SUELDO.intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].fechaIngreso").value(hasItem(sameInstant(DEFAULT_FECHA_INGRESO))))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getTrabajador() throws Exception {
        // Initialize the database
        trabajadorRepository.saveAndFlush(trabajador);

        // Get the trabajador
        restTrabajadorMockMvc
            .perform(get(ENTITY_API_URL_ID, trabajador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trabajador.getId().intValue()))
            .andExpect(jsonPath("$.sueldo").value(DEFAULT_SUELDO.intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.fechaIngreso").value(sameInstant(DEFAULT_FECHA_INGRESO)))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTrabajador() throws Exception {
        // Get the trabajador
        restTrabajadorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTrabajador() throws Exception {
        // Initialize the database
        trabajadorRepository.saveAndFlush(trabajador);

        int databaseSizeBeforeUpdate = trabajadorRepository.findAll().size();

        // Update the trabajador
        Trabajador updatedTrabajador = trabajadorRepository.findById(trabajador.getId()).get();
        // Disconnect from session so that the updates on updatedTrabajador are not directly saved in db
        em.detach(updatedTrabajador);
        updatedTrabajador
            .sueldo(UPDATED_SUELDO)
            .descripcion(UPDATED_DESCRIPCION)
            .fechaIngreso(UPDATED_FECHA_INGRESO)
            .estado(UPDATED_ESTADO)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restTrabajadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTrabajador.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTrabajador))
            )
            .andExpect(status().isOk());

        // Validate the Trabajador in the database
        List<Trabajador> trabajadorList = trabajadorRepository.findAll();
        assertThat(trabajadorList).hasSize(databaseSizeBeforeUpdate);
        Trabajador testTrabajador = trabajadorList.get(trabajadorList.size() - 1);
        assertThat(testTrabajador.getSueldo()).isEqualTo(UPDATED_SUELDO);
        assertThat(testTrabajador.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testTrabajador.getFechaIngreso()).isEqualTo(UPDATED_FECHA_INGRESO);
        assertThat(testTrabajador.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testTrabajador.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTrabajador.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingTrabajador() throws Exception {
        int databaseSizeBeforeUpdate = trabajadorRepository.findAll().size();
        trabajador.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrabajadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trabajador.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trabajador))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trabajador in the database
        List<Trabajador> trabajadorList = trabajadorRepository.findAll();
        assertThat(trabajadorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTrabajador() throws Exception {
        int databaseSizeBeforeUpdate = trabajadorRepository.findAll().size();
        trabajador.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrabajadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trabajador))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trabajador in the database
        List<Trabajador> trabajadorList = trabajadorRepository.findAll();
        assertThat(trabajadorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTrabajador() throws Exception {
        int databaseSizeBeforeUpdate = trabajadorRepository.findAll().size();
        trabajador.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrabajadorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trabajador)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Trabajador in the database
        List<Trabajador> trabajadorList = trabajadorRepository.findAll();
        assertThat(trabajadorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTrabajadorWithPatch() throws Exception {
        // Initialize the database
        trabajadorRepository.saveAndFlush(trabajador);

        int databaseSizeBeforeUpdate = trabajadorRepository.findAll().size();

        // Update the trabajador using partial update
        Trabajador partialUpdatedTrabajador = new Trabajador();
        partialUpdatedTrabajador.setId(trabajador.getId());

        partialUpdatedTrabajador.descripcion(UPDATED_DESCRIPCION).createdDate(UPDATED_CREATED_DATE);

        restTrabajadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrabajador.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrabajador))
            )
            .andExpect(status().isOk());

        // Validate the Trabajador in the database
        List<Trabajador> trabajadorList = trabajadorRepository.findAll();
        assertThat(trabajadorList).hasSize(databaseSizeBeforeUpdate);
        Trabajador testTrabajador = trabajadorList.get(trabajadorList.size() - 1);
        assertThat(testTrabajador.getSueldo()).isEqualTo(DEFAULT_SUELDO);
        assertThat(testTrabajador.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testTrabajador.getFechaIngreso()).isEqualTo(DEFAULT_FECHA_INGRESO);
        assertThat(testTrabajador.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testTrabajador.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTrabajador.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateTrabajadorWithPatch() throws Exception {
        // Initialize the database
        trabajadorRepository.saveAndFlush(trabajador);

        int databaseSizeBeforeUpdate = trabajadorRepository.findAll().size();

        // Update the trabajador using partial update
        Trabajador partialUpdatedTrabajador = new Trabajador();
        partialUpdatedTrabajador.setId(trabajador.getId());

        partialUpdatedTrabajador
            .sueldo(UPDATED_SUELDO)
            .descripcion(UPDATED_DESCRIPCION)
            .fechaIngreso(UPDATED_FECHA_INGRESO)
            .estado(UPDATED_ESTADO)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restTrabajadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrabajador.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrabajador))
            )
            .andExpect(status().isOk());

        // Validate the Trabajador in the database
        List<Trabajador> trabajadorList = trabajadorRepository.findAll();
        assertThat(trabajadorList).hasSize(databaseSizeBeforeUpdate);
        Trabajador testTrabajador = trabajadorList.get(trabajadorList.size() - 1);
        assertThat(testTrabajador.getSueldo()).isEqualTo(UPDATED_SUELDO);
        assertThat(testTrabajador.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testTrabajador.getFechaIngreso()).isEqualTo(UPDATED_FECHA_INGRESO);
        assertThat(testTrabajador.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testTrabajador.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTrabajador.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingTrabajador() throws Exception {
        int databaseSizeBeforeUpdate = trabajadorRepository.findAll().size();
        trabajador.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrabajadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, trabajador.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trabajador))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trabajador in the database
        List<Trabajador> trabajadorList = trabajadorRepository.findAll();
        assertThat(trabajadorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTrabajador() throws Exception {
        int databaseSizeBeforeUpdate = trabajadorRepository.findAll().size();
        trabajador.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrabajadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trabajador))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trabajador in the database
        List<Trabajador> trabajadorList = trabajadorRepository.findAll();
        assertThat(trabajadorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTrabajador() throws Exception {
        int databaseSizeBeforeUpdate = trabajadorRepository.findAll().size();
        trabajador.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrabajadorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(trabajador))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Trabajador in the database
        List<Trabajador> trabajadorList = trabajadorRepository.findAll();
        assertThat(trabajadorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTrabajador() throws Exception {
        // Initialize the database
        trabajadorRepository.saveAndFlush(trabajador);

        int databaseSizeBeforeDelete = trabajadorRepository.findAll().size();

        // Delete the trabajador
        restTrabajadorMockMvc
            .perform(delete(ENTITY_API_URL_ID, trabajador.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Trabajador> trabajadorList = trabajadorRepository.findAll();
        assertThat(trabajadorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
