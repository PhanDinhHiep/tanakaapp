package com.project.tanaka.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.tanaka.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrderdetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Orderdetails.class);
        Orderdetails orderdetails1 = new Orderdetails();
        orderdetails1.setId(1L);
        Orderdetails orderdetails2 = new Orderdetails();
        orderdetails2.setId(orderdetails1.getId());
        assertThat(orderdetails1).isEqualTo(orderdetails2);
        orderdetails2.setId(2L);
        assertThat(orderdetails1).isNotEqualTo(orderdetails2);
        orderdetails1.setId(null);
        assertThat(orderdetails1).isNotEqualTo(orderdetails2);
    }
}
