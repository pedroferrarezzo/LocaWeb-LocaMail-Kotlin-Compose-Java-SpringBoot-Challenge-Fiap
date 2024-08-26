package br.com.locaweb.locamail.api.dto.email;

import br.com.locaweb.locamail.api.model.Email;
import jakarta.validation.constraints.NotNull;

public record EmailExibicaoDto(

        Long id_email,
        Long id_usuario,
        String remetente,
        String destinatario,
        String cc,
        String cco,
        String assunto,
        String corpo,
        Boolean editavel,
        Boolean enviado,
        String horario,
        String data,
        Boolean agenda_atrelada

) {
        public EmailExibicaoDto(Email email) {
                this(
                        email.getId_email(),
                        email.getId_usuario(),
                        email.getRemetente(),
                        email.getDestinatario(),
                        email.getCc(),
                        email.getCco(),
                        email.getAssunto(),
                        email.getCorpo(),
                        email.getEditavel(),
                        email.getEnviado(),
                        email.getHorario(),
                        email.getData(),
                        email.getAgenda_atrelada()
                );
        }
}


