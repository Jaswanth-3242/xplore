package com.example.shop;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name = "orders") @Data @NoArgsConstructor
public class CustomerOrder {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private String address;
    @Column(length = 2000)
    private String items;
    private double total;
    private LocalDateTime createdAt = LocalDateTime.now();
}
