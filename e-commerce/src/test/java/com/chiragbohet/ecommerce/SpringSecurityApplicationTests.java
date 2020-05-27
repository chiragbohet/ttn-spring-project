package com.chiragbohet.ecommerce;

import com.chiragbohet.ecommerce.entities.Customer;
import com.chiragbohet.ecommerce.entities.User;
import com.chiragbohet.ecommerce.repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;


@SpringBootTest
class SpringSecurityApplicationTests {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    CategoryRepository categoryRepository;


    @Test
    void contextLoads() {
    }


    @Test
    void should_getCountOfAllNonDeletedCustomers() {
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
        System.out.println("\n\n\n\n\n\nResult : " + result);

    }


}
