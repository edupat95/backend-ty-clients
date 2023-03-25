package com.tyclients.tycapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tyclients.tycapp.IntegrationTest;
import com.tyclients.tycapp.domain.Deposito;
import com.tyclients.tycapp.domain.Producto;
import com.tyclients.tycapp.domain.ProductoDeposito;
import com.tyclients.tycapp.repository.ProductoDepositoRepository;
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
 * Integration tests for the {@link ProductoDepositoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductoDepositoResourceIT {

    private static final Long DEFAULT_CANTIDAD = 1L;
    private static final Long UPDATED_CANTIDAD = 2L;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/producto-depositos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductoDepositoRepository productoDepositoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductoDepositoMockMvc;

    private ProductoDeposito productoDeposito;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductoDeposito createEntity(EntityManager em) {
        ProductoDeposito productoDeposito = new ProductoDeposito()
            .cantidad(DEFAULT_CANTIDAD)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        // Add required entity
        Deposito deposito;
        if (TestUtil.findAll(em, Deposito.class).isEmpty()) {
            deposito = DepositoResourceIT.createEntity(em);
            em.persist(deposito);
            em.flush();
        } else {
            deposito = TestUtil.findAll(em, Deposito.class).get(0);
        }
        productoDeposito.setDeposito(deposito);
        // Add required entity
        Producto producto;
        if (TestUtil.findAll(em, Producto.class).isEmpty()) {
            producto = ProductoResourceIT.createEntity(em);
            em.persist(producto);
            em.flush();
        } else {
            producto = TestUtil.findAll(em, Producto.class).get(0);
        }
        productoDeposito.setProducto(producto);
        return productoDeposito;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductoDeposito createUpdatedEntity(EntityManager em) {
        ProductoDeposito productoDeposito = new ProductoDeposito()
            .cantidad(UPDATED_CANTIDAD)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        // Add required entity
        Deposito deposito;
        if (TestUtil.findAll(em, Deposito.class).isEmpty()) {
            deposito = DepositoResourceIT.createUpdatedEntity(em);
            em.persist(deposito);
            em.flush();
        } else {
            deposito = TestUtil.findAll(em, Deposito.class).get(0);
        }
        productoDeposito.setDeposito(deposito);
        // Add required entity
        Producto producto;
        if (TestUtil.findAll(em, Producto.class).isEmpty()) {
            producto = ProductoResourceIT.createUpdatedEntity(em);
            em.persist(producto);
            em.flush();
        } else {
            producto = TestUtil.findAll(em, Producto.class).get(0);
        }
        productoDeposito.setProducto(producto);
        return productoDeposito;
    }

    @BeforeEach
    public void initTest() {
        productoDeposito = createEntity(em);
    }

    @Test
    @Transactional
    void createProductoDeposito() throws Exception {
        int databaseSizeBeforeCreate = productoDepositoRepository.findAll().size();
        // Create the ProductoDeposito
        restProductoDepositoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoDeposito))
            )
            .andExpect(status().isCreated());

        // Validate the ProductoDeposito in the database
        List<ProductoDeposito> productoDepositoList = productoDepositoRepository.findAll();
        assertThat(productoDepositoList).hasSize(databaseSizeBeforeCreate + 1);
        ProductoDeposito testProductoDeposito = productoDepositoList.get(productoDepositoList.size() - 1);
        assertThat(testProductoDeposito.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testProductoDeposito.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductoDeposito.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createProductoDepositoWithExistingId() throws Exception {
        // Create the ProductoDeposito with an existing ID
        productoDeposito.setId(1L);

        int databaseSizeBeforeCreate = productoDepositoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductoDepositoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoDeposito))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoDeposito in the database
        List<ProductoDeposito> productoDepositoList = productoDepositoRepository.findAll();
        assertThat(productoDepositoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductoDepositos() throws Exception {
        // Initialize the database
        productoDepositoRepository.saveAndFlush(productoDeposito);

        // Get all the productoDepositoList
        restProductoDepositoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productoDeposito.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getProductoDeposito() throws Exception {
        // Initialize the database
        productoDepositoRepository.saveAndFlush(productoDeposito);

        // Get the productoDeposito
        restProductoDepositoMockMvc
            .perform(get(ENTITY_API_URL_ID, productoDeposito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productoDeposito.getId().intValue()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD.intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProductoDeposito() throws Exception {
        // Get the productoDeposito
        restProductoDepositoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductoDeposito() throws Exception {
        // Initialize the database
        productoDepositoRepository.saveAndFlush(productoDeposito);

        int databaseSizeBeforeUpdate = productoDepositoRepository.findAll().size();

        // Update the productoDeposito
        ProductoDeposito updatedProductoDeposito = productoDepositoRepository.findById(productoDeposito.getId()).get();
        // Disconnect from session so that the updates on updatedProductoDeposito are not directly saved in db
        em.detach(updatedProductoDeposito);
        updatedProductoDeposito.cantidad(UPDATED_CANTIDAD).createdDate(UPDATED_CREATED_DATE).updatedDate(UPDATED_UPDATED_DATE);

        restProductoDepositoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProductoDeposito.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProductoDeposito))
            )
            .andExpect(status().isOk());

        // Validate the ProductoDeposito in the database
        List<ProductoDeposito> productoDepositoList = productoDepositoRepository.findAll();
        assertThat(productoDepositoList).hasSize(databaseSizeBeforeUpdate);
        ProductoDeposito testProductoDeposito = productoDepositoList.get(productoDepositoList.size() - 1);
        assertThat(testProductoDeposito.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testProductoDeposito.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductoDeposito.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductoDeposito() throws Exception {
        int databaseSizeBeforeUpdate = productoDepositoRepository.findAll().size();
        productoDeposito.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoDepositoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productoDeposito.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productoDeposito))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoDeposito in the database
        List<ProductoDeposito> productoDepositoList = productoDepositoRepository.findAll();
        assertThat(productoDepositoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductoDeposito() throws Exception {
        int databaseSizeBeforeUpdate = productoDepositoRepository.findAll().size();
        productoDeposito.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoDepositoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productoDeposito))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoDeposito in the database
        List<ProductoDeposito> productoDepositoList = productoDepositoRepository.findAll();
        assertThat(productoDepositoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductoDeposito() throws Exception {
        int databaseSizeBeforeUpdate = productoDepositoRepository.findAll().size();
        productoDeposito.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoDepositoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoDeposito))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductoDeposito in the database
        List<ProductoDeposito> productoDepositoList = productoDepositoRepository.findAll();
        assertThat(productoDepositoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductoDepositoWithPatch() throws Exception {
        // Initialize the database
        productoDepositoRepository.saveAndFlush(productoDeposito);

        int databaseSizeBeforeUpdate = productoDepositoRepository.findAll().size();

        // Update the productoDeposito using partial update
        ProductoDeposito partialUpdatedProductoDeposito = new ProductoDeposito();
        partialUpdatedProductoDeposito.setId(productoDeposito.getId());

        partialUpdatedProductoDeposito.cantidad(UPDATED_CANTIDAD);

        restProductoDepositoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductoDeposito.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductoDeposito))
            )
            .andExpect(status().isOk());

        // Validate the ProductoDeposito in the database
        List<ProductoDeposito> productoDepositoList = productoDepositoRepository.findAll();
        assertThat(productoDepositoList).hasSize(databaseSizeBeforeUpdate);
        ProductoDeposito testProductoDeposito = productoDepositoList.get(productoDepositoList.size() - 1);
        assertThat(testProductoDeposito.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testProductoDeposito.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductoDeposito.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductoDepositoWithPatch() throws Exception {
        // Initialize the database
        productoDepositoRepository.saveAndFlush(productoDeposito);

        int databaseSizeBeforeUpdate = productoDepositoRepository.findAll().size();

        // Update the productoDeposito using partial update
        ProductoDeposito partialUpdatedProductoDeposito = new ProductoDeposito();
        partialUpdatedProductoDeposito.setId(productoDeposito.getId());

        partialUpdatedProductoDeposito.cantidad(UPDATED_CANTIDAD).createdDate(UPDATED_CREATED_DATE).updatedDate(UPDATED_UPDATED_DATE);

        restProductoDepositoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductoDeposito.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductoDeposito))
            )
            .andExpect(status().isOk());

        // Validate the ProductoDeposito in the database
        List<ProductoDeposito> productoDepositoList = productoDepositoRepository.findAll();
        assertThat(productoDepositoList).hasSize(databaseSizeBeforeUpdate);
        ProductoDeposito testProductoDeposito = productoDepositoList.get(productoDepositoList.size() - 1);
        assertThat(testProductoDeposito.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testProductoDeposito.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductoDeposito.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductoDeposito() throws Exception {
        int databaseSizeBeforeUpdate = productoDepositoRepository.findAll().size();
        productoDeposito.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoDepositoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productoDeposito.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productoDeposito))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoDeposito in the database
        List<ProductoDeposito> productoDepositoList = productoDepositoRepository.findAll();
        assertThat(productoDepositoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductoDeposito() throws Exception {
        int databaseSizeBeforeUpdate = productoDepositoRepository.findAll().size();
        productoDeposito.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoDepositoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productoDeposito))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoDeposito in the database
        List<ProductoDeposito> productoDepositoList = productoDepositoRepository.findAll();
        assertThat(productoDepositoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductoDeposito() throws Exception {
        int databaseSizeBeforeUpdate = productoDepositoRepository.findAll().size();
        productoDeposito.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoDepositoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productoDeposito))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductoDeposito in the database
        List<ProductoDeposito> productoDepositoList = productoDepositoRepository.findAll();
        assertThat(productoDepositoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductoDeposito() throws Exception {
        // Initialize the database
        productoDepositoRepository.saveAndFlush(productoDeposito);

        int databaseSizeBeforeDelete = productoDepositoRepository.findAll().size();

        // Delete the productoDeposito
        restProductoDepositoMockMvc
            .perform(delete(ENTITY_API_URL_ID, productoDeposito.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductoDeposito> productoDepositoList = productoDepositoRepository.findAll();
        assertThat(productoDepositoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
