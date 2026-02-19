package com.accenture.challenge.franchise_management_api.infrastructure.adapters.out;

import com.accenture.challenge.franchise_management_api.application.ports.out.FranchiseRepositoryPort;
import com.accenture.challenge.franchise_management_api.domain.model.Franchise;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Repository
public class FranchiseRepositoryAdapter implements FranchiseRepositoryPort {

    private final DynamoDbAsyncTable<Franchise> franchiseTable;

    public FranchiseRepositoryAdapter(DynamoDbEnhancedAsyncClient enhancedClient) {
        this.franchiseTable = enhancedClient.table("Franchise", TableSchema.fromBean(Franchise.class));
    }

    @Override
    public Mono<Franchise> save(Franchise franchise) {
        return Mono.fromFuture(franchiseTable.putItem(franchise))
                .thenReturn(franchise);
    }

    @Override
    public Mono<Franchise> findById(String id) {
        return Mono.fromFuture(franchiseTable.getItem(r -> r.key(k -> k.partitionValue(id))));
    }
}
