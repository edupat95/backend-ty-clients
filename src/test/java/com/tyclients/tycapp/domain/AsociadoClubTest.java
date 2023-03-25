package com.tyclients.tycapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.tyclients.tycapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AsociadoClubTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AsociadoClub.class);
        AsociadoClub asociadoClub1 = new AsociadoClub();
        asociadoClub1.setId(1L);
        AsociadoClub asociadoClub2 = new AsociadoClub();
        asociadoClub2.setId(asociadoClub1.getId());
        assertThat(asociadoClub1).isEqualTo(asociadoClub2);
        asociadoClub2.setId(2L);
        assertThat(asociadoClub1).isNotEqualTo(asociadoClub2);
        asociadoClub1.setId(null);
        assertThat(asociadoClub1).isNotEqualTo(asociadoClub2);
    }
}
