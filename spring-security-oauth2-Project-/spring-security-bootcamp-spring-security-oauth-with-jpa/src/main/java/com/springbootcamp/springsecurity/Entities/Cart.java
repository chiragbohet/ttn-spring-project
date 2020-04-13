package com.springbootcamp.springsecurity.Entities;

import com.springbootcamp.springsecurity.Entities.Product.ProductVariation;
import com.springbootcamp.springsecurity.Entities.Users.Customer;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
//@Table(name = "CART")
public class Cart {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;
    private int quantity;
    private boolean isWishListItem;

    @OneToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "Product_variation_id")
    private ProductVariation productVariation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductVariation getProductVariation() {
        return productVariation;
    }

    public void setProductVariation(ProductVariation productVariation) {
        this.productVariation = productVariation;
    }

    //    @ManyToOne
//    @JoinColumn(name = "ProductVariation_id")   //to map product to category
//    private Set<ProductVariation> productVariationSet;



    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
//
//    public Set<ProductVariation> getProductVariationSet() {
//        return productVariationSet;
//    }
//
//    public void setProductVariationSet(Set<ProductVariation> productVariationSet) {
//        this.productVariationSet = productVariationSet;
//    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isWishListItem() {
        return isWishListItem;
    }

    public void setWishListItem(boolean wishListItem) {
        isWishListItem = wishListItem;
    }

//    IS_WISHLIST_ITEM
//            PRODUCT_VARIATION_ID

}
//    @OneToOne(cascade = CascadeType.ALL)//cascade = CascadeType.ALL
//   @JoinColumn(name = "USER_ID")
//    private User user;
