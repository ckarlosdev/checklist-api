
package com.ck.wi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(auth -> auth
                // 1. Endpoints de "Assignment" permitidos sin token
                .requestMatchers(HttpMethod.POST, "/api/v1/assignment").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/v1/assignment").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/assignment/{id}").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/assignments").permitAll()
    
                // 2. Permitir siempre OPTIONS para evitar problemas de CORS en el navegador
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
    
                // 3. CUALQUIER OTRA RUTA requiere autenticación
                .anyRequest().authenticated()
            );
            // Necesitas configurar el soporte para JWT (Resource Server)
            // .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOriginPatterns(List.of(
            "https://oleo-soft.com",
            "https://ckarlosdev.github.io",
            "http://localhost:5173"
        ));

        config.setAllowedMethods(List.of(
            "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"
        ));

        config.setAllowedHeaders(List.of(
            "Authorization",
            "Content-Type",
            "X-Requested-With",
            "Accept",
            "Origin"
        ));

        config.setAllowCredentials(false); 
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Tu configuración actual de usuarios (está bien para pruebas)
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("ADMIN", "USER")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}
