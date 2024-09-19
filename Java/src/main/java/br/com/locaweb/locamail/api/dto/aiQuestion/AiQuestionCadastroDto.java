package br.com.locaweb.locamail.api.dto.aiQuestion;

import jakarta.validation.constraints.NotNull;

public record AiQuestionCadastroDto(
        Long id_question,
        @NotNull(message = "O id do email é obrigatório!")
        Long id_email,
        @NotNull(message = "A pergunta é obrigatória!")
        String pergunta
) {
}
