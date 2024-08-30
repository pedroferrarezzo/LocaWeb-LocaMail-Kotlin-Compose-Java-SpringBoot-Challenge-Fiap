package br.com.locaweb.locamail.api.controller;


import br.com.locaweb.locamail.api.dto.agendaConvidado.AgendaConvidadoCadastroDto;
import br.com.locaweb.locamail.api.dto.agendaConvidado.AgendaConvidadoExibicaoDto;
import br.com.locaweb.locamail.api.service.AgendaConvidadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendaConvidado")
public class AgendaConvidadoController {

    @Autowired
    private AgendaConvidadoService agendaConvidadoService;

    @PostMapping("/criaAgendaConvidado")
    @ResponseStatus(HttpStatus.CREATED)
    public AgendaConvidadoExibicaoDto criaAgendaConvidado(@RequestBody @Valid AgendaConvidadoCadastroDto agendaConvidadoCadastroDto) {
        return agendaConvidadoService.criaAgendaConvidado(agendaConvidadoCadastroDto);
    }

    @GetMapping("/listarIdConvidadoPorAgenda")
    @ResponseStatus(HttpStatus.OK)
    public List<Long> listarIdConvidadoPorAgenda(@RequestParam("idAgenda") Long id_agenda) {
        return agendaConvidadoService.listarIdConvidadoPorAgenda(id_agenda);
    }

    @DeleteMapping("/excluirPorIdAgenda")
    @ResponseStatus(HttpStatus.OK)
    public void excluirPorIdAgenda(@RequestParam("idAgenda") Long id_agenda) {
        agendaConvidadoService.excluirPorIdAgenda(id_agenda);
    }

    @DeleteMapping("/excluirPorIdAgendaEIdConvidado")
    @ResponseStatus(HttpStatus.OK)
    public void excluirPorIdAgendaEIdConvidado(@RequestParam("idAgenda") Long id_agenda, @RequestParam("idConvidado") Long id_convidado) {
        agendaConvidadoService.excluirPorIdAgendaEIdConvidado(id_agenda, id_convidado);
    }

    @DeleteMapping("/excluirPorGrupoRepeticaoExcetoIdAgenda")
    @ResponseStatus(HttpStatus.OK)
    public void excluirPorGrupoRepeticaoExcetoIdAgenda(@RequestParam("grupoRepeticao") Integer grupo_repeticao, @RequestParam("idAgenda") Long id_agenda) {
        agendaConvidadoService.excluirPorGrupoRepeticaoExcetoIdAgenda(grupo_repeticao, id_agenda);
    }
}
