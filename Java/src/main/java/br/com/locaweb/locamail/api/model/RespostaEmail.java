package br.com.locaweb.locamail.api.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "T_LCW_RESPOSTA_EMAIL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RespostaEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resposta_email")
    private Long id_resposta_email;

    @Column(name = "id_email", nullable = false)
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
}
