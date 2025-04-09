package com.openclassrooms.mddapi.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Autoriser CORS pour toutes les routes de votre API
        registry.addMapping("/**")  // Applique CORS à toutes les routes
                .allowedOrigins("http://localhost:4200")  // Autoriser cette origine spécifique
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Autoriser ces méthodes HTTP
                .allowedHeaders("*");  // Autoriser tous les en-têtes
    }
}
