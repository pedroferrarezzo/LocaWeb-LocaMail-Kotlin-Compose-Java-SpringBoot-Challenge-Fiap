package br.com.locaweb.locamail.api.dto.email;

import br.com.locaweb.locamail.api.model.Alteracao;
import br.com.locaweb.locamail.api.model.Email;
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
