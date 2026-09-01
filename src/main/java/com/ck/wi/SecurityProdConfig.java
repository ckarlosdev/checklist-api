package com.ck.wi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.List;

@Configuration
@Profile("prod")
@EnableWebSecurity
public class SecurityProdConfig {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Bean
    public SecurityFilterChain prodFilterChain(HttpSecurity http, JwtDecoder jwtDecoder) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(
                                "/api/v1/assignment/**",
                                "/api/v1/assignments",
                                "/api/v1/job/**",
                                "/api/v1/employee",
                                "/api/v1/checklist/**",
                                "/api/v1/equipment/**",
                                "/api/v1/issue-reports",
                                "/api/v1/equipments",
                                "/api/v1/photo/**",
                                "/api/v1/drEmployee/hours-summary"
                        ).permitAll()
                        .requestMatchers(
                                "/api/v1/pretask/**",
                                "/api/v1/pt/**"
                        ).permitAll()
                        .requestMatchers(
                                "/api/v1/issues/**",
                                "/api/v1/issue/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                // Stateless: no usamos sesiones
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Configura Resource Server con JWT
                .oauth2ResourceServer(rs -> rs
                        .jwt(jwt -> jwt.decoder(jwtDecoder))
                );

        return http.build();
    }

    // Cors configuration
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of(
                "https://oleo-soft.com",
                "https://ckarlosdev.github.io",
                "http://localhost:5173"
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With", "Accept", "Origin"));
        config.setAllowCredentials(false);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    // Bean JwtDecoder para validar JWT HS256
    @Bean
    public JwtDecoder jwtDecoder() {
        // Decodifica Base64
        byte[] keyBytes = java.util.Base64.getDecoder().decode(secretKey);
        SecretKey key = new SecretKeySpec(keyBytes, "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(key).build();
    }
}
