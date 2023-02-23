package com.project.tanaka.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.project.tanaka.domain.Orderdetails} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderdetailsDTO implements Serializable {

    private Long id;

    private String color;

    private BigDecimal price;

    private Integer quantity;

    private String sizes;

    private BigDecimal unitPrice;

    private Long orderId;

    private Long productId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSizes() {
        return sizes;
    }

    public void setSizes(String sizes) {
        this.sizes = sizes;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderdetailsDTO)) {
            return false;
        }

        OrderdetailsDTO orderdetailsDTO = (OrderdetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, orderdetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderdetailsDTO{" +
            "id=" + getId() +
            ", color='" + getColor() + "'" +
            ", price=" + getPrice() +
            ", quantity=" + getQuantity() +
            ", sizes='" + getSizes() + "'" +
            ", unitPrice=" + getUnitPrice() +
            ", orderId=" + getOrderId() +
            ", productId=" + getProductId() +
            "}";
    }
}
