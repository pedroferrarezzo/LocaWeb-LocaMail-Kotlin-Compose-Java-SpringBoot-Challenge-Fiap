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
}
