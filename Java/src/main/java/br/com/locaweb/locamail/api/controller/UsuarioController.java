package br.com.locaweb.locamail.api.controller;

import br.com.locaweb.locamail.api.dto.UsuarioCadastroDto;
import br.com.locaweb.locamail.api.dto.UsuarioExibicaoDto;
import br.com.locaweb.locamail.api.model.Usuario;
import br.com.locaweb.locamail.api.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/retornaUsarioPorEmail")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioExibicaoDto retornaUsarioPorEmail(@RequestParam("email") String email) {
        return usuarioService.retornaUsarioPorEmail(email);
    }

    @PostMapping("/criarUsuario")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioExibicaoDto gravar(@RequestBody @Valid UsuarioCadastroDto usuarioCadastroDto) {
        return usuarioService.criarUsuario(usuarioCadastroDto);
    }

}
