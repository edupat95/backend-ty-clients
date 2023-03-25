package com.tyclients.tycapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.tyclients.tycapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CajeroTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cajero.class);
        Cajero cajero1 = new Cajero();
        cajero1.setId(1L);
        Cajero cajero2 = new Cajero();
        cajero2.setId(cajero1.getId());
        assertThat(cajero1).isEqualTo(cajero2);
        cajero2.setId(2L);
        assertThat(cajero1).isNotEqualTo(cajero2);
        cajero1.setId(null);
        assertThat(cajero1).isNotEqualTo(cajero2);
    }
}
