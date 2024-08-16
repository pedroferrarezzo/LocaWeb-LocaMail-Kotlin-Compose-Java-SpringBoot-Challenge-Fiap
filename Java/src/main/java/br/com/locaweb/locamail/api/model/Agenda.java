package br.com.locaweb.locamail.api.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "T_LCW_AGENDA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Agenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_agenda")
    private Long id_agenda;

    @Column(name = "id_email", nullable = false)
    private Long id_email;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descritivo", nullable = false)
    private String descritivo;

    @Column(name = "id_usuario", nullable = false)
    private Long id_usuario;

    @Column(name = "cor", nullable = false)
    private Integer cor;

    @Column(name = "localizacao", nullable = false)
    private String localizacao;

    @Column(name = "notificacao", nullable = false)
    private Integer notificacao;

    @Column(name = "horario", nullable = false)
    private String horario;

    @Column(name = "data", nullable = false)
    private String data = LocalDate.now().toString();

    @Column(name = "proprietario", nullable = false)
    private String proprietario;

    @Column(name = "evento", nullable = false)
    private Boolean evento;

    @Column(name = "tarefa", nullable = false)
    private Boolean tarefa;

    @Column(name = "repeticao", nullable = false)
    private Integer repeticao;

    @Column(name = "grupo_repeticao", nullable = false)
    private Integer grupo_repeticao;

    @Column(name = "visivel", nullable = false)
    private Boolean visivel;
}
