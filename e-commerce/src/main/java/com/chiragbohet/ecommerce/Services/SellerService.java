package com.chiragbohet.ecommerce.Services;

import com.chiragbohet.ecommerce.Dtos.CustomerAdminApiDto;
import com.chiragbohet.ecommerce.Dtos.SellerAdminApiDto;
import com.chiragbohet.ecommerce.Dtos.SellerRegistrationDto;
import com.chiragbohet.ecommerce.Entities.UserRelated.Customer;
import com.chiragbohet.ecommerce.Entities.UserRelated.Seller;
import com.chiragbohet.ecommerce.Exceptions.UserAlreadyExistsException;
import com.chiragbohet.ecommerce.Exceptions.UserNotFoundException;
import com.chiragbohet.ecommerce.Repositories.SellerRepository;
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
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class SellerService {

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    EmailSenderService emailSenderService;

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

        List<SellerAdminApiDto> sellersInDTO = ObjectMapperUtils.mapAll(sellers, SellerAdminApiDto.class);

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
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setTo(seller.getEmail());
                mailMessage.setSubject("Registration completed");
                mailMessage.setFrom("chiragtest9654@gmail.com");
                mailMessage.setText("Dear seller your account has been created and is waiting for an approval.");
                emailSenderService.sendEmail(mailMessage);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
    }

}
