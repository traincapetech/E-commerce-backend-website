package com.ecommerce.vendor.repo;

import com.ecommerce.vendor.model.Vendor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface VendorRepository extends MongoRepository<Vendor, UUID> {
    Optional<Vendor> findByUsername(String username);
    Optional<Vendor> findByEmail(String email);
}
