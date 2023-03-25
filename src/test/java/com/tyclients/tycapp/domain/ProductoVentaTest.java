package com.tyclients.tycapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.tyclients.tycapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductoVentaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductoVenta.class);
        ProductoVenta productoVenta1 = new ProductoVenta();
        productoVenta1.setId(1L);
        ProductoVenta productoVenta2 = new ProductoVenta();
        productoVenta2.setId(productoVenta1.getId());
        assertThat(productoVenta1).isEqualTo(productoVenta2);
        productoVenta2.setId(2L);
        assertThat(productoVenta1).isNotEqualTo(productoVenta2);
        productoVenta1.setId(null);
        assertThat(productoVenta1).isNotEqualTo(productoVenta2);
    }
}
