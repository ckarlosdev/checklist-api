
package com.ck.wi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Deshabilitar CSRF (necesario para probar POST/PUT desde React/Postman)
                .csrf(csrf -> csrf.disable())

                // 2. Permitir todas las peticiones sin autenticación
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )

                // 3. Deshabilitar el formulario de login y el diálogo de autenticación básica
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable());

        return http.build();
    }
}