package com.project.tanaka.service.mapper;

import com.project.tanaka.domain.Products;
import com.project.tanaka.service.dto.ProductsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Products} and its DTO {@link ProductsDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductsMapper extends EntityMapper<ProductsDTO, Products> {}
