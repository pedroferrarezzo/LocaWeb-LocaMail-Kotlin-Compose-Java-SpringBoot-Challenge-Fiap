package br.com.locaweb.locamail.api.service;

import br.com.locaweb.locamail.api.dto.agenda.AgendaCadastroDto;
import br.com.locaweb.locamail.api.dto.agenda.AgendaExibicaoDto;
import br.com.locaweb.locamail.api.model.Agenda;
import br.com.locaweb.locamail.api.repository.AgendaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendaService {

    @Autowired
    private AgendaRepository agendaRepository;

    public AgendaExibicaoDto criarAgenda(AgendaCadastroDto agendaCadastroDto) {
        Agenda agenda = new Agenda();
        BeanUtils.copyProperties(agendaCadastroDto, agenda);
        return new AgendaExibicaoDto(agendaRepository.save(agenda));
    }

    public List<AgendaExibicaoDto> listarAgendaPorDia(String data, Long id_usuario) {
        return agendaRepository.listarAgendaPorDia(data, id_usuario).stream()
                .map(agenda -> new AgendaExibicaoDto(agenda))
                .collect(Collectors.toList());
    }

    public List<Integer> listarCorAgendaPorDia(String data, Long id_usuario)  {
        return agendaRepository.listarCorAgendaPorDia(data, id_usuario);
    }

    public List<Integer> listarGrupoRepeticao() {
        return agendaRepository.listarGrupoRepeticao();
    }

    public List<AgendaExibicaoDto> listarAgendaPorIdEmailEIdUsuario(Long id_email, Long id_usuario) {
        return agendaRepository.listarAgendaPorIdEmailEIdUsuario(id_email, id_usuario).stream()
                .map(agenda -> new AgendaExibicaoDto(agenda))
                .collect(Collectors.toList());
    }

    public AgendaExibicaoDto listarAgendaPorId(Long id_agenda) {
        return new AgendaExibicaoDto(agendaRepository.listarAgendaPorId(id_agenda));
    }

    public void atualizaAgenda(AgendaCadastroDto agendaCadastroDto) {
        Agenda agenda = new Agenda();
        BeanUtils.copyProperties(agendaCadastroDto, agenda);
        agendaRepository.save(agenda);
    }

    public void atualizaVisivelPorIdAgenda(Long id_agenda, Boolean visivel) {
        agendaRepository.atualizaVisivelPorIdAgenda(id_agenda, visivel);
    }

    public void excluiAgenda(AgendaCadastroDto agendaCadastroDto) {
        Agenda agenda = new Agenda();
        BeanUtils.copyProperties(agendaCadastroDto, agenda);
        agendaRepository.delete(agenda);
    }

    public void excluirPorGrupoRepeticaoExcetoData(Integer grupo_repeticao, String data) {
        agendaRepository.excluirPorGrupoRepeticaoExcetoData(grupo_repeticao, data);
    }

    public void atualizaGrupoRepeticaoPorGrupoRepeticao(Integer grupo_repeticao, Integer grupo_desejado) {
        agendaRepository.atualizaGrupoRepeticaoPorGrupoRepeticao(grupo_repeticao, grupo_desejado);
    }

    public void atualizaOpcaoRepeticaoPorGrupoRepeticao(Integer grupo_repeticao, Integer repeticao) {
        agendaRepository.atualizaOpcaoRepeticaoPorGrupoRepeticao(grupo_repeticao, repeticao);
    }

    public void atualizaOpcaoRepeticaoPorIdAgenda(Long id_agenda, Integer repeticao) {
        agendaRepository.atualizaOpcaoRepeticaoPorIdAgenda(id_agenda, repeticao);
    }

    public Long retornaValorAtualSeqPrimayKey() {
        return agendaRepository.retornaValorAtualSeqPrimayKey();
    }

    public void excluirPorGrupoRepeticao(Integer grupo_repeticao) {
        agendaRepository.excluirPorGrupoRepeticao(grupo_repeticao);
    }
}
