package com.chiragbohet.ecommerce.Services;

import com.chiragbohet.ecommerce.Dtos.CustomerAdminApiDto;
import com.chiragbohet.ecommerce.Dtos.CustomerRegistrationDto;
import com.chiragbohet.ecommerce.Entities.UserRelated.Customer;
import com.chiragbohet.ecommerce.Exceptions.UserAlreadyExistsException;
import com.chiragbohet.ecommerce.Exceptions.UserNotFoundException;
import com.chiragbohet.ecommerce.Repositories.ConfirmationTokenRepository;
import com.chiragbohet.ecommerce.Repositories.CustomerRepository;
import com.chiragbohet.ecommerce.Utilities.ConfirmationToken;
import com.chiragbohet.ecommerce.Utilities.EmailSenderService;
import com.chiragbohet.ecommerce.Utilities.ObjectMapperUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    EmailSenderService emailSenderService;

    private ModelMapper modelMapper = new ModelMapper();

    public ResponseEntity activateCustomer(Long id){

        Optional<Customer> customer = customerRepository.findById(id);

        if(customer.isPresent())
        {
            if(!customer.get().isActive())
            {
                customer.get().setActive(true);
                customerRepository.save(customer.get());
                emailSenderService.sendEmail(emailSenderService.getCustomerActivationMail(customer.get().getEmail()));
            }
                return new ResponseEntity<String>("customer account with id : " + id + " activated.",null,HttpStatus.OK);
        }
        else
            throw new UserNotFoundException("No user found with id : " + id);


    }

    public ResponseEntity deactivateCustomer(Long id){

        Optional<Customer> customer = customerRepository.findById(id);

        if(customer.isPresent())
        {
            if(customer.get().isActive())
            {
                customer.get().setActive(false);
                customerRepository.save(customer.get());
                emailSenderService.sendEmail(emailSenderService.getCustomerDeactivationMail(customer.get().getEmail()));
            }
            return new ResponseEntity<String>("customer account with id : " + id + " deactivated.",null,HttpStatus.OK);
        }
        else
            throw new UserNotFoundException("No user found with id : " + id);

    }


    public ResponseEntity getAllCustomers(Optional<Integer> page, Optional<Integer> size, Optional<String> sortProperty, Optional<String> sortDirection)
    {

        Sort.Direction sortingDirection = sortDirection.get().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        Page<Customer> customers =  customerRepository.findAll(PageRequest.of(page.get() ,size.get(), sortingDirection, sortProperty.get()));

        List<CustomerAdminApiDto> customersInDTO = ObjectMapperUtils.mapAll(customers, CustomerAdminApiDto.class);

        //Adding X-TOTAL-COUNT header representing total count of Employees
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.put("X-TOTAL-COUNT", Arrays.asList(String.valueOf(customerRepository.count())));

        return new ResponseEntity<>(customersInDTO, headers, HttpStatus.OK);

    }




    public ResponseEntity registerNewCustomer(CustomerRegistrationDto customerRegistrationDto) throws UserAlreadyExistsException {

        Customer customer = modelMapper.map(customerRegistrationDto, Customer.class);   // converting DTO to POJO

        if(customerRepository.findByEmail(customer.getEmail()) != null) // User already exists with given email
            throw new UserAlreadyExistsException("User already exists with email : " + customer.getEmail());
        else
            {
                customerRepository.save(customer);   // persisting the Customer

                ConfirmationToken confirmationToken = new ConfirmationToken(customer);
                confirmationTokenRepository.save(confirmationToken);

                emailSenderService.sendEmail(emailSenderService.getCustomerActivationMail(customer.getEmail(), confirmationToken.getConfirmationToken()));
                return ResponseEntity.status(HttpStatus.CREATED).build();
             }



    }

    public String validateRegistrationToken(String userToken){

        ConfirmationToken foundToken = confirmationTokenRepository.findByConfirmationToken(userToken);

        // TODO : add token expiration mechanism
        if(foundToken != null)
        {
            Customer customer = customerRepository.findByEmail(foundToken.getUser().getEmail());
            customer.setEnabled(true);
            customerRepository.save(customer);
            confirmationTokenRepository.delete(foundToken);
            return "account verified";
        }

        return "Invalid token!";
    }

}
