package com.tyclients.tycapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tyclients.tycapp.IntegrationTest;
import com.tyclients.tycapp.domain.Asociado;
import com.tyclients.tycapp.domain.AsociadoClub;
import com.tyclients.tycapp.domain.Club;
import com.tyclients.tycapp.repository.AsociadoClubRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.UUID;
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
 * Integration tests for the {@link AsociadoClubResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AsociadoClubResourceIT {

    private static final UUID DEFAULT_IDENTIFICADOR = UUID.randomUUID();
    private static final UUID UPDATED_IDENTIFICADOR = UUID.randomUUID();

    private static final Instant DEFAULT_FECHA_ASOCIACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_ASOCIACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_PUNTOS_CLUB = 1L;
    private static final Long UPDATED_PUNTOS_CLUB = 2L;

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/asociado-clubs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AsociadoClubRepository asociadoClubRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAsociadoClubMockMvc;

    private AsociadoClub asociadoClub;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AsociadoClub createEntity(EntityManager em) {
        AsociadoClub asociadoClub = new AsociadoClub()
            .identificador(DEFAULT_IDENTIFICADOR)
            .fechaAsociacion(DEFAULT_FECHA_ASOCIACION)
            .puntosClub(DEFAULT_PUNTOS_CLUB)
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
        asociadoClub.setAsociado(asociado);
        // Add required entity
        Club club;
        if (TestUtil.findAll(em, Club.class).isEmpty()) {
            club = ClubResourceIT.createEntity(em);
            em.persist(club);
            em.flush();
        } else {
            club = TestUtil.findAll(em, Club.class).get(0);
        }
        asociadoClub.setClub(club);
        return asociadoClub;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AsociadoClub createUpdatedEntity(EntityManager em) {
        AsociadoClub asociadoClub = new AsociadoClub()
            .identificador(UPDATED_IDENTIFICADOR)
            .fechaAsociacion(UPDATED_FECHA_ASOCIACION)
            .puntosClub(UPDATED_PUNTOS_CLUB)
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
        asociadoClub.setAsociado(asociado);
        // Add required entity
        Club club;
        if (TestUtil.findAll(em, Club.class).isEmpty()) {
            club = ClubResourceIT.createUpdatedEntity(em);
            em.persist(club);
            em.flush();
        } else {
            club = TestUtil.findAll(em, Club.class).get(0);
        }
        asociadoClub.setClub(club);
        return asociadoClub;
    }

    @BeforeEach
    public void initTest() {
        asociadoClub = createEntity(em);
    }

    @Test
    @Transactional
    void createAsociadoClub() throws Exception {
        int databaseSizeBeforeCreate = asociadoClubRepository.findAll().size();
        // Create the AsociadoClub
        restAsociadoClubMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(asociadoClub)))
            .andExpect(status().isCreated());

        // Validate the AsociadoClub in the database
        List<AsociadoClub> asociadoClubList = asociadoClubRepository.findAll();
        assertThat(asociadoClubList).hasSize(databaseSizeBeforeCreate + 1);
        AsociadoClub testAsociadoClub = asociadoClubList.get(asociadoClubList.size() - 1);
        assertThat(testAsociadoClub.getIdentificador()).isEqualTo(DEFAULT_IDENTIFICADOR);
        assertThat(testAsociadoClub.getFechaAsociacion()).isEqualTo(DEFAULT_FECHA_ASOCIACION);
        assertThat(testAsociadoClub.getPuntosClub()).isEqualTo(DEFAULT_PUNTOS_CLUB);
        assertThat(testAsociadoClub.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testAsociadoClub.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testAsociadoClub.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createAsociadoClubWithExistingId() throws Exception {
        // Create the AsociadoClub with an existing ID
        asociadoClub.setId(1L);

        int databaseSizeBeforeCreate = asociadoClubRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAsociadoClubMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(asociadoClub)))
            .andExpect(status().isBadRequest());

        // Validate the AsociadoClub in the database
        List<AsociadoClub> asociadoClubList = asociadoClubRepository.findAll();
        assertThat(asociadoClubList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdentificadorIsRequired() throws Exception {
        int databaseSizeBeforeTest = asociadoClubRepository.findAll().size();
        // set the field null
        asociadoClub.setIdentificador(null);

        // Create the AsociadoClub, which fails.

        restAsociadoClubMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(asociadoClub)))
            .andExpect(status().isBadRequest());

        List<AsociadoClub> asociadoClubList = asociadoClubRepository.findAll();
        assertThat(asociadoClubList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAsociadoClubs() throws Exception {
        // Initialize the database
        asociadoClubRepository.saveAndFlush(asociadoClub);

        // Get all the asociadoClubList
        restAsociadoClubMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(asociadoClub.getId().intValue())))
            .andExpect(jsonPath("$.[*].identificador").value(hasItem(DEFAULT_IDENTIFICADOR.toString())))
            .andExpect(jsonPath("$.[*].fechaAsociacion").value(hasItem(DEFAULT_FECHA_ASOCIACION.toString())))
            .andExpect(jsonPath("$.[*].puntosClub").value(hasItem(DEFAULT_PUNTOS_CLUB.intValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getAsociadoClub() throws Exception {
        // Initialize the database
        asociadoClubRepository.saveAndFlush(asociadoClub);

        // Get the asociadoClub
        restAsociadoClubMockMvc
            .perform(get(ENTITY_API_URL_ID, asociadoClub.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(asociadoClub.getId().intValue()))
            .andExpect(jsonPath("$.identificador").value(DEFAULT_IDENTIFICADOR.toString()))
            .andExpect(jsonPath("$.fechaAsociacion").value(DEFAULT_FECHA_ASOCIACION.toString()))
            .andExpect(jsonPath("$.puntosClub").value(DEFAULT_PUNTOS_CLUB.intValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAsociadoClub() throws Exception {
        // Get the asociadoClub
        restAsociadoClubMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAsociadoClub() throws Exception {
        // Initialize the database
        asociadoClubRepository.saveAndFlush(asociadoClub);

        int databaseSizeBeforeUpdate = asociadoClubRepository.findAll().size();

        // Update the asociadoClub
        AsociadoClub updatedAsociadoClub = asociadoClubRepository.findById(asociadoClub.getId()).get();
        // Disconnect from session so that the updates on updatedAsociadoClub are not directly saved in db
        em.detach(updatedAsociadoClub);
        updatedAsociadoClub
            .identificador(UPDATED_IDENTIFICADOR)
            .fechaAsociacion(UPDATED_FECHA_ASOCIACION)
            .puntosClub(UPDATED_PUNTOS_CLUB)
            .estado(UPDATED_ESTADO)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restAsociadoClubMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAsociadoClub.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAsociadoClub))
            )
            .andExpect(status().isOk());

        // Validate the AsociadoClub in the database
        List<AsociadoClub> asociadoClubList = asociadoClubRepository.findAll();
        assertThat(asociadoClubList).hasSize(databaseSizeBeforeUpdate);
        AsociadoClub testAsociadoClub = asociadoClubList.get(asociadoClubList.size() - 1);
        assertThat(testAsociadoClub.getIdentificador()).isEqualTo(UPDATED_IDENTIFICADOR);
        assertThat(testAsociadoClub.getFechaAsociacion()).isEqualTo(UPDATED_FECHA_ASOCIACION);
        assertThat(testAsociadoClub.getPuntosClub()).isEqualTo(UPDATED_PUNTOS_CLUB);
        assertThat(testAsociadoClub.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testAsociadoClub.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAsociadoClub.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingAsociadoClub() throws Exception {
        int databaseSizeBeforeUpdate = asociadoClubRepository.findAll().size();
        asociadoClub.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAsociadoClubMockMvc
            .perform(
                put(ENTITY_API_URL_ID, asociadoClub.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(asociadoClub))
            )
            .andExpect(status().isBadRequest());

        // Validate the AsociadoClub in the database
        List<AsociadoClub> asociadoClubList = asociadoClubRepository.findAll();
        assertThat(asociadoClubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAsociadoClub() throws Exception {
        int databaseSizeBeforeUpdate = asociadoClubRepository.findAll().size();
        asociadoClub.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAsociadoClubMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(asociadoClub))
            )
            .andExpect(status().isBadRequest());

        // Validate the AsociadoClub in the database
        List<AsociadoClub> asociadoClubList = asociadoClubRepository.findAll();
        assertThat(asociadoClubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAsociadoClub() throws Exception {
        int databaseSizeBeforeUpdate = asociadoClubRepository.findAll().size();
        asociadoClub.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAsociadoClubMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(asociadoClub)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AsociadoClub in the database
        List<AsociadoClub> asociadoClubList = asociadoClubRepository.findAll();
        assertThat(asociadoClubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAsociadoClubWithPatch() throws Exception {
        // Initialize the database
        asociadoClubRepository.saveAndFlush(asociadoClub);

        int databaseSizeBeforeUpdate = asociadoClubRepository.findAll().size();

        // Update the asociadoClub using partial update
        AsociadoClub partialUpdatedAsociadoClub = new AsociadoClub();
        partialUpdatedAsociadoClub.setId(asociadoClub.getId());

        partialUpdatedAsociadoClub.identificador(UPDATED_IDENTIFICADOR).estado(UPDATED_ESTADO).createdDate(UPDATED_CREATED_DATE);

        restAsociadoClubMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAsociadoClub.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAsociadoClub))
            )
            .andExpect(status().isOk());

        // Validate the AsociadoClub in the database
        List<AsociadoClub> asociadoClubList = asociadoClubRepository.findAll();
        assertThat(asociadoClubList).hasSize(databaseSizeBeforeUpdate);
        AsociadoClub testAsociadoClub = asociadoClubList.get(asociadoClubList.size() - 1);
        assertThat(testAsociadoClub.getIdentificador()).isEqualTo(UPDATED_IDENTIFICADOR);
        assertThat(testAsociadoClub.getFechaAsociacion()).isEqualTo(DEFAULT_FECHA_ASOCIACION);
        assertThat(testAsociadoClub.getPuntosClub()).isEqualTo(DEFAULT_PUNTOS_CLUB);
        assertThat(testAsociadoClub.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testAsociadoClub.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAsociadoClub.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateAsociadoClubWithPatch() throws Exception {
        // Initialize the database
        asociadoClubRepository.saveAndFlush(asociadoClub);

        int databaseSizeBeforeUpdate = asociadoClubRepository.findAll().size();

        // Update the asociadoClub using partial update
        AsociadoClub partialUpdatedAsociadoClub = new AsociadoClub();
        partialUpdatedAsociadoClub.setId(asociadoClub.getId());

        partialUpdatedAsociadoClub
            .identificador(UPDATED_IDENTIFICADOR)
            .fechaAsociacion(UPDATED_FECHA_ASOCIACION)
            .puntosClub(UPDATED_PUNTOS_CLUB)
            .estado(UPDATED_ESTADO)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restAsociadoClubMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAsociadoClub.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAsociadoClub))
            )
            .andExpect(status().isOk());

        // Validate the AsociadoClub in the database
        List<AsociadoClub> asociadoClubList = asociadoClubRepository.findAll();
        assertThat(asociadoClubList).hasSize(databaseSizeBeforeUpdate);
        AsociadoClub testAsociadoClub = asociadoClubList.get(asociadoClubList.size() - 1);
        assertThat(testAsociadoClub.getIdentificador()).isEqualTo(UPDATED_IDENTIFICADOR);
        assertThat(testAsociadoClub.getFechaAsociacion()).isEqualTo(UPDATED_FECHA_ASOCIACION);
        assertThat(testAsociadoClub.getPuntosClub()).isEqualTo(UPDATED_PUNTOS_CLUB);
        assertThat(testAsociadoClub.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testAsociadoClub.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAsociadoClub.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingAsociadoClub() throws Exception {
        int databaseSizeBeforeUpdate = asociadoClubRepository.findAll().size();
        asociadoClub.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAsociadoClubMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, asociadoClub.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(asociadoClub))
            )
            .andExpect(status().isBadRequest());

        // Validate the AsociadoClub in the database
        List<AsociadoClub> asociadoClubList = asociadoClubRepository.findAll();
        assertThat(asociadoClubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAsociadoClub() throws Exception {
        int databaseSizeBeforeUpdate = asociadoClubRepository.findAll().size();
        asociadoClub.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAsociadoClubMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(asociadoClub))
            )
            .andExpect(status().isBadRequest());

        // Validate the AsociadoClub in the database
        List<AsociadoClub> asociadoClubList = asociadoClubRepository.findAll();
        assertThat(asociadoClubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAsociadoClub() throws Exception {
        int databaseSizeBeforeUpdate = asociadoClubRepository.findAll().size();
        asociadoClub.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAsociadoClubMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(asociadoClub))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AsociadoClub in the database
        List<AsociadoClub> asociadoClubList = asociadoClubRepository.findAll();
        assertThat(asociadoClubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAsociadoClub() throws Exception {
        // Initialize the database
        asociadoClubRepository.saveAndFlush(asociadoClub);

        int databaseSizeBeforeDelete = asociadoClubRepository.findAll().size();

        // Delete the asociadoClub
        restAsociadoClubMockMvc
            .perform(delete(ENTITY_API_URL_ID, asociadoClub.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AsociadoClub> asociadoClubList = asociadoClubRepository.findAll();
        assertThat(asociadoClubList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
