package br.com.locaweb.locamail.api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "T_LCW_ANEXO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Anexo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_anexo")
    private Long id_anexo;

    @Column(name = "id_email", nullable = false)
    private Long id_email;

    @Column(name = "anexo", nullable = false)
    @Lob
    private byte[] anexo;
}
