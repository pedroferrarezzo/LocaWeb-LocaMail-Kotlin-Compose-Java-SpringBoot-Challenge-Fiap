package br.com.locaweb.locamail.api.repository;

import br.com.locaweb.locamail.api.model.Email;
import br.com.locaweb.locamail.api.dto.email.EmailComAlteracao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {

    @Query(value = "SELECT DISTINCT * FROM T_LCW_EMAIL INNER JOIN T_LCW_ALTERACAO ON T_LCW_EMAIL.id_email = T_LCW_ALTERACAO.alt_id_email WHERE editavel = 0 AND arquivado = 0 AND excluido = 0 AND spam = 0", nativeQuery = true)
    public List<EmailComAlteracao> listarTodosEmails();

    @Query(value = "SELECT * FROM T_LCW_EMAIL INNER JOIN T_LCW_ALTERACAO ON T_LCW_EMAIL.id_email = T_LCW_ALTERACAO.alt_id_email where T_LCW_ALTERACAO.alt_id_usuario = :id_usuario AND remetente = :remetente AND editavel = 0 AND arquivado = 0 AND excluido = 0 AND spam = 0", nativeQuery = true)
    public  List<EmailComAlteracao> listarEmailsEnviadosPorRemetente(String remetente, Long id_usuario);

    @Query(value = "SELECT * FROM T_LCW_EMAIL INNER JOIN T_LCW_ALTERACAO ON T_LCW_EMAIL.id_email = T_LCW_ALTERACAO.alt_id_email where T_LCW_ALTERACAO.alt_id_usuario = :id_usuario AND editavel = 0 AND arquivado = 0 AND excluido = 0 AND spam = 0 AND id_pasta IS NULL", nativeQuery = true)
    public List<EmailComAlteracao> listarEmailsPorDestinatario(Long id_usuario);

    @Query(value = "SELECT * FROM T_LCW_EMAIL INNER JOIN T_LCW_ALTERACAO ON T_LCW_EMAIL.id_email = T_LCW_ALTERACAO.alt_id_email where T_LCW_ALTERACAO.alt_id_usuario = :id_usuario AND editavel = 0", nativeQuery = true)
    public List<EmailComAlteracao> listarEmailsAi(Long id_usuario);

    @Query(value = "SELECT * FROM T_LCW_EMAIL where remetente = :remetente AND editavel = 1", nativeQuery = true)
    public List<Email> listarEmailsEditaveisPorRemetente(String remetente);

    @Query(value = "SELECT * FROM T_LCW_EMAIL where id_email = :id_email", nativeQuery = true)
    public Email listarEmailPorId(Long id_email);

    @Query(value = "SELECT * FROM T_LCW_EMAIL INNER JOIN T_LCW_ALTERACAO ON T_LCW_EMAIL.id_email = T_LCW_ALTERACAO.alt_id_email where T_LCW_ALTERACAO.alt_id_usuario = :id_usuario AND importante = 1 AND editavel = 0 AND arquivado = 0 AND excluido = 0 AND spam = 0", nativeQuery = true)
    public List<EmailComAlteracao> listarEmailsImportantesPorIdUsuario(Long id_usuario);

    @Query(value = "SELECT * FROM T_LCW_EMAIL INNER JOIN T_LCW_ALTERACAO ON T_LCW_EMAIL.id_email = T_LCW_ALTERACAO.alt_id_email where T_LCW_ALTERACAO.alt_id_usuario = :id_usuario AND agenda_atrelada = 1 AND editavel = 0 AND arquivado = 0 AND excluido = 0 AND spam = 0", nativeQuery = true)
    public List<EmailComAlteracao> listarEmailsSociaisPorIdUsuario(Long id_usuario);

    @Query(value = "SELECT * FROM T_LCW_EMAIL INNER JOIN T_LCW_ALTERACAO ON T_LCW_EMAIL.id_email = T_LCW_ALTERACAO.alt_id_email where T_LCW_ALTERACAO.alt_id_usuario = :id_usuario AND arquivado = 1 AND excluido = 0 AND spam = 0 AND editavel = 0", nativeQuery = true)
    public List<EmailComAlteracao> listarEmailsArquivadosPorIdUsuario(Long id_usuario);

    @Query(value = "SELECT * FROM T_LCW_EMAIL INNER JOIN T_LCW_ALTERACAO ON T_LCW_EMAIL.id_email = T_LCW_ALTERACAO.alt_id_email where T_LCW_ALTERACAO.alt_id_usuario = :id_usuario AND spam = 1 AND editavel = 0 AND excluido = 0", nativeQuery = true)
    public List<EmailComAlteracao> listarEmailsSpamPorIdUsuario(Long id_usuario);

    @Query(value = "SELECT * FROM T_LCW_EMAIL INNER JOIN T_LCW_ALTERACAO ON T_LCW_EMAIL.id_email = T_LCW_ALTERACAO.alt_id_email where T_LCW_ALTERACAO.alt_id_usuario = :id_usuario AND excluido = 1 AND editavel = 0", nativeQuery = true)
    public List<EmailComAlteracao> listarEmailsLixeiraPorIdUsuario(Long id_usuario);

    @Query(value = "SELECT * FROM T_LCW_EMAIL INNER JOIN T_LCW_ALTERACAO ON T_LCW_EMAIL.id_email = T_LCW_ALTERACAO.alt_id_email where T_LCW_ALTERACAO.alt_id_usuario = :id_usuario AND id_pasta = :id_pasta AND arquivado = 0 AND excluido = 0 AND spam = 0 AND editavel = 0", nativeQuery = true)
    public List<EmailComAlteracao> listarEmailsPorPastaEIdUsuario(Long id_usuario, Long id_pasta);
}
