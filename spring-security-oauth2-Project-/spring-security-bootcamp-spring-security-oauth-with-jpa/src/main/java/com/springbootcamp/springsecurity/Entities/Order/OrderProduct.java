package com.springbootcamp.springsecurity.Entities.Order;

import com.springbootcamp.springsecurity.Entities.Product.ProductVariation;

import javax.persistence.*;

@Entity
//@Table(name = "ORDER_PRODUCT")
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_PRODUCT_ID")
    private int id;
    @Column(name = "QUANTITY")
    private int quantity;
    @Column(name = "METADATA")
    private String metadata;

    private float price;


    @ManyToOne
    @JoinColumn(name="ORDER_ID")
    private OrderClass orderClass;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_VAR_ID")
    private ProductVariation productVariation;

    @OneToOne(mappedBy = "orderProduct")
    private OrderStatus orderStatus;

    public OrderProduct() {
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public OrderClass getOrderClass() {
        return orderClass;
    }

    public void setOrderClass(OrderClass orderClass) {
        this.orderClass = orderClass;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public ProductVariation getProductVariation() {
        return productVariation;
    }

    public void setProductVariation(ProductVariation productVariation) {
        this.productVariation = productVariation;
    }
    //    ID.
//            ORDER_ID.
//    QUANTITY.
//            PRICE.
//    PRODUCT_VARIATION_ID
//            PRODUCT_VARIATION_METADATA
}
