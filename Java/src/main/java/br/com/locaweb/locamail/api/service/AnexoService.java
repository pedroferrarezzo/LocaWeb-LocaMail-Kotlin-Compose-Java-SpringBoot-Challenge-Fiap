package br.com.locaweb.locamail.api.service;

import br.com.locaweb.locamail.api.dto.anexo.AnexoCadastroDto;
import br.com.locaweb.locamail.api.dto.anexo.AnexoExibicaoDto;
import br.com.locaweb.locamail.api.model.Anexo;
import br.com.locaweb.locamail.api.repository.AnexoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnexoService {

    @Autowired
    private AnexoRepository anexoRepository;

    public AnexoExibicaoDto criarAnexo(AnexoCadastroDto anexoCadastroDto) {
        Anexo anexo = new Anexo();
        BeanUtils.copyProperties(anexoCadastroDto, anexo);
        return new AnexoExibicaoDto(anexoRepository.save(anexo));
    }

    public List<Long> listarAnexosIdEmail() {
        return anexoRepository.listarAnexosIdEmail();
    }

    public Long verificarAnexoExistentePorIdEmail(Long id_email) {
        return anexoRepository.verificarAnexoExistentePorIdEmail(id_email);
    }

    public List<byte[]> listarAnexosArraybytePorIdEmail(Long id_email) {
        List<Blob> blobList = anexoRepository.listarAnexosArrayBytePorIdEmail(id_email);
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

    public void excluirAnexoPorIdEmail(Long id_email) {
        anexoRepository.excluirAnexoPorIdEmail(id_email);
    }
}
