package com.ecommerce.customer.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document(collection = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    private UUID id = UUID.randomUUID();

    @Field
    private String username;

    @Field
    private String email;

    @Field
    private String password;

    @Field
    private String name;

    @Field
    private String contactNumber;

    @Field
    private List<Address> addresses = new ArrayList<>();

    @Field
    private List<UUID> wishlist = new ArrayList<>();

    @Field
    private List<UUID> recentlyViewed = new ArrayList<>();

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Address {
        private String street;
        private String city;
        private String state;
        private String zipCode;
        private String country;
    }
}
