package com.chiragbohet.ecommerce.Services;

import com.chiragbohet.ecommerce.Entities.CategoryRelated.Category;
import com.chiragbohet.ecommerce.Entities.CategoryRelated.Category_;
import com.chiragbohet.ecommerce.Entities.ProductRelated.Product;
import com.chiragbohet.ecommerce.Entities.UserRelated.Customer;
import com.chiragbohet.ecommerce.Entities.UserRelated.Seller;
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
import javax.persistence.criteria.*;
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
        modelAndView.addObject("sellersCount", getNonDeletedSellersCountByCriteriaQuery().toString());

        // adding category details
        List<Object[]> categoriesWithProductCount = getLeafCategoryListWithProductCountByCriteriaQuery();
        modelAndView.addObject("categoryList", categoriesWithProductCount);

        //adding product details
        List<Object[]> productListWithStockCount = productRepository.getAllActiveProductsWithCumulativeStockCount();
        modelAndView.addObject("productList", productListWithStockCount);


        return modelAndView;

    }

    public Long getNonDeletedCustomersCountByCriteriaQuery() {

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

        return query.getSingleResult();

    }

    public Long getNonDeletedSellersCountByCriteriaQuery() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);

        // Subquery to get list of seller IDs'
        // Ref : https://www.baeldung.com/jpa-criteria-api-in-expressions
        Subquery<Seller> subquery = criteriaQuery.subquery(Seller.class);
        Root<Seller> sellerRoot = subquery.from(Seller.class);
        subquery.select(sellerRoot.get("id"));

        //SELECT COUNT(*) FROM USER WHERE NOT IS_DELETED AND ID IN (SELECT ID FROM SELLER)
        Root<User> userRoot = criteriaQuery.from(User.class);
        criteriaQuery.select(criteriaBuilder.count(userRoot));
        criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.isFalse(userRoot.get("isDeleted"))), criteriaBuilder.in(userRoot.get("id")).value(subquery));

        TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);

        return query.getSingleResult();
    }

    public List<Object[]> getLeafCategoryListWithProductCountByCriteriaQuery() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);

        Root<Category> categoryRoot = criteriaQuery.from(Category.class);
        Join<Category, Product> categoryProductJoin = categoryRoot.join(Category_.productSet, JoinType.LEFT);

        // subquery
        Subquery<Category> subquery = criteriaQuery.subquery(Category.class);
        Root<Category> subQueryCategoryRoot = subquery.from(Category.class);
        subquery.select(subQueryCategoryRoot.get("parentCategory")).distinct(true);
        subquery.where(criteriaBuilder.isNotNull(subQueryCategoryRoot.get("parentCategory")));

        criteriaQuery.multiselect(categoryRoot.get(Category_.id), categoryRoot.get(Category_.name), criteriaBuilder.count(categoryProductJoin));
        criteriaQuery.where(criteriaBuilder.not(criteriaBuilder.in(categoryRoot.get("id")).value(subquery)));   // TODO : idk why I cant use metamodel Category_.id here
        criteriaQuery.groupBy(categoryRoot.get(Category_.id));

        TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

}
