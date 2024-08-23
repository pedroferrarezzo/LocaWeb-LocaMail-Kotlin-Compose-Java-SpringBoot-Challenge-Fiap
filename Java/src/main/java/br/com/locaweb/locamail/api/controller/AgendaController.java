package br.com.locaweb.locamail.api.controller;

import br.com.locaweb.locamail.api.dto.agenda.AgendaExibicaoDto;
import br.com.locaweb.locamail.api.service.AgendaService;
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

}
