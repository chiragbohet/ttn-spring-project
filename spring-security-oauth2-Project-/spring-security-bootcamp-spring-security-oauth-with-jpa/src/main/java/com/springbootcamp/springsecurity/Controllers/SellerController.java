//package com.springbootcamp.springsecurity.Controllers;
//
//import com.springbootcamp.springsecurity.DTOs.CustomerDto;
//import com.springbootcamp.springsecurity.DTOs.SellerDto;
//import com.springbootcamp.springsecurity.Repositories.CustomerRepository;
//import com.springbootcamp.springsecurity.Repositories.SellerRepository;
//import com.springbootcamp.springsecurity.Services.CustomerService;
//import com.springbootcamp.springsecurity.Services.SellerService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/seller")
//public class SellerController {
//
//    @Autowired
//    SellerRepository sellerRepository;
//    @Autowired
//    SellerService sellerService;
////
////    @GetMapping("/{id}")
////    public SellerDto getSellerByid(@PathVariable Integer id) {
////        System.out.println("Seller registered. Details are :");
////        return SellerService.getSellerByid(id);
////    }
//
//}