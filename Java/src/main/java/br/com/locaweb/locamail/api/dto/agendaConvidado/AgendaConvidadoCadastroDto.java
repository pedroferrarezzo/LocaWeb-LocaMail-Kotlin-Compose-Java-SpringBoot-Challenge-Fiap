package br.com.locaweb.locamail.api.dto.agendaConvidado;

import jakarta.validation.constraints.NotNull;

public record AgendaConvidadoCadastroDto(

        Long id_agenda_convidado,

        @NotNull(message = "O ID da agenda é obrigatório!")
        Long id_agenda,

        @NotNull(message = "O ID do convidado é obrigatório!")
        Long id_convidado,

        @NotNull(message = "O grupo de repetição é obrigatório!")
        Integer grupo_repeticao

) {
}
