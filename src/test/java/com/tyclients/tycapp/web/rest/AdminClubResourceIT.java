package com.tyclients.tycapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tyclients.tycapp.IntegrationTest;
import com.tyclients.tycapp.domain.AdminClub;
import com.tyclients.tycapp.domain.User;
import com.tyclients.tycapp.repository.AdminClubRepository;
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
 * Integration tests for the {@link AdminClubResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdminClubResourceIT {

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/admin-clubs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AdminClubRepository adminClubRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdminClubMockMvc;

    private AdminClub adminClub;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdminClub createEntity(EntityManager em) {
        AdminClub adminClub = new AdminClub().estado(DEFAULT_ESTADO).createdDate(DEFAULT_CREATED_DATE).updatedDate(DEFAULT_UPDATED_DATE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        adminClub.setUser(user);
        return adminClub;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdminClub createUpdatedEntity(EntityManager em) {
        AdminClub adminClub = new AdminClub().estado(UPDATED_ESTADO).createdDate(UPDATED_CREATED_DATE).updatedDate(UPDATED_UPDATED_DATE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        adminClub.setUser(user);
        return adminClub;
    }

    @BeforeEach
    public void initTest() {
        adminClub = createEntity(em);
    }

    @Test
    @Transactional
    void createAdminClub() throws Exception {
        int databaseSizeBeforeCreate = adminClubRepository.findAll().size();
        // Create the AdminClub
        restAdminClubMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminClub)))
            .andExpect(status().isCreated());

        // Validate the AdminClub in the database
        List<AdminClub> adminClubList = adminClubRepository.findAll();
        assertThat(adminClubList).hasSize(databaseSizeBeforeCreate + 1);
        AdminClub testAdminClub = adminClubList.get(adminClubList.size() - 1);
        assertThat(testAdminClub.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testAdminClub.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testAdminClub.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createAdminClubWithExistingId() throws Exception {
        // Create the AdminClub with an existing ID
        adminClub.setId(1L);

        int databaseSizeBeforeCreate = adminClubRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdminClubMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminClub)))
            .andExpect(status().isBadRequest());

        // Validate the AdminClub in the database
        List<AdminClub> adminClubList = adminClubRepository.findAll();
        assertThat(adminClubList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminClubRepository.findAll().size();
        // set the field null
        adminClub.setEstado(null);

        // Create the AdminClub, which fails.

        restAdminClubMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminClub)))
            .andExpect(status().isBadRequest());

        List<AdminClub> adminClubList = adminClubRepository.findAll();
        assertThat(adminClubList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAdminClubs() throws Exception {
        // Initialize the database
        adminClubRepository.saveAndFlush(adminClub);

        // Get all the adminClubList
        restAdminClubMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adminClub.getId().intValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getAdminClub() throws Exception {
        // Initialize the database
        adminClubRepository.saveAndFlush(adminClub);

        // Get the adminClub
        restAdminClubMockMvc
            .perform(get(ENTITY_API_URL_ID, adminClub.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adminClub.getId().intValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAdminClub() throws Exception {
        // Get the adminClub
        restAdminClubMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAdminClub() throws Exception {
        // Initialize the database
        adminClubRepository.saveAndFlush(adminClub);

        int databaseSizeBeforeUpdate = adminClubRepository.findAll().size();

        // Update the adminClub
        AdminClub updatedAdminClub = adminClubRepository.findById(adminClub.getId()).get();
        // Disconnect from session so that the updates on updatedAdminClub are not directly saved in db
        em.detach(updatedAdminClub);
        updatedAdminClub.estado(UPDATED_ESTADO).createdDate(UPDATED_CREATED_DATE).updatedDate(UPDATED_UPDATED_DATE);

        restAdminClubMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAdminClub.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAdminClub))
            )
            .andExpect(status().isOk());

        // Validate the AdminClub in the database
        List<AdminClub> adminClubList = adminClubRepository.findAll();
        assertThat(adminClubList).hasSize(databaseSizeBeforeUpdate);
        AdminClub testAdminClub = adminClubList.get(adminClubList.size() - 1);
        assertThat(testAdminClub.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testAdminClub.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAdminClub.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingAdminClub() throws Exception {
        int databaseSizeBeforeUpdate = adminClubRepository.findAll().size();
        adminClub.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdminClubMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adminClub.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminClub))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminClub in the database
        List<AdminClub> adminClubList = adminClubRepository.findAll();
        assertThat(adminClubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdminClub() throws Exception {
        int databaseSizeBeforeUpdate = adminClubRepository.findAll().size();
        adminClub.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminClubMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adminClub))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminClub in the database
        List<AdminClub> adminClubList = adminClubRepository.findAll();
        assertThat(adminClubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdminClub() throws Exception {
        int databaseSizeBeforeUpdate = adminClubRepository.findAll().size();
        adminClub.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminClubMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adminClub)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdminClub in the database
        List<AdminClub> adminClubList = adminClubRepository.findAll();
        assertThat(adminClubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdminClubWithPatch() throws Exception {
        // Initialize the database
        adminClubRepository.saveAndFlush(adminClub);

        int databaseSizeBeforeUpdate = adminClubRepository.findAll().size();

        // Update the adminClub using partial update
        AdminClub partialUpdatedAdminClub = new AdminClub();
        partialUpdatedAdminClub.setId(adminClub.getId());

        partialUpdatedAdminClub.estado(UPDATED_ESTADO).createdDate(UPDATED_CREATED_DATE).updatedDate(UPDATED_UPDATED_DATE);

        restAdminClubMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdminClub.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdminClub))
            )
            .andExpect(status().isOk());

        // Validate the AdminClub in the database
        List<AdminClub> adminClubList = adminClubRepository.findAll();
        assertThat(adminClubList).hasSize(databaseSizeBeforeUpdate);
        AdminClub testAdminClub = adminClubList.get(adminClubList.size() - 1);
        assertThat(testAdminClub.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testAdminClub.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAdminClub.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateAdminClubWithPatch() throws Exception {
        // Initialize the database
        adminClubRepository.saveAndFlush(adminClub);

        int databaseSizeBeforeUpdate = adminClubRepository.findAll().size();

        // Update the adminClub using partial update
        AdminClub partialUpdatedAdminClub = new AdminClub();
        partialUpdatedAdminClub.setId(adminClub.getId());

        partialUpdatedAdminClub.estado(UPDATED_ESTADO).createdDate(UPDATED_CREATED_DATE).updatedDate(UPDATED_UPDATED_DATE);

        restAdminClubMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdminClub.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdminClub))
            )
            .andExpect(status().isOk());

        // Validate the AdminClub in the database
        List<AdminClub> adminClubList = adminClubRepository.findAll();
        assertThat(adminClubList).hasSize(databaseSizeBeforeUpdate);
        AdminClub testAdminClub = adminClubList.get(adminClubList.size() - 1);
        assertThat(testAdminClub.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testAdminClub.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAdminClub.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingAdminClub() throws Exception {
        int databaseSizeBeforeUpdate = adminClubRepository.findAll().size();
        adminClub.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdminClubMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, adminClub.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adminClub))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminClub in the database
        List<AdminClub> adminClubList = adminClubRepository.findAll();
        assertThat(adminClubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdminClub() throws Exception {
        int databaseSizeBeforeUpdate = adminClubRepository.findAll().size();
        adminClub.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminClubMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adminClub))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdminClub in the database
        List<AdminClub> adminClubList = adminClubRepository.findAll();
        assertThat(adminClubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdminClub() throws Exception {
        int databaseSizeBeforeUpdate = adminClubRepository.findAll().size();
        adminClub.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdminClubMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(adminClub))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdminClub in the database
        List<AdminClub> adminClubList = adminClubRepository.findAll();
        assertThat(adminClubList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdminClub() throws Exception {
        // Initialize the database
        adminClubRepository.saveAndFlush(adminClub);

        int databaseSizeBeforeDelete = adminClubRepository.findAll().size();

        // Delete the adminClub
        restAdminClubMockMvc
            .perform(delete(ENTITY_API_URL_ID, adminClub.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdminClub> adminClubList = adminClubRepository.findAll();
        assertThat(adminClubList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
