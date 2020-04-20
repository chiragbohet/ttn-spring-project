package com.chiragbohet.ecommerce.Services;

import com.chiragbohet.ecommerce.Dtos.AdminApi.SellerAdminApiDto;
import com.chiragbohet.ecommerce.Dtos.NewAddressDto;
import com.chiragbohet.ecommerce.Dtos.PasswordUpdateDto;
import com.chiragbohet.ecommerce.Dtos.RegistrationApi.EmailDto;
import com.chiragbohet.ecommerce.Dtos.RegistrationApi.SellerRegistrationDto;
import com.chiragbohet.ecommerce.Dtos.SellerApi.SellerDetailsDto;
import com.chiragbohet.ecommerce.Entities.UserRelated.Address;
import com.chiragbohet.ecommerce.Entities.UserRelated.Customer;
import com.chiragbohet.ecommerce.Entities.UserRelated.Seller;
import com.chiragbohet.ecommerce.Entities.UserRelated.User;
import com.chiragbohet.ecommerce.Exceptions.ConfirmPasswordNotMatchedException;
import com.chiragbohet.ecommerce.Exceptions.ResourceNotFoundException;
import com.chiragbohet.ecommerce.Exceptions.UserAlreadyExistsException;
import com.chiragbohet.ecommerce.Exceptions.UserNotFoundException;
import com.chiragbohet.ecommerce.Repositories.SellerRepository;
import com.chiragbohet.ecommerce.Repositories.UserRepository;
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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SellerService {

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    PasswordEncoder passwordEncoder;

    private ModelMapper modelMapper = new ModelMapper();


    public ResponseEntity activateSeller(Long id){

        Optional<Seller> seller = sellerRepository.findById(id);

        if(seller.isPresent())
        {
            if(!seller.get().isActive())
            {
                seller.get().setActive(true);
                sellerRepository.save(seller.get());
                emailSenderService.sendEmail(emailSenderService.getSellerActivationMail(seller.get().getEmail()));
            }
            return new ResponseEntity<String>("seller account with id : " + id + " activated.",null,HttpStatus.OK);
        }
        else
            throw new UserNotFoundException("No seller found with id : " + id);


    }

    public ResponseEntity deactivateSeller(Long id){

        Optional<Seller> seller = sellerRepository.findById(id);

        if(seller.isPresent())
        {
            if(seller.get().isActive())
            {
                seller.get().setActive(false);
                sellerRepository.save(seller.get());
                emailSenderService.sendEmail(emailSenderService.getSellerDeactivationMail(seller.get().getEmail()));
            }
            return new ResponseEntity<String>("seller account with id : " + id + " deactivated.",null,HttpStatus.OK);
        }
        else
            throw new UserNotFoundException("No seller found with id : " + id);

    }



    public ResponseEntity getAllSellers(Optional<Integer> page, Optional<Integer> size, Optional<String> sortProperty, Optional<String> sortDirection) {

        Sort.Direction sortingDirection = sortDirection.get().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        Page<Seller> sellers = sellerRepository.findAll(PageRequest.of(page.get(), size.get(), sortingDirection, sortProperty.get()));

        List<SellerAdminApiDto> sellersInDTO = ObjectMapperUtils.mapAllPage(sellers, SellerAdminApiDto.class);

        //Adding X-TOTAL-COUNT header representing total count of Employees
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.put("X-TOTAL-COUNT", Arrays.asList(String.valueOf(sellerRepository.count())));

        return new ResponseEntity<>(sellersInDTO, headers, HttpStatus.OK);

    }


        public ResponseEntity registerNewSeller(SellerRegistrationDto sellerRegistrationDto){

        Seller seller = modelMapper.map(sellerRegistrationDto, Seller.class);

        if(sellerRepository.findByEmail(seller.getEmail()) != null)
            throw new UserAlreadyExistsException("User already exists with email : " + seller.getEmail());
        else if(sellerRepository.findByCompanyNameIgnoreCase(seller.getCompanyName()) != null)
            throw new UserAlreadyExistsException("Company already exists with the given name : " + seller.getCompanyName());
        else
            {
                sellerRepository.save(seller);
                emailSenderService.sendEmail(emailSenderService.getSellerActivationMail(seller.getEmail()));
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
    }


    public  ResponseEntity getSellerByEmail(String email) {

        Seller seller = sellerRepository.findByEmail(email);
        SellerDetailsDto sellerDetailsDto = modelMapper.map(seller, SellerDetailsDto.class);

        return new ResponseEntity<SellerDetailsDto>(sellerDetailsDto,null,HttpStatus.OK);

    }

    public ResponseEntity updateSellerDetails(String email, SellerDetailsDto sellerDetailsDto) {

        Seller seller = sellerRepository.findByEmail(email);

        if(sellerRepository.findByEmail(sellerDetailsDto.getEmail()) != null)
            throw new UserAlreadyExistsException("User already exists with email : " + seller.getEmail());
        else if(sellerRepository.findByCompanyNameIgnoreCase(sellerDetailsDto.getCompanyName()) != null)
            throw new UserAlreadyExistsException("Company already exists with the given name : " + seller.getCompanyName());
        else
        {
            if(sellerDetailsDto.getFirstName() != null)
                seller.setFirstName(sellerDetailsDto.getFirstName());
            if(sellerDetailsDto.getLastName() != null)
                seller.setLastName(sellerDetailsDto.getLastName());
            if(sellerDetailsDto.getGst() != null)
                seller.setGst(sellerDetailsDto.getGst());
            if(sellerDetailsDto.getCompanyName() != null)
                seller.setCompanyName(sellerDetailsDto.getCompanyName());
            if(sellerDetailsDto.getCompanyContact() != null)
                seller.setCompanyContact(sellerDetailsDto.getCompanyContact());
            if(sellerDetailsDto.getEmail() != null)
                seller.setEmail(sellerDetailsDto.getEmail());

            sellerRepository.save(seller);

            return new ResponseEntity<String>("Profile updated!",null,HttpStatus.OK);
        }





    }

    public ResponseEntity updateSellerPassword(String email, PasswordUpdateDto passwordUpdateDto) {

        if(!passwordUpdateDto.getPassword().equals(passwordUpdateDto.getConfirmPassword()))
            throw new ConfirmPasswordNotMatchedException("Password and Confirm password don't match, please try again!");

        Seller seller = sellerRepository.findByEmail(email);

        seller.setPassword(passwordEncoder.encode(passwordUpdateDto.getPassword()));

        sellerRepository.save(seller);

        emailSenderService.sendEmail(emailSenderService.getPasswordUpdatedMail(email));

        return new ResponseEntity<String>("Password updated!", null, HttpStatus.OK);
    }

    public ResponseEntity updateSellerAddress(String email, Long id, NewAddressDto newAddressDto) {

        Seller seller = sellerRepository.findByEmail(email);

        Address address = seller.getAddress();

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

                sellerRepository.save(seller);  // changes will be cascaded to address

                return new ResponseEntity<String>("Updated Address with id " + id, null, HttpStatus.OK);
            }

        throw new ResourceNotFoundException("No address found with id : " + id + ", associated with your account");

    }


}
