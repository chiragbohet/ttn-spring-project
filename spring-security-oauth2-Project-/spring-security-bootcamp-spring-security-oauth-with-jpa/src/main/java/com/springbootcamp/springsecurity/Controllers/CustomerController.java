package com.springbootcamp.springsecurity.Controllers;

import com.springbootcamp.springsecurity.DTOs.CustomerDto;
import com.springbootcamp.springsecurity.Repositories.CustomerRepository;
import com.springbootcamp.springsecurity.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;



    @Autowired
    CustomerService customerService;

    @GetMapping("/{id}")
    public CustomerDto getCustomer(@PathVariable Integer id) {
        return customerService.getCustomerByid(id);
    }


    @GetMapping("/")
    public List<CustomerDto> getAllCustomer(){
        return  customerService.getAllCustomers();
    }
//
//    @PostMapping("/")
//    public CustomerDto registerCustomer(@RequestBody CustomerCO customerCO, WebRequest webRequest){
//        return customerService.registerCustomer(customerCO);
//    }
//
//    @PutMapping("/{id}")
//    public CustomerDto updateCustomer(@PathVariable Integer id,@RequestBody CustomerCO customerCO , WebRequest webRequest){
//        return  customerService.updateCustomer(id,customerCO);
//    }
//
//    @DeleteMapping("/{id}")
//    public Map<String,Boolean> deleteCustomer(@PathVariable Integer id){
//        return customerService.deleteCustomer(id);
//    }


}