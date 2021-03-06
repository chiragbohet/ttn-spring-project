package com.chiragbohet.ecommerce.controllers;

import com.chiragbohet.ecommerce.dtos.PasswordUpdateDto;
import com.chiragbohet.ecommerce.entities.Customer;
import com.chiragbohet.ecommerce.entities.Seller;
import com.chiragbohet.ecommerce.repositories.CustomerRepository;
import com.chiragbohet.ecommerce.repositories.SellerRepository;
import com.chiragbohet.ecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public String userHome() {
        return "customer home";
    }

    @GetMapping("/seller/")
    public String sellerHome() {
        return "seller home";
    }

    @GetMapping("/test/thymeleaf/{name}")
    public String thymeleafTest(@RequestParam(name = "name") String name) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("name", name);
        return "test";

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
