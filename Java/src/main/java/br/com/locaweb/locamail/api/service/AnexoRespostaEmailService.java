package br.com.locaweb.locamail.api.service;

import br.com.locaweb.locamail.api.dto.anexoRespostaEmail.AnexoRespostaEmailCadastroDto;
import br.com.locaweb.locamail.api.dto.anexoRespostaEmail.AnexoRespostaEmailExibicaoDto;
import br.com.locaweb.locamail.api.model.AnexoRespostaEmail;
import br.com.locaweb.locamail.api.repository.AnexoRespostaEmailRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
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
        List<Blob> blobList = anexoRespostaEmailRepository.listarAnexosArrayBytePorIdRespostaEmail(id_resposta_email);
        List<byte[]> anexos = new ArrayList<>();

        for (Blob blob : blobList) {
            try {
                byte[] bytes = blob.getBytes(1, (int) blob.length());
                anexos.add(bytes);
            } catch (SQLException ignored) {
            }
        }

        return anexos;

    }


    public void excluirAnexoPorIdRespostaEmail(Long id_resposta_email) {
        anexoRespostaEmailRepository.excluirAnexoPorIdRespostaEmail(id_resposta_email);
    }
}
