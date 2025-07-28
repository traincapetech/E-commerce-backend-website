package com.ecommerce.product.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Document(collection = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    private UUID id;

    private UUID vendorId; // Reference to Vendor

    private String name;

    private String description;

    private Double price;

    private String category;

    private String saleDescription;

    private List<String> images;

    private Integer stockQuantity;

    private Double averageRating = 0.0;

    private Instant createdAt;
    private Instant updatedAt;
}
