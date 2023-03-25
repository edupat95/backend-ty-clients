package com.tyclients.tycapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tyclients.tycapp.IntegrationTest;
import com.tyclients.tycapp.domain.AdminClub;
import com.tyclients.tycapp.domain.Club;
import com.tyclients.tycapp.domain.Evento;
import com.tyclients.tycapp.repository.EventoRepository;
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
 * Integration tests for the {@link EventoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EventoResourceIT {

    private static final Instant DEFAULT_FECHA_EVENTO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_EVENTO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_CREACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_CREACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/eventos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEventoMockMvc;

    private Evento evento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Evento createEntity(EntityManager em) {
        Evento evento = new Evento()
            .fechaEvento(DEFAULT_FECHA_EVENTO)
            .fechaCreacion(DEFAULT_FECHA_CREACION)
            .estado(DEFAULT_ESTADO)
            .direccion(DEFAULT_DIRECCION)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        // Add required entity
        AdminClub adminClub;
        if (TestUtil.findAll(em, AdminClub.class).isEmpty()) {
            adminClub = AdminClubResourceIT.createEntity(em);
            em.persist(adminClub);
            em.flush();
        } else {
            adminClub = TestUtil.findAll(em, AdminClub.class).get(0);
        }
        evento.setAdminClub(adminClub);
        // Add required entity
        Club club;
        if (TestUtil.findAll(em, Club.class).isEmpty()) {
            club = ClubResourceIT.createEntity(em);
            em.persist(club);
            em.flush();
        } else {
            club = TestUtil.findAll(em, Club.class).get(0);
        }
        evento.setClub(club);
        return evento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Evento createUpdatedEntity(EntityManager em) {
        Evento evento = new Evento()
            .fechaEvento(UPDATED_FECHA_EVENTO)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .estado(UPDATED_ESTADO)
            .direccion(UPDATED_DIRECCION)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        // Add required entity
        AdminClub adminClub;
        if (TestUtil.findAll(em, AdminClub.class).isEmpty()) {
            adminClub = AdminClubResourceIT.createUpdatedEntity(em);
            em.persist(adminClub);
            em.flush();
        } else {
            adminClub = TestUtil.findAll(em, AdminClub.class).get(0);
        }
        evento.setAdminClub(adminClub);
        // Add required entity
        Club club;
        if (TestUtil.findAll(em, Club.class).isEmpty()) {
            club = ClubResourceIT.createUpdatedEntity(em);
            em.persist(club);
            em.flush();
        } else {
            club = TestUtil.findAll(em, Club.class).get(0);
        }
        evento.setClub(club);
        return evento;
    }

    @BeforeEach
    public void initTest() {
        evento = createEntity(em);
    }

    @Test
    @Transactional
    void createEvento() throws Exception {
        int databaseSizeBeforeCreate = eventoRepository.findAll().size();
        // Create the Evento
        restEventoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(evento)))
            .andExpect(status().isCreated());

        // Validate the Evento in the database
        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeCreate + 1);
        Evento testEvento = eventoList.get(eventoList.size() - 1);
        assertThat(testEvento.getFechaEvento()).isEqualTo(DEFAULT_FECHA_EVENTO);
        assertThat(testEvento.getFechaCreacion()).isEqualTo(DEFAULT_FECHA_CREACION);
        assertThat(testEvento.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testEvento.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testEvento.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testEvento.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createEventoWithExistingId() throws Exception {
        // Create the Evento with an existing ID
        evento.setId(1L);

        int databaseSizeBeforeCreate = eventoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(evento)))
            .andExpect(status().isBadRequest());

        // Validate the Evento in the database
        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventoRepository.findAll().size();
        // set the field null
        evento.setEstado(null);

        // Create the Evento, which fails.

        restEventoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(evento)))
            .andExpect(status().isBadRequest());

        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEventos() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        // Get all the eventoList
        restEventoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evento.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaEvento").value(hasItem(DEFAULT_FECHA_EVENTO.toString())))
            .andExpect(jsonPath("$.[*].fechaCreacion").value(hasItem(DEFAULT_FECHA_CREACION.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getEvento() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        // Get the evento
        restEventoMockMvc
            .perform(get(ENTITY_API_URL_ID, evento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(evento.getId().intValue()))
            .andExpect(jsonPath("$.fechaEvento").value(DEFAULT_FECHA_EVENTO.toString()))
            .andExpect(jsonPath("$.fechaCreacion").value(DEFAULT_FECHA_CREACION.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingEvento() throws Exception {
        // Get the evento
        restEventoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEvento() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        int databaseSizeBeforeUpdate = eventoRepository.findAll().size();

        // Update the evento
        Evento updatedEvento = eventoRepository.findById(evento.getId()).get();
        // Disconnect from session so that the updates on updatedEvento are not directly saved in db
        em.detach(updatedEvento);
        updatedEvento
            .fechaEvento(UPDATED_FECHA_EVENTO)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .estado(UPDATED_ESTADO)
            .direccion(UPDATED_DIRECCION)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restEventoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEvento.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEvento))
            )
            .andExpect(status().isOk());

        // Validate the Evento in the database
        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeUpdate);
        Evento testEvento = eventoList.get(eventoList.size() - 1);
        assertThat(testEvento.getFechaEvento()).isEqualTo(UPDATED_FECHA_EVENTO);
        assertThat(testEvento.getFechaCreacion()).isEqualTo(UPDATED_FECHA_CREACION);
        assertThat(testEvento.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testEvento.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testEvento.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEvento.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingEvento() throws Exception {
        int databaseSizeBeforeUpdate = eventoRepository.findAll().size();
        evento.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, evento.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Evento in the database
        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEvento() throws Exception {
        int databaseSizeBeforeUpdate = eventoRepository.findAll().size();
        evento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Evento in the database
        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEvento() throws Exception {
        int databaseSizeBeforeUpdate = eventoRepository.findAll().size();
        evento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(evento)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Evento in the database
        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEventoWithPatch() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        int databaseSizeBeforeUpdate = eventoRepository.findAll().size();

        // Update the evento using partial update
        Evento partialUpdatedEvento = new Evento();
        partialUpdatedEvento.setId(evento.getId());

        partialUpdatedEvento.fechaCreacion(UPDATED_FECHA_CREACION).estado(UPDATED_ESTADO).updatedDate(UPDATED_UPDATED_DATE);

        restEventoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvento))
            )
            .andExpect(status().isOk());

        // Validate the Evento in the database
        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeUpdate);
        Evento testEvento = eventoList.get(eventoList.size() - 1);
        assertThat(testEvento.getFechaEvento()).isEqualTo(DEFAULT_FECHA_EVENTO);
        assertThat(testEvento.getFechaCreacion()).isEqualTo(UPDATED_FECHA_CREACION);
        assertThat(testEvento.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testEvento.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testEvento.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testEvento.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateEventoWithPatch() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        int databaseSizeBeforeUpdate = eventoRepository.findAll().size();

        // Update the evento using partial update
        Evento partialUpdatedEvento = new Evento();
        partialUpdatedEvento.setId(evento.getId());

        partialUpdatedEvento
            .fechaEvento(UPDATED_FECHA_EVENTO)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .estado(UPDATED_ESTADO)
            .direccion(UPDATED_DIRECCION)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restEventoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvento))
            )
            .andExpect(status().isOk());

        // Validate the Evento in the database
        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeUpdate);
        Evento testEvento = eventoList.get(eventoList.size() - 1);
        assertThat(testEvento.getFechaEvento()).isEqualTo(UPDATED_FECHA_EVENTO);
        assertThat(testEvento.getFechaCreacion()).isEqualTo(UPDATED_FECHA_CREACION);
        assertThat(testEvento.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testEvento.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testEvento.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEvento.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingEvento() throws Exception {
        int databaseSizeBeforeUpdate = eventoRepository.findAll().size();
        evento.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, evento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(evento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Evento in the database
        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEvento() throws Exception {
        int databaseSizeBeforeUpdate = eventoRepository.findAll().size();
        evento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(evento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Evento in the database
        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEvento() throws Exception {
        int databaseSizeBeforeUpdate = eventoRepository.findAll().size();
        evento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(evento)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Evento in the database
        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEvento() throws Exception {
        // Initialize the database
        eventoRepository.saveAndFlush(evento);

        int databaseSizeBeforeDelete = eventoRepository.findAll().size();

        // Delete the evento
        restEventoMockMvc
            .perform(delete(ENTITY_API_URL_ID, evento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Evento> eventoList = eventoRepository.findAll();
        assertThat(eventoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
