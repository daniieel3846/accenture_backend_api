package com.accenture.challenge.franchise_management_api;

import com.accenture.challenge.franchise_management_api.domain.model.Branch;
import com.accenture.challenge.franchise_management_api.domain.model.Franchise;
import com.accenture.challenge.franchise_management_api.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Pruebas de los Modelos de Dominio")
class DomainModelTest {

    private Franchise franchise;
    private Branch branch;
    private Product product;

    @BeforeEach
    void setUp() {
        franchise = new Franchise();
        branch = new Branch();
        product = new Product();
    }

    // ============ Pruebas de Franchise ============

    @Test
    @DisplayName("Franchise - Crear instancia con todos los campos")
    void testFranchiseCreation() {
        franchise.setId("franchise-1");
        franchise.setName("Mi Franquicia");
        franchise.setBranches(new ArrayList<>());

        assertThat(franchise.getId()).isEqualTo("franchise-1");
        assertThat(franchise.getName()).isEqualTo("Mi Franquicia");
        assertThat(franchise.getBranches()).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("Franchise - Verificar inicialización por defecto")
    void testFranchiseDefaultInitialization() {
        assertThat(franchise.getId()).isNull();
        assertThat(franchise.getName()).isNull();
        assertThat(franchise.getBranches()).isNull();
    }

    @Test
    @DisplayName("Franchise - Agregar sucursal")
    void testAddBranchToFranchise() {
        franchise.setBranches(new ArrayList<>());
        branch.setId("branch-1");
        branch.setName("Sucursal Centro");

        franchise.getBranches().add(branch);

        assertThat(franchise.getBranches()).hasSize(1);
        assertThat(franchise.getBranches().get(0).getName()).isEqualTo("Sucursal Centro");
    }

    @Test
    @DisplayName("Franchise - Múltiples sucursales")
    void testFranchiseMultipleBranches() {
        franchise.setBranches(new ArrayList<>());

        for (int i = 1; i <= 3; i++) {
            Branch b = new Branch();
            b.setId("branch-" + i);
            b.setName("Sucursal " + i);
            franchise.getBranches().add(b);
        }

        assertThat(franchise.getBranches()).hasSize(3);
    }

    // ============ Pruebas de Branch ============

    @Test
    @DisplayName("Branch - Crear instancia con todos los campos")
    void testBranchCreation() {
        branch.setId("branch-1");
        branch.setName("Sucursal Centro");
        branch.setProducts(new ArrayList<>());

        assertThat(branch.getId()).isEqualTo("branch-1");
        assertThat(branch.getName()).isEqualTo("Sucursal Centro");
        assertThat(branch.getProducts()).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("Branch - Actualizar información")
    void testBranchUpdate() {
        branch.setId("branch-1");
        branch.setName("Sucursal Centro");

        branch.setName("Sucursal Norte");

        assertThat(branch.getName()).isEqualTo("Sucursal Norte");

    }

    @Test
    @DisplayName("Branch - Agregar producto")
    void testAddProductToBranch() {
        branch.setProducts(new ArrayList<>());
        product.setId("product-1");
        product.setName("Producto A");
        product.setStock(100);

        branch.getProducts().add(product);

        assertThat(branch.getProducts()).hasSize(1);
        assertThat(branch.getProducts().get(0).getStock()).isEqualTo(100);
    }

    @Test
    @DisplayName("Branch - Múltiples productos")
    void testBranchMultipleProducts() {
        branch.setProducts(new ArrayList<>());

        for (int i = 1; i <= 5; i++) {
            Product p = new Product();
            p.setId("product-" + i);
            p.setName("Producto " + i);
            p.setStock(i * 10);
            branch.getProducts().add(p);
        }

        assertThat(branch.getProducts()).hasSize(5);
        assertThat(branch.getProducts().get(4).getStock()).isEqualTo(50);
    }

    @Test
    @DisplayName("Branch - Remover producto")
    void testRemoveProductFromBranch() {
        branch.setProducts(new ArrayList<>());
        product.setId("product-1");
        branch.getProducts().add(product);

        branch.getProducts().removeIf(p -> p.getId().equals("product-1"));

        assertThat(branch.getProducts()).isEmpty();
    }

    // ============ Pruebas de Product ============

    @Test
    @DisplayName("Product - Crear instancia con todos los campos")
    void testProductCreation() {
        product.setId("product-1");
        product.setName("Producto Test");
        product.setStock(100);

        assertThat(product.getId()).isEqualTo("product-1");
        assertThat(product.getName()).isEqualTo("Producto Test");
        assertThat(product.getStock()).isEqualTo(100);
    }

    @Test
    @DisplayName("Product - Actualizar stock")
    void testUpdateProductStock() {
        product.setStock(100);
        product.setStock(150);

        assertThat(product.getStock()).isEqualTo(150);
    }

    @Test
    @DisplayName("Product - Stock puede ser cero")
    void testProductZeroStock() {
        product.setStock(0);

        assertThat(product.getStock()).isEqualTo(0);
    }

    @Test
    @DisplayName("Product - Actualizar nombre")
    void testUpdateProductName() {
        product.setName("Producto Inicial");
        product.setName("Producto Actualizado");

        assertThat(product.getName()).isEqualTo("Producto Actualizado");
    }

    // ============ Pruebas de Integración de Modelos ============

    @Test
    @DisplayName("Integración - Estructura completa Franchise-Branch-Product")
    void testCompleteStructure() {
        // Crear franquicia
        franchise.setId("franchise-1");
        franchise.setName("Franquicia Principal");
        franchise.setBranches(new ArrayList<>());

        // Crear sucursal
        branch.setId("branch-1");
        branch.setName("Sucursal Centro");
        branch.setProducts(new ArrayList<>());

        // Crear productos
        Product p1 = new Product();
        p1.setId("product-1");
        p1.setName("Producto A");
        p1.setStock(100);

        Product p2 = new Product();
        p2.setId("product-2");
        p2.setName("Producto B");
        p2.setStock(200);

        // Armar estructura
        branch.getProducts().add(p1);
        branch.getProducts().add(p2);
        franchise.getBranches().add(branch);

        // Validar
        assertThat(franchise.getBranches()).hasSize(1);
        assertThat(franchise.getBranches().get(0).getProducts()).hasSize(2);
        assertThat(franchise.getBranches().get(0).getProducts().get(1).getStock()).isEqualTo(200);
    }

    @Test
    @DisplayName("Integración - Buscar producto en sucursal de franquicia")
    void testSearchProductInStructure() {
        // Setup
        franchise.setBranches(new ArrayList<>());
        branch.setId("branch-1");
        branch.setProducts(new ArrayList<>());
        product.setId("target-product");
        product.setName("Producto Buscado");

        branch.getProducts().add(product);
        franchise.getBranches().add(branch);

        // Búsqueda
        Product found = franchise.getBranches().get(0).getProducts().stream()
                .filter(p -> p.getId().equals("target-product"))
                .findFirst()
                .orElse(null);

        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Producto Buscado");
    }
}

