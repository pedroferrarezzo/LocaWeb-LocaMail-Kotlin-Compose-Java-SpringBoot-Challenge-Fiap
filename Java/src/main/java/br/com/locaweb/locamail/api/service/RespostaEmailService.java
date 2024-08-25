package br.com.locaweb.locamail.api.service;

import br.com.locaweb.locamail.api.dto.respostaEmail.RespostaEmailCadastroDto;
import br.com.locaweb.locamail.api.dto.respostaEmail.RespostaEmailExibicaoDto;
import br.com.locaweb.locamail.api.model.RespostaEmail;
import br.com.locaweb.locamail.api.repository.RespostaEmailRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RespostaEmailService {


    @Autowired
    private RespostaEmailRepository respostaEmailRepository;

    public RespostaEmailExibicaoDto criarRespostaEmail(RespostaEmailCadastroDto respostaEmailCadastroDto) {
        RespostaEmail respostaEmail = new RespostaEmail();
        BeanUtils.copyProperties(respostaEmailCadastroDto, respostaEmail);
        RespostaEmail respostaEmailPersistido = respostaEmailRepository.save(respostaEmail);
        return new RespostaEmailExibicaoDto(respostaEmailPersistido);

    }

    public RespostaEmailExibicaoDto listarRespostaEmailPorIdRespostaEmail(Long id_resposta_email) {
        return new RespostaEmailExibicaoDto(respostaEmailRepository.listarRespostaEmailPorIdRespostaEmail(id_resposta_email));
    }

    public RespostaEmailExibicaoDto atualizarRespostaEmail(RespostaEmailCadastroDto respostaEmailCadastroDto) {
        RespostaEmail respostaEmail = new RespostaEmail();
        BeanUtils.copyProperties(respostaEmailCadastroDto, respostaEmail);
        return new RespostaEmailExibicaoDto(respostaEmailRepository.save(respostaEmail));
    }

    public void excluirRespostaEmail(Long id_resposta_email) throws Exception {

        Optional<RespostaEmail> repostaEmail = respostaEmailRepository.findById(id_resposta_email);

        if (repostaEmail.isPresent()) {
            respostaEmailRepository.deleteById(id_resposta_email);
        }
        else {
            throw new Exception("Resposta Email não encontrada");
        }
    }
    public List<RespostaEmailExibicaoDto> listarRespostasEmailPorIdEmail(Long id_email) {
        List<RespostaEmail> respostaEmails = respostaEmailRepository.listarRespostasEmailPorIdEmail(id_email);

        if (respostaEmails == null) {
            return null;
        }
        List<RespostaEmailExibicaoDto> respostaEmailExibicaoDtos = respostaEmails.stream()
                .map(respostaEmail -> new RespostaEmailExibicaoDto(respostaEmail))
                .collect(Collectors.toList());
        return respostaEmailExibicaoDtos;
    }

    public void excluirRespostaEmailPorIdEmail(Long id_email) {
        respostaEmailRepository.excluirRespostaEmailPorIdEmail(id_email);
    }
}
