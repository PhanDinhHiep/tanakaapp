package com.project.tanaka.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.tanaka.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrderdetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderdetailsDTO.class);
        OrderdetailsDTO orderdetailsDTO1 = new OrderdetailsDTO();
        orderdetailsDTO1.setId(1L);
        OrderdetailsDTO orderdetailsDTO2 = new OrderdetailsDTO();
        assertThat(orderdetailsDTO1).isNotEqualTo(orderdetailsDTO2);
        orderdetailsDTO2.setId(orderdetailsDTO1.getId());
        assertThat(orderdetailsDTO1).isEqualTo(orderdetailsDTO2);
        orderdetailsDTO2.setId(2L);
        assertThat(orderdetailsDTO1).isNotEqualTo(orderdetailsDTO2);
        orderdetailsDTO1.setId(null);
        assertThat(orderdetailsDTO1).isNotEqualTo(orderdetailsDTO2);
    }
}
