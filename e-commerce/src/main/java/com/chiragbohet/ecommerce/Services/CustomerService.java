package com.chiragbohet.ecommerce.Services;

import com.chiragbohet.ecommerce.Dtos.AdminApi.CustomerAdminApiDto;
import com.chiragbohet.ecommerce.Dtos.AddressViewDto;
import com.chiragbohet.ecommerce.Dtos.CustomerApi.CustomerDetailsDto;
import com.chiragbohet.ecommerce.Dtos.CustomerApi.CustomerProfileUpdateDto;
import com.chiragbohet.ecommerce.Dtos.NewAddressDto;
import com.chiragbohet.ecommerce.Dtos.PasswordUpdateDto;
import com.chiragbohet.ecommerce.Dtos.RegistrationApi.CustomerRegistrationDto;
import com.chiragbohet.ecommerce.Entities.UserRelated.Address;
import com.chiragbohet.ecommerce.Entities.UserRelated.Customer;
import com.chiragbohet.ecommerce.Exceptions.ConfirmPasswordNotMatchedException;
import com.chiragbohet.ecommerce.Exceptions.ResourceNotFoundException;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.transaction.Transactional;
import java.util.*;

@Component
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    PasswordEncoder passwordEncoder;

    private ModelMapper modelMapper = new ModelMapper();

    public ResponseEntity getCustomerAddress(String email)
    {
        Customer customer = customerRepository.findByEmail(email);

        Set<Address> addresses = customer.getAddressSet();

        Set<AddressViewDto> addressViewDtoSet = ObjectMapperUtils.mapAllSet(addresses, AddressViewDto.class);

        return new ResponseEntity<Set<AddressViewDto>>(addressViewDtoSet,null,HttpStatus.OK);
    }

    public ResponseEntity getCustomerByEmail(String email)
    {
       Customer customer = customerRepository.findByEmail(email);

        CustomerDetailsDto customerDetailsDto = modelMapper.map(customer, CustomerDetailsDto.class);

        return new ResponseEntity<CustomerDetailsDto>(customerDetailsDto,null,HttpStatus.OK);
    }

    public ResponseEntity activateCustomer(Long id){

        Optional<Customer> customer = customerRepository.findById(id);

        if(customer.isPresent())
        {
            if(!customer.get().isActive())
            {
                customer.get().setActive(true);
                customerRepository.save(customer.get());
                emailSenderService.sendEmail(emailSenderService.getCustomerRegistrationCompletedMail(customer.get().getEmail()));
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

        List<CustomerAdminApiDto> customersInDTO = ObjectMapperUtils.mapAllPage(customers, CustomerAdminApiDto.class);

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

                emailSenderService.sendEmail(emailSenderService.getCustomerAwaitingActivationMail(customer.getEmail(), confirmationToken.getConfirmationToken()));
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

    public ResponseEntity updateCustomerDetails(String email, CustomerProfileUpdateDto customerProfileUpdateDto) {

        Customer customerToUpdate = customerRepository.findByEmail(email);

        if(customerProfileUpdateDto.getFirstName() != null)
            customerToUpdate.setFirstName(customerProfileUpdateDto.getFirstName());
        if(customerProfileUpdateDto.getLastName() != null)
            customerToUpdate.setLastName(customerProfileUpdateDto.getLastName());
        if(customerProfileUpdateDto.getMiddleName() != null)
            customerToUpdate.setMiddleName(customerProfileUpdateDto.getMiddleName());
        if(customerProfileUpdateDto.getContact() != null)
            customerToUpdate.setContact(customerProfileUpdateDto.getContact());

        customerRepository.save(customerToUpdate);

        return new ResponseEntity<String>("Profile Updated!", null, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity updateCustomerPassword(String email, PasswordUpdateDto passwordUpdateDto) {

        if(!passwordUpdateDto.getPassword().equals(passwordUpdateDto.getConfirmPassword()))
            throw new ConfirmPasswordNotMatchedException("Password and Confirm password don't match, please try again!");

        Customer customer = customerRepository.findByEmail(email);

        customer.setPassword(passwordEncoder.encode(passwordUpdateDto.getPassword()));

        customerRepository.save(customer);

        emailSenderService.sendEmail(emailSenderService.getPasswordUpdatedMail(email));

        return new ResponseEntity<String>("Password updated!", null, HttpStatus.OK);
    }

    public ResponseEntity addNewCustomerAddress(String email, NewAddressDto newAddressDto) {

        Customer customer = customerRepository.findByEmail(email);

        Address address = modelMapper.map(newAddressDto, Address.class);

        customer.addAddress(address);

        customerRepository.save(customer);

        return new ResponseEntity<String>("Added new address!", null, HttpStatus.OK);
    }

    public ResponseEntity deleteCustomerAddress(String email, Long id) {

        Customer customer = customerRepository.findByEmail(email);

        Set<Address> addressSet =  customer.getAddressSet();

        for(Address address : addressSet)
        {
            if(address.getId() == id)
            {
                customer.deleteAddress(address);
                customerRepository.save(customer);
                return new ResponseEntity<String>("Deleted Address with id " + id, null, HttpStatus.OK);
            }
        }

        throw new ResourceNotFoundException("No address found with id : " + id + ", associated with your account");

    }

    public ResponseEntity updateCustomerAddress(String email, Long id, NewAddressDto newAddressDto) {

        Customer customer = customerRepository.findByEmail(email);

        Set<Address> addressSet =  customer.getAddressSet();

        for(Address address : addressSet)
        {
            if(address.getId() == id)
            {
                if(newAddressDto.getCity() != null)
                    address.setCity(newAddressDto.getCity());
                if(newAddressDto.getState() != null)
                    address.setState(newAddressDto.getState());
                if(newAddressDto.getCountry() != null)
                    address.setCountry(newAddressDto.getCountry());
                if(newAddressDto.getAddressLine() != null)
                    address.setAddressLine(newAddressDto.getAddressLine());
                if(newAddressDto.getZipCode() != null)
                    address.setZipCode(newAddressDto.getZipCode());
                if(newAddressDto.getLabel() != null)
                    address.setLabel(newAddressDto.getLabel());

                customerRepository.save(customer);  // changes will be cascaded to address

                return new ResponseEntity<String>("Updated Address with id " + id, null, HttpStatus.OK);
            }
        }

        throw new ResourceNotFoundException("No address found with id : " + id + ", associated with your account");


    }
}
