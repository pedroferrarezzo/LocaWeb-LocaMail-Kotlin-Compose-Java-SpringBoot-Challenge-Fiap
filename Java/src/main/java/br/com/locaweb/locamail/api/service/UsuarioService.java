package br.com.locaweb.locamail.api.service;

import br.com.locaweb.locamail.api.dto.usuario.UsuarioCadastroDto;
import br.com.locaweb.locamail.api.dto.usuario.UsuarioExibicaoDto;
import br.com.locaweb.locamail.api.model.Usuario;
import br.com.locaweb.locamail.api.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioExibicaoDto criarUsuario(UsuarioCadastroDto usuarioCadastroDto) {
        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(usuarioCadastroDto, usuario);
        Usuario usuarioPersistido = usuarioRepository.save(usuario);
        return new UsuarioExibicaoDto(usuarioPersistido);
    }

    public List<UsuarioExibicaoDto> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.listarUsuarios();
        if (usuarios == null) {
            return null;
        }
        List<UsuarioExibicaoDto> usuariosDto = usuarios.stream()
                .map(usuario -> new UsuarioExibicaoDto(usuario))
                .collect(Collectors.toList());
       return usuariosDto;
    }

    public void atualizaAutenticaUsuario(Long id_usuario, Boolean autenticado) {
        usuarioRepository.atualizaAutenticaUsuario(id_usuario, autenticado);
    }

    public List<UsuarioExibicaoDto> listarUsuariosAutenticados() {
        List<Usuario> usuarios = usuarioRepository.listarUsuariosAutenticados();
        if (usuarios == null) {
            return null;
        }
        List<UsuarioExibicaoDto> usuariosDto = usuarios.stream()
                .map(usuario -> new UsuarioExibicaoDto(usuario))
                .collect(Collectors.toList());
        return usuariosDto;
    }

    public UsuarioExibicaoDto retornaUsarioPorEmail(String email) {
        Usuario usuario = usuarioRepository.retornaUsarioPorEmail(email);
        if (usuario == null) {
            return null;
        }

        return new UsuarioExibicaoDto(usuario);
    }

    public UsuarioExibicaoDto retornaUsuarioPorId(Long id_usuario) {
        Usuario usuario = usuarioRepository.retornaUsuarioPorId(id_usuario);
        if (usuario == null) {
            return null;
        }
        return new UsuarioExibicaoDto(usuario);
    }

    public UsuarioExibicaoDto listarUsuarioSelecionado() {
        Usuario usuario = usuarioRepository.listarUsuarioSelecionado();
        if (usuario == null) {
            return null;
        }
        return new UsuarioExibicaoDto(usuario);
    }

    public void desselecionarUsuarioSelecionadoAtual() {
        usuarioRepository.desselecionarUsuarioSelecionadoAtual();
    }

    public void selecionarUsuario(Long id_usuario) {
        usuarioRepository.selecionarUsuario(id_usuario);
    }

    public List<UsuarioExibicaoDto> listarUsuariosNaoSelecionados() {
        List<Usuario> usuarios = usuarioRepository.listarUsuariosNaoSelecionados();
        if (usuarios == null) {
            return null;
        }
        List<UsuarioExibicaoDto> usuariosDto = usuarios.stream()
                .map(usuario -> new UsuarioExibicaoDto(usuario))
                .collect(Collectors.toList());
        return usuariosDto;
    }
}
