package br.com.locaweb.locamail.api.service;

import br.com.locaweb.locamail.api.dto.pasta.PastaCadastroDto;
import br.com.locaweb.locamail.api.dto.pasta.PastaExibicaoDto;
import br.com.locaweb.locamail.api.model.Pasta;
import br.com.locaweb.locamail.api.repository.PastaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PastaService {
    @Autowired
    private PastaRepository pastaRepository;

    public PastaExibicaoDto criarPasta(PastaCadastroDto pastaCadastroDto) {
        Pasta pasta = new Pasta();
        BeanUtils.copyProperties(pastaCadastroDto, pasta);
        return new PastaExibicaoDto(pastaRepository.save(pasta));
    }

    public List<PastaExibicaoDto> listarPastasPorIdUsuario(Long id_usuario) {
        return pastaRepository.listarPastasPorIdUsuario(id_usuario).stream()
                .map(pasta -> new PastaExibicaoDto(pasta))
                .collect(Collectors.toList());
    }

    public void excluirPasta(PastaCadastroDto pastaCadastroDto) {
        Pasta pasta = new Pasta();
        BeanUtils.copyProperties(pastaCadastroDto, pasta);
        pastaRepository.delete(pasta = pasta);
    }



}
