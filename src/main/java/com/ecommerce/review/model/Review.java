package com.ecommerce.review.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Document(collection = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    private String id;

    private String productId;

    private String customerId;

    private int rating; // 1 to 5

    private String comment;

    private Instant createdAt;
    private Instant updatedAt;
}
