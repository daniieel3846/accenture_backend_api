package com.accenture.challenge.franchise_management_api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@DynamoDbBean
public class Branch {
    private String id;
    private String name;
    private List<Product> products;

    public Branch() {
        this.id = UUID.randomUUID().toString();
    }
}
