package com.tyclients.tycapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.tyclients.tycapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FormaPagoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormaPago.class);
        FormaPago formaPago1 = new FormaPago();
        formaPago1.setId(1L);
        FormaPago formaPago2 = new FormaPago();
        formaPago2.setId(formaPago1.getId());
        assertThat(formaPago1).isEqualTo(formaPago2);
        formaPago2.setId(2L);
        assertThat(formaPago1).isNotEqualTo(formaPago2);
        formaPago1.setId(null);
        assertThat(formaPago1).isNotEqualTo(formaPago2);
    }
}
