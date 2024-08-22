package br.com.locaweb.locamail.api.controller;

import br.com.locaweb.locamail.api.service.AnexoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("listarAnexosArraybytePorIdEmail")
    @ResponseStatus(HttpStatus.OK)
    public List<byte[]> listarAnexosArraybytePorIdEmail(@RequestParam("idEmail") Long id_email) {

        return anexoService.listarAnexosArraybytePorIdEmail(id_email);
    }



}
