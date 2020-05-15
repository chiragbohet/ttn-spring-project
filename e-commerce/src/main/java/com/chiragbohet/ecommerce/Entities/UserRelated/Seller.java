package com.chiragbohet.ecommerce.Entities.UserRelated;

import com.chiragbohet.ecommerce.Entities.ProductRelated.Product;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "SELLER")
public class Seller extends User {

    //TODO :  add validation using REGEX
    @Column(name = "GST")
    private String gst;

    @Column(name = "COMPANY_CONTACT")
    private String companyContact;

    @Column(name = "COMPANY_NAME")
    private String companyName;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "SELLER_PRODUCT", joinColumns = @JoinColumn(name = "SELLER_ID"), inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID"))
    Set<Product> productSet;


    public Seller(){

        this.setActive(false);  // will be activated by Admin
        this.setDeleted(false);

        // spring security related fields
        this.setAccountNonExpired(true);
        this.setAccountNonLocked(true);
        this.setCredentialsNonExpired(true);
        this.setEnabled(true);
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Address address;

    public void addAddress(Address address)
    {
        if(address != null) {
            if (this.address == null) {
                this.address = address;
                address.setUser(this);
            }

        }
    }

    public void addProduct(Product... products) {
        if (products != null) {
            if (productSet == null)
                productSet = new HashSet<>();

            for (Product product : products) {
                if (!productSet.contains(product)) {
                    productSet.add(product);
                    product.setSeller(this);

                }
            }
        }
    }

}
