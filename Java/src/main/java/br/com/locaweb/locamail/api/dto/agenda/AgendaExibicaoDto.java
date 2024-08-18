package br.com.locaweb.locamail.api.dto.agenda;

import br.com.locaweb.locamail.api.model.Agenda;

public record AgendaExibicaoDto(

        Long id_agenda,
        String nome,
        String descritivo,
        Long id_usuario,
        Integer cor,
        String localizacao,
        Integer notificacao,
        String horario,
        String data,
        String proprietario,
        Boolean evento,
        Boolean tarefa,
        Integer repeticao,
        Integer grupo_repeticao,
        Boolean visivel,
        Long id_email

) {
    public AgendaExibicaoDto(Agenda agenda) {
        this(
                agenda.getId_agenda(),
                agenda.getNome(),
                agenda.getDescritivo(),
                agenda.getId_usuario(),
                agenda.getCor(),
                agenda.getLocalizacao(),
                agenda.getNotificacao(),
                agenda.getHorario(),
                agenda.getData(),
                agenda.getProprietario(),
                agenda.getEvento(),
                agenda.getTarefa(),
                agenda.getRepeticao(),
                agenda.getGrupo_repeticao(),
                agenda.getVisivel(),
                agenda.getId_email()
        );
    }
}

