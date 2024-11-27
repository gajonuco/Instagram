package com.sistema_boft.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/login**", "/error**").permitAll() // Permite acesso público
                .anyRequest().authenticated() // Exige autenticação para outros endpoints
            )
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/oauth2/authorization/instagram") // Página de login customizada
                .defaultSuccessUrl("/api/photos", true) // Redireciona após login bem-sucedido
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/") // Redireciona após logout
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            );

        return http.build();
    }
}
