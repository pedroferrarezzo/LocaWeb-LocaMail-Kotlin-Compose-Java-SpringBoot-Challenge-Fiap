package br.com.locaweb.locamail.api.controller;


import br.com.locaweb.locamail.api.dto.agendaConvidado.AgendaConvidadoCadastroDto;
import br.com.locaweb.locamail.api.dto.agendaConvidado.AgendaConvidadoExibicaoDto;
import br.com.locaweb.locamail.api.service.AgendaConvidadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agendaConvidado")
public class AgendaConvidadoController {

    @Autowired
    private AgendaConvidadoService agendaConvidadoService;

    @PostMapping("/criaAgendaConvidado")
    @ResponseStatus(HttpStatus.OK)
    public AgendaConvidadoExibicaoDto criaAgendaConvidado(@RequestBody @Valid AgendaConvidadoCadastroDto agendaConvidadoCadastroDto) {
        return agendaConvidadoService.criaAgendaConvidado(agendaConvidadoCadastroDto);
    }

    @DeleteMapping("/excluirPorIdAgenda")
    @ResponseStatus(HttpStatus.OK)
    public void excluirPorIdAgenda(@RequestParam("idAgenda") Long id_agenda) {
        agendaConvidadoService.excluirPorIdAgenda(id_agenda);
    }
}
