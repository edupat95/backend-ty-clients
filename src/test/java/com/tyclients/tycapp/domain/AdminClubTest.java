package com.tyclients.tycapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.tyclients.tycapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdminClubTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdminClub.class);
        AdminClub adminClub1 = new AdminClub();
        adminClub1.setId(1L);
        AdminClub adminClub2 = new AdminClub();
        adminClub2.setId(adminClub1.getId());
        assertThat(adminClub1).isEqualTo(adminClub2);
        adminClub2.setId(2L);
        assertThat(adminClub1).isNotEqualTo(adminClub2);
        adminClub1.setId(null);
        assertThat(adminClub1).isNotEqualTo(adminClub2);
    }
}
