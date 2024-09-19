package br.com.locaweb.locamail.api.service;

import br.com.locaweb.locamail.api.client.BlackListCheckerClient;
import br.com.locaweb.locamail.api.dto.email.EmailCadastroDto;
import br.com.locaweb.locamail.api.dto.email.EmailExibicaoDto;
import br.com.locaweb.locamail.api.model.Email;
import br.com.locaweb.locamail.api.dto.email.EmailComAlteracaoDto;
import br.com.locaweb.locamail.api.model.RespostaEmail;
import br.com.locaweb.locamail.api.model.Usuario;
import br.com.locaweb.locamail.api.repository.EmailRepository;
import br.com.locaweb.locamail.api.repository.RespostaEmailRepository;
import br.com.locaweb.locamail.api.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.locaweb.locamail.api.utils.ListUtils.stringParaLista;
import static br.com.locaweb.locamail.api.utils.ObjectMapperUtils.emailComAlteracaoObjectMapper;

@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RespostaEmailRepository respostaEmailRepository;

    @Autowired
    private BlackListCheckerClient blackListCheckerClient;

    public EmailExibicaoDto criarEmail(EmailCadastroDto emailCadastroDto) {
        var email = new Email();
        BeanUtils.copyProperties(emailCadastroDto, email);

        var domain = getDomainFromEmail(emailCadastroDto.remetente());
        var blackListVerify = blackListCheckerClient.blackListVerify(domain);

        if(blackListVerify.getDetections() > 0)
            email.setIs_spam(true);

        Email emailPersistido = emailRepository.save(email);

        return new EmailExibicaoDto(emailPersistido);
    }

    public void excluirEmail(Long id_email) throws Exception {

        Optional<Email> email = emailRepository.findById(id_email);

        if (email.isPresent()) {
            emailRepository.deleteById(id_email);

        }
        else {
            throw new Exception("Email não encontrado");
        }
    }

    public EmailExibicaoDto atualizarEmail(EmailCadastroDto emailCadastroDto) {
        Email email = new Email();
        BeanUtils.copyProperties(emailCadastroDto, email);

        var domain = getDomainFromEmail(emailCadastroDto.remetente());
        var blackListVerify = blackListCheckerClient.blackListVerify(domain);

        if(blackListVerify.getDetections() > 0)
            email.setIs_spam(true);

        return new EmailExibicaoDto(emailRepository.save(email));
    }

    public EmailExibicaoDto listarEmailPorId(Long id_email) {
        return new EmailExibicaoDto(emailRepository.listarEmailPorId(id_email));
    }

    public List<EmailExibicaoDto> listarEmailsEditaveisPorRemetente(String remetente) {
        List<Email> emails = emailRepository.listarEmailsEditaveisPorRemetente(remetente);
        if (emails == null) {
            return null;
        }
        List<EmailExibicaoDto> emailExibicaoDtos = emails.stream()
                .map(email -> new EmailExibicaoDto(email))
                .collect(Collectors.toList());
        return emailExibicaoDtos;
    }

    public List<EmailComAlteracaoDto> listarTodosEmails() {
        List<Object[]> listRaw = emailRepository.listarTodosEmails();
        List<EmailComAlteracaoDto> listLoop = emailComAlteracaoObjectMapper(listRaw);
        List<EmailComAlteracaoDto> listTodosEmails = new ArrayList<>();


        for (EmailComAlteracaoDto email : listLoop) {
            Usuario usuario = usuarioRepository.retornaUsarioPorEmail(email.getRemetente());
            Long idRemetente = usuario != null ? usuario.getId_usuario() : null;

            if (!listTodosEmails.isEmpty() && idRemetente != null) {
                if (!email.getAlt_id_usuario().equals(idRemetente) &&
                        !listTodosEmails.get(listTodosEmails.size() - 1).getAlt_id_email().equals(email.getAlt_id_email())) {
                    listTodosEmails.add(email);
                }
            } else if (idRemetente != null) {
                if (!email.getAlt_id_usuario().equals(idRemetente)) {
                    listTodosEmails.add(email);
                }
            }
        }
        return listTodosEmails;
    }

    public List<EmailComAlteracaoDto> listarEmailsEnviadosPorRemetente(
            String remetente,
            Long id_usuario
    ) {
        List<Object[]> listRaw = emailRepository.listarEmailsEnviadosPorRemetente(remetente, id_usuario);
        return emailComAlteracaoObjectMapper(listRaw);
    }

    public List<EmailComAlteracaoDto> listarEmailsPorDestinatario(
            String destinatario,
            Long id_usuario) {

        List<Object[]> resultRaw = emailRepository.listarEmailsPorDestinatario(id_usuario);
        List<EmailComAlteracaoDto> emailList = emailComAlteracaoObjectMapper(resultRaw);

        return emailList.stream()
                .filter(email -> {
                    List<RespostaEmail> respostaEmailList =
                            respostaEmailRepository.listarRespostasEmailPorIdEmail(email.getId_email());



                    return stringParaLista(email.getDestinatario()).contains(destinatario) ||
                            stringParaLista(email.getCc()).contains(destinatario) ||
                            stringParaLista(email.getCco()).contains(destinatario) ||
                            respostaEmailList.stream().anyMatch(respostaEmail ->
                                    stringParaLista(respostaEmail.getDestinatario()).contains(destinatario) ||
                                            stringParaLista(respostaEmail.getCc()).contains(destinatario) ||
                                            stringParaLista(respostaEmail.getCco()).contains(destinatario)
                            );
                })
                .collect(Collectors.toList());
    }

    public List<EmailComAlteracaoDto> listarEmailsPorPastaEIdUsuario(Long id_usuario, Long id_pasta) {
        return emailComAlteracaoObjectMapper(emailRepository.listarEmailsPorPastaEIdUsuario(id_usuario, id_pasta));
    }

    public List<EmailComAlteracaoDto> listarEmailsImportantesPorIdUsuario(Long id_usuario) {
        return emailComAlteracaoObjectMapper(emailRepository.listarEmailsImportantesPorIdUsuario(id_usuario));
    }

    public List<EmailComAlteracaoDto> listarEmailsAi(Long id_usuario) {
        return emailComAlteracaoObjectMapper(emailRepository.listarEmailsAi(id_usuario));
    }

    public List<EmailComAlteracaoDto> listarEmailsArquivadosPorIdUsuario(Long id_usuario) {
        return emailComAlteracaoObjectMapper(emailRepository.listarEmailsArquivadosPorIdUsuario(id_usuario));
    }

    public List<EmailComAlteracaoDto> listarEmailsLixeiraPorIdUsuario(Long id_usuario) {
        return emailComAlteracaoObjectMapper(emailRepository.listarEmailsLixeiraPorIdUsuario(id_usuario));
    }

    public List<EmailComAlteracaoDto> listarEmailsSpamPorIdUsuario(Long id_usuario) {
        return emailComAlteracaoObjectMapper(emailRepository.listarEmailsSpamPorIdUsuario(id_usuario));
    }

    public List<EmailComAlteracaoDto> listarEmailsSociaisPorIdUsuario(Long id_usuario) {
        return emailComAlteracaoObjectMapper(emailRepository.listarEmailsSociaisPorIdUsuario(id_usuario));
    }

    private String getDomainFromEmail(String email) {
        String[] parts = email.split("@");
        return parts[1];
    }

}
