package br.com.locaweb.locamail.api.controller;

import br.com.locaweb.locamail.api.dto.email.EmailCadastroDto;
import br.com.locaweb.locamail.api.dto.email.EmailComAlteracaoDto;
import br.com.locaweb.locamail.api.dto.email.EmailExibicaoDto;
import br.com.locaweb.locamail.api.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/criarEmail")
    @ResponseStatus(HttpStatus.CREATED)
    public EmailExibicaoDto criarEmail (@RequestBody @Valid EmailCadastroDto emailCadastroDto) {
        return emailService.criarEmail(emailCadastroDto);
    }

    @PutMapping("/atualizarEmail")
    @ResponseStatus(HttpStatus.OK)
    public EmailExibicaoDto atualizarEmail (@RequestBody @Valid EmailCadastroDto emailCadastroDto) {
        return emailService.atualizarEmail(emailCadastroDto);
    }

    @GetMapping("/listarEmailsPorDestinatario")
    @ResponseStatus(HttpStatus.OK)
    public List<EmailComAlteracaoDto> listarEmailsPorDestinatario(@RequestParam("destinatario") String destinatario,
                                                                  @RequestParam("idUsuario") Long id_usuario) {
        return emailService.listarEmailsPorDestinatario(destinatario, id_usuario);
    }

    @GetMapping("/listarEmailsEnviadosPorRemetente")
    @ResponseStatus(HttpStatus.OK)
    public List<EmailComAlteracaoDto> listarEmailsEnviadosPorRemetente(@RequestParam("remetente") String remetente,
                                                                  @RequestParam("idUsuario") Long id_usuario) {
        return emailService.listarEmailsEnviadosPorRemetente(remetente, id_usuario);
    }

    @GetMapping("/listarEmailsImportantesPorIdUsuario")
    @ResponseStatus(HttpStatus.OK)
    public List<EmailComAlteracaoDto> listarEmailsImportantesPorIdUsuario(@RequestParam("idUsuario") Long id_usuario) {
        return emailService.listarEmailsImportantesPorIdUsuario(id_usuario);
    }

    @GetMapping("/listarEmailsArquivadosPorIdUsuario")
    @ResponseStatus(HttpStatus.OK)
    public List<EmailComAlteracaoDto> listarEmailsArquivadosPorIdUsuario(@RequestParam("idUsuario") Long id_usuario) {
        return emailService.listarEmailsArquivadosPorIdUsuario(id_usuario);
    }

    @GetMapping("/listarEmailsPorPastaEIdUsuario")
    @ResponseStatus(HttpStatus.OK)
    public List<EmailComAlteracaoDto> listarEmailsPorPastaEIdUsuario(@RequestParam("idUsuario") Long id_usuario, @RequestParam("pasta") Long pasta) {
        return emailService.listarEmailsPorPastaEIdUsuario(id_usuario, pasta);
    }

    @GetMapping("/listarEmailsLixeiraPorIdUsuario")
    @ResponseStatus(HttpStatus.OK)
    public List<EmailComAlteracaoDto> listarEmailsLixeiraPorIdUsuario(@RequestParam("idUsuario") Long id_usuario) {
        return emailService.listarEmailsLixeiraPorIdUsuario(id_usuario);
    }

    @GetMapping("/listarEmailsSpamPorIdUsuario")
    @ResponseStatus(HttpStatus.OK)
    public List<EmailComAlteracaoDto> listarEmailsSpamPorIdUsuario(@RequestParam("idUsuario") Long id_usuario) {
        return emailService.listarEmailsSpamPorIdUsuario(id_usuario);
    }

    @GetMapping("/listarEmailsSociaisPorIdUsuario")
    @ResponseStatus(HttpStatus.OK)
    public List<EmailComAlteracaoDto> listarEmailsSociaisPorIdUsuario(@RequestParam("idUsuario") Long id_usuario) {
        return emailService.listarEmailsSociaisPorIdUsuario(id_usuario);
    }

    @GetMapping("/listarEmailsEditaveisPorRemetente")
    @ResponseStatus(HttpStatus.OK)
    public List<EmailExibicaoDto> listarEmailsEditaveisPorRemetente(@RequestParam("remetente") String remetente) {
        return emailService.listarEmailsEditaveisPorRemetente(remetente);
    }

    @GetMapping("/listarTodosEmails")
    @ResponseStatus(HttpStatus.OK)
    public List<EmailComAlteracaoDto> listarTodosEmails() {
        return emailService.listarTodosEmails();
    }

    @GetMapping("/listarEmailPorId")
    @ResponseStatus(HttpStatus.OK)
    public EmailExibicaoDto listarEmailPorId(@RequestParam("idEmail") Long id_email) {
        return emailService.listarEmailPorId(id_email);
    }

    @DeleteMapping("/excluirEmail")
    @ResponseStatus(HttpStatus.OK)
    public void excluirEmail(@RequestParam("idEmail") Long id_email) throws Exception {
        emailService.excluirEmail(id_email);
    }


}
