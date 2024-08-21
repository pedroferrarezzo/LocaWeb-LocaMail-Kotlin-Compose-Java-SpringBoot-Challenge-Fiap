package br.com.locaweb.locamail.api.controller;

import br.com.locaweb.locamail.api.dto.pasta.PastaCadastroDto;
import br.com.locaweb.locamail.api.dto.pasta.PastaExibicaoDto;
import br.com.locaweb.locamail.api.service.PastaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pasta")
public class PastaController {

    @Autowired
    private PastaService pastaService;

    @GetMapping("/listarPastasPorIdUsuario")
    @ResponseStatus(HttpStatus.OK)
    public List<PastaExibicaoDto> listarPastasPorIdUsuario(@RequestParam("idUsuario") Long id_usuario) {
        return pastaService.listarPastasPorIdUsuario(id_usuario);
    }

    @PostMapping("/criarPasta")
    @ResponseStatus(HttpStatus.OK)
    public PastaExibicaoDto criarPasta(@RequestBody @Valid PastaCadastroDto pastaCadastroDto) {
        return pastaService.criarPasta(pastaCadastroDto);
    }

    @DeleteMapping("/excluirPasta")
    @ResponseStatus(HttpStatus.OK)
    public void excluirPasta(@RequestParam("pasta") Long pasta) throws Exception {
        pastaService.excluirPasta(pasta);
    }

}
