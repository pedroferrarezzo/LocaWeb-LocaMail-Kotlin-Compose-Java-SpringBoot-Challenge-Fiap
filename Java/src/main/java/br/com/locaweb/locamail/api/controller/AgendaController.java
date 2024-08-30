package br.com.locaweb.locamail.api.controller;

import br.com.locaweb.locamail.api.dto.agenda.AgendaCadastroDto;
import br.com.locaweb.locamail.api.dto.agenda.AgendaExibicaoDto;
import br.com.locaweb.locamail.api.service.AgendaService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agenda")
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    @GetMapping("/listarAgendaPorIdEmailEIdUsuario")
    @ResponseStatus(HttpStatus.OK)
    public List<AgendaExibicaoDto> listarAgendaPorIdEmailEIdUsuario(@RequestParam("idEmail") Long id_email, @RequestParam("idUsuario") Long id_usuario) {
        return agendaService.listarAgendaPorIdEmailEIdUsuario(id_email, id_usuario);
    }

    @PostMapping("/criarAgenda")
    @ResponseStatus(HttpStatus.OK)
    public AgendaExibicaoDto criarAgenda(@RequestBody @Valid  AgendaCadastroDto agendaCadastroDto) {
        return agendaService.criarAgenda(agendaCadastroDto);
    }

    @PutMapping("/atualizaAgenda")
    @ResponseStatus(HttpStatus.OK)
    public void atualizaAgenda(@RequestBody @Valid  AgendaCadastroDto agendaCadastroDto) {
        agendaService.atualizaAgenda(agendaCadastroDto);
    }

    @GetMapping("/retornaValorAtualSeqPrimayKey")
    @ResponseStatus(HttpStatus.OK)
    public Long retornaValorAtualSeqPrimayKey() {
        return agendaService.retornaValorAtualSeqPrimayKey();
    }

    @GetMapping("/listarGrupoRepeticao")
    @ResponseStatus(HttpStatus.OK)
    public List<Integer> listarGrupoRepeticao() {
        return agendaService.listarGrupoRepeticao();
    }


    @GetMapping("/listarAgendaPorDia")
    @ResponseStatus(HttpStatus.OK)
    public List<AgendaExibicaoDto> listarAgendaPorDia(
            @RequestParam("data") String data,
            @RequestParam("idUsuario") Long id_usuario
    ) {
        return agendaService.listarAgendaPorDia(
                data, id_usuario);
    }

    @GetMapping("/listarAgendaPorId")
    @ResponseStatus(HttpStatus.OK)
    public AgendaExibicaoDto listarAgendaPorId(@RequestParam("idAgenda") Long id_agenda
    ) {
        return agendaService.listarAgendaPorId(id_agenda);
    }

    @GetMapping("/listarCorAgendaPorDia")
    @ResponseStatus(HttpStatus.OK)
    public List<Integer> listarCorAgendaPorDia(
            @RequestParam("data") String data,
            @RequestParam("idUsuario") Long id_usuario
    ) {
        return agendaService.listarCorAgendaPorDia(
                data, id_usuario);
    }

    @DeleteMapping("/excluiAgenda")
    @ResponseStatus(HttpStatus.OK)
    public void excluiAgenda(@RequestParam("idAgenda") Long id_agenda) throws Exception {
        agendaService.excluiAgenda(id_agenda);
    }

    @PatchMapping("/atualizaVisivelPorIdAgenda")
    @ResponseStatus(HttpStatus.OK)
    public void atualizaVisivelPorIdAgenda(@RequestParam("idAgenda") Long id_agenda, @RequestParam("visivel") Boolean visivel) {
        agendaService.atualizaVisivelPorIdAgenda(id_agenda, visivel);
    }

    @DeleteMapping("/excluirPorGrupoRepeticao")
    @ResponseStatus(HttpStatus.OK)
    public void excluirPorGrupoRepeticao(@RequestParam("grupoRepeticao") Integer grupo_repeticao)  {
        agendaService.excluirPorGrupoRepeticao(grupo_repeticao);
    }

    @DeleteMapping("/excluirPorGrupoRepeticaoExcetoData")
    @ResponseStatus(HttpStatus.OK)
    public void excluirPorGrupoRepeticaoExcetoData(@RequestParam("grupoRepeticao") Integer grupo_repeticao, @RequestParam("data") String data)  {
        agendaService.excluirPorGrupoRepeticaoExcetoData(grupo_repeticao, data);
    }

    @PutMapping("/atualizaOpcaoRepeticaoPorGrupoRepeticao")
    @ResponseStatus(HttpStatus.OK)
    public void atualizaOpcaoRepeticaoPorGrupoRepeticao(@RequestParam("grupoRepeticao") Integer grupo_repeticao, @RequestParam("repeticao") Integer repeticao)  {
        agendaService.atualizaOpcaoRepeticaoPorGrupoRepeticao(grupo_repeticao, repeticao);
    }

    @PutMapping("/atualizaOpcaoRepeticaoPorIdAgenda")
    @ResponseStatus(HttpStatus.OK)
    public void atualizaOpcaoRepeticaoPorIdAgenda(@RequestParam("idAgenda") Long id_agenda, @RequestParam("repeticao") Integer repeticao)  {
        agendaService.atualizaOpcaoRepeticaoPorIdAgenda(id_agenda, repeticao);
    }

}
