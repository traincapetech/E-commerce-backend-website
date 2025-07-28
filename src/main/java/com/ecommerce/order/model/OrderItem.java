package com.ecommerce.order.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {
    private UUID productId;
    private int quantity;
    private double price;
}
