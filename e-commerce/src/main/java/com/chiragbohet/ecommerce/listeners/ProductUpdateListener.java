package com.chiragbohet.ecommerce.listeners;

import com.chiragbohet.ecommerce.entities.Product;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

//Simply logging the information for now.

@Log4j2
public class ProductUpdateListener implements MessageListener {

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

        System.out.println("Product details :\n" + product);

    }


}
