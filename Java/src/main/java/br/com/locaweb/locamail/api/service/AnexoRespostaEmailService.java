package br.com.locaweb.locamail.api.service;

import br.com.locaweb.locamail.api.dto.anexoRespostaEmail.AnexoRespostaEmailCadastroDto;
import br.com.locaweb.locamail.api.dto.anexoRespostaEmail.AnexoRespostaEmailExibicaoDto;
import br.com.locaweb.locamail.api.model.AnexoRespostaEmail;
import br.com.locaweb.locamail.api.repository.AnexoRespostaEmailRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnexoRespostaEmailService {

    @Autowired
    private AnexoRespostaEmailRepository anexoRespostaEmailRepository;

    public AnexoRespostaEmailExibicaoDto criarAnexo(AnexoRespostaEmailCadastroDto anexoRespostaEmailCadastroDto) {
        AnexoRespostaEmail anexoRespostaEmail = new AnexoRespostaEmail();
        BeanUtils.copyProperties(anexoRespostaEmailCadastroDto, anexoRespostaEmail);
        return new AnexoRespostaEmailExibicaoDto(anexoRespostaEmailRepository.save(anexoRespostaEmail));
    }

    public List<Long> listarAnexosIdRespostaEmail() {
        return anexoRespostaEmailRepository.listarAnexosIdRespostaEmail();
    }


    public Long verificarAnexoExistentePorIdRespostaEmail(Long id_resposta_email) {
        return anexoRespostaEmailRepository.verificarAnexoExistentePorIdEmail(id_resposta_email);
    }


    public List<byte[]> listarAnexosArrayBytePorIdRespostaEmail(Long id_resposta_email)

    {
        return anexoRespostaEmailRepository.listarAnexosArrayBytePorIdRespostaEmail(id_resposta_email);

    }


    public void excluirAnexoPorIdRespostaEmail(Long id_resposta_email) {
        anexoRespostaEmailRepository.excluirAnexoPorIdRespostaEmail(id_resposta_email);
    }
}
