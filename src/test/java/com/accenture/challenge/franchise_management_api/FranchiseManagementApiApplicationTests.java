package com.accenture.challenge.franchise_management_api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Test de Contexto de Aplicación")
class FranchiseManagementApiApplicationTests {

	@Test
	@DisplayName("La clase de aplicación debe poder ser instanciada")
	void testApplicationCanBeInstantiated() {
		FranchiseManagementApiApplication app = new FranchiseManagementApiApplication();
		assertThat(app).isNotNull();
	}

}
