package br.com.entregadores;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EntregadoresApplication {

	public static void main(String[] args) {
		SpringApplication.run(EntregadoresApplication.class, args);
	}

}
