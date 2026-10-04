package br.edu.infnet.skoob_registro_leitura_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Skoob — Serviço de Registro de Leitura")
                        .version("1.0")
                        .description("Serviço independente responsável pelo acompanhamento de leitura dos usuários sobre os livros.")
                        .contact(new Contact()
                                .name("Ingrid Munhoz")
                                .email("ingrid@email.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }
}