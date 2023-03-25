package com.tyclients.tycapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.tyclients.tycapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AccesoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Acceso.class);
        Acceso acceso1 = new Acceso();
        acceso1.setId(1L);
        Acceso acceso2 = new Acceso();
        acceso2.setId(acceso1.getId());
        assertThat(acceso1).isEqualTo(acceso2);
        acceso2.setId(2L);
        assertThat(acceso1).isNotEqualTo(acceso2);
        acceso1.setId(null);
        assertThat(acceso1).isNotEqualTo(acceso2);
    }
}
