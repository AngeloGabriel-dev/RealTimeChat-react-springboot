package com.rtchat_api.real_time_chat_api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("security", securityScheme()))
                .info(
                        new Info()
                                .title("Rest API - Real Time Chat")
                                .description("API para a gestão de uma aplicação de chat em tempo real")
                                .version("v1")
                                .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0"))
                                .contact(new Contact().name("Angelo Gabriel").email("angelogabriel01@live.com"))
                );


    }

    private SecurityScheme securityScheme(){
        return new SecurityScheme()
                .description("Insira um bearer token válido para prosseguir.")
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("security");
    }
}
