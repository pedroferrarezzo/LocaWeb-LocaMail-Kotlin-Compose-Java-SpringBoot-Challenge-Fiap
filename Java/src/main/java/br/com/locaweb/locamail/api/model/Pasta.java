package br.com.locaweb.locamail.api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "T_LCW_PASTA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Pasta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pasta")
    private Long id_pasta;

    @Column(name = "id_usuario", nullable = false)
    private Long id_usuario;

    @Column(name = "nome", nullable = false)
    private String nome;
}
