package org.example;

import org.example.domain.entity.OrderEntity;
import org.example.domain.entity.OrderStatus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringRunner {
    public static void main(String[] args) {
        SpringApplication.run(SpringRunner.class, args);
//        OrderEntity entity = new OrderEntity();
//        entity.setId(2L);
//        entity.setStatus(OrderStatus.DELIVERED);
//        System.out.println(entity);
    }
}
