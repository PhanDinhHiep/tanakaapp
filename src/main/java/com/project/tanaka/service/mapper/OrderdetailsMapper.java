package com.project.tanaka.service.mapper;

import com.project.tanaka.domain.Orderdetails;
import com.project.tanaka.service.dto.OrderdetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Orderdetails} and its DTO {@link OrderdetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrderdetailsMapper extends EntityMapper<OrderdetailsDTO, Orderdetails> {}
