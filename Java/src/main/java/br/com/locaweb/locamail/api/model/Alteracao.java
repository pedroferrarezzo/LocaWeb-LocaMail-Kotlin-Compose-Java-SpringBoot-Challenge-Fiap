package br.com.locaweb.locamail.api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "T_LCW_ALTERACAO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Alteracao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alteracao")
    private Long id_alteracao;

    @Column(name = "alt_id_usuario", nullable = false)
    private Long alt_id_usuario;

    @Column(name = "alt_id_email", nullable = false)
    private Long alt_id_email;

    @Column(name = "id_pasta")
    private Long id_pasta;

    @Column(name = "importante", nullable = false)
    private Boolean importante;

    @Column(name = "lido", nullable = false)
    private Boolean lido;

    @Column(name = "arquivado", nullable = false)
    private Boolean arquivado;

    @Column(name = "excluido", nullable = false)
    private Boolean excluido;

    @Column(name = "spam", nullable = false)
    private Boolean spam;
}
