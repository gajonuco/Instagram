package com.sistema_boft.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String instagramId; // ID único do Instagram do usuário
    private String username; // Nome de usuário no Instagram
    private String accessToken; // Token OAuth2 para acesso à API do Instagram

    private boolean loggedOut; // Indica se o usuário já foi deslogado
}
