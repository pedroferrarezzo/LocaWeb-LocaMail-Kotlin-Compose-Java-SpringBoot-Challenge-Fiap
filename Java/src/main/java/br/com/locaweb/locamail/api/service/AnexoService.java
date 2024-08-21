package br.com.locaweb.locamail.api.service;

import br.com.locaweb.locamail.api.dto.anexo.AnexoCadastroDto;
import br.com.locaweb.locamail.api.dto.anexo.AnexoExibicaoDto;
import br.com.locaweb.locamail.api.model.Anexo;
import br.com.locaweb.locamail.api.repository.AnexoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return anexoRepository.listarAnexosArrayBytePorIdEmail(id_email);
    }

    public void excluirAnexoPorIdEmail(Long id_email) {
        anexoRepository.excluirAnexoPorIdEmail(id_email);
    }
}
