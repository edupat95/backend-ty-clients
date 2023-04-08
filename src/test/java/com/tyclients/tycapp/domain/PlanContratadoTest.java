package com.tyclients.tycapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.tyclients.tycapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlanContratadoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanContratado.class);
        PlanContratado planContratado1 = new PlanContratado();
        planContratado1.setId(1L);
        PlanContratado planContratado2 = new PlanContratado();
        planContratado2.setId(planContratado1.getId());
        assertThat(planContratado1).isEqualTo(planContratado2);
        planContratado2.setId(2L);
        assertThat(planContratado1).isNotEqualTo(planContratado2);
        planContratado1.setId(null);
        assertThat(planContratado1).isNotEqualTo(planContratado2);
    }
}
