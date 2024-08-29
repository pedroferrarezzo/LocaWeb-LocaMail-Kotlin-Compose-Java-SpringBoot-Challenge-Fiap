package br.com.locaweb.locamail.api.controller;

import br.com.locaweb.locamail.api.dto.convidado.ConvidadoCadastroDto;
import br.com.locaweb.locamail.api.dto.convidado.ConvidadoExibicaoDto;
import br.com.locaweb.locamail.api.service.ConvidadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/convidado")
public class ConvidadoController {
    @Autowired
    private ConvidadoService convidadoService;

    @GetMapping("/verificarConvidadoExiste")
    @ResponseStatus(HttpStatus.OK)
    public String verificarConvidadoExiste(@RequestParam("email") String email) {
        return convidadoService.verificarConvidadoExiste(email);
    }

    @GetMapping("/listarConvidado")
    @ResponseStatus(HttpStatus.OK)
    public List<ConvidadoExibicaoDto> listarConvidado() {
        return convidadoService.listarConvidado();
    }

    @PostMapping("/criarConvidado")
    @ResponseStatus(HttpStatus.CREATED)
    public ConvidadoExibicaoDto criarConvidado(@RequestBody @Valid ConvidadoCadastroDto convidadoCadastroDto) {
        return convidadoService.criarConvidado(convidadoCadastroDto);
    }


}
