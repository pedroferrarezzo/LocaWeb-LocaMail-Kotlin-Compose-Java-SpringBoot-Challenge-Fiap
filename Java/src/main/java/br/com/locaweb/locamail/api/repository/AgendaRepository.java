package br.com.locaweb.locamail.api.repository;

import br.com.locaweb.locamail.api.model.Agenda;
import br.com.locaweb.locamail.api.model.Pasta;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {

    @Query(value = "SELECT * FROM T_LCW_AGENDA WHERE data = :data AND id_usuario = :id_usuario AND visivel = 1", nativeQuery = true)
    public List<Agenda> listarAgendaPorDia(String data, Long id_usuario);

    @Query(value = "SELECT DISTINCT cor FROM T_LCW_AGENDA WHERE data = :data AND id_usuario = :id_usuario AND visivel = 1", nativeQuery = true)
    public List<Integer> listarCorAgendaPorDia(String data, Long id_usuario);

    @Query(value = "SELECT DISTINCT grupo_repeticao FROM T_LCW_AGENDA", nativeQuery = true)
    public List<Integer> listarGrupoRepeticao();

    @Query(value = "SELECT * FROM T_LCW_AGENDA WHERE id_agenda = :id_agenda", nativeQuery = true)
    public Agenda listarAgendaPorId(Long id_agenda);

    @Query(value = "SELECT * FROM T_LCW_AGENDA WHERE id_email = :id_email AND id_usuario = :id_usuario AND visivel = 0", nativeQuery = true)
    public List<Agenda> listarAgendaPorIdEmailEIdUsuario(Long id_email, Long id_usuario);

    @Query(value = "DELETE FROM T_LCW_AGENDA WHERE grupo_repeticao = :grupo_repeticao AND data != :data", nativeQuery = true)
    public void excluirPorGrupoRepeticaoExcetoData(Integer grupo_repeticao, String data);

    @Query(value = "UPDATE T_LCW_AGENDA SET grupo_repeticao = :grupo_desejado WHERE grupo_repeticao = :grupo_repeticao", nativeQuery = true)
    public void atualizaGrupoRepeticaoPorGrupoRepeticao(Integer grupo_repeticao, Integer grupo_desejado);

    @Query(value = "UPDATE T_LCW_AGENDA SET repeticao = :repeticao WHERE grupo_repeticao = :grupo_repeticao", nativeQuery = true)
    public void atualizaOpcaoRepeticaoPorGrupoRepeticao(Integer grupo_repeticao, Integer repeticao);

    @Query(value = "UPDATE T_LCW_AGENDA SET repeticao = :repeticao WHERE id_agenda = :id_agenda", nativeQuery = true)
    public void atualizaOpcaoRepeticaoPorIdAgenda(Long id_agenda, Integer repeticao);

    @Query(value = "UPDATE T_LCW_AGENDA SET visivel = :visivel WHERE id_agenda = :id_agenda", nativeQuery = true)
    public void atualizaVisivelPorIdAgenda(Long id_agenda, Boolean visivel);

    @Query(value = "SELECT id_agenda FROM T_LCW_AGENDA ORDER BY id_agenda DESC FETCH FIRST 1 ROWS ONLY", nativeQuery = true)
    public Long retornaValorAtualSeqPrimayKey();

    @Query(value = "DELETE FROM T_LCW_AGENDA WHERE grupo_repeticao = :grupo_repeticao", nativeQuery = true)
    public void excluirPorGrupoRepeticao(Integer grupo_repeticao);

}
