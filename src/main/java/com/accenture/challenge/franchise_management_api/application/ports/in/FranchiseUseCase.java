package com.accenture.challenge.franchise_management_api.application.ports.in;

import com.accenture.challenge.franchise_management_api.infrastructure.adapters.dto.TopStockProductResponse;
import com.accenture.challenge.franchise_management_api.domain.model.Branch;
import com.accenture.challenge.franchise_management_api.domain.model.Franchise;
import com.accenture.challenge.franchise_management_api.domain.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranchiseUseCase {
    Mono<Franchise> createFranchise(Franchise franchise);
    Mono<Franchise> addBranchToFranchise(String franchiseId, Branch branch);
    Mono<Franchise> addProductToBranch(String franchiseId, String branchId, Product product);
    Mono<Franchise> deleteProductFromBranch(String franchiseId, String branchId, String productId);
    Mono<Franchise> updateProductStock(String franchiseId, String branchId, String productId, Integer newStock);
    Flux<TopStockProductResponse> getTopStockProductsByFranchise(String franchiseId);
    Mono<Franchise> updateFranchiseName(String franchiseId, String newName);
    Mono<Franchise> updateBranchName(String franchiseId, String branchId, String newName);
    Mono<Franchise> updateProductName(String franchiseId, String branchId, String productId, String newName);
}
