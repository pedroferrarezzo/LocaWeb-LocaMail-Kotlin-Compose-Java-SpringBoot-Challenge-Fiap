package br.com.locaweb.locamail.api.service;

import br.com.locaweb.locamail.api.dto.agendaConvidado.AgendaConvidadoCadastroDto;
import br.com.locaweb.locamail.api.dto.agendaConvidado.AgendaConvidadoExibicaoDto;
import br.com.locaweb.locamail.api.model.AgendaConvidado;
import br.com.locaweb.locamail.api.repository.AgendaConvidadoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaConvidadoService {

    @Autowired
    private AgendaConvidadoRepository agendaConvidadoRepository;

    public AgendaConvidadoExibicaoDto criaAgendaConvidado(AgendaConvidadoCadastroDto agendaConvidadoCadastroDto) {
        AgendaConvidado agendaConvidado = new AgendaConvidado();
        BeanUtils.copyProperties(agendaConvidadoCadastroDto, agendaConvidado);
        return new AgendaConvidadoExibicaoDto(agendaConvidadoRepository.save(agendaConvidado));
    }

    public List<Long> listarIdConvidadoPorAgenda(Long id_agenda)
    {
        return agendaConvidadoRepository.listarIdConvidadoPorAgenda(id_agenda);
    }

    public void excluirPorGrupoRepeticaoExcetoIdAgenda(Integer grupo_repeticao, Long id_agenda) {
        agendaConvidadoRepository.excluirPorGrupoRepeticaoExcetoIdAgenda(grupo_repeticao, id_agenda);
    }

    public void excluirPorIdAgenda(Long id_agenda) {
        agendaConvidadoRepository.excluirPorIdAgenda(id_agenda);
    }

    public void excluirPorIdAgendaEIdConvidado(Long id_agenda, Long id_convidado) {
        agendaConvidadoRepository.excluirPorIdAgendaEIdConvidado(
                id_agenda = id_agenda,
                id_convidado = id_convidado
        );
    }
}
