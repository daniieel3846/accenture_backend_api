package com.accenture.challenge.franchise_management_api.domain.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class Franchise {
    private String id;
    private String name;
    private List<Branch> branches;

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }
}
