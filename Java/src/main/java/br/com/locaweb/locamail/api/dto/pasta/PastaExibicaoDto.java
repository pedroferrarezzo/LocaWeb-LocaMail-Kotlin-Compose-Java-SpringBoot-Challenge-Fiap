package br.com.locaweb.locamail.api.dto.pasta;

import br.com.locaweb.locamail.api.model.Pasta;

public record PastaExibicaoDto(

        Long id_pasta,
        Long id_usuario,
        String nome

) {
    public PastaExibicaoDto(Pasta pasta) {
        this(
                pasta.getId_pasta(),
                pasta.getId_usuario(),
                pasta.getNome()
        );
    }
}

