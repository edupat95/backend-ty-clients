package com.tyclients.tycapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tyclients.tycapp.IntegrationTest;
import com.tyclients.tycapp.domain.Club;
import com.tyclients.tycapp.domain.TipoProducto;
import com.tyclients.tycapp.repository.TipoProductoRepository;
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
 * Integration tests for the {@link TipoProductoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TipoProductoResourceIT {

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tipo-productos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TipoProductoRepository tipoProductoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoProductoMockMvc;

    private TipoProducto tipoProducto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoProducto createEntity(EntityManager em) {
        TipoProducto tipoProducto = new TipoProducto().estado(DEFAULT_ESTADO).name(DEFAULT_NAME);
        // Add required entity
        Club club;
        if (TestUtil.findAll(em, Club.class).isEmpty()) {
            club = ClubResourceIT.createEntity(em);
            em.persist(club);
            em.flush();
        } else {
            club = TestUtil.findAll(em, Club.class).get(0);
        }
        tipoProducto.setClub(club);
        return tipoProducto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoProducto createUpdatedEntity(EntityManager em) {
        TipoProducto tipoProducto = new TipoProducto().estado(UPDATED_ESTADO).name(UPDATED_NAME);
        // Add required entity
        Club club;
        if (TestUtil.findAll(em, Club.class).isEmpty()) {
            club = ClubResourceIT.createUpdatedEntity(em);
            em.persist(club);
            em.flush();
        } else {
            club = TestUtil.findAll(em, Club.class).get(0);
        }
        tipoProducto.setClub(club);
        return tipoProducto;
    }

    @BeforeEach
    public void initTest() {
        tipoProducto = createEntity(em);
    }

    @Test
    @Transactional
    void createTipoProducto() throws Exception {
        int databaseSizeBeforeCreate = tipoProductoRepository.findAll().size();
        // Create the TipoProducto
        restTipoProductoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoProducto)))
            .andExpect(status().isCreated());

        // Validate the TipoProducto in the database
        List<TipoProducto> tipoProductoList = tipoProductoRepository.findAll();
        assertThat(tipoProductoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoProducto testTipoProducto = tipoProductoList.get(tipoProductoList.size() - 1);
        assertThat(testTipoProducto.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testTipoProducto.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createTipoProductoWithExistingId() throws Exception {
        // Create the TipoProducto with an existing ID
        tipoProducto.setId(1L);

        int databaseSizeBeforeCreate = tipoProductoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoProductoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoProducto)))
            .andExpect(status().isBadRequest());

        // Validate the TipoProducto in the database
        List<TipoProducto> tipoProductoList = tipoProductoRepository.findAll();
        assertThat(tipoProductoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoProductoRepository.findAll().size();
        // set the field null
        tipoProducto.setEstado(null);

        // Create the TipoProducto, which fails.

        restTipoProductoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoProducto)))
            .andExpect(status().isBadRequest());

        List<TipoProducto> tipoProductoList = tipoProductoRepository.findAll();
        assertThat(tipoProductoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTipoProductos() throws Exception {
        // Initialize the database
        tipoProductoRepository.saveAndFlush(tipoProducto);

        // Get all the tipoProductoList
        restTipoProductoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoProducto.getId().intValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getTipoProducto() throws Exception {
        // Initialize the database
        tipoProductoRepository.saveAndFlush(tipoProducto);

        // Get the tipoProducto
        restTipoProductoMockMvc
            .perform(get(ENTITY_API_URL_ID, tipoProducto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoProducto.getId().intValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingTipoProducto() throws Exception {
        // Get the tipoProducto
        restTipoProductoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTipoProducto() throws Exception {
        // Initialize the database
        tipoProductoRepository.saveAndFlush(tipoProducto);

        int databaseSizeBeforeUpdate = tipoProductoRepository.findAll().size();

        // Update the tipoProducto
        TipoProducto updatedTipoProducto = tipoProductoRepository.findById(tipoProducto.getId()).get();
        // Disconnect from session so that the updates on updatedTipoProducto are not directly saved in db
        em.detach(updatedTipoProducto);
        updatedTipoProducto.estado(UPDATED_ESTADO).name(UPDATED_NAME);

        restTipoProductoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTipoProducto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTipoProducto))
            )
            .andExpect(status().isOk());

        // Validate the TipoProducto in the database
        List<TipoProducto> tipoProductoList = tipoProductoRepository.findAll();
        assertThat(tipoProductoList).hasSize(databaseSizeBeforeUpdate);
        TipoProducto testTipoProducto = tipoProductoList.get(tipoProductoList.size() - 1);
        assertThat(testTipoProducto.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testTipoProducto.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingTipoProducto() throws Exception {
        int databaseSizeBeforeUpdate = tipoProductoRepository.findAll().size();
        tipoProducto.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoProductoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoProducto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoProducto))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoProducto in the database
        List<TipoProducto> tipoProductoList = tipoProductoRepository.findAll();
        assertThat(tipoProductoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTipoProducto() throws Exception {
        int databaseSizeBeforeUpdate = tipoProductoRepository.findAll().size();
        tipoProducto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoProductoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoProducto))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoProducto in the database
        List<TipoProducto> tipoProductoList = tipoProductoRepository.findAll();
        assertThat(tipoProductoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTipoProducto() throws Exception {
        int databaseSizeBeforeUpdate = tipoProductoRepository.findAll().size();
        tipoProducto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoProductoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoProducto)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoProducto in the database
        List<TipoProducto> tipoProductoList = tipoProductoRepository.findAll();
        assertThat(tipoProductoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTipoProductoWithPatch() throws Exception {
        // Initialize the database
        tipoProductoRepository.saveAndFlush(tipoProducto);

        int databaseSizeBeforeUpdate = tipoProductoRepository.findAll().size();

        // Update the tipoProducto using partial update
        TipoProducto partialUpdatedTipoProducto = new TipoProducto();
        partialUpdatedTipoProducto.setId(tipoProducto.getId());

        restTipoProductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoProducto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoProducto))
            )
            .andExpect(status().isOk());

        // Validate the TipoProducto in the database
        List<TipoProducto> tipoProductoList = tipoProductoRepository.findAll();
        assertThat(tipoProductoList).hasSize(databaseSizeBeforeUpdate);
        TipoProducto testTipoProducto = tipoProductoList.get(tipoProductoList.size() - 1);
        assertThat(testTipoProducto.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testTipoProducto.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateTipoProductoWithPatch() throws Exception {
        // Initialize the database
        tipoProductoRepository.saveAndFlush(tipoProducto);

        int databaseSizeBeforeUpdate = tipoProductoRepository.findAll().size();

        // Update the tipoProducto using partial update
        TipoProducto partialUpdatedTipoProducto = new TipoProducto();
        partialUpdatedTipoProducto.setId(tipoProducto.getId());

        partialUpdatedTipoProducto.estado(UPDATED_ESTADO).name(UPDATED_NAME);

        restTipoProductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoProducto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoProducto))
            )
            .andExpect(status().isOk());

        // Validate the TipoProducto in the database
        List<TipoProducto> tipoProductoList = tipoProductoRepository.findAll();
        assertThat(tipoProductoList).hasSize(databaseSizeBeforeUpdate);
        TipoProducto testTipoProducto = tipoProductoList.get(tipoProductoList.size() - 1);
        assertThat(testTipoProducto.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testTipoProducto.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingTipoProducto() throws Exception {
        int databaseSizeBeforeUpdate = tipoProductoRepository.findAll().size();
        tipoProducto.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoProductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tipoProducto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoProducto))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoProducto in the database
        List<TipoProducto> tipoProductoList = tipoProductoRepository.findAll();
        assertThat(tipoProductoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTipoProducto() throws Exception {
        int databaseSizeBeforeUpdate = tipoProductoRepository.findAll().size();
        tipoProducto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoProductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoProducto))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoProducto in the database
        List<TipoProducto> tipoProductoList = tipoProductoRepository.findAll();
        assertThat(tipoProductoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTipoProducto() throws Exception {
        int databaseSizeBeforeUpdate = tipoProductoRepository.findAll().size();
        tipoProducto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoProductoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tipoProducto))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoProducto in the database
        List<TipoProducto> tipoProductoList = tipoProductoRepository.findAll();
        assertThat(tipoProductoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTipoProducto() throws Exception {
        // Initialize the database
        tipoProductoRepository.saveAndFlush(tipoProducto);

        int databaseSizeBeforeDelete = tipoProductoRepository.findAll().size();

        // Delete the tipoProducto
        restTipoProductoMockMvc
            .perform(delete(ENTITY_API_URL_ID, tipoProducto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoProducto> tipoProductoList = tipoProductoRepository.findAll();
        assertThat(tipoProductoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
