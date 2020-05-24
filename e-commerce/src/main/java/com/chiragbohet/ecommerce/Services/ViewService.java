package com.chiragbohet.ecommerce.Services;

import com.chiragbohet.ecommerce.Entities.UserRelated.Customer;
import com.chiragbohet.ecommerce.Entities.UserRelated.User;
import com.chiragbohet.ecommerce.Repositories.CategoryRepository;
import com.chiragbohet.ecommerce.Repositories.ProductRepository;
import com.chiragbohet.ecommerce.Repositories.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Log4j2
@Service
public class ViewService {

    @PersistenceContext
    private EntityManager entityManager;

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
        modelAndView.addObject("customersCount", getNonDeletedCustomersCountByCriteriaQuery().toString());
        modelAndView.addObject("sellersCount", userRepository.getNonDeletedSellersCount().toString());

        // adding category details
        List<Object[]> categoriesWithProductCount = categoryRepository.getLeafCategoryListWithProductCount();
        modelAndView.addObject("categoryList", categoriesWithProductCount);

        //adding product details
        List<Object[]> productListWithStockCount = productRepository.getAllActiveProductsWithCumulativeStockCount();
        modelAndView.addObject("productList", productListWithStockCount);


        return modelAndView;

    }

    private Long getNonDeletedCustomersCountByCriteriaQuery() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);

        Root<User> userRoot = criteriaQuery.from(User.class);

        // Subquery to get list of customer IDs'
        // Ref : https://www.baeldung.com/jpa-criteria-api-in-expressions
        Subquery<Customer> subquery = criteriaQuery.subquery(Customer.class);
        Root<Customer> customerRoot = subquery.from(Customer.class);
        subquery.select(customerRoot.get("id"));

        //SELECT COUNT(*) FROM USER WHERE NOT IS_DELETED AND ID IN (SELECT ID FROM CUSTOMER)
        criteriaQuery.select(criteriaBuilder.count(userRoot));
        criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.isFalse(userRoot.get("isDeleted")), criteriaBuilder.in(userRoot.get("id")).value(subquery)));

        TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);

        Long result = query.getSingleResult();

        return result;
    }


}
