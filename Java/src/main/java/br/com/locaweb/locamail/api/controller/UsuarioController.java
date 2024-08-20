package br.com.locaweb.locamail.api.controller;

import br.com.locaweb.locamail.api.dto.usuario.UsuarioCadastroDto;
import br.com.locaweb.locamail.api.dto.usuario.UsuarioExibicaoDto;
import br.com.locaweb.locamail.api.dto.usuario.UsuarioExibicaoNoPasswdDto;
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

    @GetMapping("/listarUsuarioSelecionado")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioExibicaoDto listarUsuarioSelecionado() {
        return usuarioService.listarUsuarioSelecionado();
    }

    @PostMapping("/criarUsuario")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioExibicaoNoPasswdDto gravar(@RequestBody @Valid UsuarioCadastroDto usuarioCadastroDto) {
        return usuarioService.criarUsuario(usuarioCadastroDto);
    }

    @PatchMapping("/desselecionarUsuarioSelecionadoAtual")
    @ResponseStatus(HttpStatus.OK)
    public void desselecionarUsuarioSelecionadoAtual() {
        usuarioService.desselecionarUsuarioSelecionadoAtual();
    }

    @PatchMapping("/selecionarUsuario")
    @ResponseStatus(HttpStatus.OK)
    public void selecionarUsuario(@RequestParam("idUsuario") Long id_usuario) {
        usuarioService.selecionarUsuario(id_usuario);
    }

    @PatchMapping("/atualizaAutenticaUsuario")
    @ResponseStatus(HttpStatus.OK)
    public void atualizaAutenticaUsuario(@RequestParam("idUsuario") Long id_usuario, @RequestParam("autenticado") Boolean autenticado) {
        usuarioService.atualizaAutenticaUsuario(id_usuario, autenticado);
    }


}
