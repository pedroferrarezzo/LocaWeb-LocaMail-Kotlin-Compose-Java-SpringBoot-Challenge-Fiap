package br.com.locaweb.locamail.api.dto.convidado;

import br.com.locaweb.locamail.api.model.Convidado;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record ConvidadoExibicaoDto(

        Long id_convidado,
        String email

) {
        public ConvidadoExibicaoDto(Convidado convidado) {
                this(
                        convidado.getId_convidado(),
                        convidado.getEmail()
                );
        }
}


