package com.chiragbohet.ecommerce.repositories;

import com.chiragbohet.ecommerce.entities.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    Seller findByEmail(String email);

    Seller findByCompanyNameIgnoreCase(String companyName);

    Seller findByGst(String gst);

    @Query(value = "FROM SELLER WHERE isActive = true", nativeQuery = true)
    List<Seller> getAllActiveSellers();
}
