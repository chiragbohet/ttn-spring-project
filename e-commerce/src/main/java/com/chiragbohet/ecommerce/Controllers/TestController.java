package com.chiragbohet.ecommerce.Controllers;

import com.chiragbohet.ecommerce.Dtos.PasswordUpdateDto;
import com.chiragbohet.ecommerce.Entities.UserRelated.Customer;
import com.chiragbohet.ecommerce.Entities.UserRelated.Seller;
import com.chiragbohet.ecommerce.Repositories.CustomerRepository;
import com.chiragbohet.ecommerce.Repositories.SellerRepository;
import com.chiragbohet.ecommerce.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO : Remove this
@RestController
public class TestController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    SellerRepository sellerRepository;


    @GetMapping("/test/customer")
    public List<Customer> testCustomer(){

        return (List<Customer>) customerRepository.findAll();
    }

    @GetMapping("/test/seller")
    public List<Seller> testSeller(){

        return sellerRepository.findAll();
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/admin/")
    public String adminHome(){
        return "Admin home";
    }

    @GetMapping("/customer/")
    public String userHome(){
        return "customer home";
    }

    @GetMapping("/seller/")
    public String sellerHome(){
        return "seller home";
    }

//    @Secured({"ROLE_ADMIN","ROLE_CUSTOMER","ROLE_SELLER"})
//    @GetMapping("/whoami")
//    public ResponseEntity whoAmI(Principal principal)
//    {
//        User user = userRepository.findByEmail(principal.getName());
//        user.getClass().is
//    }

    @GetMapping("/map")
    public ResponseEntity getMap()
    {

        Map<String,String> keyValue = new HashMap<>();
        keyValue.put("1","Chirag");
        keyValue.put("Chirag","good");

        return new ResponseEntity<Map<String, String>>(keyValue, null, HttpStatus.OK);
    }

    @PostMapping("/map")
    public ResponseEntity putMap(@RequestBody Map<String, String> map) {
        Map<String, String> response = new HashMap<>(map);
        return new ResponseEntity<Map<String, String>>(response, null, HttpStatus.OK);
    }

    @GetMapping("/test/test")
    public ResponseEntity testing(@RequestBody PasswordUpdateDto dto) {
        return new ResponseEntity<>(dto, null, HttpStatus.OK);
    }
}
