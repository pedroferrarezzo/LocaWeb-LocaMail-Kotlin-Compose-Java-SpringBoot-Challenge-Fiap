package br.com.locaweb.locamail.api.dto.anexoRespostaEmail;

import jakarta.validation.constraints.NotNull;

public record AnexoRespostaEmailCadastroDto(
        Long id_anexo_resposta_email,

        @NotNull(message = "O ID da resposta de email é obrigatória!")
        Long id_resposta_email,

        @NotNull(message = "O anexo do email é obrigatório!")
        byte[] anexo
) {
}
