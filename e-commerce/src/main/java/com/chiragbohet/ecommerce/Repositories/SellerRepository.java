package com.chiragbohet.ecommerce.Repositories;

import com.chiragbohet.ecommerce.Entities.UserRelated.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    Seller findByEmail(String email);
    Seller findByCompanyNameIgnoreCase(String companyName);
    Seller findByGst(String gst);
}
