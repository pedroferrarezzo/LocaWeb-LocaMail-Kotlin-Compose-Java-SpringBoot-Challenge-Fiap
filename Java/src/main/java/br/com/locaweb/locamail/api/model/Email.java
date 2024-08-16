package br.com.locaweb.locamail.api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "T_LCW_EMAIL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_email")
    private Long id_email;

    @Column(name = "id_usuario", nullable = false)
    private Long id_usuario;

    @Column(name = "remetente", nullable = false)
    private String remetente;

    @Column(name = "destinatario", nullable = false)
    private String destinatario;

    @Column(name = "cc", nullable = false)
    private String cc;

    @Column(name = "cco", nullable = false)
    private String cco;

    @Column(name = "assunto", nullable = false)
    private String assunto;

    @Column(name = "corpo", nullable = false)
    private String corpo;

    @Column(name = "editavel", nullable = false)
    private Boolean editavel;

    @Column(name = "enviado", nullable = false)
    private Boolean enviado;

    @Column(name = "horario", nullable = false)
    private String horario;

    @Column(name = "data", nullable = false)
    private String data;

    @Column(name = "agenda_atrelada", nullable = false)
    private Boolean agenda_atrelada;
}
