package com.accenture.challenge.franchise_management_api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class Product {
    private String id;
    private String name;
    private Integer stock;
}