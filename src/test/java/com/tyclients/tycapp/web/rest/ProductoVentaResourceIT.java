package com.tyclients.tycapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tyclients.tycapp.IntegrationTest;
import com.tyclients.tycapp.domain.Producto;
import com.tyclients.tycapp.domain.ProductoVenta;
import com.tyclients.tycapp.domain.Venta;
import com.tyclients.tycapp.repository.ProductoVentaRepository;
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
 * Integration tests for the {@link ProductoVentaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductoVentaResourceIT {

    private static final Long DEFAULT_COSTO_TOTAL = 1L;
    private static final Long UPDATED_COSTO_TOTAL = 2L;

    private static final Long DEFAULT_COSTO_TOTAL_PUNTOS = 1L;
    private static final Long UPDATED_COSTO_TOTAL_PUNTOS = 2L;

    private static final Long DEFAULT_CANTIDAD = 1L;
    private static final Long UPDATED_CANTIDAD = 2L;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/producto-ventas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductoVentaRepository productoVentaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductoVentaMockMvc;

    private ProductoVenta productoVenta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductoVenta createEntity(EntityManager em) {
        ProductoVenta productoVenta = new ProductoVenta()
            .costoTotal(DEFAULT_COSTO_TOTAL)
            .costoTotalPuntos(DEFAULT_COSTO_TOTAL_PUNTOS)
            .cantidad(DEFAULT_CANTIDAD)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        // Add required entity
        Venta venta;
        if (TestUtil.findAll(em, Venta.class).isEmpty()) {
            venta = VentaResourceIT.createEntity(em);
            em.persist(venta);
            em.flush();
        } else {
            venta = TestUtil.findAll(em, Venta.class).get(0);
        }
        productoVenta.setVenta(venta);
        // Add required entity
        Producto producto;
        if (TestUtil.findAll(em, Producto.class).isEmpty()) {
            producto = ProductoResourceIT.createEntity(em);
            em.persist(producto);
            em.flush();
        } else {
            producto = TestUtil.findAll(em, Producto.class).get(0);
        }
        productoVenta.setProducto(producto);
        return productoVenta;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductoVenta createUpdatedEntity(EntityManager em) {
        ProductoVenta productoVenta = new ProductoVenta()
            .costoTotal(UPDATED_COSTO_TOTAL)
            .costoTotalPuntos(UPDATED_COSTO_TOTAL_PUNTOS)
            .cantidad(UPDATED_CANTIDAD)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        // Add required entity
        Venta venta;
        if (TestUtil.findAll(em, Venta.class).isEmpty()) {
            venta = VentaResourceIT.createUpdatedEntity(em);
            em.persist(venta);
            em.flush();
        } else {
            venta = TestUtil.findAll(em, Venta.class).get(0);
        }
        productoVenta.setVenta(venta);
        // Add required entity
        Producto producto;
        if (TestUtil.findAll(em, Producto.class).isEmpty()) {
            producto = ProductoResourceIT.createUpdatedEntity(em);
            em.persist(producto);
            em.flush();
        } else {
            producto = TestUtil.findAll(em, Producto.class).get(0);
        }
        productoVenta.setProducto(producto);
        return productoVenta;
    }

    @BeforeEach
    public void initTest() {
        productoVenta = createEntity(em);
    }

    @Test
    @Transactional
    void createProductoVenta() throws Exception {
        int databaseSizeBeforeCreate = productoVentaRepository.findAll().size();
        // Create the ProductoVenta
        restProductoVentaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoVenta)))
            .andExpect(status().isCreated());

        // Validate the ProductoVenta in the database
        List<ProductoVenta> productoVentaList = productoVentaRepository.findAll();
        assertThat(productoVentaList).hasSize(databaseSizeBeforeCreate + 1);
        ProductoVenta testProductoVenta = productoVentaList.get(productoVentaList.size() - 1);
        assertThat(testProductoVenta.getCostoTotal()).isEqualTo(DEFAULT_COSTO_TOTAL);
        assertThat(testProductoVenta.getCostoTotalPuntos()).isEqualTo(DEFAULT_COSTO_TOTAL_PUNTOS);
        assertThat(testProductoVenta.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testProductoVenta.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductoVenta.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createProductoVentaWithExistingId() throws Exception {
        // Create the ProductoVenta with an existing ID
        productoVenta.setId(1L);

        int databaseSizeBeforeCreate = productoVentaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductoVentaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoVenta)))
            .andExpect(status().isBadRequest());

        // Validate the ProductoVenta in the database
        List<ProductoVenta> productoVentaList = productoVentaRepository.findAll();
        assertThat(productoVentaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductoVentas() throws Exception {
        // Initialize the database
        productoVentaRepository.saveAndFlush(productoVenta);

        // Get all the productoVentaList
        restProductoVentaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productoVenta.getId().intValue())))
            .andExpect(jsonPath("$.[*].costoTotal").value(hasItem(DEFAULT_COSTO_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].costoTotalPuntos").value(hasItem(DEFAULT_COSTO_TOTAL_PUNTOS.intValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getProductoVenta() throws Exception {
        // Initialize the database
        productoVentaRepository.saveAndFlush(productoVenta);

        // Get the productoVenta
        restProductoVentaMockMvc
            .perform(get(ENTITY_API_URL_ID, productoVenta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productoVenta.getId().intValue()))
            .andExpect(jsonPath("$.costoTotal").value(DEFAULT_COSTO_TOTAL.intValue()))
            .andExpect(jsonPath("$.costoTotalPuntos").value(DEFAULT_COSTO_TOTAL_PUNTOS.intValue()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD.intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProductoVenta() throws Exception {
        // Get the productoVenta
        restProductoVentaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductoVenta() throws Exception {
        // Initialize the database
        productoVentaRepository.saveAndFlush(productoVenta);

        int databaseSizeBeforeUpdate = productoVentaRepository.findAll().size();

        // Update the productoVenta
        ProductoVenta updatedProductoVenta = productoVentaRepository.findById(productoVenta.getId()).get();
        // Disconnect from session so that the updates on updatedProductoVenta are not directly saved in db
        em.detach(updatedProductoVenta);
        updatedProductoVenta
            .costoTotal(UPDATED_COSTO_TOTAL)
            .costoTotalPuntos(UPDATED_COSTO_TOTAL_PUNTOS)
            .cantidad(UPDATED_CANTIDAD)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restProductoVentaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProductoVenta.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProductoVenta))
            )
            .andExpect(status().isOk());

        // Validate the ProductoVenta in the database
        List<ProductoVenta> productoVentaList = productoVentaRepository.findAll();
        assertThat(productoVentaList).hasSize(databaseSizeBeforeUpdate);
        ProductoVenta testProductoVenta = productoVentaList.get(productoVentaList.size() - 1);
        assertThat(testProductoVenta.getCostoTotal()).isEqualTo(UPDATED_COSTO_TOTAL);
        assertThat(testProductoVenta.getCostoTotalPuntos()).isEqualTo(UPDATED_COSTO_TOTAL_PUNTOS);
        assertThat(testProductoVenta.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testProductoVenta.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductoVenta.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductoVenta() throws Exception {
        int databaseSizeBeforeUpdate = productoVentaRepository.findAll().size();
        productoVenta.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoVentaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productoVenta.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productoVenta))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoVenta in the database
        List<ProductoVenta> productoVentaList = productoVentaRepository.findAll();
        assertThat(productoVentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductoVenta() throws Exception {
        int databaseSizeBeforeUpdate = productoVentaRepository.findAll().size();
        productoVenta.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoVentaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productoVenta))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoVenta in the database
        List<ProductoVenta> productoVentaList = productoVentaRepository.findAll();
        assertThat(productoVentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductoVenta() throws Exception {
        int databaseSizeBeforeUpdate = productoVentaRepository.findAll().size();
        productoVenta.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoVentaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoVenta)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductoVenta in the database
        List<ProductoVenta> productoVentaList = productoVentaRepository.findAll();
        assertThat(productoVentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductoVentaWithPatch() throws Exception {
        // Initialize the database
        productoVentaRepository.saveAndFlush(productoVenta);

        int databaseSizeBeforeUpdate = productoVentaRepository.findAll().size();

        // Update the productoVenta using partial update
        ProductoVenta partialUpdatedProductoVenta = new ProductoVenta();
        partialUpdatedProductoVenta.setId(productoVenta.getId());

        partialUpdatedProductoVenta.costoTotal(UPDATED_COSTO_TOTAL).updatedDate(UPDATED_UPDATED_DATE);

        restProductoVentaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductoVenta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductoVenta))
            )
            .andExpect(status().isOk());

        // Validate the ProductoVenta in the database
        List<ProductoVenta> productoVentaList = productoVentaRepository.findAll();
        assertThat(productoVentaList).hasSize(databaseSizeBeforeUpdate);
        ProductoVenta testProductoVenta = productoVentaList.get(productoVentaList.size() - 1);
        assertThat(testProductoVenta.getCostoTotal()).isEqualTo(UPDATED_COSTO_TOTAL);
        assertThat(testProductoVenta.getCostoTotalPuntos()).isEqualTo(DEFAULT_COSTO_TOTAL_PUNTOS);
        assertThat(testProductoVenta.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testProductoVenta.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductoVenta.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductoVentaWithPatch() throws Exception {
        // Initialize the database
        productoVentaRepository.saveAndFlush(productoVenta);

        int databaseSizeBeforeUpdate = productoVentaRepository.findAll().size();

        // Update the productoVenta using partial update
        ProductoVenta partialUpdatedProductoVenta = new ProductoVenta();
        partialUpdatedProductoVenta.setId(productoVenta.getId());

        partialUpdatedProductoVenta
            .costoTotal(UPDATED_COSTO_TOTAL)
            .costoTotalPuntos(UPDATED_COSTO_TOTAL_PUNTOS)
            .cantidad(UPDATED_CANTIDAD)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restProductoVentaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductoVenta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductoVenta))
            )
            .andExpect(status().isOk());

        // Validate the ProductoVenta in the database
        List<ProductoVenta> productoVentaList = productoVentaRepository.findAll();
        assertThat(productoVentaList).hasSize(databaseSizeBeforeUpdate);
        ProductoVenta testProductoVenta = productoVentaList.get(productoVentaList.size() - 1);
        assertThat(testProductoVenta.getCostoTotal()).isEqualTo(UPDATED_COSTO_TOTAL);
        assertThat(testProductoVenta.getCostoTotalPuntos()).isEqualTo(UPDATED_COSTO_TOTAL_PUNTOS);
        assertThat(testProductoVenta.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testProductoVenta.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductoVenta.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductoVenta() throws Exception {
        int databaseSizeBeforeUpdate = productoVentaRepository.findAll().size();
        productoVenta.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoVentaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productoVenta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productoVenta))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoVenta in the database
        List<ProductoVenta> productoVentaList = productoVentaRepository.findAll();
        assertThat(productoVentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductoVenta() throws Exception {
        int databaseSizeBeforeUpdate = productoVentaRepository.findAll().size();
        productoVenta.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoVentaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productoVenta))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoVenta in the database
        List<ProductoVenta> productoVentaList = productoVentaRepository.findAll();
        assertThat(productoVentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductoVenta() throws Exception {
        int databaseSizeBeforeUpdate = productoVentaRepository.findAll().size();
        productoVenta.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoVentaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(productoVenta))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductoVenta in the database
        List<ProductoVenta> productoVentaList = productoVentaRepository.findAll();
        assertThat(productoVentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductoVenta() throws Exception {
        // Initialize the database
        productoVentaRepository.saveAndFlush(productoVenta);

        int databaseSizeBeforeDelete = productoVentaRepository.findAll().size();

        // Delete the productoVenta
        restProductoVentaMockMvc
            .perform(delete(ENTITY_API_URL_ID, productoVenta.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductoVenta> productoVentaList = productoVentaRepository.findAll();
        assertThat(productoVentaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
