package br.com.locaweb.locamail.api.dto.alteracao;

import br.com.locaweb.locamail.api.model.Alteracao;
import jakarta.validation.constraints.NotNull;

public record AlteracaoExibicaoDto(

        Long id_alteracao,
        Long alt_id_usuario,
        Long alt_id_email,
        Long id_pasta,
        Boolean importante,
        Boolean lido,
        Boolean arquivado,
        Boolean excluido,
        Boolean spam

) {
        public AlteracaoExibicaoDto(Alteracao alteracao) {
                this(
                        alteracao.getId_alteracao(),
                        alteracao.getAlt_id_usuario(),
                        alteracao.getAlt_id_email(),
                        alteracao.getId_pasta(),
                        alteracao.getImportante(),
                        alteracao.getLido(),
                        alteracao.getArquivado(),
                        alteracao.getExcluido(),
                        alteracao.getSpam()
                );
        }
}


