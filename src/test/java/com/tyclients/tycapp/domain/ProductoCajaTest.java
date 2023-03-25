package com.tyclients.tycapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.tyclients.tycapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductoCajaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductoCaja.class);
        ProductoCaja productoCaja1 = new ProductoCaja();
        productoCaja1.setId(1L);
        ProductoCaja productoCaja2 = new ProductoCaja();
        productoCaja2.setId(productoCaja1.getId());
        assertThat(productoCaja1).isEqualTo(productoCaja2);
        productoCaja2.setId(2L);
        assertThat(productoCaja1).isNotEqualTo(productoCaja2);
        productoCaja1.setId(null);
        assertThat(productoCaja1).isNotEqualTo(productoCaja2);
    }
}
