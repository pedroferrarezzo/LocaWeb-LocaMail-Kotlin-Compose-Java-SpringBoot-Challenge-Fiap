package br.com.locaweb.locamail.api.service;

import br.com.locaweb.locamail.api.dto.email.EmailCadastroDto;
import br.com.locaweb.locamail.api.dto.email.EmailExibicaoDto;
import br.com.locaweb.locamail.api.model.Email;
import br.com.locaweb.locamail.api.model.EmailComAlteracao;
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
import java.util.stream.Collectors;

import static br.com.locaweb.locamail.api.utils.ListUtils.stringParaLista;

@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RespostaEmailRepository respostaEmailRepository;

    public EmailExibicaoDto criarEmail(EmailCadastroDto emailCadastroDto) {
        Email email = new Email();
        BeanUtils.copyProperties(emailCadastroDto, email);
        Email emailPersistido = emailRepository.save(email);
        return new EmailExibicaoDto(emailPersistido);
    }

    public void excluirEmail(EmailCadastroDto emailCadastroDto) {
        Email email = new Email();
        BeanUtils.copyProperties(emailCadastroDto, email);
        emailRepository.delete(email);
    }

    public void atualizarEmail(EmailCadastroDto emailCadastroDto) {
        Email email = new Email();
        BeanUtils.copyProperties(emailCadastroDto, email);
        emailRepository.save(email);
    }

    public List<EmailComAlteracao> listarTodosEmails() {
        List<EmailComAlteracao> listTodosEmails = new ArrayList<>();

        for (EmailComAlteracao email : emailRepository.listarTodosEmails()) {
            Usuario usuario = usuarioRepository.retornaUsarioPorEmail(email.getEmail().getRemetente());
            Long idRemetente = usuario != null ? usuario.getId_usuario() : null;

            if (!listTodosEmails.isEmpty() && idRemetente != null) {
                if (!email.getAlteracao().getAlt_id_usuario().equals(idRemetente) &&
                        !listTodosEmails.get(listTodosEmails.size() - 1).getAlteracao().getAlt_id_email().equals(email.getAlteracao().getAlt_id_email())) {
                    listTodosEmails.add(email);
                }
            } else if (idRemetente != null) {
                if (!email.getAlteracao().getAlt_id_usuario().equals(idRemetente)) {
                    listTodosEmails.add(email);
                }
            }
        }
        return listTodosEmails;
    }

    public List<EmailComAlteracao> listarEmailsEnviadosPorRemetente(
            String remetente,
            Long id_usuario
    ) {
        return emailRepository.listarEmailsEnviadosPorRemetente(remetente, id_usuario);
    }

    public List<EmailComAlteracao> listarEmailsPorDestinatario(
            String destinatario,
            Long id_usuario) {

        List<EmailComAlteracao> emailListDb = emailRepository.listarEmailsPorDestinatario(id_usuario);

        return emailListDb.stream()
                .filter(email -> {
                    List<RespostaEmail> respostaEmailList =
                            respostaEmailRepository.listarRespostasEmailPorIdEmail(email.getEmail().getId_email());

                    return stringParaLista(email.getEmail().getDestinatario()).contains(destinatario) ||
                            stringParaLista(email.getEmail().getCc()).contains(destinatario) ||
                            stringParaLista(email.getEmail().getCco()).contains(destinatario) ||
                            respostaEmailList.stream().anyMatch(respostaEmail ->
                                    stringParaLista(respostaEmail.getDestinatario()).contains(destinatario) ||
                                            stringParaLista(respostaEmail.getCc()).contains(destinatario) ||
                                            stringParaLista(respostaEmail.getCco()).contains(destinatario)
                            );
                })
                .collect(Collectors.toList());
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

    public List<EmailComAlteracao> listarEmailsPorPastaEIdUsuario(Long id_usuario, Long id_pasta) {
        return emailRepository.listarEmailsPorPastaEIdUsuario(id_usuario, id_pasta);
    }

    public List<EmailComAlteracao> listarEmailsImportantesPorIdUsuario(Long id_usuario) {
        return emailRepository.listarEmailsImportantesPorIdUsuario(id_usuario);
    }

    public List<EmailComAlteracao> listarEmailsAi(Long id_usuario) {
        return emailRepository.listarEmailsAi(id_usuario);
    }

    public List<EmailComAlteracao> listarEmailsArquivadosPorIdUsuario(Long id_usuario) {
        return emailRepository.listarEmailsArquivadosPorIdUsuario(id_usuario);
    }

    public List<EmailComAlteracao> listarEmailsLixeiraPorIdUsuario(Long id_usuario) {
        return emailRepository.listarEmailsLixeiraPorIdUsuario(id_usuario);
    }

    public List<EmailComAlteracao> listarEmailsSpamPorIdUsuario(Long id_usuario) {
        return emailRepository.listarEmailsSpamPorIdUsuario(id_usuario);
    }

    public List<EmailComAlteracao> listarEmailsSociaisPorIdUsuario(Long id_usuario) {
        return emailRepository.listarEmailsSociaisPorIdUsuario(id_usuario);
    }
}
