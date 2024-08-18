package br.com.locaweb.locamail.api.dto.agendaConvidado;

import br.com.locaweb.locamail.api.model.AgendaConvidado;

public record AgendaConvidadoExibicaoDto(

        Long id_agenda_convidado,
        Long id_agenda,
        Long id_convidado,
        Integer grupo_repeticao

) {
    public AgendaConvidadoExibicaoDto(AgendaConvidado agendaConvidado) {
        this(
                agendaConvidado.getId_agenda_convidado(),
                agendaConvidado.getId_agenda(),
                agendaConvidado.getId_convidado(),
                agendaConvidado.getGrupo_repeticao()
        );
    }
}

