package br.com.locaweb.locamail.api.model;

import br.com.locaweb.locamail.api.dto.email.EmailExibicaoDto;
import jakarta.persistence.*;
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
