package com.project.tanaka.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.project.tanaka.domain.Orders} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrdersDTO implements Serializable {

    private Long id;

    private LocalDate deliveredDate;

    private String note;

    private LocalDate orderDate;

    private String orderStatus;

    private BigDecimal totalPrice;

    private Long customerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(LocalDate deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdersDTO)) {
            return false;
        }

        OrdersDTO ordersDTO = (OrdersDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ordersDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdersDTO{" +
            "id=" + getId() +
            ", deliveredDate='" + getDeliveredDate() + "'" +
            ", note='" + getNote() + "'" +
            ", orderDate='" + getOrderDate() + "'" +
            ", orderStatus='" + getOrderStatus() + "'" +
            ", totalPrice=" + getTotalPrice() +
            ", customerId=" + getCustomerId() +
            "}";
    }
}
