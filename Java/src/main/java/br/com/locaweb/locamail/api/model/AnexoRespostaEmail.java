package br.com.locaweb.locamail.api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "T_LCW_ANEXO_RESPOSTA_EMAIL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AnexoRespostaEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_anexo_resposta_email")
    private Long id_anexo_resposta_email;

    @Column(name = "id_resposta_email", nullable = false)
    private Long id_resposta_email;

    @Column(name = "anexo", nullable = false)
    @Lob
    private byte[] anexo;
}
