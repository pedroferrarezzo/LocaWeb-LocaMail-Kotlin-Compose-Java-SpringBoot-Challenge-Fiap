package br.com.locaweb.locamail.api.dto.anexoRespostaEmail;

import br.com.locaweb.locamail.api.model.AnexoRespostaEmail;

public record AnexoRespostaEmailExibicaoDto(
        Long id_anexo_resposta_email,
        Long id_resposta_email,
        byte[] anexo
) {
    public AnexoRespostaEmailExibicaoDto(AnexoRespostaEmail anexoRespostaEmail) {
        this(
                anexoRespostaEmail.getId_anexo_resposta_email(),
                anexoRespostaEmail.getId_resposta_email(),
                anexoRespostaEmail.getAnexo()
        );
    }
}
