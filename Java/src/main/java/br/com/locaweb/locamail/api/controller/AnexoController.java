package br.com.locaweb.locamail.api.controller;

import br.com.locaweb.locamail.api.service.AnexoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/anexo")
public class AnexoController {

    @Autowired
    private AnexoService anexoService;


    @GetMapping("listarAnexosIdEmail")
    @ResponseStatus(HttpStatus.OK)
    public List<Long> listarAnexosIdEmail() {
        return anexoService.listarAnexosIdEmail();
    }
}
