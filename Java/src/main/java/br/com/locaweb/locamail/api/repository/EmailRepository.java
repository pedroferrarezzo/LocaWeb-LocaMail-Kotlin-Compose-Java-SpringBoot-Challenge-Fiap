package br.com.locaweb.locamail.api.repository;

import br.com.locaweb.locamail.api.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {

    @Query(value = "SELECT DISTINCT * FROM T_LCW_EMAIL INNER JOIN T_LCW_ALTERACAO ON T_LCW_EMAIL.id_email = T_LCW_ALTERACAO.alt_id_email WHERE editavel = 0 AND arquivado = 0 AND excluido = 0 AND spam = 0", nativeQuery = true)
    List<Object[]> listarTodosEmails();

    @Query(value = "SELECT * FROM T_LCW_EMAIL INNER JOIN T_LCW_ALTERACAO ON T_LCW_EMAIL.id_email = T_LCW_ALTERACAO.alt_id_email WHERE T_LCW_ALTERACAO.alt_id_usuario = :id_usuario AND remetente = :remetente AND editavel = 0 AND arquivado = 0 AND excluido = 0 AND spam = 0", nativeQuery = true)
    List<Object[]> listarEmailsEnviadosPorRemetente(@Param("remetente") String remetente, @Param("id_usuario") Long id_usuario);

    @Query(value = "SELECT * FROM T_LCW_EMAIL INNER JOIN T_LCW_ALTERACAO ON T_LCW_EMAIL.id_email = T_LCW_ALTERACAO.alt_id_email WHERE T_LCW_ALTERACAO.alt_id_usuario = :id_usuario AND editavel = 0 AND arquivado = 0 AND excluido = 0 AND spam = 0 AND id_pasta IS NULL", nativeQuery = true)
    List<Object[]> listarEmailsPorDestinatario(@Param("id_usuario") Long id_usuario);

    @Query(value = "SELECT * FROM T_LCW_EMAIL INNER JOIN T_LCW_ALTERACAO ON T_LCW_EMAIL.id_email = T_LCW_ALTERACAO.alt_id_email WHERE T_LCW_ALTERACAO.alt_id_usuario = :id_usuario AND editavel = 0", nativeQuery = true)
    List<Object[]> listarEmailsAi(@Param("id_usuario") Long id_usuario);

    @Query(value = "SELECT * FROM T_LCW_EMAIL WHERE remetente = :remetente AND editavel = 1", nativeQuery = true)
    List<Email> listarEmailsEditaveisPorRemetente(@Param("remetente") String remetente);

    @Query(value = "SELECT * FROM T_LCW_EMAIL WHERE id_email = :id_email", nativeQuery = true)
    Email listarEmailPorId(@Param("id_email") Long id_email);

    @Query(value = "SELECT * FROM T_LCW_EMAIL INNER JOIN T_LCW_ALTERACAO ON T_LCW_EMAIL.id_email = T_LCW_ALTERACAO.alt_id_email WHERE T_LCW_ALTERACAO.alt_id_usuario = :id_usuario AND importante = 1 AND editavel = 0 AND arquivado = 0 AND excluido = 0 AND spam = 0", nativeQuery = true)
    List<Object[]> listarEmailsImportantesPorIdUsuario(@Param("id_usuario") Long id_usuario);

    @Query(value = "SELECT * FROM T_LCW_EMAIL INNER JOIN T_LCW_ALTERACAO ON T_LCW_EMAIL.id_email = T_LCW_ALTERACAO.alt_id_email WHERE T_LCW_ALTERACAO.alt_id_usuario = :id_usuario AND agenda_atrelada = 1 AND editavel = 0 AND arquivado = 0 AND excluido = 0 AND spam = 0", nativeQuery = true)
    List<Object[]> listarEmailsSociaisPorIdUsuario(@Param("id_usuario") Long id_usuario);

    @Query(value = "SELECT * FROM T_LCW_EMAIL INNER JOIN T_LCW_ALTERACAO ON T_LCW_EMAIL.id_email = T_LCW_ALTERACAO.alt_id_email WHERE T_LCW_ALTERACAO.alt_id_usuario = :id_usuario AND arquivado = 1 AND excluido = 0 AND spam = 0 AND editavel = 0", nativeQuery = true)
    List<Object[]> listarEmailsArquivadosPorIdUsuario(@Param("id_usuario") Long id_usuario);

    @Query(value = "SELECT * FROM T_LCW_EMAIL INNER JOIN T_LCW_ALTERACAO ON T_LCW_EMAIL.id_email = T_LCW_ALTERACAO.alt_id_email WHERE T_LCW_ALTERACAO.alt_id_usuario = :id_usuario AND spam = 1 AND editavel = 0 AND excluido = 0", nativeQuery = true)
    List<Object[]> listarEmailsSpamPorIdUsuario(@Param("id_usuario") Long id_usuario);

    @Query(value = "SELECT * FROM T_LCW_EMAIL INNER JOIN T_LCW_ALTERACAO ON T_LCW_EMAIL.id_email = T_LCW_ALTERACAO.alt_id_email WHERE T_LCW_ALTERACAO.alt_id_usuario = :id_usuario AND excluido = 1 AND editavel = 0", nativeQuery = true)
    List<Object[]> listarEmailsLixeiraPorIdUsuario(@Param("id_usuario") Long id_usuario);

    @Query(value = "SELECT * FROM T_LCW_EMAIL INNER JOIN T_LCW_ALTERACAO ON T_LCW_EMAIL.id_email = T_LCW_ALTERACAO.alt_id_email WHERE T_LCW_ALTERACAO.alt_id_usuario = :id_usuario AND id_pasta = :id_pasta AND arquivado = 0 AND excluido = 0 AND spam = 0 AND editavel = 0", nativeQuery = true)
    List<Object[]> listarEmailsPorPastaEIdUsuario(@Param("id_usuario") Long id_usuario, @Param("id_pasta") Long id_pasta);
}
