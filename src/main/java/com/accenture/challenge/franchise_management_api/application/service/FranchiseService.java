package com.accenture.challenge.franchise_management_api.application.service;

import com.accenture.challenge.franchise_management_api.infrastructure.adapters.dto.TopStockProductResponse;
import com.accenture.challenge.franchise_management_api.application.ports.in.FranchiseUseCase;
import com.accenture.challenge.franchise_management_api.application.ports.out.FranchiseRepositoryPort;
import com.accenture.challenge.franchise_management_api.domain.model.Branch;
import com.accenture.challenge.franchise_management_api.domain.model.Franchise;
import com.accenture.challenge.franchise_management_api.domain.model.Product;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class FranchiseService implements FranchiseUseCase {

    private final FranchiseRepositoryPort franchiseRepositoryPort;

    public FranchiseService(FranchiseRepositoryPort franchiseRepositoryPort) {
        this.franchiseRepositoryPort = franchiseRepositoryPort;
    }

    @Override
    public Mono<Franchise> createFranchise(Franchise franchise) {
        if (franchise.getId() == null || franchise.getId().isEmpty()) {
            franchise.setId(UUID.randomUUID().toString());
        }
        return franchiseRepositoryPort.save(franchise);
    }

    @Override
    public Mono<Franchise> addBranchToFranchise(String franchiseId, Branch branch) {
        return franchiseRepositoryPort.findById(franchiseId)
                .flatMap(franchise -> {
                    if (franchise.getBranches() == null) {
                        franchise.setBranches(new java.util.ArrayList<>());
                    }
                    if (branch.getId() == null) {
                        branch.setId(java.util.UUID.randomUUID().toString());
                    }

                    franchise.getBranches().add(branch);
                    return franchiseRepositoryPort.save(franchise);
                })
                .switchIfEmpty(Mono.error(new RuntimeException("No se encontró la franquicia con ID: " + franchiseId)));
    }

    @Override
    public Mono<Franchise> addProductToBranch(String franchiseId, String branchId, Product product) {
        return franchiseRepositoryPort.findById(franchiseId)
                .flatMap(franchise -> {
                    Branch branch = franchise.getBranches().stream()
                            .filter(b -> b.getId().equals(branchId))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

                    if (branch.getProducts() == null) {
                        branch.setProducts(new java.util.ArrayList<>());
                    }

                    if (product.getId() == null) {
                        product.setId(java.util.UUID.randomUUID().toString());
                    }

                    branch.getProducts().add(product);

                    return franchiseRepositoryPort.save(franchise);
                }).switchIfEmpty(Mono.error(new RuntimeException("Franquicia no encontrada")));
    }

    @Override
    public Mono<Franchise> deleteProductFromBranch(String franchiseId, String branchId, String productId) {
        return franchiseRepositoryPort.findById(franchiseId)
                .flatMap(franchise -> {
                    Branch branch = franchise.getBranches().stream()
                            .filter(b -> b.getId().equals(branchId))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

                    if (branch.getProducts() != null) {
                        boolean removed = branch.getProducts().removeIf(p -> p.getId().equals(productId));
                        if (!removed) {
                            return Mono.error(new RuntimeException("Producto no encontrado en esta sucursal"));
                        }
                    }

                    return franchiseRepositoryPort.save(franchise);
                }).switchIfEmpty(Mono.error(new RuntimeException("Franquicia no encontrada")));
    }

    @Override
    public Mono<Franchise> updateProductStock(String franchiseId, String branchId, String productId, Integer newStock) {
        return franchiseRepositoryPort.findById(franchiseId)
                .flatMap(franchise -> {
                    Branch branch = franchise.getBranches().stream()
                            .filter(b -> b.getId().equals(branchId))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

                    Product product = branch.getProducts().stream()
                            .filter(p -> p.getId().equals(productId))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Producto no encontrado en esta sucursal"));

                    product.setStock(newStock);

                    return franchiseRepositoryPort.save(franchise);
                }).switchIfEmpty(Mono.error(new RuntimeException("Franquicia no encontrada")));
    }

    @Override
    public Flux<TopStockProductResponse> getTopStockProductsByFranchise(String franchiseId) {
        return franchiseRepositoryPort.findById(franchiseId)
                .flatMapMany(franchise -> {
                    if (franchise.getBranches() == null) {
                        return Flux.empty();
                    }

                    return Flux.fromIterable(franchise.getBranches())
                            .map(branch -> {
                                Product topProduct = branch.getProducts().stream()
                                        .filter(p -> p.getStock() != null)
                                        .max(java.util.Comparator.comparing(Product::getStock))
                                        .orElse(new Product());

                                return new TopStockProductResponse(
                                        branch.getName(),
                                        topProduct.getName() != null ? topProduct.getName() : "Sin productos",
                                        topProduct.getStock() != null ? topProduct.getStock() : 0
                                );
                            });
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Franquicia no encontrada")));
    }

    @Override
    public Mono<Franchise> updateFranchiseName(String franchiseId, String newName) {
        return franchiseRepositoryPort.findById(franchiseId)
                .flatMap(franchise -> {
                    franchise.setName(newName);
                    return franchiseRepositoryPort.save(franchise);
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Franquicia no encontrada")));
    }

    @Override
    public Mono<Franchise> updateBranchName(String franchiseId, String branchId, String newName) {
        return franchiseRepositoryPort.findById(franchiseId)
                .flatMap(franchise -> {
                    Branch branch = franchise.getBranches().stream()
                            .filter(b -> b.getId().equals(branchId))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

                    branch.setName(newName);
                    return franchiseRepositoryPort.save(franchise);
                });
    }

    @Override
    public Mono<Franchise> updateProductName(String franchiseId, String branchId, String productId, String newName) {
        return franchiseRepositoryPort.findById(franchiseId)
                .flatMap(franchise -> {
                    Branch branch = franchise.getBranches().stream()
                            .filter(b -> b.getId().equals(branchId))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

                    Product product = branch.getProducts().stream()
                            .filter(p -> p.getId().equals(productId))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

                    product.setName(newName);
                    return franchiseRepositoryPort.save(franchise);
                });
    }
}
