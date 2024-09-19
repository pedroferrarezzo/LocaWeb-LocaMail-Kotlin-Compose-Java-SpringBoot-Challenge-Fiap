package br.com.locaweb.locamail.api.service;

import br.com.locaweb.locamail.api.dto.convidado.ConvidadoCadastroDto;
import br.com.locaweb.locamail.api.dto.convidado.ConvidadoExibicaoDto;
import br.com.locaweb.locamail.api.dto.respostaEmail.RespostaEmailExibicaoDto;
import br.com.locaweb.locamail.api.model.Convidado;
import br.com.locaweb.locamail.api.repository.AlteracaoRepository;
import br.com.locaweb.locamail.api.repository.ConvidadoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConvidadoService {

    @Autowired
    private ConvidadoRepository convidadoRepository;

    public ConvidadoExibicaoDto criarConvidado(ConvidadoCadastroDto convidadoCadastroDto) {
        Convidado convidado = new Convidado();
        BeanUtils.copyProperties(convidadoCadastroDto, convidado);
        return new ConvidadoExibicaoDto(convidadoRepository.save(convidado));
    }

    public List<ConvidadoExibicaoDto> listarConvidado() {
        List<Convidado> convidados = convidadoRepository.listarConvidado();
        if (convidados == null) {
            return null;
        }
        List<ConvidadoExibicaoDto> convidadoExibicaoDtos = convidados.stream()
                .map(convidado -> new ConvidadoExibicaoDto(convidado))
                .collect(Collectors.toList());
        return convidadoExibicaoDtos;
    }

    public ConvidadoExibicaoDto listarConvidadoPorId(Long id_convidado) {
        return new ConvidadoExibicaoDto(convidadoRepository.listarConvidadoPorId(id_convidado));
    }

    public String verificarConvidadoExiste(String email) {
        return convidadoRepository.verificarConvidadoExiste(email);
    }
}
