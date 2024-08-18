package br.com.locaweb.locamail.api.repository;

import br.com.locaweb.locamail.api.model.AgendaConvidado;
import br.com.locaweb.locamail.api.model.Pasta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AgendaConvidadoRepository extends JpaRepository<AgendaConvidado, Long> {

    @Query(value = "SELECT id_convidado FROM T_LCW_AGENDA_CONVIDADO WHERE id_agenda = :id_agenda", nativeQuery = true)
    public List<Long> listarIdConvidadoPorAgenda(Long id_agenda);

    @Query(value = "DELETE FROM T_LCW_AGENDA_CONVIDADO WHERE grupo_repeticao = :grupo_repeticao AND id_agenda != :id_agenda", nativeQuery = true)
    public void excluirPorGrupoRepeticaoExcetoIdAgenda(Integer grupo_repeticao, Long id_agenda);

    @Query(value = "DELETE FROM T_LCW_AGENDA_CONVIDADO WHERE id_agenda = :id_agenda", nativeQuery = true)
    public void excluirPorIdAgenda(Long id_agenda);

    @Query(value = "DELETE FROM T_LCW_AGENDA_CONVIDADO WHERE id_agenda = :id_agenda AND id_convidado = :id_convidado", nativeQuery = true)
    public void excluirPorIdAgendaEIdConvidado(Long id_agenda, Long id_convidado);

}
