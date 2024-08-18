package br.com.locaweb.locamail.api.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UsuarioCadastroDto (

    Long id_usuario,

    @NotNull(message = "O email do usuário é obrigatório!")
    @Email(message = "O email deve estar no formato correto!")
    String email,

    @NotNull(message = "O nome do usuário é obrigatório!")
    String nome,

    @NotNull(message = "A senha do usuário é obrigatória!")
    String senha,

    @NotNull(message = "O status de autenticação do usuário é obrigatório!")
    Boolean autenticado,

    @NotNull(message = "O byte Array da imagem de perfil do usuário é obrigatório!")
    byte[] profile_image,

    @NotNull(message = "O status de seleção do usuário é obrigatório!")
    Boolean selected_user

) {

}
