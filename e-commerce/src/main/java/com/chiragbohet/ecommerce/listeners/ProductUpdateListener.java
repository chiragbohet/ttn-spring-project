package com.chiragbohet.ecommerce.listeners;

import com.chiragbohet.ecommerce.entities.Product;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;


@Log4j2
public class ProductUpdateListener implements MessageListener {

    private static String PRODUCT_UPDATE_LIST_NAME = "UPDATED_PRODUCTS";
    // TODO : Why is this not getting injected?
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onMessage(Message message) {

        log.info("ProductUpdateListener -> onMessage() -> Product added/updated : ");


        Product product = null;

        try {
            ByteArrayInputStream in = new ByteArrayInputStream(message.getBody());
            ObjectInputStream is = new ObjectInputStream(in);
            product = (Product) is.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        log.info("Product details :\n" + product);

        log.info("Writing product details to redis - ");
        redisTemplate.opsForList().leftPush(PRODUCT_UPDATE_LIST_NAME, product);


//        // TODO : Remove the code below
//        // reading from redis
//        List<Object> lastUpdatedProduct =  redisTemplate.opsForList().range(PRODUCT_UPDATE_LIST_NAME, -1L, -1L);
//
//        if(!lastUpdatedProduct.isEmpty())
//        {
//            List<Product> productList = ObjectMapperUtils.mapAllList(lastUpdatedProduct, Product.class);
//            for (Product productEntry : productList)
//            {
//                log.info("Product details : " + productEntry);
//            }
//        }


    }




}
