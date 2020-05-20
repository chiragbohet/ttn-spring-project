package com.chiragbohet.ecommerce.Services;

import com.chiragbohet.ecommerce.Repositories.CategoryRepository;
import com.chiragbohet.ecommerce.Repositories.ProductRepository;
import com.chiragbohet.ecommerce.Repositories.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Log4j2
@Service
public class ViewService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    public ModelAndView viewAdminHome(HttpServletRequest request) {
        log.trace("class viewService -> viewAdminHome()");
        ModelAndView modelAndView = new ModelAndView("admin-home");

        // adding admin email/name
        modelAndView.addObject("adminEmail", request.getUserPrincipal().getName());

        //adding customer and seller count
        modelAndView.addObject("customersCount", userRepository.getNonDeletedCustomersCount().toString());
        modelAndView.addObject("sellersCount", userRepository.getNonDeletedSellersCount().toString());

        // adding category details
        List<Object[]> categoriesWithProductCount = categoryRepository.getLeafCategoryListWithProductCount();
        modelAndView.addObject("categoryList", categoriesWithProductCount);

        //adding product details
        List<Object[]> productListWithStockCount = productRepository.getAllActiveProductsWithCumulativeStockCount();
        modelAndView.addObject("productList", productListWithStockCount);


        return modelAndView;

    }
}
