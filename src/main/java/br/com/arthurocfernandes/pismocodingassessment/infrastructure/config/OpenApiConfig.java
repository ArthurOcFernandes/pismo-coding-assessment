package br.com.arthurocfernandes.pismocodingassessment.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Pismo Coding Assessment API",
                version = "v1",
                description = "API documentation for the Pismo coding assessment",
                contact = @Contact(name = "Pismo Coding Assessment"),
                license = @License(name = "Private")
        )
)
public class OpenApiConfig {
}

