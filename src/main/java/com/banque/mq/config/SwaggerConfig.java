package com.banque.mq.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration de la documentation Swagger/OpenAPI pour l’API REST exposée par l’application.
 */
@Configuration
public class SwaggerConfig {

    @Value("${api.name}")
    private String apiName;

    @Value("${api.version}")
    private String apiVersion;

    /**
     * Méthode pour initialiser la configuration OpenAPI utilisée pour générer la documentation Swagger de l'application.
     * @return OpenAPI
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                    .title(apiName)
                    .version(apiVersion)
                    .description("Documentation de l'API MQ Routing"));
    }
}
