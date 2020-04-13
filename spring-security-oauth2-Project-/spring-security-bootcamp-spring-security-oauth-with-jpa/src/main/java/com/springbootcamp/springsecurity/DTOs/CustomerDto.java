package com.springbootcamp.springsecurity.DTOs;

import com.springbootcamp.springsecurity.Entities.Users.Customer;
import com.springbootcamp.springsecurity.Exceptions.AccountDoesNotExist;
import com.springbootcamp.springsecurity.Repositories.CustomerRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@Component
public class CustomerDto extends com.springbootcamp.springsecurity.dto.UserDto {

    @Autowired
    CustomerRepository customerRepository;
    private int contact;

    public CustomerDto(int id, String email, String firstName, String lastName, String contact) {

    }

    public CustomerDto(){

    }

    public CustomerDto getCustomerById(int id) throws AccountDoesNotExist {
        Optional<Customer> optionalCustomer=customerRepository.findById(id);
        if(!optionalCustomer.isPresent()) throw new AccountDoesNotExist();
        else {
            Customer customer=optionalCustomer.get();
            CustomerDto customerDto=new CustomerDto();
            customerDto.setId(customer.getId());
            customerDto.setEmail(customer.getEmail());
            customerDto.setFirstName(customer.getFirstName());
            customerDto.setLastName(customer.getLastName());
            return customerDto;
        }

    }
//
//    public List<CustomerDto> getAllCustomers(){
//        Iterable<Customer>customerIterable=new ArrayList<>();
//        List<CustomerDto> customerDtoList=new ArrayList<>();
//
//        customerIterable=customerRepository.findAll();
//        customerIterable.getClass().cast(customerDtoList);
//        System.out.println();
//        return customerDtoList;
//    }
////

    public List<CustomerDto> getAllCustomers(){
        Iterable<Customer> customersList= customerRepository.findAll();
        List<CustomerDto> customerDtoList = new ArrayList<>();
        customersList.forEach(customer -> customerDtoList
                .add(new CustomerDto(customer.getId(),
                customer.getEmail(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getContact())));
        return customerDtoList;
    }
}
