package br.com.locaweb.locamail.api.dto.usuario;

import br.com.locaweb.locamail.api.model.Usuario;
import jakarta.validation.constraints.NotNull;

public record UsuarioExibicaoDto(

    Long id_usuario,
    String email,

    String nome,

    Boolean autenticado,
    String senha,

    byte[] profile_image,

    Boolean selected_user

) {
    public UsuarioExibicaoDto(Usuario usuario) {
        this(
                usuario.getId_usuario(),
                usuario.getEmail(),
                usuario.getNome(),
                usuario.getAutenticado(),
                usuario.getSenha(),
                usuario.getProfile_image(),
                usuario.getSelected_user()
        );
    }
}
