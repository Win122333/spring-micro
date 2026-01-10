package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringRunner {
    public static void main(String[] args) {
        var c = SpringApplication.run(SpringRunner.class, args);
    }
}
