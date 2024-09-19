package br.com.locaweb.locamail.api.controller;

import br.com.locaweb.locamail.api.dto.anexo.AnexoCadastroDto;
import br.com.locaweb.locamail.api.dto.anexo.AnexoExibicaoDto;
import br.com.locaweb.locamail.api.dto.anexoRespostaEmail.AnexoRespostaEmailCadastroDto;
import br.com.locaweb.locamail.api.dto.anexoRespostaEmail.AnexoRespostaEmailExibicaoDto;
import br.com.locaweb.locamail.api.dto.respostaEmail.RespostaEmailCadastroDto;
import br.com.locaweb.locamail.api.dto.respostaEmail.RespostaEmailExibicaoDto;
import br.com.locaweb.locamail.api.service.AnexoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Blob;
import java.util.List;

@RestController
@RequestMapping("/anexo")
public class AnexoController {

    @Autowired
    private AnexoService anexoService;

    @PostMapping("/criarAnexo")
    @ResponseStatus(HttpStatus.CREATED)
    public AnexoExibicaoDto criarAnexo(@RequestBody @Valid AnexoCadastroDto anexoCadastroDto) {
        return anexoService.criarAnexo(anexoCadastroDto);
    }

    @GetMapping("/listarAnexosIdEmail")
    @ResponseStatus(HttpStatus.OK)
    public List<Long> listarAnexosIdEmail() {
        return anexoService.listarAnexosIdEmail();
    }

    @GetMapping("/listarAnexosArraybytePorIdEmail")
    @ResponseStatus(HttpStatus.OK)
    public List<byte[]> listarAnexosArraybytePorIdEmail(@RequestParam("idEmail") Long id_email) {
        return anexoService.listarAnexosArraybytePorIdEmail(id_email);
    }

    @DeleteMapping("/excluirAnexoPorIdEmail")
    @ResponseStatus(HttpStatus.OK)
    public void excluirAnexoPorIdEmail(@RequestParam("idEmail") Long id_email) {
        anexoService.excluirAnexoPorIdEmail(id_email);
    }
}
