package com.project.tanaka.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderdetailsMapperTest {

    private OrderdetailsMapper orderdetailsMapper;

    @BeforeEach
    public void setUp() {
        orderdetailsMapper = new OrderdetailsMapperImpl();
    }
}
