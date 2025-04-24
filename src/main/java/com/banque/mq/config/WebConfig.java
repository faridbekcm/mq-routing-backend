package com.banque.mq.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Classe de configuration Spring définissant les règles CORS (Cross-Origin Resource Sharing).
 * Elle permet à l’IHM Angular (port 4200) d'accéder aux ressources exposées par l'API backend (port 8080 ou autre).
 * Cette configuration est essentielle pour permettre les appels entre frontend et backend en environnement de développement.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configure les règles CORS pour les endpoints REST commençant par /api/**.
     * Autorise les requêtes HTTP provenant de http://localhost:4200 avec toutes les méthodes et en-têtes.
     * Cela permet à l'application Angular de communiquer sans restrictions avec le backend.
     *
     * @param registry l’objet utilisé pour enregistrer les règles CORS.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:4200") // TODO: manage CORS and secirity in other JIRA
                .allowedMethods("*")
                .allowedHeaders("*");
    }
}
