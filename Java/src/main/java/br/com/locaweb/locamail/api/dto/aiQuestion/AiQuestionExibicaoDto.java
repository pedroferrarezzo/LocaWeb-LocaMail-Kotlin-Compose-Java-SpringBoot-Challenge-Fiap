package br.com.locaweb.locamail.api.dto.aiQuestion;

import br.com.locaweb.locamail.api.model.AiQuestion;

public record AiQuestionExibicaoDto(
        Long id_question,
        Long id_email,
        String pergunta
) {
    public AiQuestionExibicaoDto(AiQuestion aiQuestion) {
        this(
                aiQuestion.getId_question(),
                aiQuestion.getId_email(),
                aiQuestion.getPergunta()
        );
    }
}
