package com.ecommerce.order.model;

import com.ecommerce.delivery.model.DeliveryBoy;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Document(collection = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    private UUID id;

    private UUID customerId;
    private List<OrderItem> items;
    private double totalAmount;
    private ShippingAddress shippingAddress;

    private String orderStatus;
    private String paymentStatus;

    private Instant createdAt;
    private Instant updatedAt;
    private Instant deliveredAt;

    @DBRef
    private DeliveryBoy deliveryBoy;
}
