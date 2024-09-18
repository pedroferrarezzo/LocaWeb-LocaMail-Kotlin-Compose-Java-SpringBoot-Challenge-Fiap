package br.com.locaweb.locamail.api.controller;

import br.com.locaweb.locamail.api.dto.alteracao.AlteracaoCadastroDto;
import br.com.locaweb.locamail.api.dto.alteracao.AlteracaoExibicaoDto;
import br.com.locaweb.locamail.api.dto.respostaEmail.RespostaEmailCadastroDto;
import br.com.locaweb.locamail.api.dto.respostaEmail.RespostaEmailExibicaoDto;
import br.com.locaweb.locamail.api.model.RespostaEmail;
import br.com.locaweb.locamail.api.service.RespostaEmailService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/respostaEmail")
@CircuitBreaker(name = "appLocaMailCircuitBreaker")
public class RespostaEmailController {

    @Autowired
    private RespostaEmailService respostaEmailService;

    @PostMapping("/criarRespostaEmail")
    @ResponseStatus(HttpStatus.CREATED)
    public RespostaEmailExibicaoDto criarRespostaEmail(@RequestBody @Valid RespostaEmailCadastroDto respostaEmailCadastroDto) {
        return respostaEmailService.criarRespostaEmail(respostaEmailCadastroDto);
    }

    @PutMapping("/atualizarRespostaEmail")
    @ResponseStatus(HttpStatus.OK)
    public RespostaEmailExibicaoDto atualizarRespostaEmail(@RequestBody @Valid RespostaEmailCadastroDto respostaEmailCadastroDto) {
        return respostaEmailService.atualizarRespostaEmail(respostaEmailCadastroDto);
    }

    @GetMapping("/listarRespostasEmailPorIdEmail")
    @ResponseStatus(HttpStatus.OK)
    public List<RespostaEmailExibicaoDto> listarRespostasEmailPorIdEmail(@RequestParam("idEmail") Long id_email) {
        return respostaEmailService.listarRespostasEmailPorIdEmail(id_email);
    }

    @GetMapping("/listarRespostaEmailPorIdRespostaEmail")
    @ResponseStatus(HttpStatus.OK)
    public RespostaEmailExibicaoDto listarRespostaEmailPorIdRespostaEmail(@RequestParam("idRespostaEmail") Long id_resposta_email) {
        return respostaEmailService.listarRespostaEmailPorIdRespostaEmail(id_resposta_email);
    }

    @DeleteMapping("/excluirRespostaEmailPorIdEmail")
    @ResponseStatus(HttpStatus.OK)
    public void excluirRespostaEmailPorIdEmail(@RequestParam("idEmail") Long id_email) {
        respostaEmailService.excluirRespostaEmailPorIdEmail(id_email);
    }

    @DeleteMapping("/excluirRespostaEmail")
    @ResponseStatus(HttpStatus.OK)
    public void excluirRespostaEmail(@RequestParam("idRespostaEmail") Long id_resposta_email) throws Exception {
        respostaEmailService.excluirRespostaEmail(id_resposta_email);
    }
}
