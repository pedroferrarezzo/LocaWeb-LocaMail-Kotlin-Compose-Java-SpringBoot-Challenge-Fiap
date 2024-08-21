package br.com.locaweb.locamail.api.repository;

import br.com.locaweb.locamail.api.model.AnexoRespostaEmail;
import br.com.locaweb.locamail.api.model.Pasta;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AnexoRespostaEmailRepository extends JpaRepository<AnexoRespostaEmail, Long> {

    @Query(value = "SELECT id_resposta_email FROM T_LCW_ANEXO_RESPOSTA_EMAIL", nativeQuery = true)
    public List<Long> listarAnexosIdRespostaEmail();

    @Query(value = "SELECT id_resposta_email FROM T_LCW_ANEXO_RESPOSTA_EMAIL where id_resposta_email = :id_resposta_email LIMIT 1", nativeQuery = true)
    public Long verificarAnexoExistentePorIdEmail(@Param("id_resposta_email") Long id_resposta_email);

    @Query(value = "SELECT anexo FROM T_LCW_ANEXO_RESPOSTA_EMAIL where id_resposta_email = :id_resposta_email", nativeQuery = true)
    public List<byte[]> listarAnexosArrayBytePorIdRespostaEmail(@Param("id_resposta_email") Long id_resposta_email);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM T_LCW_ANEXO_RESPOSTA_EMAIL where id_resposta_email = :id_resposta_email", nativeQuery = true)
    public void excluirAnexoPorIdRespostaEmail(@Param("id_resposta_email") Long id_resposta_email);
}
