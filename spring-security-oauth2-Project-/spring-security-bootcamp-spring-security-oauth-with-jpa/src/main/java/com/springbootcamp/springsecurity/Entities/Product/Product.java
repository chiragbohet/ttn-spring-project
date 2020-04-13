package com.springbootcamp.springsecurity.Entities.Product;


import com.springbootcamp.springsecurity.Entities.Users.Seller;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
@Getter
@Setter
@Entity
//@Table(name = "PRODUCT")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String name;

  private String description;

  private boolean isCancelable;

  private boolean isReturnable;

  private String brand;

  private boolean isActive;


  @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,fetch = FetchType.EAGER)    // to map product to product variant
 @Fetch(value=FetchMode.SUBSELECT)
  private List<ProductVariation> productVariationList;


  @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)

  @JoinColumn(name = "CATEGORY_ID")   //to map product to category
  private Category category;

  @ManyToOne
  @JoinColumn(name = "Seller_id")   //to map product to category
  private Seller seller;



  public Product() {
  }



  //@Column(name = "SELLER_ID")
  //private String sellerId;






}
