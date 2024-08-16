package br.com.locaweb.locamail.api.repository;

import br.com.locaweb.locamail.api.model.Alteracao;
import br.com.locaweb.locamail.api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AlteracaoRepository extends JpaRepository<Usuario, Long> {

    @Query(value = "SELECT * FROM T_LCW_ALTERACAO where alt_id_email = :id_email AND alt_id_usuario = :id_usuario", nativeQuery = true)
    public Alteracao listarAlteracaoPorIdEmailEIdUsuario(Long id_email, Long id_usuario);

    @Query(value = "SELECT * FROM T_LCW_ALTERACAO where alt_id_usuario = :id_usuario and id_pasta = :id_pasta", nativeQuery = true)
    public List<Alteracao> listarAlteracaoPorIdUsuarioEIdPasta(Long id_usuario, Long id_pasta);

    @Query(value = "SELECT * FROM T_LCW_ALTERACAO where alt_id_email = :id_email", nativeQuery = true)
    public List<Alteracao> listarAlteracaoPorIdEmail(Long id_email);

    @Query(value = "SELECT alt_id_usuario FROM T_LCW_ALTERACAO where alt_id_email = :id_email", nativeQuery = true)
    public List<Long> listarAltIdUsuarioPorIdEmail(Long id_email);

    @Query(value = "SELECT importante FROM T_LCW_ALTERACAO where alt_id_email = :id_email AND alt_id_usuario = :id_usuario", nativeQuery = true)
    public Boolean verificarImportantePorIdEmailEIdUsuario(Long id_email, Long id_usuario);

    @Query(value = "SELECT lido FROM T_LCW_ALTERACAO where alt_id_email = :id_email AND alt_id_usuario = :id_usuario", nativeQuery = true)
    public Boolean verificarLidoPorIdEmailEIdUsuario(Long id_email, Long id_usuario);

    @Query(value = "SELECT arquivado FROM T_LCW_ALTERACAO where alt_id_email = :id_email AND alt_id_usuario = :id_usuario", nativeQuery = true)
    public Boolean verificarArquivadoPorIdEmailEIdUsuario(Long id_email, Long id_usuario);

    @Query(value = "SELECT excluido FROM T_LCW_ALTERACAO where alt_id_email = :id_email AND alt_id_usuario = :id_usuario", nativeQuery = true)
    public Boolean verificarExcluidoPorIdEmailEIdUsuario(Long id_email, Long id_usuario);

    @Query(value = "SELECT spam FROM T_LCW_ALTERACAO where alt_id_email = :id_email AND alt_id_usuario = :id_usuario", nativeQuery = true)
    public Boolean verificarSpamPorIdEmailEIdUsuario(Long id_email, Long id_usuario);

    @Query(value = "SELECT id_pasta FROM T_LCW_ALTERACAO where alt_id_email = :id_email AND alt_id_usuario = :id_usuario", nativeQuery = true)
    public Long verificarPastaPorIdEmailEIdUsuario(Long id_email, Long id_usuario);

    @Query(value = "UPDATE T_LCW_ALTERACAO SET importante = :importante where alt_id_email = :id_email AND alt_id_usuario = :id_usuario", nativeQuery = true)
    public void atualizarImportantePorIdEmailEIdUsuario(Boolean importante, Long id_email, Long id_usuario);

    @Query(value = "UPDATE T_LCW_ALTERACAO SET arquivado = :arquivado where alt_id_email = :id_email AND alt_id_usuario = :id_usuario", nativeQuery = true)
    public void atualizarArquivadoPorIdEmailEIdUsuario(Boolean arquivado, Long id_email, Long id_usuario);

    @Query(value = "UPDATE T_LCW_ALTERACAO SET spam = :spam where alt_id_email = :id_email AND alt_id_usuario = :id_usuario", nativeQuery = true)
    public void atualizarSpamPorIdEmailEIdUsuario(Boolean spam, Long id_email, Long id_usuario);

    @Query(value = "UPDATE T_LCW_ALTERACAO SET excluido = :excluido where alt_id_email = :id_email AND alt_id_usuario = :id_usuario", nativeQuery = true)
    public void atualizarExcluidoPorIdEmailEIdUsuario(Boolean excluido, Long id_email, Long id_usuario);

    @Query(value = "UPDATE T_LCW_ALTERACAO SET lido = :lido where alt_id_email = :id_email AND alt_id_usuario = :id_usuario", nativeQuery = true)
    public void atualizarLidoPorIdEmailEIdUsuario(Boolean lido, Long id_email, Long id_usuario);

    @Query(value = "UPDATE T_LCW_ALTERACAO SET id_pasta = :pasta where alt_id_email = :id_email AND alt_id_usuario = :id_usuario", nativeQuery = true)
    public void atualizarPastaPorIdEmailEIdUsuario(Long pasta, Long id_email, Long id_usuario);

    @Query(value = "UPDATE T_LCW_ALTERACAO SET id_pasta = :pasta where id_alteracao = :id_alteracao", nativeQuery = true)
    public void atualizarPastaPorIdAlteracao(Long pasta, Long id_alteracao);

    @Query(value = "DELETE FROM T_LCW_ALTERACAO where alt_id_email = :id_email AND alt_id_usuario = :id_usuario", nativeQuery = true)
    public void excluiAlteracaoPorIdEmailEIdUsuario(Long id_email, Long id_usuario);

}
