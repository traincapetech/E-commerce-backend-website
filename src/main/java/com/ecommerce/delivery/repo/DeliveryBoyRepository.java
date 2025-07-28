package com.ecommerce.delivery.repo;

import com.ecommerce.delivery.model.DeliveryBoy;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryBoyRepository extends MongoRepository<DeliveryBoy, UUID> {
    Optional<DeliveryBoy> findFirstByAvailableTrue();
}
