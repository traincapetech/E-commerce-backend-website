package com.ecommerce.vendor.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vendor {


    private UUID id;

    private String username;

    private String email;

    private String password;

    private String name;

    private String contactNumber;


    private byte[] governmentId;


    private String governmentIdContentType;

    private Instant createdAt;

    private Instant updatedAt;
}
