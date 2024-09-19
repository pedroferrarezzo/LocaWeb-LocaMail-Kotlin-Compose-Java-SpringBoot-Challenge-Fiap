package br.com.locaweb.locamail.api.repository;

import br.com.locaweb.locamail.api.model.Alteracao;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlteracaoRepository extends JpaRepository<Alteracao, Long> {

    @Query(value = "SELECT * FROM T_LCW_ALTERACAO WHERE alt_id_email = :id_email AND alt_id_usuario = :id_usuario", nativeQuery = true)
    Optional<Alteracao> listarAlteracaoPorIdEmailEIdUsuario(@Param("id_email") Long id_email, @Param("id_usuario") Long id_usuario);

    @Query(value = "SELECT * FROM T_LCW_ALTERACAO WHERE alt_id_usuario = :id_usuario AND id_pasta = :id_pasta", nativeQuery = true)
    List<Alteracao> listarAlteracaoPorIdUsuarioEIdPasta(@Param("id_usuario") Long id_usuario, @Param("id_pasta") Long id_pasta);

    @Query(value = "SELECT * FROM T_LCW_ALTERACAO WHERE alt_id_email = :id_email", nativeQuery = true)
    List<Alteracao> listarAlteracaoPorIdEmail(@Param("id_email") Long id_email);

    @Query(value = "SELECT alt_id_usuario FROM T_LCW_ALTERACAO WHERE alt_id_email = :id_email", nativeQuery = true)
    List<Long> listarAltIdUsuarioPorIdEmail(@Param("id_email") Long id_email);

    @Query(value = "SELECT importante FROM T_LCW_ALTERACAO WHERE alt_id_email = :id_email AND alt_id_usuario = :id_usuario", nativeQuery = true)
    Long verificarImportantePorIdEmailEIdUsuario(@Param("id_email") Long id_email, @Param("id_usuario") Long id_usuario);

    @Query(value = "SELECT lido FROM T_LCW_ALTERACAO WHERE alt_id_email = :id_email AND alt_id_usuario = :id_usuario", nativeQuery = true)
    Long verificarLidoPorIdEmailEIdUsuario(@Param("id_email") Long id_email, @Param("id_usuario") Long id_usuario);

    @Query(value = "SELECT arquivado FROM T_LCW_ALTERACAO WHERE alt_id_email = :id_email AND alt_id_usuario = :id_usuario", nativeQuery = true)
    Long verificarArquivadoPorIdEmailEIdUsuario(@Param("id_email") Long id_email, @Param("id_usuario") Long id_usuario);

    @Query(value = "SELECT excluido FROM T_LCW_ALTERACAO WHERE alt_id_email = :id_email AND alt_id_usuario = :id_usuario", nativeQuery = true)
    Long verificarExcluidoPorIdEmailEIdUsuario(@Param("id_email") Long id_email, @Param("id_usuario") Long id_usuario);

    @Query(value = "SELECT spam FROM T_LCW_ALTERACAO WHERE alt_id_email = :id_email AND alt_id_usuario = :id_usuario", nativeQuery = true)
    Long verificarSpamPorIdEmailEIdUsuario(@Param("id_email") Long id_email, @Param("id_usuario") Long id_usuario);

    @Query(value = "SELECT id_pasta FROM T_LCW_ALTERACAO WHERE alt_id_email = :id_email AND alt_id_usuario = :id_usuario", nativeQuery = true)
    Long verificarPastaPorIdEmailEIdUsuario(@Param("id_email") Long id_email, @Param("id_usuario") Long id_usuario);

    @Transactional
    @Modifying
    @Query(value = "UPDATE T_LCW_ALTERACAO SET importante = :importante WHERE alt_id_email = :id_email AND alt_id_usuario = :id_usuario", nativeQuery = true)
    void atualizarImportantePorIdEmailEIdUsuario(@Param("importante") Boolean importante, @Param("id_email") Long id_email, @Param("id_usuario") Long id_usuario);

    @Transactional
    @Modifying
    @Query(value = "UPDATE T_LCW_ALTERACAO SET arquivado = :arquivado WHERE alt_id_email = :id_email AND alt_id_usuario = :id_usuario", nativeQuery = true)
    void atualizarArquivadoPorIdEmailEIdUsuario(@Param("arquivado") Boolean arquivado, @Param("id_email") Long id_email, @Param("id_usuario") Long id_usuario);

    @Transactional
    @Modifying
    @Query(value = "UPDATE T_LCW_ALTERACAO SET spam = :spam WHERE alt_id_email = :id_email AND alt_id_usuario = :id_usuario", nativeQuery = true)
    void atualizarSpamPorIdEmailEIdUsuario(@Param("spam") Boolean spam, @Param("id_email") Long id_email, @Param("id_usuario") Long id_usuario);

    @Transactional
    @Modifying
    @Query(value = "UPDATE T_LCW_ALTERACAO SET excluido = :excluido WHERE alt_id_email = :id_email AND alt_id_usuario = :id_usuario", nativeQuery = true)
    void atualizarExcluidoPorIdEmailEIdUsuario(@Param("excluido") Boolean excluido, @Param("id_email") Long id_email, @Param("id_usuario") Long id_usuario);

    @Transactional
    @Modifying
    @Query(value = "UPDATE T_LCW_ALTERACAO SET lido = :lido WHERE alt_id_email = :id_email AND alt_id_usuario = :id_usuario", nativeQuery = true)
    void atualizarLidoPorIdEmailEIdUsuario(@Param("lido") Boolean lido, @Param("id_email") Long id_email, @Param("id_usuario") Long id_usuario);

    @Transactional
    @Modifying
    @Query(value = "UPDATE T_LCW_ALTERACAO SET id_pasta = :pasta WHERE alt_id_email = :id_email AND alt_id_usuario = :id_usuario", nativeQuery = true)
    void atualizarPastaPorIdEmailEIdUsuario(@Param("pasta") Long pasta, @Param("id_email") Long id_email, @Param("id_usuario") Long id_usuario);

    @Transactional
    @Modifying
    @Query(value = "UPDATE T_LCW_ALTERACAO SET id_pasta = :pasta WHERE id_alteracao = :id_alteracao", nativeQuery = true)
    void atualizarPastaPorIdAlteracao(@Param("pasta") Long pasta, @Param("id_alteracao") Long id_alteracao);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM T_LCW_ALTERACAO WHERE alt_id_email = :id_email AND alt_id_usuario = :id_usuario", nativeQuery = true)
    void excluiAlteracaoPorIdEmailEIdUsuario(@Param("id_email") Long id_email, @Param("id_usuario") Long id_usuario);
}
