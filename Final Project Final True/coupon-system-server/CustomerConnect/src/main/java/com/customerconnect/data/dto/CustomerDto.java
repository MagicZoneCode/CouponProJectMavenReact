package com.customerconnect.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    private UUID uuid;
    private String firstName;
    private String lastName;
    private String email;
}


