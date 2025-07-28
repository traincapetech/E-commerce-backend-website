package com.ecommerce.order.repo;

import com.ecommerce.order.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByCustomerId(UUID customerId);
    List<Order> findByDeliveryBoy_Id(UUID deliveryBoyId);
}
