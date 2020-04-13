package com.springbootcamp.springsecurity.Services;

import com.springbootcamp.springsecurity.DTOs.SellerDto;
import com.springbootcamp.springsecurity.Repositories.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class SellerService {
    @Autowired
    SellerRepository sellerRepository;
//    public  SellerDto getSellerByid(int id){
//        SellerDto sellerDto=new SellerDto();
//        sellerDto=(SellerDto) sellerRepository.findById(id);
//        return  sellerDto;
//    }
}
