package com.chiragbohet.ecommerce.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "ORDER_TABLE")
public class Order {

    @Column(name = "ID")
    @Id
    Long id;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_USER_ID")
    Customer customer;

    @Column(name = "AMOUNT_PAID")
    BigDecimal amountPaid;

    Date dateCreated;

    String paymentMethod;

    @Embedded
    OrderAddress orderAddress;

}
