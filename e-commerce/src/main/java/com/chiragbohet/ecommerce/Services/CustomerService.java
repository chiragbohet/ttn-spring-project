package com.chiragbohet.ecommerce.Services;

import com.chiragbohet.ecommerce.Dtos.CustomerRegistrationDto;
import com.chiragbohet.ecommerce.Entities.UserRelated.Customer;
import com.chiragbohet.ecommerce.Exceptions.UserAlreadyExistsException;
import com.chiragbohet.ecommerce.Repositories.ConfirmationTokenRepository;
import com.chiragbohet.ecommerce.Repositories.CustomerRepository;
import com.chiragbohet.ecommerce.Utilities.ConfirmationToken;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    EmailSenderService emailSenderService;

    /***
     * Sets required fields for a customer to function like isActive, isEnabled etc.
     * @param customer
     * @return customer with required fields set
     */
    Customer initializeNewCustomer(Customer customer){

        customer.setActive(false);  // will be activated via email
        customer.setDeleted(false);
        customer.setAccountNonExpired(true);
        customer.setAccountNonLocked(true);
        customer.setCredentialsNonExpired(true);
        customer.setEnabled(true);

        return customer;
    }

    private ModelMapper modelMapper = new ModelMapper();

    public ResponseEntity createNewCustomer(CustomerRegistrationDto customerRegistrationDto) throws UserAlreadyExistsException {

        Customer customer = modelMapper.map(customerRegistrationDto, Customer.class);   // converting DTO to POJO

        if(customerRepository.findByEmail(customer.getEmail()) != null) // User already exists with given email
            throw new UserAlreadyExistsException("User already exists with email : " + customer.getEmail());
        else
            {
                customerRepository.save(initializeNewCustomer(customer));   // persisting the Customer

                ConfirmationToken confirmationToken = new ConfirmationToken(customer);
                confirmationTokenRepository.save(confirmationToken);

                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setTo(customer.getEmail());
                mailMessage.setSubject("Complete Registration!");
                mailMessage.setFrom("chiragtest9654@gmail.com");
                mailMessage.setText("To confirm your account, please click here : "
                        +"http://localhost:8080/register/confirm?token="+confirmationToken.getConfirmationToken());

                emailSenderService.sendEmail(mailMessage);

             }


        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public String validateRegistrationToken(String userToken){

        ConfirmationToken foundToken = confirmationTokenRepository.findByConfirmationToken(userToken);

        if(foundToken != null)
        {
            Customer customer = customerRepository.findByEmail(foundToken.getUser().getEmail());
            customer.setEnabled(true);
            customerRepository.save(customer);
            return "account verified";
        }

        return "something went wrong!";
    }

}
