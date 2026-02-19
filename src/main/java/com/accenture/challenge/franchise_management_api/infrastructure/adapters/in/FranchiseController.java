package com.accenture.challenge.franchise_management_api.infrastructure.adapters.in;

import com.accenture.challenge.franchise_management_api.application.dto.NameUpdateDTO;
import com.accenture.challenge.franchise_management_api.application.dto.StockUpdateDTO;
import com.accenture.challenge.franchise_management_api.infrastructure.adapters.dto.TopStockProductResponse;
import com.accenture.challenge.franchise_management_api.application.ports.in.FranchiseUseCase;
import com.accenture.challenge.franchise_management_api.domain.model.Branch;
import com.accenture.challenge.franchise_management_api.domain.model.Franchise;
import com.accenture.challenge.franchise_management_api.domain.model.Product;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/franchise")
public class FranchiseController {

    private final FranchiseUseCase franchiseUseCase;

    public FranchiseController(FranchiseUseCase franchiseUseCase) {
        this.franchiseUseCase = franchiseUseCase;
    }

    @PostMapping
    public Mono<Franchise> createFranchise(@RequestBody Franchise franchise) {
        return franchiseUseCase.createFranchise(franchise);
    }

    @PostMapping("/{id}/branch")
    public Mono<Franchise> addBranch(@PathVariable String id, @RequestBody Branch branch) {
        return franchiseUseCase.addBranchToFranchise(id, branch);
    }

    @PostMapping("/{franchiseId}/branch/{branchId}/product")
    public Mono<Franchise> addProduct(@PathVariable String franchiseId, @PathVariable String branchId, @RequestBody Product product) {
        return franchiseUseCase.addProductToBranch(franchiseId, branchId, product);
    }

    @DeleteMapping("/{franchiseId}/branch/{branchId}/product/{productId}")
    public Mono<Franchise> deleteProduct(@PathVariable String franchiseId, @PathVariable String branchId, @PathVariable String productId) {
        return franchiseUseCase.deleteProductFromBranch(franchiseId, branchId, productId);
    }

    @PutMapping("/{franchiseId}/branch/{branchId}/product/{productId}/stock")
    public Mono<Franchise> updateStock(@PathVariable String franchiseId, @PathVariable String branchId,
                                       @PathVariable String productId, @RequestBody StockUpdateDTO stockUpdateDTO) {
        return franchiseUseCase.updateProductStock(franchiseId, branchId, productId, stockUpdateDTO.getNewStock());
    }

    @GetMapping("/{franchiseId}/top-products")
    public Flux<TopStockProductResponse> getTopProducts(@PathVariable String franchiseId) {
        return franchiseUseCase.getTopStockProductsByFranchise(franchiseId);
    }

    @PutMapping("/{id}/name")
    public Mono<Franchise> updateFranchiseName(@PathVariable String id, @RequestBody NameUpdateDTO dto) {
        return franchiseUseCase.updateFranchiseName(id, dto.getNewName());
    }

    @PutMapping("/{franchiseId}/branch/{branchId}/name")
    public Mono<Franchise> updateBranchName(@PathVariable String franchiseId, @PathVariable String branchId, @RequestBody NameUpdateDTO dto) {
        return franchiseUseCase.updateBranchName(franchiseId, branchId, dto.getNewName());
    }

    @PutMapping("/{franchiseId}/branch/{branchId}/product/{productId}/name")
    public Mono<Franchise> updateProductName(@PathVariable String franchiseId, @PathVariable String branchId,
            @PathVariable String productId, @RequestBody NameUpdateDTO dto) {
        return franchiseUseCase.updateProductName(franchiseId, branchId, productId, dto.getNewName());
    }
}
