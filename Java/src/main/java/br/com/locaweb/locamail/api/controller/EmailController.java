package br.com.locaweb.locamail.api.controller;

import br.com.locaweb.locamail.api.dto.email.EmailCadastroDto;
import br.com.locaweb.locamail.api.dto.email.EmailExibicaoDto;
import br.com.locaweb.locamail.api.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/criarEmail")
    @ResponseStatus(HttpStatus.CREATED)
    public EmailExibicaoDto criarEmail(@RequestBody @Valid EmailCadastroDto emailCadastroDto) {
        return emailService.criarEmail(emailCadastroDto);
    }


}
