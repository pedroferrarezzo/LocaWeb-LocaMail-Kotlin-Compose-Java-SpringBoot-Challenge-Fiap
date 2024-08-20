package br.com.locaweb.locamail.api.controller;

import br.com.locaweb.locamail.api.dto.respostaEmail.RespostaEmailExibicaoDto;
import br.com.locaweb.locamail.api.service.RespostaEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/respostaEmail")
public class RespostaEmailController {


    @Autowired
    private RespostaEmailService respostaEmailService;

    @GetMapping("/listarRespostasEmailPorIdEmail")
    @ResponseStatus(HttpStatus.OK)
    public List<RespostaEmailExibicaoDto> listarRespostasEmailPorIdEmail(Long id_email) {
        return respostaEmailService.listarRespostasEmailPorIdEmail(id_email);
    }
}
