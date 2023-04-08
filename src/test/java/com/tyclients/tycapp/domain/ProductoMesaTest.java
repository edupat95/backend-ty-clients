package com.tyclients.tycapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.tyclients.tycapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductoMesaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductoMesa.class);
        ProductoMesa productoMesa1 = new ProductoMesa();
        productoMesa1.setId(1L);
        ProductoMesa productoMesa2 = new ProductoMesa();
        productoMesa2.setId(productoMesa1.getId());
        assertThat(productoMesa1).isEqualTo(productoMesa2);
        productoMesa2.setId(2L);
        assertThat(productoMesa1).isNotEqualTo(productoMesa2);
        productoMesa1.setId(null);
        assertThat(productoMesa1).isNotEqualTo(productoMesa2);
    }
}
