package br.com.locaweb.locamail.api.service;

import br.com.locaweb.locamail.api.dto.pasta.PastaCadastroDto;
import br.com.locaweb.locamail.api.dto.pasta.PastaExibicaoDto;
import br.com.locaweb.locamail.api.model.Pasta;
import br.com.locaweb.locamail.api.repository.PastaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
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

    public void excluirPasta(Long id_pasta) throws Exception {
        Optional<Pasta> pasta = pastaRepository.findById(id_pasta);

        if (pasta.isPresent()) {
            pastaRepository.deleteById(id_pasta);
        }
        else {
            throw new Exception("Pasta não encontrada");
        }
    }



}
