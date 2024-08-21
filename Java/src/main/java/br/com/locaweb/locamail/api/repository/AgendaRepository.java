package br.com.locaweb.locamail.api.repository;

import br.com.locaweb.locamail.api.model.Agenda;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {

    @Query(value = "SELECT * FROM T_LCW_AGENDA WHERE data = :data AND id_usuario = :id_usuario AND visivel = 1", nativeQuery = true)
    List<Agenda> listarAgendaPorDia(@Param("data") String data, @Param("id_usuario") Long id_usuario);

    @Query(value = "SELECT DISTINCT cor FROM T_LCW_AGENDA WHERE data = :data AND id_usuario = :id_usuario AND visivel = 1", nativeQuery = true)
    List<Integer> listarCorAgendaPorDia(@Param("data") String data, @Param("id_usuario") Long id_usuario);

    @Query(value = "SELECT DISTINCT grupo_repeticao FROM T_LCW_AGENDA", nativeQuery = true)
    List<Integer> listarGrupoRepeticao();

    @Query(value = "SELECT * FROM T_LCW_AGENDA WHERE id_agenda = :id_agenda", nativeQuery = true)
    Agenda listarAgendaPorId(@Param("id_agenda") Long id_agenda);

    @Query(value = "SELECT * FROM T_LCW_AGENDA WHERE id_email = :id_email AND id_usuario = :id_usuario AND visivel = 0", nativeQuery = true)
    List<Agenda> listarAgendaPorIdEmailEIdUsuario(@Param("id_email") Long id_email, @Param("id_usuario") Long id_usuario);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM T_LCW_AGENDA WHERE grupo_repeticao = :grupo_repeticao AND data != :data", nativeQuery = true)
    void excluirPorGrupoRepeticaoExcetoData(@Param("grupo_repeticao") Integer grupo_repeticao, @Param("data") String data);

    @Transactional
    @Modifying
    @Query(value = "UPDATE T_LCW_AGENDA SET grupo_repeticao = :grupo_desejado WHERE grupo_repeticao = :grupo_repeticao", nativeQuery = true)
    void atualizaGrupoRepeticaoPorGrupoRepeticao(@Param("grupo_repeticao") Integer grupo_repeticao, @Param("grupo_desejado") Integer grupo_desejado);

    @Transactional
    @Modifying
    @Query(value = "UPDATE T_LCW_AGENDA SET repeticao = :repeticao WHERE grupo_repeticao = :grupo_repeticao", nativeQuery = true)
    void atualizaOpcaoRepeticaoPorGrupoRepeticao(@Param("grupo_repeticao") Integer grupo_repeticao, @Param("repeticao") Integer repeticao);

    @Transactional
    @Modifying
    @Query(value = "UPDATE T_LCW_AGENDA SET repeticao = :repeticao WHERE id_agenda = :id_agenda", nativeQuery = true)
    void atualizaOpcaoRepeticaoPorIdAgenda(@Param("id_agenda") Long id_agenda, @Param("repeticao") Integer repeticao);

    @Transactional
    @Modifying
    @Query(value = "UPDATE T_LCW_AGENDA SET visivel = :visivel WHERE id_agenda = :id_agenda", nativeQuery = true)
    void atualizaVisivelPorIdAgenda(@Param("id_agenda") Long id_agenda, @Param("visivel") Boolean visivel);

    @Query(value = "SELECT id_agenda FROM T_LCW_AGENDA ORDER BY id_agenda DESC FETCH FIRST 1 ROWS ONLY", nativeQuery = true)
    Long retornaValorAtualSeqPrimayKey();

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM T_LCW_AGENDA WHERE grupo_repeticao = :grupo_repeticao", nativeQuery = true)
    void excluirPorGrupoRepeticao(@Param("grupo_repeticao") Integer grupo_repeticao);
}
