package br.com.locaweb.locamail.api.dto.usuario;

import br.com.locaweb.locamail.api.model.Usuario;

public record UsuarioExibicaoNoPasswdDto(

    Long id_usuario,
    String email,

    String nome,

    Boolean autenticado,

    byte[] profile_image,

    Boolean selected_user

) {
    public UsuarioExibicaoNoPasswdDto(Usuario usuario) {
        this(
                usuario.getId_usuario(),
                usuario.getEmail(),
                usuario.getNome(),
                usuario.getAutenticado(),
                usuario.getProfile_image(),
                usuario.getSelected_user()
        );
    }
}
