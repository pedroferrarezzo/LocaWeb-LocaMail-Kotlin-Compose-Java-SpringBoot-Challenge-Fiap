package br.com.locaweb.locamail.api.repository;

import br.com.locaweb.locamail.api.model.AiQuestion;
import br.com.locaweb.locamail.api.model.Alteracao;
import br.com.locaweb.locamail.api.model.Anexo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Blob;
import java.util.List;


@Repository
public interface AiQuestionRepository extends JpaRepository<AiQuestion, Long> {

    @Query(value = "SELECT * FROM T_LCW_AI_QUESTION WHERE id_question = :id_question AND id_email = :id_email", nativeQuery = true)
    AiQuestion obterPergunta(@Param("id_question") Long id_question, @Param("id_email") Long id_email);

}
