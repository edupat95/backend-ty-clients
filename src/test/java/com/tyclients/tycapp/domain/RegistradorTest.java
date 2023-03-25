package com.tyclients.tycapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.tyclients.tycapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RegistradorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Registrador.class);
        Registrador registrador1 = new Registrador();
        registrador1.setId(1L);
        Registrador registrador2 = new Registrador();
        registrador2.setId(registrador1.getId());
        assertThat(registrador1).isEqualTo(registrador2);
        registrador2.setId(2L);
        assertThat(registrador1).isNotEqualTo(registrador2);
        registrador1.setId(null);
        assertThat(registrador1).isNotEqualTo(registrador2);
    }
}
