package com.tyclients.tycapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tyclients.tycapp.IntegrationTest;
import com.tyclients.tycapp.domain.Asociado;
import com.tyclients.tycapp.domain.Documento;
import com.tyclients.tycapp.repository.AsociadoRepository;
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
 * Integration tests for the {@link AsociadoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AsociadoResourceIT {

    private static final UUID DEFAULT_IDENTIFICADOR_GENERAL = UUID.randomUUID();
    private static final UUID UPDATED_IDENTIFICADOR_GENERAL = UUID.randomUUID();

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/asociados";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AsociadoRepository asociadoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAsociadoMockMvc;

    private Asociado asociado;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Asociado createEntity(EntityManager em) {
        Asociado asociado = new Asociado()
            .identificadorGeneral(DEFAULT_IDENTIFICADOR_GENERAL)
            .estado(DEFAULT_ESTADO)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        // Add required entity
        Documento documento;
        if (TestUtil.findAll(em, Documento.class).isEmpty()) {
            documento = DocumentoResourceIT.createEntity(em);
            em.persist(documento);
            em.flush();
        } else {
            documento = TestUtil.findAll(em, Documento.class).get(0);
        }
        asociado.setDocumento(documento);
        return asociado;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Asociado createUpdatedEntity(EntityManager em) {
        Asociado asociado = new Asociado()
            .identificadorGeneral(UPDATED_IDENTIFICADOR_GENERAL)
            .estado(UPDATED_ESTADO)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        // Add required entity
        Documento documento;
        if (TestUtil.findAll(em, Documento.class).isEmpty()) {
            documento = DocumentoResourceIT.createUpdatedEntity(em);
            em.persist(documento);
            em.flush();
        } else {
            documento = TestUtil.findAll(em, Documento.class).get(0);
        }
        asociado.setDocumento(documento);
        return asociado;
    }

    @BeforeEach
    public void initTest() {
        asociado = createEntity(em);
    }

    @Test
    @Transactional
    void createAsociado() throws Exception {
        int databaseSizeBeforeCreate = asociadoRepository.findAll().size();
        // Create the Asociado
        restAsociadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(asociado)))
            .andExpect(status().isCreated());

        // Validate the Asociado in the database
        List<Asociado> asociadoList = asociadoRepository.findAll();
        assertThat(asociadoList).hasSize(databaseSizeBeforeCreate + 1);
        Asociado testAsociado = asociadoList.get(asociadoList.size() - 1);
        assertThat(testAsociado.getIdentificadorGeneral()).isEqualTo(DEFAULT_IDENTIFICADOR_GENERAL);
        assertThat(testAsociado.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testAsociado.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testAsociado.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createAsociadoWithExistingId() throws Exception {
        // Create the Asociado with an existing ID
        asociado.setId(1L);

        int databaseSizeBeforeCreate = asociadoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAsociadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(asociado)))
            .andExpect(status().isBadRequest());

        // Validate the Asociado in the database
        List<Asociado> asociadoList = asociadoRepository.findAll();
        assertThat(asociadoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAsociados() throws Exception {
        // Initialize the database
        asociadoRepository.saveAndFlush(asociado);

        // Get all the asociadoList
        restAsociadoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(asociado.getId().intValue())))
            .andExpect(jsonPath("$.[*].identificadorGeneral").value(hasItem(DEFAULT_IDENTIFICADOR_GENERAL.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getAsociado() throws Exception {
        // Initialize the database
        asociadoRepository.saveAndFlush(asociado);

        // Get the asociado
        restAsociadoMockMvc
            .perform(get(ENTITY_API_URL_ID, asociado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(asociado.getId().intValue()))
            .andExpect(jsonPath("$.identificadorGeneral").value(DEFAULT_IDENTIFICADOR_GENERAL.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAsociado() throws Exception {
        // Get the asociado
        restAsociadoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAsociado() throws Exception {
        // Initialize the database
        asociadoRepository.saveAndFlush(asociado);

        int databaseSizeBeforeUpdate = asociadoRepository.findAll().size();

        // Update the asociado
        Asociado updatedAsociado = asociadoRepository.findById(asociado.getId()).get();
        // Disconnect from session so that the updates on updatedAsociado are not directly saved in db
        em.detach(updatedAsociado);
        updatedAsociado
            .identificadorGeneral(UPDATED_IDENTIFICADOR_GENERAL)
            .estado(UPDATED_ESTADO)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restAsociadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAsociado.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAsociado))
            )
            .andExpect(status().isOk());

        // Validate the Asociado in the database
        List<Asociado> asociadoList = asociadoRepository.findAll();
        assertThat(asociadoList).hasSize(databaseSizeBeforeUpdate);
        Asociado testAsociado = asociadoList.get(asociadoList.size() - 1);
        assertThat(testAsociado.getIdentificadorGeneral()).isEqualTo(UPDATED_IDENTIFICADOR_GENERAL);
        assertThat(testAsociado.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testAsociado.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAsociado.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingAsociado() throws Exception {
        int databaseSizeBeforeUpdate = asociadoRepository.findAll().size();
        asociado.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAsociadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, asociado.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(asociado))
            )
            .andExpect(status().isBadRequest());

        // Validate the Asociado in the database
        List<Asociado> asociadoList = asociadoRepository.findAll();
        assertThat(asociadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAsociado() throws Exception {
        int databaseSizeBeforeUpdate = asociadoRepository.findAll().size();
        asociado.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAsociadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(asociado))
            )
            .andExpect(status().isBadRequest());

        // Validate the Asociado in the database
        List<Asociado> asociadoList = asociadoRepository.findAll();
        assertThat(asociadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAsociado() throws Exception {
        int databaseSizeBeforeUpdate = asociadoRepository.findAll().size();
        asociado.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAsociadoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(asociado)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Asociado in the database
        List<Asociado> asociadoList = asociadoRepository.findAll();
        assertThat(asociadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAsociadoWithPatch() throws Exception {
        // Initialize the database
        asociadoRepository.saveAndFlush(asociado);

        int databaseSizeBeforeUpdate = asociadoRepository.findAll().size();

        // Update the asociado using partial update
        Asociado partialUpdatedAsociado = new Asociado();
        partialUpdatedAsociado.setId(asociado.getId());

        partialUpdatedAsociado.identificadorGeneral(UPDATED_IDENTIFICADOR_GENERAL).estado(UPDATED_ESTADO);

        restAsociadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAsociado.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAsociado))
            )
            .andExpect(status().isOk());

        // Validate the Asociado in the database
        List<Asociado> asociadoList = asociadoRepository.findAll();
        assertThat(asociadoList).hasSize(databaseSizeBeforeUpdate);
        Asociado testAsociado = asociadoList.get(asociadoList.size() - 1);
        assertThat(testAsociado.getIdentificadorGeneral()).isEqualTo(UPDATED_IDENTIFICADOR_GENERAL);
        assertThat(testAsociado.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testAsociado.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testAsociado.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateAsociadoWithPatch() throws Exception {
        // Initialize the database
        asociadoRepository.saveAndFlush(asociado);

        int databaseSizeBeforeUpdate = asociadoRepository.findAll().size();

        // Update the asociado using partial update
        Asociado partialUpdatedAsociado = new Asociado();
        partialUpdatedAsociado.setId(asociado.getId());

        partialUpdatedAsociado
            .identificadorGeneral(UPDATED_IDENTIFICADOR_GENERAL)
            .estado(UPDATED_ESTADO)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restAsociadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAsociado.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAsociado))
            )
            .andExpect(status().isOk());

        // Validate the Asociado in the database
        List<Asociado> asociadoList = asociadoRepository.findAll();
        assertThat(asociadoList).hasSize(databaseSizeBeforeUpdate);
        Asociado testAsociado = asociadoList.get(asociadoList.size() - 1);
        assertThat(testAsociado.getIdentificadorGeneral()).isEqualTo(UPDATED_IDENTIFICADOR_GENERAL);
        assertThat(testAsociado.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testAsociado.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAsociado.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingAsociado() throws Exception {
        int databaseSizeBeforeUpdate = asociadoRepository.findAll().size();
        asociado.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAsociadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, asociado.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(asociado))
            )
            .andExpect(status().isBadRequest());

        // Validate the Asociado in the database
        List<Asociado> asociadoList = asociadoRepository.findAll();
        assertThat(asociadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAsociado() throws Exception {
        int databaseSizeBeforeUpdate = asociadoRepository.findAll().size();
        asociado.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAsociadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(asociado))
            )
            .andExpect(status().isBadRequest());

        // Validate the Asociado in the database
        List<Asociado> asociadoList = asociadoRepository.findAll();
        assertThat(asociadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAsociado() throws Exception {
        int databaseSizeBeforeUpdate = asociadoRepository.findAll().size();
        asociado.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAsociadoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(asociado)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Asociado in the database
        List<Asociado> asociadoList = asociadoRepository.findAll();
        assertThat(asociadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAsociado() throws Exception {
        // Initialize the database
        asociadoRepository.saveAndFlush(asociado);

        int databaseSizeBeforeDelete = asociadoRepository.findAll().size();

        // Delete the asociado
        restAsociadoMockMvc
            .perform(delete(ENTITY_API_URL_ID, asociado.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Asociado> asociadoList = asociadoRepository.findAll();
        assertThat(asociadoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
