package com.springbootcamp.springsecurity;

import com.springbootcamp.springsecurity.Entities.Address;
import com.springbootcamp.springsecurity.Entities.Cart;
import com.springbootcamp.springsecurity.Entities.Product.Category;
import com.springbootcamp.springsecurity.Entities.Product.Product;
import com.springbootcamp.springsecurity.Entities.Product.ProductVariation;
import com.springbootcamp.springsecurity.Entities.ProductReview;
import com.springbootcamp.springsecurity.Entities.Role;
import com.springbootcamp.springsecurity.Entities.Users.Customer;
import com.springbootcamp.springsecurity.Entities.Users.Seller;
import com.springbootcamp.springsecurity.Entities.Users.User;
import com.springbootcamp.springsecurity.Repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class SpringSecurityApplicationTests {


	@Autowired
	UserRepository userRepository;
	@Autowired
	AddressRepository addressRepository;
	@Autowired
	SellerRepository sellerRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	ProductVariationRepository productVariationRepository;
	@Autowired
	ProductReviewRepository productReviewRepository;
	@Autowired
	CartRepository cartRepository;
	@Test
	void contextLoads() {
	}

    @Test
	void addCustomer(){

		Customer user =new Customer();
		Address address=new Address();
		Role role=new Role();
		List<Address> addressList=new ArrayList<>();
		List<Role> roleList=new ArrayList<>();
        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
		user.setFirstName("Anshul");
		user.setLastName("Poonia");
		user.setDeleted(false);
		user.setActive(true);
		user.setContact("9845638972");
		user.setEmail("pooniaanshul@gmail.com");
		user.setPassword(passwordEncoder.encode("anshul@1234"));
		role.setAuthority("ROLE_USER");
		roleList.add(role);
		address.setAddressLine("Shakti Nagar");
		address.setCity("New Delhi");
		address.setCountry("India");
		address.setLable("Home");
		address.setState("New Delhi");
		address.setZipcode(110007);
		address.setUser(user);
		addressList.add(address);
		user.setRoleList(roleList);
		user.setAddressList(addressList);
		userRepository.save(user);
	}

	@Test
	void addSeller(){

		Seller seller =new Seller();
		Address address=new Address();
		Address address1=new Address();
		Role role=new Role();
		Role role2=new Role();
		List<Address> addressList=new ArrayList<>();
		List<Role> roleList=new ArrayList<>();
		PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

		seller.setFirstName("Chirag");
		seller.setLastName("Bohet");
		seller.setDeleted(false);
		seller.setActive(true);
		seller.setCompanyContact("9654476321");
		seller.setEmail("bohet.chirag@gmail.com");
		seller.setPassword(passwordEncoder.encode("chirag@1234"));
		seller.setCompanyName("Aman Suppliments");
		seller.setGst(236645);
		role.setAuthority("ROLE_SELLER");
		role2.setAuthority("ROLE_ADMIN");
		roleList.add(role);
		roleList.add(role2);
		address.setAddressLine("Nirman Vihar");
		address.setCity("East Delhi");
		address.setCountry("India");
		address.setLable("Office");
		address.setState("New Delhi");
		address.setZipcode(110456);
		address.setUser(seller);
		address1.setAddressLine("NSL Tech Zone");
		address1.setCity("Greater Noida");
		address1.setCountry("India");
		address1.setLable("Office");
		address1.setState("UP");
		address1.setZipcode(201302);
		address1.setUser(seller);
		addressList.add(address);
		addressList.add(address1);
		seller.setRoleList(roleList);
		seller.setAddressList(addressList);
		sellerRepository.save(seller);
	}

	@Test
	void addProduct(){

		Product product=new Product();
		ProductVariation productVariation=new ProductVariation();
		ProductVariation productVariation1=new ProductVariation();
		Category category=new Category();
		List<ProductVariation> productVariationList =new ArrayList<>();
		List<List<ProductVariation>> productList=new ArrayList<>();
		Seller seller =new Seller();
		Address address=new Address();
		Address address1=new Address();
		Role role=new Role();
		Role role2=new Role();
		List<Address> addressList=new ArrayList<>();
		List<Role> roleList=new ArrayList<>();
		seller.setFirstName("Flip");
		seller.setLastName("cart");
		seller.setDeleted(false);
		seller.setActive(true);
		seller.setCompanyContact("1233357789");
		seller.setEmail("flipkart@gmail.com");
		seller.setPassword("flipkart@1234");
		seller.setCompanyName("flipkart");
		seller.setGst(1233455);
		role.setAuthority("ROLE_SELLER");
		roleList.add(role);
		address.setAddressLine("RICCO INDUSTRIAL AREA");
		address.setCity("OKHLA");
		address.setCountry("India");
		address.setLable("Office");
		address.setState("UP");
		address.setZipcode(207230);
		address.setUser(seller);
		addressList.add(address);
		seller.setRoleList(roleList);
		seller.setAddressList(addressList);
		product.setCancelable(false);
		product.setBrand("Flipkart Basics");
		product.setActive(true);
		product.setName("Flipkart basics Data cable");
		product.setDescription("Data Cable");
		product.setSeller(seller);
		productVariation.setPrice(200f);
		productVariation.setQuantityAvailable(50);
		productVariation.setPrimaryImage_Name("Datacable image");
		productVariation.setMetadata("3 feet datacable");
		productVariation1.setPrice(250f);
		productVariation1.setQuantityAvailable(100);
		productVariation1.setPrimaryImage_Name("Datacable image");
		productVariation1.setMetadata("6 feet datacable");
		productVariationList.add(productVariation);
		productVariationList.add(productVariation1);
		product.setProductVariationList(productVariationList);
		category.setName("Mobile Accessories");
		product.setCategory(category);
		sellerRepository.save(seller);
		productVariation.setProduct(product);
		productRepository.save(product);

	}

	@Test
	void addProdcutReview(){
		ProductReview productReview =new ProductReview();
		productReview.setRating(5);
		productReview.setReview("Amazing product");
		Product product=new Product();
		Customer customer=new Customer();
		product=productRepository.findById(3).get();
		productReview.setProduct(product);
		customer= (Customer) userRepository.findById(2).get();
		productReview.setCustomer(customer);
		productReviewRepository.save(productReview);

	}

	@Test
			public void adddummy() {
        PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
		List<Role> roleList = new ArrayList<>();
		Role role = new Role();
		Role role2 = new Role();
		role.setAuthority("ROLE_SELLER");
		role2.setAuthority("ROLE_ADMIN");
		roleList.add(role);
		roleList.add(role2);
		Seller seller = new Seller();
		seller.setFirstName("dummy");
		seller.setEmail("123@abc");
		seller.setPassword(passwordEncoder.encode("pass"));
		seller.setRoleList(roleList);
		sellerRepository.save(seller);
	}


//	@Test
//	void addCart(){
//		Cart cart=new Cart();
//		ProductVariation productVariation=new ProductVariation();
//		Customer customer =new Customer();
//		productVariation=productVariationRepository.findById(2).get();
//		cart.setProductVariation(productVariation);
//		customer=(Customer) userRepository.findById(2).get();
//		cart.setQuantity(1);
//		cart.setCustomer(customer);
//		cart.setWishListItem(true);
//		cartRepository.save(cart);
//	}


}
