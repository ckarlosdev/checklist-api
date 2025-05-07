package com.ck.wi; // Reemplaza con el paquete de tu aplicación

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**") // Aplica la configuración a todas las rutas bajo /api/
                        .allowedOrigins("http://127.0.0.1:5500") // Permite peticiones desde tu frontend de desarrollo
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos HTTP permitidos
                        .allowedHeaders("*") // Permite todos los encabezados
                        .allowCredentials(true) // Si necesitas manejar cookies o encabezados de autenticación
                        .maxAge(3600); // Tiempo máximo (en segundos) que el navegador puede cachear la respuesta CORS
            }
        };
    }
}