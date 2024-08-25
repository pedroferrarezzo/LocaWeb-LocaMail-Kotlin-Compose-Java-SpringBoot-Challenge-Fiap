package br.com.locaweb.locamail.api.controller;


import br.com.locaweb.locamail.api.dto.anexo.AnexoCadastroDto;
import br.com.locaweb.locamail.api.dto.anexo.AnexoExibicaoDto;
import br.com.locaweb.locamail.api.dto.anexoRespostaEmail.AnexoRespostaEmailCadastroDto;
import br.com.locaweb.locamail.api.dto.anexoRespostaEmail.AnexoRespostaEmailExibicaoDto;
import br.com.locaweb.locamail.api.model.AnexoRespostaEmail;
import br.com.locaweb.locamail.api.service.AnexoRespostaEmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/anexoRespostaEmail")
public class AnexoRespostaEmailController {

    @Autowired
    private AnexoRespostaEmailService anexoRespostaEmailService;

    @PostMapping("/criarAnexo")
    @ResponseStatus(HttpStatus.CREATED)
    public AnexoRespostaEmailExibicaoDto criarAnexo(@RequestBody @Valid AnexoRespostaEmailCadastroDto anexoRespostaEmailCadastroDto) {
        return anexoRespostaEmailService.criarAnexo(anexoRespostaEmailCadastroDto);
    }

    @DeleteMapping("/excluirAnexoPorIdRespostaEmail")
    @ResponseStatus(HttpStatus.OK)
    public void excluirAnexoPorIdRespostaEmail(@RequestParam("idRespostaEmail") Long id_resposta_email) {
        anexoRespostaEmailService.excluirAnexoPorIdRespostaEmail(id_resposta_email);
    }

    @GetMapping("/listarAnexosArrayBytePorIdRespostaEmail")
    @ResponseStatus(HttpStatus.OK)
    public List<byte[]> listarAnexosArrayBytePorIdRespostaEmail(@RequestParam("idRespostaEmail") Long id_resposta_email) {
        return anexoRespostaEmailService.listarAnexosArrayBytePorIdRespostaEmail(id_resposta_email);
    }
}
