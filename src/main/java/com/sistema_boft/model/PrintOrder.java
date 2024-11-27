package com.sistema_boft.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrintOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // Usuário que realizou o pedido

    @ManyToMany
    @JoinTable(
        name = "print_order_photos",
        joinColumns = @JoinColumn(name = "print_order_id"),
        inverseJoinColumns = @JoinColumn(name = "photo_id")
    )
    private List<Photo> photos; // Fotos escolhidas para impressão

    private LocalDateTime orderDate; // Data do pedido
    private boolean completed; // Indica se a impressão foi concluída
}
