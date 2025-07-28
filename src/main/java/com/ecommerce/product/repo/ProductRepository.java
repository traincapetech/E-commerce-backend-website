package com.ecommerce.product.repo;

import com.ecommerce.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends MongoRepository<Product, UUID> {
    List<Product> findByVendorId(UUID vendorId);
}
