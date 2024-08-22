package br.com.locaweb.locamail.api.service;

import br.com.locaweb.locamail.api.dto.alteracao.AlteracaoCadastroDto;
import br.com.locaweb.locamail.api.dto.alteracao.AlteracaoExibicaoDto;
import br.com.locaweb.locamail.api.model.Alteracao;
import br.com.locaweb.locamail.api.repository.AlteracaoRepository;
import br.com.locaweb.locamail.api.repository.EmailRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlteracaoService {

    @Autowired
    private AlteracaoRepository alteracaoRepository;

    public AlteracaoExibicaoDto criarAlteracao(AlteracaoCadastroDto alteracaoCadastroDto) {
        Alteracao alteracao = new Alteracao();
        BeanUtils.copyProperties(alteracaoCadastroDto, alteracao);
        Alteracao alteracaoPersistida = alteracaoRepository.save(alteracao);
        return new AlteracaoExibicaoDto(alteracaoPersistida);
    }

    public void atualizarImportantePorIdEmail(Boolean importante, Long idEmail, Long idUsuario) {
        alteracaoRepository.atualizarImportantePorIdEmailEIdUsuario(importante, idEmail, idUsuario);
    }

    public AlteracaoExibicaoDto listarAlteracaoPorIdEmailEIdUsuario(Long idEmail, Long idUsuario) {
        return new AlteracaoExibicaoDto(alteracaoRepository.listarAlteracaoPorIdEmailEIdUsuario(idEmail, idUsuario));
    }

    public Boolean verificarLidoPorIdEmailEIdUsuario(Long idEmail, Long idUsuario) {
        return alteracaoRepository.verificarLidoPorIdEmailEIdUsuario(idEmail, idUsuario);
    }

    public List<AlteracaoExibicaoDto> listarAlteracaoPorIdEmail(Long idEmail) {
        List<Alteracao> alteracaos = alteracaoRepository.listarAlteracaoPorIdEmail(idEmail);
        if (alteracaos == null) {
            return null;
        }
        List<AlteracaoExibicaoDto> alteracaoExibicaoDtos = alteracaos.stream()
                .map(alteracao -> new AlteracaoExibicaoDto(alteracao))
                .collect(Collectors.toList());

        return alteracaoExibicaoDtos;
    }

    public List<AlteracaoExibicaoDto> listarAlteracaoPorIdUsuarioEIdPasta(Long idUsuario, Long idPasta) {
        List<Alteracao> alteracaos = alteracaoRepository.listarAlteracaoPorIdUsuarioEIdPasta(idUsuario, idPasta);
        if (alteracaos == null) {
            return null;
        }
        List<AlteracaoExibicaoDto> alteracaoExibicaoDtos = alteracaos.stream()
                .map(alteracao -> new AlteracaoExibicaoDto(alteracao))
                .collect(Collectors.toList());
        return alteracaoExibicaoDtos;
    }

    public List<Long> listarAltIdUsuarioPorIdEmail(Long idEmail) {
        return alteracaoRepository.listarAltIdUsuarioPorIdEmail(idEmail);
    }

    public void atualizarArquivadoPorIdEmailEIdUsuario(Boolean arquivado, Long idEmail, Long idUsuario) {
        alteracaoRepository.atualizarArquivadoPorIdEmailEIdUsuario(arquivado, idEmail, idUsuario);
    }

    public void atualizarLidoPorIdEmailEIdUsuario(Boolean lido, Long idEmail, Long idUsuario) {
        alteracaoRepository.atualizarLidoPorIdEmailEIdUsuario(lido, idEmail, idUsuario);
    }

    public void atualizarExcluidoPorIdEmailEIdUsuario(Boolean excluido, Long idEmail, Long idUsuario) {
        alteracaoRepository.atualizarExcluidoPorIdEmailEIdUsuario(excluido, idEmail, idUsuario);
    }

    public void atualizarSpamPorIdEmailEIdUsuario(Boolean spam, Long idEmail, Long idUsuario) {
        alteracaoRepository.atualizarSpamPorIdEmailEIdUsuario(spam, idEmail, idUsuario);
    }

    public void atualizarPastaPorIdEmailEIdUsuario(Long pasta, Long idEmail, Long idUsuario) {
        alteracaoRepository.atualizarPastaPorIdEmailEIdUsuario(pasta, idEmail, idUsuario);
    }

    public void atualizarPastaPorIdAlteracao(Long pasta, Long idAlteracao) {
        alteracaoRepository.atualizarPastaPorIdAlteracao(pasta, idAlteracao);
    }

    public void excluiAlteracaoPorIdEmailEIdUsuario(Long idEmail, Long idUsuario) {
        alteracaoRepository.excluiAlteracaoPorIdEmailEIdUsuario(idEmail, idUsuario);
    }

    public Boolean verificarImportantePorIdEmailEIdUsuario(Long idEmail, Long idUsuario) {
        return alteracaoRepository.verificarImportantePorIdEmailEIdUsuario(idEmail, idUsuario);
    }

    public Long verificarPastaPorIdEmailEIdUsuario(Long idEmail, Long idUsuario) {
        return alteracaoRepository.verificarPastaPorIdEmailEIdUsuario(idEmail, idUsuario);
    }

    public Boolean verificarExcluidoPorIdEmailEIdUsuario(Long idEmail, Long idUsuario) {
        return alteracaoRepository.verificarExcluidoPorIdEmailEIdUsuario(idEmail, idUsuario);
    }

    public Boolean verificarArquivadoPorIdEmailEIdUsuario(Long idEmail, Long idUsuario) {
        return alteracaoRepository.verificarArquivadoPorIdEmailEIdUsuario(idEmail, idUsuario);
    }

    public Boolean verificarSpamPorIdEmailEIdUsuario(Long idEmail, Long idUsuario) {
        return alteracaoRepository.verificarSpamPorIdEmailEIdUsuario(idEmail, idUsuario);
    }
}
