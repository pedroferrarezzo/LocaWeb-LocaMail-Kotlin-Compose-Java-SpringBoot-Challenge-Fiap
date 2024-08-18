package br.com.locaweb.locamail.api.dto.convidado;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record ConvidadoCadastroDto(

        Long id_convidado,

        @NotNull(message = "O email do convidado é obrigatório!")
        @Email(message = "O email deve estar no formato correto!")
        String email

) {

}

