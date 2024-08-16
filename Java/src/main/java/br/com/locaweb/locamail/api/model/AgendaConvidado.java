package br.com.locaweb.locamail.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "T_LCW_AGENDA_CONVIDADO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AgendaConvidado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_agenda_convidado")
    private Long id_agenda_convidado;

    @Column(name = "id_agenda", nullable = false)
    private Long id_agenda;

    @Column(name = "id_convidado", nullable = false)
    private Long id_convidado;

    @Column(name = "grupo_repeticao", nullable = false)
    private Integer grupo_repeticao;
}
