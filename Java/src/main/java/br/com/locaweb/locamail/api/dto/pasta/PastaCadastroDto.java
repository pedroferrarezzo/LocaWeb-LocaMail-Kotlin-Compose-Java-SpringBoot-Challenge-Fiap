package br.com.locaweb.locamail.api.dto.pasta;

import jakarta.validation.constraints.NotNull;

public record PastaCadastroDto(

        Long id_pasta,

        @NotNull(message = "O ID do usuário é obrigatório!")
        Long id_usuario,

        @NotNull(message = "O nome da pasta é obrigatório!")
        String nome

) {

}

