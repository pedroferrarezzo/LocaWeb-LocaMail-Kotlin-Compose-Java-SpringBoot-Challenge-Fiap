package br.com.locaweb.locamail.api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "T_LCW_CONVIDADO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Convidado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_convidado")
    private Long id_convidado;

    @Column(name = "email", nullable = false)
    private String email;
}