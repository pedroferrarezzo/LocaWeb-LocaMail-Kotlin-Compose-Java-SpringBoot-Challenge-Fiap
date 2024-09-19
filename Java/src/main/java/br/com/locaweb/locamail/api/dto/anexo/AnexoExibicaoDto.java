package br.com.locaweb.locamail.api.dto.anexo;

import br.com.locaweb.locamail.api.model.Anexo;
import jakarta.validation.constraints.NotNull;

public record AnexoExibicaoDto(
        Long id_anexo,


        Long id_email,

        byte[] anexo
) {

    public AnexoExibicaoDto(Anexo anexo) {
        this(
                anexo.getId_anexo(),
                anexo.getId_email(),
                anexo.getAnexo()
        );
    }
}
