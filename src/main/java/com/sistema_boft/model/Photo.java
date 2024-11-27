package com.sistema_boft.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String instagramPhotoId; // ID da foto no Instagram
    private String url; // URL da foto
    private String caption; // Descrição da foto (se houver)

    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // Associação com o usuário dono das fotos
}
