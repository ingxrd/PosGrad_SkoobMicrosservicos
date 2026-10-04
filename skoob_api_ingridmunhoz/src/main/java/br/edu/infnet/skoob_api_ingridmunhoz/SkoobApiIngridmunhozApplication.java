package br.edu.infnet.skoob_api_ingridmunhoz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SkoobApiIngridmunhozApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkoobApiIngridmunhozApplication.class, args);
	}
}
