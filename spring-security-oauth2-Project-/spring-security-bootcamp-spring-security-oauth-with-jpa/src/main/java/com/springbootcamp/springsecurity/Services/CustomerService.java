package com.springbootcamp.springsecurity.Services;

import com.springbootcamp.springsecurity.DTOs.CustomerDto;
import com.springbootcamp.springsecurity.Exceptions.AccountDoesNotExist;
import com.springbootcamp.springsecurity.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerService {


    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerDto customerDto;

       public CustomerDto getCustomerByid(int id) {
           CustomerDto customerDTO = new CustomerDto();
           try {
               customerDTO=customerDto.getCustomerById(id);
           } catch (AccountDoesNotExist accountDoesNotExist) {
               accountDoesNotExist.printStackTrace();
           }
           return customerDTO;
       }


       public List<CustomerDto> getAllCustomers() {
           List<CustomerDto> customerDtoList = new ArrayList<>();
           customerDtoList=customerDto.getAllCustomers();
           return customerDtoList;
       }
}
