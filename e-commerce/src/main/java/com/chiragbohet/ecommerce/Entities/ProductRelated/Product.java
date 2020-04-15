package com.chiragbohet.ecommerce.Entities.ProductRelated;

import com.chiragbohet.ecommerce.Entities.UserRelated.Seller;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "PRODUCT")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    String description;

    @Column(name = "BRAND")
    String brand;

    @Column(name = "IS_CANCELLABLE")
    boolean isCancellable;

    @Column(name = "IS_RETURNABLE")
    boolean isReturnable;

    @Column(name = "IS_ACTIVE")
    boolean isActive;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "SELLER_USER_ID")
    List<Seller> sellerList;


    //TODO : Add category field

}