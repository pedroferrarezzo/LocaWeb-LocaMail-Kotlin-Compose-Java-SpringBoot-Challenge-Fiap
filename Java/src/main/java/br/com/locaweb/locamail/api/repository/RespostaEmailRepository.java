package br.com.locaweb.locamail.api.repository;

import br.com.locaweb.locamail.api.model.Convidado;
import br.com.locaweb.locamail.api.model.RespostaEmail;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RespostaEmailRepository extends JpaRepository<RespostaEmail, Long> {

    @Query(value = "SELECT * FROM T_LCW_RESPOSTA_EMAIL  WHERE id_email = :id_email", nativeQuery = true)
    public List<RespostaEmail> listarRespostasEmailPorIdEmail(@Param("id_email") Long id_email);

    @Query(value = "SELECT * FROM T_LCW_RESPOSTA_EMAIL  WHERE id_resposta_email = :id_resposta_email", nativeQuery = true)
    public RespostaEmail listarRespostaEmailPorIdRespostaEmail(@Param("id_resposta_email") Long id_resposta_email);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM T_LCW_RESPOSTA_EMAIL WHERE id_email = :id_email", nativeQuery = true)
    public void excluirRespostaEmailPorIdEmail(@Param("id_email") Long id_email);

}
