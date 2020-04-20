package com.chiragbohet.ecommerce.Entities.OrderRelated;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "ORDER_PRODUCT")
public class OrderProduct {

    @Id
    @Column(name = "ID")
    Long id;

    @Column(name = "QUANTITY")
    Integer quantity;

    @Column(name = "PRICE")
    BigDecimal price;



}
