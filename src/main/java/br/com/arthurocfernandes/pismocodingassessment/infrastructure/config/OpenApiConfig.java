package br.com.arthurocfernandes.pismocodingassessment.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

        @Bean
        public OpenAPI openAPI() {
                return new OpenAPI()
                        .info(new Info()
                                .title("Pismo Coding Assessment API")
                                .version("v1")
                                .description("API documentation for Pismo Coding Assessment. It emulates a financial bank scenario with accounts and transactions.")
                                .contact(new Contact()
                                        .name("Arthur Fernandes")
                                        .email("arthurofssilva@gmail.com")));
        }

        @Bean
        public GroupedOpenApi v1Api() {
                return GroupedOpenApi.builder()
                        .group("v1")
                        .pathsToMatch("/api/v1/**")
                        .build();
        }
}