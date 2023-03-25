package com.tyclients.tycapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.tyclients.tycapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductoDepositoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductoDeposito.class);
        ProductoDeposito productoDeposito1 = new ProductoDeposito();
        productoDeposito1.setId(1L);
        ProductoDeposito productoDeposito2 = new ProductoDeposito();
        productoDeposito2.setId(productoDeposito1.getId());
        assertThat(productoDeposito1).isEqualTo(productoDeposito2);
        productoDeposito2.setId(2L);
        assertThat(productoDeposito1).isNotEqualTo(productoDeposito2);
        productoDeposito1.setId(null);
        assertThat(productoDeposito1).isNotEqualTo(productoDeposito2);
    }
}
