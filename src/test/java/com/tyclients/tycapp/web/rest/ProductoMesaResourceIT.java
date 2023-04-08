package com.tyclients.tycapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tyclients.tycapp.IntegrationTest;
import com.tyclients.tycapp.domain.Producto;
import com.tyclients.tycapp.domain.ProductoMesa;
import com.tyclients.tycapp.repository.ProductoMesaRepository;
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
 * Integration tests for the {@link ProductoMesaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductoMesaResourceIT {

    private static final Long DEFAULT_COSTO_TOTAL = 1L;
    private static final Long UPDATED_COSTO_TOTAL = 2L;

    private static final Long DEFAULT_COSTO_TOTAL_PUNTOS = 1L;
    private static final Long UPDATED_COSTO_TOTAL_PUNTOS = 2L;

    private static final Long DEFAULT_CANTIDAD = 1L;
    private static final Long UPDATED_CANTIDAD = 2L;

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/producto-mesas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductoMesaRepository productoMesaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductoMesaMockMvc;

    private ProductoMesa productoMesa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductoMesa createEntity(EntityManager em) {
        ProductoMesa productoMesa = new ProductoMesa()
            .costoTotal(DEFAULT_COSTO_TOTAL)
            .costoTotalPuntos(DEFAULT_COSTO_TOTAL_PUNTOS)
            .cantidad(DEFAULT_CANTIDAD)
            .estado(DEFAULT_ESTADO)
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
        productoMesa.setProducto(producto);
        return productoMesa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductoMesa createUpdatedEntity(EntityManager em) {
        ProductoMesa productoMesa = new ProductoMesa()
            .costoTotal(UPDATED_COSTO_TOTAL)
            .costoTotalPuntos(UPDATED_COSTO_TOTAL_PUNTOS)
            .cantidad(UPDATED_CANTIDAD)
            .estado(UPDATED_ESTADO)
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
        productoMesa.setProducto(producto);
        return productoMesa;
    }

    @BeforeEach
    public void initTest() {
        productoMesa = createEntity(em);
    }

    @Test
    @Transactional
    void createProductoMesa() throws Exception {
        int databaseSizeBeforeCreate = productoMesaRepository.findAll().size();
        // Create the ProductoMesa
        restProductoMesaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoMesa)))
            .andExpect(status().isCreated());

        // Validate the ProductoMesa in the database
        List<ProductoMesa> productoMesaList = productoMesaRepository.findAll();
        assertThat(productoMesaList).hasSize(databaseSizeBeforeCreate + 1);
        ProductoMesa testProductoMesa = productoMesaList.get(productoMesaList.size() - 1);
        assertThat(testProductoMesa.getCostoTotal()).isEqualTo(DEFAULT_COSTO_TOTAL);
        assertThat(testProductoMesa.getCostoTotalPuntos()).isEqualTo(DEFAULT_COSTO_TOTAL_PUNTOS);
        assertThat(testProductoMesa.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testProductoMesa.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testProductoMesa.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductoMesa.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createProductoMesaWithExistingId() throws Exception {
        // Create the ProductoMesa with an existing ID
        productoMesa.setId(1L);

        int databaseSizeBeforeCreate = productoMesaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductoMesaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoMesa)))
            .andExpect(status().isBadRequest());

        // Validate the ProductoMesa in the database
        List<ProductoMesa> productoMesaList = productoMesaRepository.findAll();
        assertThat(productoMesaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = productoMesaRepository.findAll().size();
        // set the field null
        productoMesa.setEstado(null);

        // Create the ProductoMesa, which fails.

        restProductoMesaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoMesa)))
            .andExpect(status().isBadRequest());

        List<ProductoMesa> productoMesaList = productoMesaRepository.findAll();
        assertThat(productoMesaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProductoMesas() throws Exception {
        // Initialize the database
        productoMesaRepository.saveAndFlush(productoMesa);

        // Get all the productoMesaList
        restProductoMesaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productoMesa.getId().intValue())))
            .andExpect(jsonPath("$.[*].costoTotal").value(hasItem(DEFAULT_COSTO_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].costoTotalPuntos").value(hasItem(DEFAULT_COSTO_TOTAL_PUNTOS.intValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD.intValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getProductoMesa() throws Exception {
        // Initialize the database
        productoMesaRepository.saveAndFlush(productoMesa);

        // Get the productoMesa
        restProductoMesaMockMvc
            .perform(get(ENTITY_API_URL_ID, productoMesa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productoMesa.getId().intValue()))
            .andExpect(jsonPath("$.costoTotal").value(DEFAULT_COSTO_TOTAL.intValue()))
            .andExpect(jsonPath("$.costoTotalPuntos").value(DEFAULT_COSTO_TOTAL_PUNTOS.intValue()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD.intValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProductoMesa() throws Exception {
        // Get the productoMesa
        restProductoMesaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductoMesa() throws Exception {
        // Initialize the database
        productoMesaRepository.saveAndFlush(productoMesa);

        int databaseSizeBeforeUpdate = productoMesaRepository.findAll().size();

        // Update the productoMesa
        ProductoMesa updatedProductoMesa = productoMesaRepository.findById(productoMesa.getId()).get();
        // Disconnect from session so that the updates on updatedProductoMesa are not directly saved in db
        em.detach(updatedProductoMesa);
        updatedProductoMesa
            .costoTotal(UPDATED_COSTO_TOTAL)
            .costoTotalPuntos(UPDATED_COSTO_TOTAL_PUNTOS)
            .cantidad(UPDATED_CANTIDAD)
            .estado(UPDATED_ESTADO)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restProductoMesaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProductoMesa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProductoMesa))
            )
            .andExpect(status().isOk());

        // Validate the ProductoMesa in the database
        List<ProductoMesa> productoMesaList = productoMesaRepository.findAll();
        assertThat(productoMesaList).hasSize(databaseSizeBeforeUpdate);
        ProductoMesa testProductoMesa = productoMesaList.get(productoMesaList.size() - 1);
        assertThat(testProductoMesa.getCostoTotal()).isEqualTo(UPDATED_COSTO_TOTAL);
        assertThat(testProductoMesa.getCostoTotalPuntos()).isEqualTo(UPDATED_COSTO_TOTAL_PUNTOS);
        assertThat(testProductoMesa.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testProductoMesa.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testProductoMesa.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductoMesa.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductoMesa() throws Exception {
        int databaseSizeBeforeUpdate = productoMesaRepository.findAll().size();
        productoMesa.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoMesaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productoMesa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productoMesa))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoMesa in the database
        List<ProductoMesa> productoMesaList = productoMesaRepository.findAll();
        assertThat(productoMesaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductoMesa() throws Exception {
        int databaseSizeBeforeUpdate = productoMesaRepository.findAll().size();
        productoMesa.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoMesaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productoMesa))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoMesa in the database
        List<ProductoMesa> productoMesaList = productoMesaRepository.findAll();
        assertThat(productoMesaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductoMesa() throws Exception {
        int databaseSizeBeforeUpdate = productoMesaRepository.findAll().size();
        productoMesa.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoMesaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoMesa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductoMesa in the database
        List<ProductoMesa> productoMesaList = productoMesaRepository.findAll();
        assertThat(productoMesaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductoMesaWithPatch() throws Exception {
        // Initialize the database
        productoMesaRepository.saveAndFlush(productoMesa);

        int databaseSizeBeforeUpdate = productoMesaRepository.findAll().size();

        // Update the productoMesa using partial update
        ProductoMesa partialUpdatedProductoMesa = new ProductoMesa();
        partialUpdatedProductoMesa.setId(productoMesa.getId());

        partialUpdatedProductoMesa.createdDate(UPDATED_CREATED_DATE).updatedDate(UPDATED_UPDATED_DATE);

        restProductoMesaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductoMesa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductoMesa))
            )
            .andExpect(status().isOk());

        // Validate the ProductoMesa in the database
        List<ProductoMesa> productoMesaList = productoMesaRepository.findAll();
        assertThat(productoMesaList).hasSize(databaseSizeBeforeUpdate);
        ProductoMesa testProductoMesa = productoMesaList.get(productoMesaList.size() - 1);
        assertThat(testProductoMesa.getCostoTotal()).isEqualTo(DEFAULT_COSTO_TOTAL);
        assertThat(testProductoMesa.getCostoTotalPuntos()).isEqualTo(DEFAULT_COSTO_TOTAL_PUNTOS);
        assertThat(testProductoMesa.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testProductoMesa.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testProductoMesa.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductoMesa.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductoMesaWithPatch() throws Exception {
        // Initialize the database
        productoMesaRepository.saveAndFlush(productoMesa);

        int databaseSizeBeforeUpdate = productoMesaRepository.findAll().size();

        // Update the productoMesa using partial update
        ProductoMesa partialUpdatedProductoMesa = new ProductoMesa();
        partialUpdatedProductoMesa.setId(productoMesa.getId());

        partialUpdatedProductoMesa
            .costoTotal(UPDATED_COSTO_TOTAL)
            .costoTotalPuntos(UPDATED_COSTO_TOTAL_PUNTOS)
            .cantidad(UPDATED_CANTIDAD)
            .estado(UPDATED_ESTADO)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restProductoMesaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductoMesa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductoMesa))
            )
            .andExpect(status().isOk());

        // Validate the ProductoMesa in the database
        List<ProductoMesa> productoMesaList = productoMesaRepository.findAll();
        assertThat(productoMesaList).hasSize(databaseSizeBeforeUpdate);
        ProductoMesa testProductoMesa = productoMesaList.get(productoMesaList.size() - 1);
        assertThat(testProductoMesa.getCostoTotal()).isEqualTo(UPDATED_COSTO_TOTAL);
        assertThat(testProductoMesa.getCostoTotalPuntos()).isEqualTo(UPDATED_COSTO_TOTAL_PUNTOS);
        assertThat(testProductoMesa.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testProductoMesa.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testProductoMesa.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductoMesa.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductoMesa() throws Exception {
        int databaseSizeBeforeUpdate = productoMesaRepository.findAll().size();
        productoMesa.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoMesaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productoMesa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productoMesa))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoMesa in the database
        List<ProductoMesa> productoMesaList = productoMesaRepository.findAll();
        assertThat(productoMesaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductoMesa() throws Exception {
        int databaseSizeBeforeUpdate = productoMesaRepository.findAll().size();
        productoMesa.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoMesaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productoMesa))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoMesa in the database
        List<ProductoMesa> productoMesaList = productoMesaRepository.findAll();
        assertThat(productoMesaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductoMesa() throws Exception {
        int databaseSizeBeforeUpdate = productoMesaRepository.findAll().size();
        productoMesa.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoMesaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(productoMesa))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductoMesa in the database
        List<ProductoMesa> productoMesaList = productoMesaRepository.findAll();
        assertThat(productoMesaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductoMesa() throws Exception {
        // Initialize the database
        productoMesaRepository.saveAndFlush(productoMesa);

        int databaseSizeBeforeDelete = productoMesaRepository.findAll().size();

        // Delete the productoMesa
        restProductoMesaMockMvc
            .perform(delete(ENTITY_API_URL_ID, productoMesa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductoMesa> productoMesaList = productoMesaRepository.findAll();
        assertThat(productoMesaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
