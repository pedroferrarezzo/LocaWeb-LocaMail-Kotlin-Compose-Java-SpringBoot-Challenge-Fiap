package br.com.locaweb.locamail.api.dto.anexo;

import jakarta.validation.constraints.NotNull;

public record AnexoCadastroDto(
        Long id_anexo,

        @NotNull(message = "O Id do email é obrigatório!")
        Long id_email,
        @NotNull(message = "O anexo é obrigatório!")
        byte[] anexo
) {
}
