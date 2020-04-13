package com.springbootcamp.springsecurity.Repositories;

import com.springbootcamp.springsecurity.Entities.ProductReview;
import org.springframework.data.repository.CrudRepository;

public interface ProductReviewRepository extends CrudRepository<ProductReview,Integer> {
}
