package br.com.locaweb.locamail.api.controller;

import br.com.locaweb.locamail.api.dto.respostaEmail.RespostaEmailExibicaoDto;
import br.com.locaweb.locamail.api.service.RespostaEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/respostaEmail")
public class RespostaEmailController {


    @Autowired
    private RespostaEmailService respostaEmailService;

    @GetMapping("/listarRespostasEmailPorIdEmail")
    @ResponseStatus(HttpStatus.OK)
    public List<RespostaEmailExibicaoDto> listarRespostasEmailPorIdEmail(@RequestParam("idEmail") Long id_email) {
        return respostaEmailService.listarRespostasEmailPorIdEmail(id_email);
    }

    @DeleteMapping("/excluirRespostaEmailPorIdEmail")
    @ResponseStatus(HttpStatus.OK)
    public void excluirRespostaEmailPorIdEmail(@RequestParam("idEmail") Long id_email) {
        respostaEmailService.excluirRespostaEmailPorIdEmail(id_email);
    }

    @DeleteMapping("/excluirRespostaEmail")
    @ResponseStatus(HttpStatus.OK)
    public void excluirRespostaEmail(@RequestParam("idRespostaEmail") Long id_resposta_email) throws Exception {
        respostaEmailService.excluirRespostaEmail(id_resposta_email);
    }
}
