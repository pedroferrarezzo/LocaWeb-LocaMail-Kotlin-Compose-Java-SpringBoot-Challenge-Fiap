package br.com.locaweb.locamail.api.dto.respostaEmail;

import br.com.locaweb.locamail.api.model.RespostaEmail;

public record RespostaEmailExibicaoDto(

        Long id_resposta_email,
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
        String data

) {
    public RespostaEmailExibicaoDto(RespostaEmail respostaEmail) {
        this(
                respostaEmail.getId_resposta_email(),
                respostaEmail.getId_email(),
                respostaEmail.getId_usuario(),
                respostaEmail.getRemetente(),
                respostaEmail.getDestinatario(),
                respostaEmail.getCc(),
                respostaEmail.getCco(),
                respostaEmail.getAssunto(),
                respostaEmail.getCorpo(),
                respostaEmail.getEditavel(),
                respostaEmail.getEnviado(),
                respostaEmail.getHorario(),
                respostaEmail.getData()
        );
    }
}

