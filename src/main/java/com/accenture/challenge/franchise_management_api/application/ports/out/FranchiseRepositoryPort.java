package com.accenture.challenge.franchise_management_api.application.ports.out;

import com.accenture.challenge.franchise_management_api.domain.model.Franchise;
import reactor.core.publisher.Mono;

public interface FranchiseRepositoryPort {
    Mono<Franchise> save(Franchise franchise);
    Mono<Franchise> findById(String id);
}
