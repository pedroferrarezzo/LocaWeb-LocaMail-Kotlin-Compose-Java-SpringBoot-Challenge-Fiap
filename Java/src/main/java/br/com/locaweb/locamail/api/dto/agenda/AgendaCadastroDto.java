package br.com.locaweb.locamail.api.dto.agenda;

import jakarta.validation.constraints.NotNull;

public record AgendaCadastroDto(

        Long id_agenda,

        Long id_email,

        @NotNull(message = "O nome da agenda é obrigatório!")
        String nome,

        @NotNull(message = "O descritivo da agenda é obrigatório!")
        String descritivo,

        @NotNull(message = "O ID do usuário é obrigatório!")
        Long id_usuario,

        @NotNull(message = "A cor da agenda é obrigatória!")
        Integer cor,

        @NotNull(message = "A localização da agenda é obrigatória!")
        String localizacao,

        @NotNull(message = "O status de notificação é obrigatório!")
        Integer notificacao,

        @NotNull(message = "O horário da agenda é obrigatório!")
        String horario,

        @NotNull(message = "A data da agenda é obrigatória!")
        String data,

        @NotNull(message = "O nome do proprietário da agenda é obrigatório!")
        String proprietario,

        @NotNull(message = "O status de evento é obrigatório!")
        Boolean evento,

        @NotNull(message = "O status de tarefa é obrigatório!")
        Boolean tarefa,

        @NotNull(message = "O status de repetição é obrigatório!")
        Integer repeticao,

        @NotNull(message = "O grupo de repetição é obrigatório!")
        Integer grupo_repeticao,

        @NotNull(message = "O status de visibilidade é obrigatório!")
        Boolean visivel
) {

}

