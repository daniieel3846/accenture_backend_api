package com.accenture.challenge.franchise_management_api;

import com.accenture.challenge.franchise_management_api.application.dto.NameUpdateDTO;
import com.accenture.challenge.franchise_management_api.application.dto.StockUpdateDTO;
import com.accenture.challenge.franchise_management_api.infrastructure.adapters.dto.TopStockProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Pruebas de DTOs (Data Transfer Objects)")
class DTOTest {

    private NameUpdateDTO nameUpdateDTO;
    private StockUpdateDTO stockUpdateDTO;
    private TopStockProductResponse topStockResponse;

    @BeforeEach
    void setUp() {
        nameUpdateDTO = new NameUpdateDTO();
        stockUpdateDTO = new StockUpdateDTO();
        topStockResponse = new TopStockProductResponse();
    }

    // ============ Pruebas de NameUpdateDTO ============

    @Test
    @DisplayName("NameUpdateDTO - Crear con constructor sin parámetros")
    void testNameUpdateDTODefaultConstructor() {
        assertThat(nameUpdateDTO).isNotNull();
        assertThat(nameUpdateDTO.getNewName()).isNull();
    }

    @Test
    @DisplayName("NameUpdateDTO - Establecer y obtener nuevo nombre")
    void testNameUpdateDTOSetAndGet() {
        nameUpdateDTO.setNewName("Nuevo Nombre");

        assertThat(nameUpdateDTO.getNewName()).isEqualTo("Nuevo Nombre");
    }

    @Test
    @DisplayName("NameUpdateDTO - Nombre vacío")
    void testNameUpdateDTOEmptyName() {
        nameUpdateDTO.setNewName("");

        assertThat(nameUpdateDTO.getNewName()).isEmpty();
    }

    @Test
    @DisplayName("NameUpdateDTO - Nombre con caracteres especiales")
    void testNameUpdateDTOSpecialCharacters() {
        String specialName = "Franquicia & Cia. #1 - 2025";
        nameUpdateDTO.setNewName(specialName);

        assertThat(nameUpdateDTO.getNewName()).isEqualTo(specialName);
    }

    // ============ Pruebas de StockUpdateDTO ============

    @Test
    @DisplayName("StockUpdateDTO - Crear con constructor sin parámetros")
    void testStockUpdateDTODefaultConstructor() {
        assertThat(stockUpdateDTO).isNotNull();
        assertThat(stockUpdateDTO.getNewStock()).isNull();
    }

    @Test
    @DisplayName("StockUpdateDTO - Establecer y obtener stock")
    void testStockUpdateDTOSetAndGet() {
        stockUpdateDTO.setNewStock(500);

        assertThat(stockUpdateDTO.getNewStock()).isEqualTo(500);
    }

    @Test
    @DisplayName("StockUpdateDTO - Stock cero")
    void testStockUpdateDTOZeroStock() {
        stockUpdateDTO.setNewStock(0);

        assertThat(stockUpdateDTO.getNewStock()).isEqualTo(0);
    }

    @Test
    @DisplayName("StockUpdateDTO - Stock negativo")
    void testStockUpdateDTONegativeStock() {
        stockUpdateDTO.setNewStock(-100);

        assertThat(stockUpdateDTO.getNewStock()).isEqualTo(-100);
    }

    @Test
    @DisplayName("StockUpdateDTO - Stock muy grande")
    void testStockUpdateDTOLargeStock() {
        stockUpdateDTO.setNewStock(999999);

        assertThat(stockUpdateDTO.getNewStock()).isEqualTo(999999);
    }

    // ============ Pruebas de TopStockProductResponse ============

    @Test
    @DisplayName("TopStockProductResponse - Crear con constructor sin parámetros")
    void testTopStockResponseDefaultConstructor() {
        assertThat(topStockResponse).isNotNull();
        assertThat(topStockResponse.getBranchName()).isNull();
        assertThat(topStockResponse.getProductName()).isNull();
        assertThat(topStockResponse.getStock()).isNull();
    }

    @Test
    @DisplayName("TopStockProductResponse - Crear con constructor con parámetros")
    void testTopStockResponseWithParameters() {
        TopStockProductResponse response = new TopStockProductResponse(
                "Sucursal Centro",
                "Producto Premium",
                250
        );

        assertThat(response.getBranchName()).isEqualTo("Sucursal Centro");
        assertThat(response.getProductName()).isEqualTo("Producto Premium");
        assertThat(response.getStock()).isEqualTo(250);
    }

    @Test
    @DisplayName("TopStockProductResponse - Establecer y obtener campos")
    void testTopStockResponseSetAndGet() {
        topStockResponse.setBranchName("Sucursal Sur");
        topStockResponse.setProductName("Producto A");
        topStockResponse.setStock(150);

        assertThat(topStockResponse.getBranchName()).isEqualTo("Sucursal Sur");
        assertThat(topStockResponse.getProductName()).isEqualTo("Producto A");
        assertThat(topStockResponse.getStock()).isEqualTo(150);
    }

    @Test
    @DisplayName("TopStockProductResponse - Stock cero")
    void testTopStockResponseZeroStock() {
        topStockResponse.setBranchName("Sucursal");
        topStockResponse.setProductName("Producto");
        topStockResponse.setStock(0);

        assertThat(topStockResponse.getStock()).isEqualTo(0);
    }

    @Test
    @DisplayName("TopStockProductResponse - Producto sin nombre")
    void testTopStockResponseNoProductName() {
        topStockResponse.setBranchName("Sucursal");
        topStockResponse.setProductName("Sin productos");
        topStockResponse.setStock(0);

        assertThat(topStockResponse.getProductName()).isEqualTo("Sin productos");
    }

    @Test
    @DisplayName("TopStockProductResponse - Comparación de igualdad")
    void testTopStockResponseEquality() {
        TopStockProductResponse response1 = new TopStockProductResponse("Sucursal", "Producto", 100);
        TopStockProductResponse response2 = new TopStockProductResponse("Sucursal", "Producto", 100);

        // En este caso, como no está sobrescrito equals, comparamos por propiedades
        assertThat(response1.getBranchName()).isEqualTo(response2.getBranchName());
        assertThat(response1.getProductName()).isEqualTo(response2.getProductName());
        assertThat(response1.getStock()).isEqualTo(response2.getStock());
    }

    // ============ Pruebas de Validación ============

    @Test
    @DisplayName("NameUpdateDTO - Validación de múltiples cambios")
    void testNameUpdateDTOMultipleChanges() {
        nameUpdateDTO.setNewName("Nombre 1");
        assertThat(nameUpdateDTO.getNewName()).isEqualTo("Nombre 1");

        nameUpdateDTO.setNewName("Nombre 2");
        assertThat(nameUpdateDTO.getNewName()).isEqualTo("Nombre 2");

        nameUpdateDTO.setNewName("Nombre 3");
        assertThat(nameUpdateDTO.getNewName()).isEqualTo("Nombre 3");
    }

    @Test
    @DisplayName("StockUpdateDTO - Cambios de stock secuenciales")
    void testStockUpdateDTOSequentialChanges() {
        stockUpdateDTO.setNewStock(100);
        assertThat(stockUpdateDTO.getNewStock()).isEqualTo(100);

        stockUpdateDTO.setNewStock(150);
        assertThat(stockUpdateDTO.getNewStock()).isEqualTo(150);

        stockUpdateDTO.setNewStock(0);
        assertThat(stockUpdateDTO.getNewStock()).isEqualTo(0);
    }
}

