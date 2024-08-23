package br.com.locaweb.locamail.api.controller;


import br.com.locaweb.locamail.api.service.AgendaConvidadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agendaConvidado")
public class AgendaConvidadoController {

    @Autowired
    private AgendaConvidadoService agendaConvidadoService;


    @DeleteMapping("/excluirPorIdAgenda")
    @ResponseStatus(HttpStatus.OK)
    public void excluirPorIdAgenda(@RequestParam("idAgenda") Long id_agenda) {
        agendaConvidadoService.excluirPorIdAgenda(id_agenda);
    }
}
