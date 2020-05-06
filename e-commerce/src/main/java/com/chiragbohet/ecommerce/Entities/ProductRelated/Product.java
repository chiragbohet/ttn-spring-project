package com.chiragbohet.ecommerce.Entities.ProductRelated;

import com.chiragbohet.ecommerce.Entities.CategoryRelated.Category;
import com.chiragbohet.ecommerce.Entities.UserRelated.Seller;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@Table(name = "PRODUCT")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    String description;

    @Column(name = "BRAND")
    String brand;

    @Column(name = "IS_CANCELLABLE")
    Boolean isCancellable;

    @Column(name = "IS_RETURNABLE")
    Boolean isReturnable;

    @Column(name = "IS_ACTIVE")
    Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    Category category;

    @ManyToOne
    @JoinColumn(name = "SELLER_ID")
    Seller seller;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<ProductVariation> productVariationSet;

    public Product()
    {
        this.isCancellable = false;
        this.isReturnable = false;
        this.isActive = false;
    }


}