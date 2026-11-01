package com.example.shop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ShopApplication {
    public static void main(String[] args) { SpringApplication.run(ShopApplication.class, args); }

    @Bean
    CommandLineRunner seed(ProductRepo repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.save(new Product(null, "Wireless Headphones", "Bluetooth over-ear, 30h battery", 2499.0));
                repo.save(new Product(null, "Smart Watch", "Fitness tracking, AMOLED display", 3999.0));
                repo.save(new Product(null, "Backpack", "Water-resistant 25L laptop bag", 1299.0));
                repo.save(new Product(null, "Mechanical Keyboard", "RGB, blue switches", 2199.0));
            }
        };
    }
}
