package com.chiragbohet.ecommerce.services;

import com.chiragbohet.ecommerce.co.CustomerRegistrationCo;
import com.chiragbohet.ecommerce.co.EmailCo;
import com.chiragbohet.ecommerce.dtos.AddressViewDto;
import com.chiragbohet.ecommerce.dtos.AdminApi.CustomerAdminApiDto;
import com.chiragbohet.ecommerce.dtos.CustomerApi.CustomerDetailsDto;
import com.chiragbohet.ecommerce.dtos.CustomerApi.CustomerProfileUpdateDto;
import com.chiragbohet.ecommerce.dtos.NewAddressDto;
import com.chiragbohet.ecommerce.dtos.PasswordUpdateDto;
import com.chiragbohet.ecommerce.entities.Address;
import com.chiragbohet.ecommerce.entities.Customer;
import com.chiragbohet.ecommerce.entities.User;
import com.chiragbohet.ecommerce.exceptions.ConfirmPasswordNotMatchedException;
import com.chiragbohet.ecommerce.exceptions.ResourceNotFoundException;
import com.chiragbohet.ecommerce.exceptions.UserAlreadyExistsException;
import com.chiragbohet.ecommerce.exceptions.UserNotFoundException;
import com.chiragbohet.ecommerce.repositories.ConfirmationTokenRepository;
import com.chiragbohet.ecommerce.repositories.CustomerRepository;
import com.chiragbohet.ecommerce.repositories.RoleRepository;
import com.chiragbohet.ecommerce.repositories.UserRepository;
import com.chiragbohet.ecommerce.utilities.ConfirmationToken;
import com.chiragbohet.ecommerce.utilities.EmailSenderService;
import com.chiragbohet.ecommerce.utilities.ObjectMapperUtils;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ModelMapper modelMapper;

    public ResponseEntity registerNewCustomer(CustomerRegistrationCo co) {

        // checking if password and confirm password match
        if (!co.getPassword().equals(co.getConfirmPassword()))
            throw new ConfirmPasswordNotMatchedException("Password and Confirm password don't match!");

        // checking if a Customer already exists with the given email
        if (customerRepository.findByEmail(co.getEmail()) != null) // User already exists with given email
            throw new UserAlreadyExistsException("User already exists with email : " + co.getEmail());

        Customer newCustomer = modelMapper.map(co, Customer.class);   // Converting co to Actual Object

        newCustomer.addRoles(roleRepository.findByAuthority("ROLE_CUSTOMER"));

        String encyptedPassword = passwordEncoder.encode(newCustomer.getPassword());

        newCustomer.setPassword(encyptedPassword);

        customerRepository.save(newCustomer);   // persisting the Customer

        createCustomerActivationTokenAndSendEmail(newCustomer);

        return new ResponseEntity<String>("Please check your email for further instructions.", null, HttpStatus.CREATED);


    }

    public void createCustomerActivationTokenAndSendEmail(Customer customer) {

        ConfirmationToken confirmationToken = new ConfirmationToken(customer);

        confirmationTokenRepository.save(confirmationToken);

        emailSenderService.sendEmail(emailSenderService.getCustomerAwaitingActivationMail(customer.getEmail(), confirmationToken.getConfirmationToken()));
    }

    public ResponseEntity resendActivationLink(EmailCo emailCo) {

        Customer customer = customerRepository.findByEmail(emailCo.getEmail());

        if (customer == null)
            throw new UserNotFoundException("No user found with associated with the given email!. Kindly register yourself first.");
        if (customer.isActive())
            return new ResponseEntity<String>("Your account is already active!", null, HttpStatus.CONFLICT);

        //delete the old token if already existing
        deleteOldConfirmationTokenIfExists(customer);

        // create new token and send
        createCustomerActivationTokenAndSendEmail(customer);

        return new ResponseEntity<String>("Please check your email for new registration link!", null, HttpStatus.OK);

    }

    public void deleteOldConfirmationTokenIfExists(User user) {
        ConfirmationToken tokenToDelete = confirmationTokenRepository.findByUser(user);

        if (tokenToDelete != null)
            confirmationTokenRepository.delete(tokenToDelete);

    }

    public ResponseEntity getCustomerAddress(String email) {

        Customer customer = customerRepository.findByEmail(email);

        Set<Address> addresses = customer.getAddressSet();

        Set<AddressViewDto> addressViewDtoSet = ObjectMapperUtils.mapAllSet(addresses, AddressViewDto.class);

        return new ResponseEntity<Set<AddressViewDto>>(addressViewDtoSet, null, HttpStatus.OK);
    }

    public ResponseEntity getCustomerByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email);

        CustomerDetailsDto customerDetailsDto = modelMapper.map(customer, CustomerDetailsDto.class);

        return new ResponseEntity<CustomerDetailsDto>(customerDetailsDto, null, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity activateCustomer(Long id) {


        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isPresent()) {
            if (!customer.get().isActive()) {
                //Delete customer activation token, if any
                confirmationTokenRepository.deleteByUserId(id);

                customer.get().setActive(true);
                customerRepository.save(customer.get());
                emailSenderService.sendEmail(emailSenderService.getCustomerRegistrationCompletedMail(customer.get().getEmail()));
            }
            return new ResponseEntity<String>("customer account with id : " + id + " activated.", null, HttpStatus.OK);
        } else
            throw new UserNotFoundException("No user found with id : " + id);


    }

    public ResponseEntity deactivateCustomer(Long id) {

        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isPresent()) {
            if (customer.get().isActive()) {
                customer.get().setActive(false);
                customerRepository.save(customer.get());
                emailSenderService.sendEmail(emailSenderService.getCustomerDeactivationMail(customer.get().getEmail()));
            }
            return new ResponseEntity<String>("customer account with id : " + id + " deactivated.", null, HttpStatus.OK);
        } else
            throw new UserNotFoundException("No user found with id : " + id);

    }


    public ResponseEntity getAllCustomers(Optional<Integer> page, Optional<Integer> size, Optional<String> sortProperty, Optional<String> sortDirection) {

        Sort.Direction sortingDirection = sortDirection.get().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        Page<Customer> customers = customerRepository.findAll(PageRequest.of(page.get(), size.get(), sortingDirection, sortProperty.get()));

        List<CustomerAdminApiDto> customersInDTO = ObjectMapperUtils.mapAllPage(customers, CustomerAdminApiDto.class);

        //Adding X-TOTAL-COUNT header representing total count of Employees
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.put("X-TOTAL-COUNT", Arrays.asList(String.valueOf(customerRepository.count())));

        return new ResponseEntity<>(customersInDTO, headers, HttpStatus.OK);

    }


    public ResponseEntity validateRegistrationToken(String userToken) {

        ConfirmationToken foundToken = confirmationTokenRepository.findByConfirmationToken(userToken);


        if (foundToken != null) {
            if (foundToken.isExpired()) {
                createCustomerActivationTokenAndSendEmail((Customer) foundToken.getUser());
                return new ResponseEntity<String>("This token is expired, please check your email for a new token!", null, HttpStatus.OK);
            } else {
                Customer customer = customerRepository.findByEmail(foundToken.getUser().getEmail());
                customer.setActive(true);
                customer.setEnabled(true);
                customerRepository.save(customer);
                confirmationTokenRepository.delete(foundToken);
                return new ResponseEntity<String>("Account Verified", null, HttpStatus.OK);
            }


        }

        return new ResponseEntity<String>("Invalid token!", null, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity updateCustomerDetails(String email, CustomerProfileUpdateDto customerProfileUpdateDto) {

        Customer customerToUpdate = customerRepository.findByEmail(email);

        if (customerProfileUpdateDto.getFirstName() != null)
            customerToUpdate.setFirstName(customerProfileUpdateDto.getFirstName());
        if (customerProfileUpdateDto.getLastName() != null)
            customerToUpdate.setLastName(customerProfileUpdateDto.getLastName());
        if (customerProfileUpdateDto.getMiddleName() != null)
            customerToUpdate.setMiddleName(customerProfileUpdateDto.getMiddleName());
        if (customerProfileUpdateDto.getContact() != null)
            customerToUpdate.setContact(customerProfileUpdateDto.getContact());

        customerRepository.save(customerToUpdate);

        return new ResponseEntity<String>("Profile Updated!", null, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity updateCustomerPassword(String email, PasswordUpdateDto passwordUpdateDto) {

        if (!passwordUpdateDto.getPassword().equals(passwordUpdateDto.getConfirmPassword()))
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

        Set<Address> addressSet = customer.getAddressSet();

        for (Address address : addressSet) {
            if (address.getId() == id) {
                customer.deleteAddress(address);
                customerRepository.save(customer);
                return new ResponseEntity<String>("Deleted Address with id " + id, null, HttpStatus.OK);
            }
        }

        throw new ResourceNotFoundException("No address found with id : " + id + ", associated with your account");

    }

    public ResponseEntity updateCustomerAddress(String email, Long id, NewAddressDto newAddressDto) {

        Customer customer = customerRepository.findByEmail(email);

        Set<Address> addressSet = customer.getAddressSet();

        for (Address address : addressSet) {
            if (address.getId() == id) {
                if (newAddressDto.getCity() != null)
                    address.setCity(newAddressDto.getCity());
                if (newAddressDto.getState() != null)
                    address.setState(newAddressDto.getState());
                if (newAddressDto.getCountry() != null)
                    address.setCountry(newAddressDto.getCountry());
                if (newAddressDto.getAddressLine() != null)
                    address.setAddressLine(newAddressDto.getAddressLine());
                if (newAddressDto.getZipCode() != null)
                    address.setZipCode(newAddressDto.getZipCode());
                if (newAddressDto.getLabel() != null)
                    address.setLabel(newAddressDto.getLabel());

                customerRepository.save(customer);  // changes will be cascaded to address

                return new ResponseEntity<String>("Updated Address with id " + id, null, HttpStatus.OK);
            }
        }

        throw new ResourceNotFoundException("No address found with id : " + id + ", associated with your account");


    }


    public ResponseEntity forgotPassword(EmailCo emailCo) {

        User user = userRepository.findByEmail(emailCo.getEmail());

        if (user == null)
            throw new UserNotFoundException("No user found with associated with the given email!. Kindly register yourself first.");
        else if (!user.isActive())
            return new ResponseEntity<String>("Your account is not active please activate it first. For more help contact admin.", null, HttpStatus.FORBIDDEN);
        else {
            ConfirmationToken confirmationToken = new ConfirmationToken(user);
            confirmationTokenRepository.save(confirmationToken);

            emailSenderService.sendEmail(emailSenderService.getUserForgotPasswordEmail(user.getEmail(), confirmationToken.getConfirmationToken()));

            return new ResponseEntity<String>("Please check your email for further instructions.", null, HttpStatus.OK);

        }

    }

    public ResponseEntity validateForgotPasswordRequestAndResetPassword(String token, PasswordUpdateDto passwordUpdateDto) {

        if (!passwordUpdateDto.getPassword().equals(passwordUpdateDto.getConfirmPassword()))
            throw new ConfirmPasswordNotMatchedException("Password and confirm password do not match");

        ConfirmationToken confirmationToken = confirmationTokenRepository.findByConfirmationToken(token);

        if (confirmationToken.isExpired()) {
            return new ResponseEntity<String>("Password reset link is expired, please generate a new one!", null, HttpStatus.UNAUTHORIZED);
        } else {
            User user = confirmationToken.getUser();

            user.setPassword(passwordEncoder.encode(passwordUpdateDto.getPassword()));

            userRepository.save(user);

            return new ResponseEntity<String>("Password updated!", null, HttpStatus.OK);
        }


    }

}
