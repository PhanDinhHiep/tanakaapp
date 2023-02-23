package com.project.tanaka.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * A Orderdetails.
 */
@Entity
@Table(name = "orderdetails")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Orderdetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "color")
    private String color;

    @Column(name = "price", precision = 21, scale = 2)
    private BigDecimal price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "sizes")
    private String sizes;

    @Column(name = "unit_price", precision = 21, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "product_id")
    private Long productId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Orderdetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColor() {
        return this.color;
    }

    public Orderdetails color(String color) {
        this.setColor(color);
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public Orderdetails price(BigDecimal price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public Orderdetails quantity(Integer quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSizes() {
        return this.sizes;
    }

    public Orderdetails sizes(String sizes) {
        this.setSizes(sizes);
        return this;
    }

    public void setSizes(String sizes) {
        this.sizes = sizes;
    }

    public BigDecimal getUnitPrice() {
        return this.unitPrice;
    }

    public Orderdetails unitPrice(BigDecimal unitPrice) {
        this.setUnitPrice(unitPrice);
        return this;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getOrderId() {
        return this.orderId;
    }

    public Orderdetails orderId(Long orderId) {
        this.setOrderId(orderId);
        return this;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return this.productId;
    }

    public Orderdetails productId(Long productId) {
        this.setProductId(productId);
        return this;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Orderdetails)) {
            return false;
        }
        return id != null && id.equals(((Orderdetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Orderdetails{" +
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
