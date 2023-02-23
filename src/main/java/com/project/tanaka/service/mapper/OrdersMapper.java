package com.project.tanaka.service.mapper;

import com.project.tanaka.domain.Orders;
import com.project.tanaka.service.dto.OrdersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Orders} and its DTO {@link OrdersDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrdersMapper extends EntityMapper<OrdersDTO, Orders> {}
