package br.com.locaweb.locamail.api.service;

import br.com.locaweb.locamail.api.dto.agendaConvidado.AgendaConvidadoCadastroDto;
import br.com.locaweb.locamail.api.dto.agendaConvidado.AgendaConvidadoExibicaoDto;
import br.com.locaweb.locamail.api.dto.aiQuestion.AiQuestionCadastroDto;
import br.com.locaweb.locamail.api.dto.aiQuestion.AiQuestionExibicaoDto;
import br.com.locaweb.locamail.api.model.AgendaConvidado;
import br.com.locaweb.locamail.api.model.AiQuestion;
import br.com.locaweb.locamail.api.repository.AiQuestionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiQuestionService {

    @Autowired
    private AiQuestionRepository aiQuestionRepository;

    public AiQuestionExibicaoDto criarPergunta(AiQuestionCadastroDto aiQuestionCadastroDto) {
        AiQuestion aiQuestion = new AiQuestion();
        BeanUtils.copyProperties(aiQuestionCadastroDto, aiQuestion);
        return new AiQuestionExibicaoDto(aiQuestionRepository.save(aiQuestion));
    }

    public AiQuestionExibicaoDto obterPergunta(Long id_question, Long id_email) {
        return new AiQuestionExibicaoDto(aiQuestionRepository.obterPergunta(id_question, id_email));
    }
}
