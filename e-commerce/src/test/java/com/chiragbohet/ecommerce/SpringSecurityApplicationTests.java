package com.chiragbohet.ecommerce;

import com.chiragbohet.ecommerce.Entities.CategoryRelated.Category;
import com.chiragbohet.ecommerce.Entities.CategoryRelated.Category_;
import com.chiragbohet.ecommerce.Entities.ProductRelated.Product;
import com.chiragbohet.ecommerce.Entities.ProductRelated.Product_;
import com.chiragbohet.ecommerce.Entities.UserRelated.Customer;
import com.chiragbohet.ecommerce.Entities.UserRelated.User;
import com.chiragbohet.ecommerce.Repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;


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


    // SELECT CATEGORY.ID, CATEGORY.NAME, COUNT(PRODUCT.ID)
    // FROM CATEGORY  LEFT JOIN PRODUCT ON CATEGORY.ID = PRODUCT.CATEGORY_ID
    // WHERE CATEGORY.ID NOT IN (SELECT DISTINCT PARENT_ID FROM CATEGORY WHERE PARENT_ID IS NOT NULL)
    // GROUP BY CATEGORY.ID

    void should_getLeafCategoryListWithProductCount() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);

        Root<Category> categoryRoot = criteriaQuery.from(Category.class);
        Root<Product> productRoot = criteriaQuery.from(Product.class);

        // Subquery to get ID's of leaf categories in subquery
        //SELECT DISTINCT PARENT_ID FROM CATEGORY WHERE PARENT_ID IS NOT NULL
        Subquery<Category> subquery = criteriaQuery.subquery(Category.class);
        Root<Category> subQueryCategoryRoot = subquery.from(Category.class);
        subquery.select(subQueryCategoryRoot.get("parentCategory")).distinct(true);
        subquery.where(criteriaBuilder.isNotNull(subQueryCategoryRoot.get("parentCategory")));

        // Ref : http://www.thejavageek.com/2014/04/28/criteria-api-joins/
        Join<Category, Product> categoryProductJoin = categoryRoot.join(Category_.productSet, JoinType.LEFT);
        //categoryProductJoin.on(criteriaBuilder.equal(productRoot.get(Product_.category), categoryRoot.get(Category_.id)));

        criteriaQuery.multiselect(categoryRoot.get(Category_.id), categoryRoot.get(Category_.name), criteriaBuilder.count(productRoot.get(Product_.id)));

        criteriaQuery.where(criteriaBuilder.equal(productRoot.get(Product_.category), categoryRoot.get(Category_.id)));
        criteriaQuery.where(criteriaBuilder.not(criteriaBuilder.in(categoryRoot.get("id")).value(subquery)));

        //criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(productRoot.get(Product_.category), categoryRoot.get(Category_.id)), criteriaBuilder.not(criteriaBuilder.in(categoryRoot.get("id")).value(subquery))));
        // criteriaQuery.where(criteriaBuilder.not(criteriaBuilder.in(categoryRoot.get("id")).value(subquery)));
        criteriaQuery.groupBy(categoryRoot.get("id"));


        TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);

        List<Object[]> results = query.getResultList();

        for (Object[] array : results) {
            for (Object object : array) {
                System.out.print(object.toString() + "\t");
            }
            System.out.println("");
        }

    }

    @Test
    void do_something() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);

        Root<Category> categoryRoot = criteriaQuery.from(Category.class);
        Join<Category, Product> categoryProductJoin = categoryRoot.join(Category_.productSet, JoinType.LEFT);

        Subquery<Category> subquery = criteriaQuery.subquery(Category.class);
        Root<Category> subQueryCategoryRoot = subquery.from(Category.class);
        subquery.select(subQueryCategoryRoot.get("parentCategory")).distinct(true);
        subquery.where(criteriaBuilder.isNotNull(subQueryCategoryRoot.get("parentCategory")));

        criteriaQuery.multiselect(categoryRoot.get(Category_.id), categoryRoot.get(Category_.name), criteriaBuilder.count(categoryProductJoin));
        criteriaQuery.where(criteriaBuilder.not(criteriaBuilder.in(categoryRoot.get("id")).value(subquery)));
        criteriaQuery.groupBy(categoryRoot.get(Category_.id));

        TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
        // System.out.println(query.getResultList());

        List<Object[]> results = query.getResultList();

        for (Object[] array : results) {
            for (Object object : array) {
                System.out.print(object + "\t");
            }
            System.out.println("");
        }
    }


    void should_getAllProductsForACategory() {
        Category mobiles = categoryRepository.findByName("Mobiles");

        for (Product product : mobiles.getProductSet()) {
            System.out.println(product.getName());
        }

    }


}
