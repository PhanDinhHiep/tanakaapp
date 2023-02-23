package com.project.tanaka.service.mapper;

import com.project.tanaka.domain.Customers;
import com.project.tanaka.service.dto.CustomersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Customers} and its DTO {@link CustomersDTO}.
 */
@Mapper(componentModel = "spring")
public interface CustomersMapper extends EntityMapper<CustomersDTO, Customers> {}
