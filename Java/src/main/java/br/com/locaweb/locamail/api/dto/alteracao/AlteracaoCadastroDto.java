package br.com.locaweb.locamail.api.dto.alteracao;

import jakarta.validation.constraints.NotNull;

public record AlteracaoCadastroDto(

        Long id_alteracao,

        @NotNull(message = "O ID do usuário é obrigatório!")
        Long alt_id_usuario,

        @NotNull(message = "O ID do email é obrigatório!")
        Long alt_id_email,

        Long id_pasta,

        @NotNull(message = "O status de importância é obrigatório!")
        Boolean importante,

        @NotNull(message = "O status de leitura é obrigatório!")
        Boolean lido,

        @NotNull(message = "O status de arquivamento é obrigatório!")
        Boolean arquivado,

        @NotNull(message = "O status de exclusão é obrigatório!")
        Boolean excluido,

        @NotNull(message = "O status de spam é obrigatório!")
        Boolean spam

) {

}

