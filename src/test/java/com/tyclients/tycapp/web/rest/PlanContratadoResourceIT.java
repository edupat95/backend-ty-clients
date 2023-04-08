package com.tyclients.tycapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tyclients.tycapp.IntegrationTest;
import com.tyclients.tycapp.domain.Plan;
import com.tyclients.tycapp.domain.PlanContratado;
import com.tyclients.tycapp.repository.PlanContratadoRepository;
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
 * Integration tests for the {@link PlanContratadoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlanContratadoResourceIT {

    private static final Integer DEFAULT_TIEMPO_CONTRATADO = 1;
    private static final Integer UPDATED_TIEMPO_CONTRATADO = 2;

    private static final Instant DEFAULT_FECHA_VENCIMIENTO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_VENCIMIENTO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/plan-contratados";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlanContratadoRepository planContratadoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanContratadoMockMvc;

    private PlanContratado planContratado;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanContratado createEntity(EntityManager em) {
        PlanContratado planContratado = new PlanContratado()
            .tiempoContratado(DEFAULT_TIEMPO_CONTRATADO)
            .fechaVencimiento(DEFAULT_FECHA_VENCIMIENTO)
            .estado(DEFAULT_ESTADO)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        // Add required entity
        Plan plan;
        if (TestUtil.findAll(em, Plan.class).isEmpty()) {
            plan = PlanResourceIT.createEntity(em);
            em.persist(plan);
            em.flush();
        } else {
            plan = TestUtil.findAll(em, Plan.class).get(0);
        }
        planContratado.setPlan(plan);
        return planContratado;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanContratado createUpdatedEntity(EntityManager em) {
        PlanContratado planContratado = new PlanContratado()
            .tiempoContratado(UPDATED_TIEMPO_CONTRATADO)
            .fechaVencimiento(UPDATED_FECHA_VENCIMIENTO)
            .estado(UPDATED_ESTADO)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        // Add required entity
        Plan plan;
        if (TestUtil.findAll(em, Plan.class).isEmpty()) {
            plan = PlanResourceIT.createUpdatedEntity(em);
            em.persist(plan);
            em.flush();
        } else {
            plan = TestUtil.findAll(em, Plan.class).get(0);
        }
        planContratado.setPlan(plan);
        return planContratado;
    }

    @BeforeEach
    public void initTest() {
        planContratado = createEntity(em);
    }

    @Test
    @Transactional
    void createPlanContratado() throws Exception {
        int databaseSizeBeforeCreate = planContratadoRepository.findAll().size();
        // Create the PlanContratado
        restPlanContratadoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planContratado))
            )
            .andExpect(status().isCreated());

        // Validate the PlanContratado in the database
        List<PlanContratado> planContratadoList = planContratadoRepository.findAll();
        assertThat(planContratadoList).hasSize(databaseSizeBeforeCreate + 1);
        PlanContratado testPlanContratado = planContratadoList.get(planContratadoList.size() - 1);
        assertThat(testPlanContratado.getTiempoContratado()).isEqualTo(DEFAULT_TIEMPO_CONTRATADO);
        assertThat(testPlanContratado.getFechaVencimiento()).isEqualTo(DEFAULT_FECHA_VENCIMIENTO);
        assertThat(testPlanContratado.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testPlanContratado.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPlanContratado.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createPlanContratadoWithExistingId() throws Exception {
        // Create the PlanContratado with an existing ID
        planContratado.setId(1L);

        int databaseSizeBeforeCreate = planContratadoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanContratadoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planContratado))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanContratado in the database
        List<PlanContratado> planContratadoList = planContratadoRepository.findAll();
        assertThat(planContratadoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = planContratadoRepository.findAll().size();
        // set the field null
        planContratado.setEstado(null);

        // Create the PlanContratado, which fails.

        restPlanContratadoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planContratado))
            )
            .andExpect(status().isBadRequest());

        List<PlanContratado> planContratadoList = planContratadoRepository.findAll();
        assertThat(planContratadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPlanContratados() throws Exception {
        // Initialize the database
        planContratadoRepository.saveAndFlush(planContratado);

        // Get all the planContratadoList
        restPlanContratadoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planContratado.getId().intValue())))
            .andExpect(jsonPath("$.[*].tiempoContratado").value(hasItem(DEFAULT_TIEMPO_CONTRATADO)))
            .andExpect(jsonPath("$.[*].fechaVencimiento").value(hasItem(DEFAULT_FECHA_VENCIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getPlanContratado() throws Exception {
        // Initialize the database
        planContratadoRepository.saveAndFlush(planContratado);

        // Get the planContratado
        restPlanContratadoMockMvc
            .perform(get(ENTITY_API_URL_ID, planContratado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(planContratado.getId().intValue()))
            .andExpect(jsonPath("$.tiempoContratado").value(DEFAULT_TIEMPO_CONTRATADO))
            .andExpect(jsonPath("$.fechaVencimiento").value(DEFAULT_FECHA_VENCIMIENTO.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPlanContratado() throws Exception {
        // Get the planContratado
        restPlanContratadoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPlanContratado() throws Exception {
        // Initialize the database
        planContratadoRepository.saveAndFlush(planContratado);

        int databaseSizeBeforeUpdate = planContratadoRepository.findAll().size();

        // Update the planContratado
        PlanContratado updatedPlanContratado = planContratadoRepository.findById(planContratado.getId()).get();
        // Disconnect from session so that the updates on updatedPlanContratado are not directly saved in db
        em.detach(updatedPlanContratado);
        updatedPlanContratado
            .tiempoContratado(UPDATED_TIEMPO_CONTRATADO)
            .fechaVencimiento(UPDATED_FECHA_VENCIMIENTO)
            .estado(UPDATED_ESTADO)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restPlanContratadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPlanContratado.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPlanContratado))
            )
            .andExpect(status().isOk());

        // Validate the PlanContratado in the database
        List<PlanContratado> planContratadoList = planContratadoRepository.findAll();
        assertThat(planContratadoList).hasSize(databaseSizeBeforeUpdate);
        PlanContratado testPlanContratado = planContratadoList.get(planContratadoList.size() - 1);
        assertThat(testPlanContratado.getTiempoContratado()).isEqualTo(UPDATED_TIEMPO_CONTRATADO);
        assertThat(testPlanContratado.getFechaVencimiento()).isEqualTo(UPDATED_FECHA_VENCIMIENTO);
        assertThat(testPlanContratado.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testPlanContratado.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPlanContratado.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingPlanContratado() throws Exception {
        int databaseSizeBeforeUpdate = planContratadoRepository.findAll().size();
        planContratado.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanContratadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, planContratado.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(planContratado))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanContratado in the database
        List<PlanContratado> planContratadoList = planContratadoRepository.findAll();
        assertThat(planContratadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlanContratado() throws Exception {
        int databaseSizeBeforeUpdate = planContratadoRepository.findAll().size();
        planContratado.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanContratadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(planContratado))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanContratado in the database
        List<PlanContratado> planContratadoList = planContratadoRepository.findAll();
        assertThat(planContratadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlanContratado() throws Exception {
        int databaseSizeBeforeUpdate = planContratadoRepository.findAll().size();
        planContratado.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanContratadoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planContratado)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlanContratado in the database
        List<PlanContratado> planContratadoList = planContratadoRepository.findAll();
        assertThat(planContratadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlanContratadoWithPatch() throws Exception {
        // Initialize the database
        planContratadoRepository.saveAndFlush(planContratado);

        int databaseSizeBeforeUpdate = planContratadoRepository.findAll().size();

        // Update the planContratado using partial update
        PlanContratado partialUpdatedPlanContratado = new PlanContratado();
        partialUpdatedPlanContratado.setId(planContratado.getId());

        partialUpdatedPlanContratado.fechaVencimiento(UPDATED_FECHA_VENCIMIENTO).updatedDate(UPDATED_UPDATED_DATE);

        restPlanContratadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlanContratado.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlanContratado))
            )
            .andExpect(status().isOk());

        // Validate the PlanContratado in the database
        List<PlanContratado> planContratadoList = planContratadoRepository.findAll();
        assertThat(planContratadoList).hasSize(databaseSizeBeforeUpdate);
        PlanContratado testPlanContratado = planContratadoList.get(planContratadoList.size() - 1);
        assertThat(testPlanContratado.getTiempoContratado()).isEqualTo(DEFAULT_TIEMPO_CONTRATADO);
        assertThat(testPlanContratado.getFechaVencimiento()).isEqualTo(UPDATED_FECHA_VENCIMIENTO);
        assertThat(testPlanContratado.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testPlanContratado.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPlanContratado.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdatePlanContratadoWithPatch() throws Exception {
        // Initialize the database
        planContratadoRepository.saveAndFlush(planContratado);

        int databaseSizeBeforeUpdate = planContratadoRepository.findAll().size();

        // Update the planContratado using partial update
        PlanContratado partialUpdatedPlanContratado = new PlanContratado();
        partialUpdatedPlanContratado.setId(planContratado.getId());

        partialUpdatedPlanContratado
            .tiempoContratado(UPDATED_TIEMPO_CONTRATADO)
            .fechaVencimiento(UPDATED_FECHA_VENCIMIENTO)
            .estado(UPDATED_ESTADO)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restPlanContratadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlanContratado.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlanContratado))
            )
            .andExpect(status().isOk());

        // Validate the PlanContratado in the database
        List<PlanContratado> planContratadoList = planContratadoRepository.findAll();
        assertThat(planContratadoList).hasSize(databaseSizeBeforeUpdate);
        PlanContratado testPlanContratado = planContratadoList.get(planContratadoList.size() - 1);
        assertThat(testPlanContratado.getTiempoContratado()).isEqualTo(UPDATED_TIEMPO_CONTRATADO);
        assertThat(testPlanContratado.getFechaVencimiento()).isEqualTo(UPDATED_FECHA_VENCIMIENTO);
        assertThat(testPlanContratado.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testPlanContratado.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPlanContratado.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingPlanContratado() throws Exception {
        int databaseSizeBeforeUpdate = planContratadoRepository.findAll().size();
        planContratado.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanContratadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, planContratado.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(planContratado))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanContratado in the database
        List<PlanContratado> planContratadoList = planContratadoRepository.findAll();
        assertThat(planContratadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlanContratado() throws Exception {
        int databaseSizeBeforeUpdate = planContratadoRepository.findAll().size();
        planContratado.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanContratadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(planContratado))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanContratado in the database
        List<PlanContratado> planContratadoList = planContratadoRepository.findAll();
        assertThat(planContratadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlanContratado() throws Exception {
        int databaseSizeBeforeUpdate = planContratadoRepository.findAll().size();
        planContratado.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanContratadoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(planContratado))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlanContratado in the database
        List<PlanContratado> planContratadoList = planContratadoRepository.findAll();
        assertThat(planContratadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlanContratado() throws Exception {
        // Initialize the database
        planContratadoRepository.saveAndFlush(planContratado);

        int databaseSizeBeforeDelete = planContratadoRepository.findAll().size();

        // Delete the planContratado
        restPlanContratadoMockMvc
            .perform(delete(ENTITY_API_URL_ID, planContratado.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlanContratado> planContratadoList = planContratadoRepository.findAll();
        assertThat(planContratadoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
