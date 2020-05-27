package com.chiragbohet.ecommerce.entities;

import com.chiragbohet.ecommerce.utilities.Auditable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Entity
@ToString
@Table(name = "PRODUCT")
public class Product extends Auditable {

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

    @Setter(AccessLevel.NONE)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SELLER_ID")
    Seller seller;

    @Column(name = "IS_DELETED")
    Boolean isDeleted;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<ProductVariation> productVariationSet;

    public Product() {
        this.isCancellable = false;
        this.isReturnable = false;
        this.isActive = false;
    }

    public void addProductVariation(ProductVariation... productVariations) {
        if(productVariations != null) {
            if(productVariationSet == null)
                productVariationSet = new HashSet<>();

            for (ProductVariation productVariation : productVariations) {
                productVariation.setProduct(this);
                productVariationSet.add(productVariation);
            }
        }
    }

    public void setSeller(Seller seller) {
        if (seller != null) {
            if (this.seller == seller)   // prevent infinite loop
                return;

            this.seller = seller;
            seller.addProduct(this);
        }
    }

    public void setCategory(Category category) {
        if (category != null) {
            if (category == this.category)
                return;

            this.category = category;
            category.addProduct(this);

        }
    }

}