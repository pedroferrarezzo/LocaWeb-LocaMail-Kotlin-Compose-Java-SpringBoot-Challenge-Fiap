package br.com.locaweb.locamail.api.controller;

import br.com.locaweb.locamail.api.dto.alteracao.AlteracaoCadastroDto;
import br.com.locaweb.locamail.api.dto.alteracao.AlteracaoExibicaoDto;
import br.com.locaweb.locamail.api.service.AlteracaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alteracao")
public class AlteracaoController {
    @Autowired
    private AlteracaoService alteracaoService;

    @PostMapping("/criarAlteracao")
    @ResponseStatus(HttpStatus.CREATED)
    public AlteracaoExibicaoDto criarAlteracao (@RequestBody @Valid AlteracaoCadastroDto alteracaoCadastroDto) {
        return alteracaoService.criarAlteracao(alteracaoCadastroDto);
    }

    @PatchMapping("/atualizarLidoPorIdEmailEIdusuario")
    @ResponseStatus(HttpStatus.OK)
    public void atualizarLidoPorIdEmailEIdusuario(@RequestParam("lido") Boolean lido, @RequestParam("idEmail") Long id_email, @RequestParam("idUsuario") Long id_usuario) {
        alteracaoService.atualizarLidoPorIdEmailEIdUsuario(lido, id_email, id_usuario);
    }


    @PatchMapping("/atualizarPastaPorIdEmailEIdUsuario")
    @ResponseStatus(HttpStatus.OK)
    public void atualizarPastaPorIdEmailEIdUsuario(@RequestParam("pasta") Long pasta, @RequestParam("idEmail") Long id_email, @RequestParam("idUsuario") Long id_usuario) {
        alteracaoService.atualizarPastaPorIdEmailEIdUsuario(pasta, id_email, id_usuario);
    }


    @PatchMapping("/atualizarImportantePorIdEmail")
    @ResponseStatus(HttpStatus.OK)
    public void atualizarImportantePorIdEmail(@RequestParam("importante") Boolean importante, @RequestParam("idEmail") Long id_email, @RequestParam("idUsuario") Long id_usuario) {
        alteracaoService.atualizarImportantePorIdEmail(importante, id_email, id_usuario);
    }
}