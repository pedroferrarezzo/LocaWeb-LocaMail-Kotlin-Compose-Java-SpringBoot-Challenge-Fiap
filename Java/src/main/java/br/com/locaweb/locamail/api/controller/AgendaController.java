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

}
