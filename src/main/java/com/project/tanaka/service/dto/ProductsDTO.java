package com.project.tanaka.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.project.tanaka.domain.Products} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductsDTO implements Serializable {

    private Long id;

    private String description;

    private String name;

    private BigDecimal price;

    private String status;

    private Long categoryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductsDTO)) {
            return false;
        }

        ProductsDTO productsDTO = (ProductsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductsDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", name='" + getName() + "'" +
            ", price=" + getPrice() +
            ", status='" + getStatus() + "'" +
            ", categoryId=" + getCategoryId() +
            "}";
    }
}
