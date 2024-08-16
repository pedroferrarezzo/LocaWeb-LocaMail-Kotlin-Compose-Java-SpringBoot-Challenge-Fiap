package br.com.locaweb.locamail.api.model;

import jakarta.persistence.Embedded;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EmailComAlteracao {

    @Embedded
    private Email email;

    @Embedded
    private Alteracao alteracao;
}
