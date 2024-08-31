package br.com.locaweb.locamail.api.controller;


import br.com.locaweb.locamail.api.dto.aiQuestion.AiQuestionCadastroDto;
import br.com.locaweb.locamail.api.dto.aiQuestion.AiQuestionExibicaoDto;
import br.com.locaweb.locamail.api.service.AiQuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aiQuestion")
public class AiQuestionController {

    @Autowired
    private AiQuestionService aiQuestionService;

    @PostMapping("/criarPergunta")
    @ResponseStatus(HttpStatus.CREATED)
    public AiQuestionExibicaoDto criarPergunta (@RequestBody @Valid AiQuestionCadastroDto aiQuestionCadastroDto) {
        return aiQuestionService.criarPergunta(aiQuestionCadastroDto);
    }

    @GetMapping("/obterPergunta")
    @ResponseStatus(HttpStatus.OK)
    public AiQuestionExibicaoDto obterPergunta (@RequestParam("idQuestion") Long id_question, @RequestParam("idEmail") Long id_email) {
        return aiQuestionService.obterPergunta(id_question, id_email);
    }
}
