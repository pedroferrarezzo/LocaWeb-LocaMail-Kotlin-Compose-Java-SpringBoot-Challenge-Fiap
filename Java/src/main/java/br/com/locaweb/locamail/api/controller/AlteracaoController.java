package br.com.locaweb.locamail.api.controller;

import br.com.locaweb.locamail.api.dto.alteracao.AlteracaoCadastroDto;
import br.com.locaweb.locamail.api.dto.alteracao.AlteracaoExibicaoDto;
import br.com.locaweb.locamail.api.service.AlteracaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PatchMapping("/atualizarExcluidoPorIdEmailEIdusuario")
    @ResponseStatus(HttpStatus.OK)
    public void atualizarExcluidoPorIdEmailEIdusuario(@RequestParam("excluido") Boolean excluido, @RequestParam("idEmail") Long id_email, @RequestParam("idUsuario") Long id_usuario) {
        alteracaoService.atualizarExcluidoPorIdEmailEIdUsuario(excluido, id_email, id_usuario);
    }

    @PatchMapping("/atualizarSpamPorIdEmailEIdusuario")
    @ResponseStatus(HttpStatus.OK)
    public void atualizarSpamPorIdEmailEIdusuario(@RequestParam("spam") Boolean spam, @RequestParam("idEmail") Long id_email, @RequestParam("idUsuario") Long id_usuario) {
        alteracaoService.atualizarSpamPorIdEmailEIdUsuario(spam, id_email, id_usuario);
    }

    @PatchMapping("/atualizarArquivadoPorIdEmailEIdusuario")
    @ResponseStatus(HttpStatus.OK)
    public void atualizarArquivadoPorIdEmailEIdusuario(@RequestParam("arquivado") Boolean arquivado, @RequestParam("idEmail") Long id_email, @RequestParam("idUsuario") Long id_usuario) {
        alteracaoService.atualizarArquivadoPorIdEmailEIdUsuario(arquivado, id_email, id_usuario);
    }


    @PatchMapping("/atualizarPastaPorIdEmailEIdUsuario")
    @ResponseStatus(HttpStatus.OK)
    public void atualizarPastaPorIdEmailEIdUsuario(@RequestParam(value = "pasta", required = false) Long pasta, @RequestParam("idEmail") Long id_email, @RequestParam("idUsuario") Long id_usuario) {
        alteracaoService.atualizarPastaPorIdEmailEIdUsuario(pasta, id_email, id_usuario);
    }


    @PatchMapping("/atualizarImportantePorIdEmail")
    @ResponseStatus(HttpStatus.OK)
    public void atualizarImportantePorIdEmail(@RequestParam("importante") Boolean importante, @RequestParam("idEmail") Long id_email, @RequestParam("idUsuario") Long id_usuario) {
        alteracaoService.atualizarImportantePorIdEmail(importante, id_email, id_usuario);
    }

    @PatchMapping("/atualizarPastaPorIdAlteracao")
    @ResponseStatus(HttpStatus.OK)
    public void atualizarPastaPorIdAlteracao(@RequestParam(value = "pasta", required = false) Long pasta, @RequestParam("idAlteracao") Long id_alteracao) {
        alteracaoService.atualizarPastaPorIdAlteracao(pasta, id_alteracao);
    }

    @GetMapping("/listarAlteracaoPorIdUsuarioEIdPasta")
    @ResponseStatus(HttpStatus.OK)
    public List<AlteracaoExibicaoDto> listarAlteracaoPorIdUsuarioEIdPasta(@RequestParam("idUsuario") Long id_usuario, @RequestParam("pasta") Long pasta) {
        return alteracaoService.listarAlteracaoPorIdUsuarioEIdPasta(id_usuario, pasta);
    }

    @GetMapping("/listarAltIdUsuarioPorIdEmail")
    @ResponseStatus(HttpStatus.OK)
    public List<Long> listarAltIdUsuarioPorIdEmail(@RequestParam("idEmail") Long id_email) {
        return alteracaoService.listarAltIdUsuarioPorIdEmail(id_email);
    }

    @GetMapping("/listarAlteracaoPorIdEmailEIdUsuario")
    @ResponseStatus(HttpStatus.OK)
    public AlteracaoExibicaoDto listarAlteracaoPorIdEmailEIdUsuario(@RequestParam("idEmail") Long id_email, @RequestParam("idUsuario") Long id_usuario) {
        return alteracaoService.listarAlteracaoPorIdEmailEIdUsuario(id_email, id_usuario);
    }

    @GetMapping("/listarAlteracaoPorIdEmail")
    @ResponseStatus(HttpStatus.OK)
    public List<AlteracaoExibicaoDto> listarAlteracaoPorIdEmail(@RequestParam("idEmail") Long id_email) {
        return alteracaoService.listarAlteracaoPorIdEmail(id_email);
    }


    @GetMapping("/verificarLidoPorIdEmailEIdUsuario")
    @ResponseStatus(HttpStatus.OK)
    public Boolean verificarLidoPorIdEmailEIdUsuario(@RequestParam("idEmail") Long id_email, @RequestParam("idUsuario") Long id_usuario) {
        return alteracaoService.verificarLidoPorIdEmailEIdUsuario(id_email, id_usuario);
    }

    @GetMapping("/verificarExcluidoPorIdEmailEIdUsuario")
    @ResponseStatus(HttpStatus.OK)
    public Boolean verificarExcluidoPorIdEmailEIdUsuario(@RequestParam("idEmail") Long id_email, @RequestParam("idUsuario") Long id_usuario) {
        return alteracaoService.verificarExcluidoPorIdEmailEIdUsuario(id_email, id_usuario);
    }

    @GetMapping("/verificarSpamPorIdEmailEIdUsuario")
    @ResponseStatus(HttpStatus.OK)
    public Boolean verificarSpamPorIdEmailEIdUsuario(@RequestParam("idEmail") Long id_email, @RequestParam("idUsuario") Long id_usuario) {
        return alteracaoService.verificarSpamPorIdEmailEIdUsuario(id_email, id_usuario);
    }

    @GetMapping("/verificarArquivadoPorIdEmailEIdUsuario")
    @ResponseStatus(HttpStatus.OK)
    public Boolean verificarArquivadoPorIdEmailEIdUsuario(@RequestParam("idEmail") Long id_email, @RequestParam("idUsuario") Long id_usuario) {
        return alteracaoService.verificarArquivadoPorIdEmailEIdUsuario(id_email, id_usuario);
    }

    @GetMapping("/verificarImportantePorIdEmailEIdUsuario")
    @ResponseStatus(HttpStatus.OK)
    public Boolean verificarImportantePorIdEmailEIdUsuario(@RequestParam("idEmail") Long id_email, @RequestParam("idUsuario") Long id_usuario) {
        return alteracaoService.verificarImportantePorIdEmailEIdUsuario(id_email, id_usuario);
    }



    @DeleteMapping("/excluiAlteracaoPorIdEmailEIdUsuario")
    @ResponseStatus(HttpStatus.OK)
    public void excluiAlteracaoPorIdEmailEIdUsuario(@RequestParam("idEmail") Long id_email, @RequestParam("idUsuario") Long id_usuario) {
        alteracaoService.excluiAlteracaoPorIdEmailEIdUsuario(id_email, id_usuario);
    }
}
