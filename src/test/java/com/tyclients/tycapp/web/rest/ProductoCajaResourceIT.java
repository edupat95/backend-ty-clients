package com.tyclients.tycapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tyclients.tycapp.IntegrationTest;
import com.tyclients.tycapp.domain.Caja;
import com.tyclients.tycapp.domain.Producto;
import com.tyclients.tycapp.domain.ProductoCaja;
import com.tyclients.tycapp.repository.ProductoCajaRepository;
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
 * Integration tests for the {@link ProductoCajaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductoCajaResourceIT {

    private static final Long DEFAULT_CANTIDAD = 1L;
    private static final Long UPDATED_CANTIDAD = 2L;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/producto-cajas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductoCajaRepository productoCajaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductoCajaMockMvc;

    private ProductoCaja productoCaja;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductoCaja createEntity(EntityManager em) {
        ProductoCaja productoCaja = new ProductoCaja()
            .cantidad(DEFAULT_CANTIDAD)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        // Add required entity
        Producto producto;
        if (TestUtil.findAll(em, Producto.class).isEmpty()) {
            producto = ProductoResourceIT.createEntity(em);
            em.persist(producto);
            em.flush();
        } else {
            producto = TestUtil.findAll(em, Producto.class).get(0);
        }
        productoCaja.setProducto(producto);
        // Add required entity
        Caja caja;
        if (TestUtil.findAll(em, Caja.class).isEmpty()) {
            caja = CajaResourceIT.createEntity(em);
            em.persist(caja);
            em.flush();
        } else {
            caja = TestUtil.findAll(em, Caja.class).get(0);
        }
        productoCaja.setCaja(caja);
        return productoCaja;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductoCaja createUpdatedEntity(EntityManager em) {
        ProductoCaja productoCaja = new ProductoCaja()
            .cantidad(UPDATED_CANTIDAD)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        // Add required entity
        Producto producto;
        if (TestUtil.findAll(em, Producto.class).isEmpty()) {
            producto = ProductoResourceIT.createUpdatedEntity(em);
            em.persist(producto);
            em.flush();
        } else {
            producto = TestUtil.findAll(em, Producto.class).get(0);
        }
        productoCaja.setProducto(producto);
        // Add required entity
        Caja caja;
        if (TestUtil.findAll(em, Caja.class).isEmpty()) {
            caja = CajaResourceIT.createUpdatedEntity(em);
            em.persist(caja);
            em.flush();
        } else {
            caja = TestUtil.findAll(em, Caja.class).get(0);
        }
        productoCaja.setCaja(caja);
        return productoCaja;
    }

    @BeforeEach
    public void initTest() {
        productoCaja = createEntity(em);
    }

    @Test
    @Transactional
    void createProductoCaja() throws Exception {
        int databaseSizeBeforeCreate = productoCajaRepository.findAll().size();
        // Create the ProductoCaja
        restProductoCajaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoCaja)))
            .andExpect(status().isCreated());

        // Validate the ProductoCaja in the database
        List<ProductoCaja> productoCajaList = productoCajaRepository.findAll();
        assertThat(productoCajaList).hasSize(databaseSizeBeforeCreate + 1);
        ProductoCaja testProductoCaja = productoCajaList.get(productoCajaList.size() - 1);
        assertThat(testProductoCaja.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testProductoCaja.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductoCaja.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createProductoCajaWithExistingId() throws Exception {
        // Create the ProductoCaja with an existing ID
        productoCaja.setId(1L);

        int databaseSizeBeforeCreate = productoCajaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductoCajaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoCaja)))
            .andExpect(status().isBadRequest());

        // Validate the ProductoCaja in the database
        List<ProductoCaja> productoCajaList = productoCajaRepository.findAll();
        assertThat(productoCajaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductoCajas() throws Exception {
        // Initialize the database
        productoCajaRepository.saveAndFlush(productoCaja);

        // Get all the productoCajaList
        restProductoCajaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productoCaja.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getProductoCaja() throws Exception {
        // Initialize the database
        productoCajaRepository.saveAndFlush(productoCaja);

        // Get the productoCaja
        restProductoCajaMockMvc
            .perform(get(ENTITY_API_URL_ID, productoCaja.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productoCaja.getId().intValue()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD.intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProductoCaja() throws Exception {
        // Get the productoCaja
        restProductoCajaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductoCaja() throws Exception {
        // Initialize the database
        productoCajaRepository.saveAndFlush(productoCaja);

        int databaseSizeBeforeUpdate = productoCajaRepository.findAll().size();

        // Update the productoCaja
        ProductoCaja updatedProductoCaja = productoCajaRepository.findById(productoCaja.getId()).get();
        // Disconnect from session so that the updates on updatedProductoCaja are not directly saved in db
        em.detach(updatedProductoCaja);
        updatedProductoCaja.cantidad(UPDATED_CANTIDAD).createdDate(UPDATED_CREATED_DATE).updatedDate(UPDATED_UPDATED_DATE);

        restProductoCajaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProductoCaja.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProductoCaja))
            )
            .andExpect(status().isOk());

        // Validate the ProductoCaja in the database
        List<ProductoCaja> productoCajaList = productoCajaRepository.findAll();
        assertThat(productoCajaList).hasSize(databaseSizeBeforeUpdate);
        ProductoCaja testProductoCaja = productoCajaList.get(productoCajaList.size() - 1);
        assertThat(testProductoCaja.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testProductoCaja.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductoCaja.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductoCaja() throws Exception {
        int databaseSizeBeforeUpdate = productoCajaRepository.findAll().size();
        productoCaja.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoCajaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productoCaja.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productoCaja))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoCaja in the database
        List<ProductoCaja> productoCajaList = productoCajaRepository.findAll();
        assertThat(productoCajaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductoCaja() throws Exception {
        int databaseSizeBeforeUpdate = productoCajaRepository.findAll().size();
        productoCaja.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoCajaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productoCaja))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoCaja in the database
        List<ProductoCaja> productoCajaList = productoCajaRepository.findAll();
        assertThat(productoCajaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductoCaja() throws Exception {
        int databaseSizeBeforeUpdate = productoCajaRepository.findAll().size();
        productoCaja.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoCajaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoCaja)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductoCaja in the database
        List<ProductoCaja> productoCajaList = productoCajaRepository.findAll();
        assertThat(productoCajaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductoCajaWithPatch() throws Exception {
        // Initialize the database
        productoCajaRepository.saveAndFlush(productoCaja);

        int databaseSizeBeforeUpdate = productoCajaRepository.findAll().size();

        // Update the productoCaja using partial update
        ProductoCaja partialUpdatedProductoCaja = new ProductoCaja();
        partialUpdatedProductoCaja.setId(productoCaja.getId());

        partialUpdatedProductoCaja.cantidad(UPDATED_CANTIDAD).createdDate(UPDATED_CREATED_DATE).updatedDate(UPDATED_UPDATED_DATE);

        restProductoCajaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductoCaja.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductoCaja))
            )
            .andExpect(status().isOk());

        // Validate the ProductoCaja in the database
        List<ProductoCaja> productoCajaList = productoCajaRepository.findAll();
        assertThat(productoCajaList).hasSize(databaseSizeBeforeUpdate);
        ProductoCaja testProductoCaja = productoCajaList.get(productoCajaList.size() - 1);
        assertThat(testProductoCaja.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testProductoCaja.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductoCaja.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductoCajaWithPatch() throws Exception {
        // Initialize the database
        productoCajaRepository.saveAndFlush(productoCaja);

        int databaseSizeBeforeUpdate = productoCajaRepository.findAll().size();

        // Update the productoCaja using partial update
        ProductoCaja partialUpdatedProductoCaja = new ProductoCaja();
        partialUpdatedProductoCaja.setId(productoCaja.getId());

        partialUpdatedProductoCaja.cantidad(UPDATED_CANTIDAD).createdDate(UPDATED_CREATED_DATE).updatedDate(UPDATED_UPDATED_DATE);

        restProductoCajaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductoCaja.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductoCaja))
            )
            .andExpect(status().isOk());

        // Validate the ProductoCaja in the database
        List<ProductoCaja> productoCajaList = productoCajaRepository.findAll();
        assertThat(productoCajaList).hasSize(databaseSizeBeforeUpdate);
        ProductoCaja testProductoCaja = productoCajaList.get(productoCajaList.size() - 1);
        assertThat(testProductoCaja.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testProductoCaja.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductoCaja.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductoCaja() throws Exception {
        int databaseSizeBeforeUpdate = productoCajaRepository.findAll().size();
        productoCaja.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoCajaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productoCaja.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productoCaja))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoCaja in the database
        List<ProductoCaja> productoCajaList = productoCajaRepository.findAll();
        assertThat(productoCajaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductoCaja() throws Exception {
        int databaseSizeBeforeUpdate = productoCajaRepository.findAll().size();
        productoCaja.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoCajaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productoCaja))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoCaja in the database
        List<ProductoCaja> productoCajaList = productoCajaRepository.findAll();
        assertThat(productoCajaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductoCaja() throws Exception {
        int databaseSizeBeforeUpdate = productoCajaRepository.findAll().size();
        productoCaja.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoCajaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(productoCaja))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductoCaja in the database
        List<ProductoCaja> productoCajaList = productoCajaRepository.findAll();
        assertThat(productoCajaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductoCaja() throws Exception {
        // Initialize the database
        productoCajaRepository.saveAndFlush(productoCaja);

        int databaseSizeBeforeDelete = productoCajaRepository.findAll().size();

        // Delete the productoCaja
        restProductoCajaMockMvc
            .perform(delete(ENTITY_API_URL_ID, productoCaja.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductoCaja> productoCajaList = productoCajaRepository.findAll();
        assertThat(productoCajaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
