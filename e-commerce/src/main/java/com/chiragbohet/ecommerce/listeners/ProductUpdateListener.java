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
import java.util.List;

//Simply logging the information for now.

@Log4j2
public class ProductUpdateListener implements MessageListener {

    //TODO : Get this from somewhere else.
    private static final String PRODUCT_UPDATE_QUEUE_NAME = "PRODUCT-UPDATE-QUEUE";
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onMessage(Message message) {

        log.trace("ProductUpdateListener -> onMessage() -> Product added/updated : ");

        Product product = null;

        try {
            ByteArrayInputStream in = new ByteArrayInputStream(message.getBody());
            ObjectInputStream is = new ObjectInputStream(in);
            product = (Product) is.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        log.trace("Product details received by RabbitMQ:\n" + product);

        //Pushing to redis
        log.trace("Writing Product details to Redis:\n" + product);
        redisTemplate.opsForList().leftPush(PRODUCT_UPDATE_QUEUE_NAME, product);

        //Reading from redis for confirmation
        List<Object> readFromRedis = redisTemplate.opsForList().range(PRODUCT_UPDATE_QUEUE_NAME, 0L, -1L);

        if (!readFromRedis.isEmpty()) {
            for (Object obj : readFromRedis) {
                log.trace("Product read from redis : " + obj.toString());
            }
        }


//       if(!readFromRedis.isEmpty())
//      {
//          List<Product> products = ObjectMapperUtils.mapAllList(readFromRedis, Product.class);
//
//          for (Product productFromRedis : products)
//          {
//              log.trace("Product read from redis : "+ product);
//          }
//      }


    }


}
