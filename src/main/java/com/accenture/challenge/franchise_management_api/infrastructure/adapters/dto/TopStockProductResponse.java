package com.accenture.challenge.franchise_management_api.infrastructure.adapters.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopStockProductResponse {
    private String branchName;
    private String productName;
    private Integer stock;
}