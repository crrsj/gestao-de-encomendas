package br.com.entregadores;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition(
		info = @Info(
				title = "API - Gerenciamento de Encomendas ",
				version = "1.0",
				description = "API para ajudar entregadores a organizar suas entregas.",
				contact = @Contact(name = "Carlos Roberto", email = "crrsj1@gmail.com")
		)
)
public class EntregadoresApplication {

	public static void main(String[] args) {
		SpringApplication.run(EntregadoresApplication.class, args);
	}

}
