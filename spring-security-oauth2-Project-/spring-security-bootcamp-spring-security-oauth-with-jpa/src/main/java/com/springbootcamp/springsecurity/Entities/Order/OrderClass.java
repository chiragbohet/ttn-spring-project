package com.springbootcamp.springsecurity.Entities.Order;

import com.springbootcamp.springsecurity.Entities.Addresscopy;
import com.springbootcamp.springsecurity.Entities.Users.Customer;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
//@Table(name="ORDER")
public class OrderClass {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      @Column(name = "ORDER_ID")
      private int id;
      @Column(name = "AMOUNT_PAID")
      private float amountPaid;
      @Temporal(value = TemporalType.DATE)
      @Column(name = "DATE_CREATED")
      private Date dateCreated;
      @Column(name = "PAYMENT_METHOD")
      private String paymentMethod;

      @Embedded
      Addresscopy addresscopy;


      @ManyToOne   //to map order to user
      @JoinColumn(name = "Customer_ID")
      private Customer customer;


      @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)    //order tp produuct
      @Fetch(FetchMode.SUBSELECT)
      private List<OrderProduct> orderProductList;

      public OrderClass() {
      }

      public Addresscopy getAddresscopy() {
            return addresscopy;
      }

      public void setAddresscopy(Addresscopy addresscopy) {
            this.addresscopy = addresscopy;
      }

      public List<OrderProduct> getOrderProductList() {
            return orderProductList;
      }

      public void setOrderProductList(List<OrderProduct> orderProductList) {
            this.orderProductList = orderProductList;
      }

      public int getId() {
            return id;
      }

      public void setId(int id) {
            this.id = id;
      }

      public float getAmountPaid() {
            return amountPaid;
      }

      public void setAmountPaid(float amountPaid) {
            this.amountPaid = amountPaid;
      }

      public Date getDateCreated() {
            return dateCreated;
      }

      public void setDateCreated(Date dateCreated) {
            this.dateCreated = dateCreated;
      }

      public String getPaymentMethod() {
            return paymentMethod;
      }

      public void setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
      }

      public Customer getCustomer() {
            return customer;
      }

      public void setCustomer(Customer customer) {
            this.customer = customer;
      }


}

//    ID.
//            CUSTOMER_USER_ID
//    AMOUNT_PAID
//            DATE_CREATED
//    PAYMENT_METHOD
//            CUSTOMER_ADDRESS_CITY
//    CUSTOMER_ADDRESS_STATE
//            CUSTOMER_ADDRESS_COUNTRY
//    CUSTOMER_ADDRESS_ADDRESS_LINE
//            CUSTOMER_ADDRESS_ZIP_CODE
//    CUSTOMER_ADDRESS_LABEL (Ex. office/home)