package com.tyclients.tycapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tyclients.tycapp.IntegrationTest;
import com.tyclients.tycapp.domain.Documento;
import com.tyclients.tycapp.repository.DocumentoRepository;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link DocumentoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DocumentoResourceIT {

    private static final Long DEFAULT_NUMERO_TRAMITE = 1L;
    private static final Long UPDATED_NUMERO_TRAMITE = 2L;

    private static final String DEFAULT_APELLIDOS = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDOS = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRES = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRES = "BBBBBBBBBB";

    private static final String DEFAULT_SEXO = "AAAAAAAAAA";
    private static final String UPDATED_SEXO = "BBBBBBBBBB";

    private static final Long DEFAULT_NUMERO_DNI = 1L;
    private static final Long UPDATED_NUMERO_DNI = 2L;

    private static final String DEFAULT_EJEMPLAR = "AAAAAAAAAA";
    private static final String UPDATED_EJEMPLAR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_NACIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_NACIMIENTO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_EMISION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_EMISION = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_INICIO_FIN_CUIL = 1;
    private static final Integer UPDATED_INICIO_FIN_CUIL = 2;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/documentos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentoMockMvc;

    private Documento documento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Documento createEntity(EntityManager em) {
        Documento documento = new Documento()
            .numeroTramite(DEFAULT_NUMERO_TRAMITE)
            .apellidos(DEFAULT_APELLIDOS)
            .nombres(DEFAULT_NOMBRES)
            .sexo(DEFAULT_SEXO)
            .numeroDni(DEFAULT_NUMERO_DNI)
            .ejemplar(DEFAULT_EJEMPLAR)
            .nacimiento(DEFAULT_NACIMIENTO)
            .fechaEmision(DEFAULT_FECHA_EMISION)
            .inicioFinCuil(DEFAULT_INICIO_FIN_CUIL)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return documento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Documento createUpdatedEntity(EntityManager em) {
        Documento documento = new Documento()
            .numeroTramite(UPDATED_NUMERO_TRAMITE)
            .apellidos(UPDATED_APELLIDOS)
            .nombres(UPDATED_NOMBRES)
            .sexo(UPDATED_SEXO)
            .numeroDni(UPDATED_NUMERO_DNI)
            .ejemplar(UPDATED_EJEMPLAR)
            .nacimiento(UPDATED_NACIMIENTO)
            .fechaEmision(UPDATED_FECHA_EMISION)
            .inicioFinCuil(UPDATED_INICIO_FIN_CUIL)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        return documento;
    }

    @BeforeEach
    public void initTest() {
        documento = createEntity(em);
    }

    @Test
    @Transactional
    void createDocumento() throws Exception {
        int databaseSizeBeforeCreate = documentoRepository.findAll().size();
        // Create the Documento
        restDocumentoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documento)))
            .andExpect(status().isCreated());

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll();
        assertThat(documentoList).hasSize(databaseSizeBeforeCreate + 1);
        Documento testDocumento = documentoList.get(documentoList.size() - 1);
        assertThat(testDocumento.getNumeroTramite()).isEqualTo(DEFAULT_NUMERO_TRAMITE);
        assertThat(testDocumento.getApellidos()).isEqualTo(DEFAULT_APELLIDOS);
        assertThat(testDocumento.getNombres()).isEqualTo(DEFAULT_NOMBRES);
        assertThat(testDocumento.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testDocumento.getNumeroDni()).isEqualTo(DEFAULT_NUMERO_DNI);
        assertThat(testDocumento.getEjemplar()).isEqualTo(DEFAULT_EJEMPLAR);
        assertThat(testDocumento.getNacimiento()).isEqualTo(DEFAULT_NACIMIENTO);
        assertThat(testDocumento.getFechaEmision()).isEqualTo(DEFAULT_FECHA_EMISION);
        assertThat(testDocumento.getInicioFinCuil()).isEqualTo(DEFAULT_INICIO_FIN_CUIL);
        assertThat(testDocumento.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDocumento.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createDocumentoWithExistingId() throws Exception {
        // Create the Documento with an existing ID
        documento.setId(1L);

        int databaseSizeBeforeCreate = documentoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documento)))
            .andExpect(status().isBadRequest());

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll();
        assertThat(documentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDocumentos() throws Exception {
        // Initialize the database
        documentoRepository.saveAndFlush(documento);

        // Get all the documentoList
        restDocumentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documento.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroTramite").value(hasItem(DEFAULT_NUMERO_TRAMITE.intValue())))
            .andExpect(jsonPath("$.[*].apellidos").value(hasItem(DEFAULT_APELLIDOS)))
            .andExpect(jsonPath("$.[*].nombres").value(hasItem(DEFAULT_NOMBRES)))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO)))
            .andExpect(jsonPath("$.[*].numeroDni").value(hasItem(DEFAULT_NUMERO_DNI.intValue())))
            .andExpect(jsonPath("$.[*].ejemplar").value(hasItem(DEFAULT_EJEMPLAR)))
            .andExpect(jsonPath("$.[*].nacimiento").value(hasItem(DEFAULT_NACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].fechaEmision").value(hasItem(DEFAULT_FECHA_EMISION.toString())))
            .andExpect(jsonPath("$.[*].inicioFinCuil").value(hasItem(DEFAULT_INICIO_FIN_CUIL)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getDocumento() throws Exception {
        // Initialize the database
        documentoRepository.saveAndFlush(documento);

        // Get the documento
        restDocumentoMockMvc
            .perform(get(ENTITY_API_URL_ID, documento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(documento.getId().intValue()))
            .andExpect(jsonPath("$.numeroTramite").value(DEFAULT_NUMERO_TRAMITE.intValue()))
            .andExpect(jsonPath("$.apellidos").value(DEFAULT_APELLIDOS))
            .andExpect(jsonPath("$.nombres").value(DEFAULT_NOMBRES))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO))
            .andExpect(jsonPath("$.numeroDni").value(DEFAULT_NUMERO_DNI.intValue()))
            .andExpect(jsonPath("$.ejemplar").value(DEFAULT_EJEMPLAR))
            .andExpect(jsonPath("$.nacimiento").value(DEFAULT_NACIMIENTO.toString()))
            .andExpect(jsonPath("$.fechaEmision").value(DEFAULT_FECHA_EMISION.toString()))
            .andExpect(jsonPath("$.inicioFinCuil").value(DEFAULT_INICIO_FIN_CUIL))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDocumento() throws Exception {
        // Get the documento
        restDocumentoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDocumento() throws Exception {
        // Initialize the database
        documentoRepository.saveAndFlush(documento);

        int databaseSizeBeforeUpdate = documentoRepository.findAll().size();

        // Update the documento
        Documento updatedDocumento = documentoRepository.findById(documento.getId()).get();
        // Disconnect from session so that the updates on updatedDocumento are not directly saved in db
        em.detach(updatedDocumento);
        updatedDocumento
            .numeroTramite(UPDATED_NUMERO_TRAMITE)
            .apellidos(UPDATED_APELLIDOS)
            .nombres(UPDATED_NOMBRES)
            .sexo(UPDATED_SEXO)
            .numeroDni(UPDATED_NUMERO_DNI)
            .ejemplar(UPDATED_EJEMPLAR)
            .nacimiento(UPDATED_NACIMIENTO)
            .fechaEmision(UPDATED_FECHA_EMISION)
            .inicioFinCuil(UPDATED_INICIO_FIN_CUIL)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restDocumentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDocumento.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDocumento))
            )
            .andExpect(status().isOk());

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll();
        assertThat(documentoList).hasSize(databaseSizeBeforeUpdate);
        Documento testDocumento = documentoList.get(documentoList.size() - 1);
        assertThat(testDocumento.getNumeroTramite()).isEqualTo(UPDATED_NUMERO_TRAMITE);
        assertThat(testDocumento.getApellidos()).isEqualTo(UPDATED_APELLIDOS);
        assertThat(testDocumento.getNombres()).isEqualTo(UPDATED_NOMBRES);
        assertThat(testDocumento.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testDocumento.getNumeroDni()).isEqualTo(UPDATED_NUMERO_DNI);
        assertThat(testDocumento.getEjemplar()).isEqualTo(UPDATED_EJEMPLAR);
        assertThat(testDocumento.getNacimiento()).isEqualTo(UPDATED_NACIMIENTO);
        assertThat(testDocumento.getFechaEmision()).isEqualTo(UPDATED_FECHA_EMISION);
        assertThat(testDocumento.getInicioFinCuil()).isEqualTo(UPDATED_INICIO_FIN_CUIL);
        assertThat(testDocumento.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDocumento.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingDocumento() throws Exception {
        int databaseSizeBeforeUpdate = documentoRepository.findAll().size();
        documento.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, documento.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll();
        assertThat(documentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDocumento() throws Exception {
        int databaseSizeBeforeUpdate = documentoRepository.findAll().size();
        documento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll();
        assertThat(documentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDocumento() throws Exception {
        int databaseSizeBeforeUpdate = documentoRepository.findAll().size();
        documento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documento)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll();
        assertThat(documentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDocumentoWithPatch() throws Exception {
        // Initialize the database
        documentoRepository.saveAndFlush(documento);

        int databaseSizeBeforeUpdate = documentoRepository.findAll().size();

        // Update the documento using partial update
        Documento partialUpdatedDocumento = new Documento();
        partialUpdatedDocumento.setId(documento.getId());

        partialUpdatedDocumento
            .apellidos(UPDATED_APELLIDOS)
            .fechaEmision(UPDATED_FECHA_EMISION)
            .inicioFinCuil(UPDATED_INICIO_FIN_CUIL)
            .updatedDate(UPDATED_UPDATED_DATE);

        restDocumentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocumento))
            )
            .andExpect(status().isOk());

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll();
        assertThat(documentoList).hasSize(databaseSizeBeforeUpdate);
        Documento testDocumento = documentoList.get(documentoList.size() - 1);
        assertThat(testDocumento.getNumeroTramite()).isEqualTo(DEFAULT_NUMERO_TRAMITE);
        assertThat(testDocumento.getApellidos()).isEqualTo(UPDATED_APELLIDOS);
        assertThat(testDocumento.getNombres()).isEqualTo(DEFAULT_NOMBRES);
        assertThat(testDocumento.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testDocumento.getNumeroDni()).isEqualTo(DEFAULT_NUMERO_DNI);
        assertThat(testDocumento.getEjemplar()).isEqualTo(DEFAULT_EJEMPLAR);
        assertThat(testDocumento.getNacimiento()).isEqualTo(DEFAULT_NACIMIENTO);
        assertThat(testDocumento.getFechaEmision()).isEqualTo(UPDATED_FECHA_EMISION);
        assertThat(testDocumento.getInicioFinCuil()).isEqualTo(UPDATED_INICIO_FIN_CUIL);
        assertThat(testDocumento.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDocumento.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateDocumentoWithPatch() throws Exception {
        // Initialize the database
        documentoRepository.saveAndFlush(documento);

        int databaseSizeBeforeUpdate = documentoRepository.findAll().size();

        // Update the documento using partial update
        Documento partialUpdatedDocumento = new Documento();
        partialUpdatedDocumento.setId(documento.getId());

        partialUpdatedDocumento
            .numeroTramite(UPDATED_NUMERO_TRAMITE)
            .apellidos(UPDATED_APELLIDOS)
            .nombres(UPDATED_NOMBRES)
            .sexo(UPDATED_SEXO)
            .numeroDni(UPDATED_NUMERO_DNI)
            .ejemplar(UPDATED_EJEMPLAR)
            .nacimiento(UPDATED_NACIMIENTO)
            .fechaEmision(UPDATED_FECHA_EMISION)
            .inicioFinCuil(UPDATED_INICIO_FIN_CUIL)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restDocumentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocumento))
            )
            .andExpect(status().isOk());

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll();
        assertThat(documentoList).hasSize(databaseSizeBeforeUpdate);
        Documento testDocumento = documentoList.get(documentoList.size() - 1);
        assertThat(testDocumento.getNumeroTramite()).isEqualTo(UPDATED_NUMERO_TRAMITE);
        assertThat(testDocumento.getApellidos()).isEqualTo(UPDATED_APELLIDOS);
        assertThat(testDocumento.getNombres()).isEqualTo(UPDATED_NOMBRES);
        assertThat(testDocumento.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testDocumento.getNumeroDni()).isEqualTo(UPDATED_NUMERO_DNI);
        assertThat(testDocumento.getEjemplar()).isEqualTo(UPDATED_EJEMPLAR);
        assertThat(testDocumento.getNacimiento()).isEqualTo(UPDATED_NACIMIENTO);
        assertThat(testDocumento.getFechaEmision()).isEqualTo(UPDATED_FECHA_EMISION);
        assertThat(testDocumento.getInicioFinCuil()).isEqualTo(UPDATED_INICIO_FIN_CUIL);
        assertThat(testDocumento.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDocumento.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingDocumento() throws Exception {
        int databaseSizeBeforeUpdate = documentoRepository.findAll().size();
        documento.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, documento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(documento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll();
        assertThat(documentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDocumento() throws Exception {
        int databaseSizeBeforeUpdate = documentoRepository.findAll().size();
        documento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(documento))
            )
            .andExpect(status().isBadRequest());

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll();
        assertThat(documentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDocumento() throws Exception {
        int databaseSizeBeforeUpdate = documentoRepository.findAll().size();
        documento.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(documento))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Documento in the database
        List<Documento> documentoList = documentoRepository.findAll();
        assertThat(documentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDocumento() throws Exception {
        // Initialize the database
        documentoRepository.saveAndFlush(documento);

        int databaseSizeBeforeDelete = documentoRepository.findAll().size();

        // Delete the documento
        restDocumentoMockMvc
            .perform(delete(ENTITY_API_URL_ID, documento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Documento> documentoList = documentoRepository.findAll();
        assertThat(documentoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
