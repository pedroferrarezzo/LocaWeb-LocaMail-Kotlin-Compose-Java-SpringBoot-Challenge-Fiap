package br.com.fiap.locawebmailapp.service

import androidx.compose.foundation.lazy.layout.IntervalList
import br.com.fiap.locawebmailapp.model.Agenda
import br.com.fiap.locawebmailapp.model.AgendaConvidado
import br.com.fiap.locawebmailapp.model.Alteracao
import br.com.fiap.locawebmailapp.model.Anexo
import br.com.fiap.locawebmailapp.model.AnexoRespostaEmail
import br.com.fiap.locawebmailapp.model.Convidado
import br.com.fiap.locawebmailapp.model.Email
import br.com.fiap.locawebmailapp.model.EmailComAlteracao
import br.com.fiap.locawebmailapp.model.Pasta
import br.com.fiap.locawebmailapp.model.RespostaEmail
import br.com.fiap.locawebmailapp.model.Usuario
import br.com.fiap.locawebmailapp.model.UsuarioSemSenha
import br.com.fiap.locawebmailapp.model.ai.AiQuestion
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface LocaMailApiService {

    @GET("/usuario/retornaUsarioPorEmail")
    fun retornaUsarioPorEmail(@Query("email") email: String): Call<Usuario?>

    @GET("/usuario/listarUsuarioSelecionado")
    fun listarUsuarioSelecionado(): Call<Usuario?>

    @POST("/usuario/criarUsuario")
    fun criarUsuario(@Body usuario: Usuario): Call<UsuarioSemSenha?>

    @POST("/respostaEmail/criarRespostaEmail")
    fun criarRespostaEmail(@Body respostaEmail: RespostaEmail): Call<RespostaEmail?>

    @POST("/anexoRespostaEmail/criarAnexo")
    fun criarAnexoRespostaEmail(@Body anexoRespostaEmail: AnexoRespostaEmail): Call<AnexoRespostaEmail?>

    @POST("/anexo/criarAnexo")
    fun criarAnexo(@Body anexo: Anexo): Call<Anexo?>

    @PUT("/email/atualizarEmail")
    fun atualizarEmail(@Body email: Email): Call<Email?>

    @PUT("/respostaEmail/atualizarRespostaEmail")
    fun atualizarRespostaEmail(@Body respostaEmail: RespostaEmail): Call<RespostaEmail?>

    @GET("/usuario/listarUsuarios")
    fun listarUsuarios(): Call<List<Usuario>?>

    @POST("/alteracao/criarAlteracao")
    fun criarAlteracao(@Body alteracao: Alteracao): Call<Alteracao?>

    @POST("/convidado/criarConvidado")
    fun criarConvidado(@Body convidado: Convidado): Call<Convidado?>

    @GET("/convidado/verificarConvidadoExiste")
    fun verificarConvidadoExiste(@Query("email") email: String): Call<String?>

    @GET("/alteracao/listarAlteracaoPorIdEmail")
    fun listarAlteracaoPorIdEmail(@Query("idEmail") id_email: Long): Call<List<Alteracao>?>

    @POST("/email/criarEmail")
    fun criarEmail(@Body email: Email): Call<Email?>

    @GET("/email/listarEmailPorId")
    fun listarEmailPorId(@Query("idEmail") id_email: Long): Call<Email?>

    @GET("/alteracao/listarAlteracaoPorIdEmailEIdUsuario")
    fun listarAlteracaoPorIdEmailEIdUsuario(@Query("idEmail") id_email: Long, @Query("idUsuario") id_usuario: Long): Call<Alteracao?>

    @GET("/email/listarEmailsPorDestinatario")
    fun listarEmailsPorDestinatario(@Query("destinatario") destinatario: String, @Query("idUsuario") id_usuario: Long): Call<List<EmailComAlteracao>?>

    @GET("/email/listarEmailsEnviadosPorRemetente")
    fun listarEmailsEnviadosPorRemetente(@Query("remetente") remetente: String, @Query("idUsuario") id_usuario: Long): Call<List<EmailComAlteracao>?>

    @GET("/agenda/listarAgendaPorDia")
    fun listarAgendaPorDia(@Query("data") data: String, @Query("idUsuario") id_usuario: Long): Call<List<Agenda>?>

    @GET("/agenda/listarCorAgendaPorDia")
    fun listarCorAgendaPorDia(@Query("data") data: String, @Query("idUsuario") id_usuario: Long): Call<List<Int>?>

    @GET("/email/listarEmailsImportantesPorIdUsuario")
    fun listarEmailsImportantesPorIdUsuario(@Query("idUsuario") id_usuario: Long): Call<List<EmailComAlteracao>?>

    @GET("/email/listarEmailsSpamPorIdUsuario")
    fun listarEmailsSpamPorIdUsuario(@Query("idUsuario") id_usuario: Long): Call<List<EmailComAlteracao>?>

    @GET("/email/listarEmailsSociaisPorIdUsuario")
    fun listarEmailsSociaisPorIdUsuario(@Query("idUsuario") id_usuario: Long): Call<List<EmailComAlteracao>?>

    @GET("/email/listarTodosEmails")
    fun listarTodosEmails(): Call<List<EmailComAlteracao>?>

    @GET("/convidado/listarConvidado")
    fun listarConvidado(): Call<List<Convidado>?>

    @GET("/email/listarEmailsArquivadosPorIdUsuario")
    fun listarEmailsArquivadosPorIdUsuario(@Query("idUsuario") id_usuario: Long): Call<List<EmailComAlteracao>?>


    @GET("/email/listarEmailsPorPastaEIdUsuario")
    fun listarEmailsPorPastaEIdUsuario(@Query("idUsuario") id_usuario: Long, @Query("pasta") pasta: Long): Call<List<EmailComAlteracao>?>

    @GET("/email/listarEmailsLixeiraPorIdUsuario")
    fun listarEmailsLixeiraPorIdUsuario(@Query("idUsuario") id_usuario: Long): Call<List<EmailComAlteracao>?>

    @GET("/email/listarEmailsEditaveisPorRemetente")
    fun listarEmailsEditaveisPorRemetente(@Query("remetente") remetente: String): Call<List<Email>?>

    @GET("/pasta/listarPastasPorIdUsuario")
    fun listarPastasPorIdUsuario(@Query("idUsuario") id_usuario: Long): Call<List<Pasta>?>

    @GET("/usuario/listarUsuariosAutenticados")
    fun listarUsuariosAutenticados(): Call<List<Usuario>?>

    @GET("/alteracao/verificarLidoPorIdEmailEIdUsuario")
    fun verificarLidoPorIdEmailEIdUsuario(@Query("idEmail") id_email: Long, @Query("idUsuario") id_usuario: Long): Call<Boolean?>


    @GET("/alteracao/verificarExcluidoPorIdEmailEIdUsuario")
    fun verificarExcluidoPorIdEmailEIdUsuario(@Query("idEmail") id_email: Long, @Query("idUsuario") id_usuario: Long): Call<Boolean?>


    @GET("/alteracao/verificarSpamPorIdEmailEIdUsuario")
    fun verificarSpamPorIdEmailEIdUsuario(@Query("idEmail") id_email: Long, @Query("idUsuario") id_usuario: Long): Call<Boolean?>

    @GET("/alteracao/verificarArquivadoPorIdEmailEIdUsuario")
    fun verificarArquivadoPorIdEmailEIdUsuario(@Query("idEmail") id_email: Long, @Query("idUsuario") id_usuario: Long): Call<Boolean?>

    @GET("/alteracao/verificarImportantePorIdEmailEIdUsuario")
    fun verificarImportantePorIdEmailEIdUsuario(@Query("idEmail") id_email: Long, @Query("idUsuario") id_usuario: Long): Call<Boolean?>


    @GET("/usuario/listarUsuariosNaoSelecionados")
    fun listarUsuariosNaoSelecionados(): Call<List<Usuario>?>

    @POST("/pasta/criarPasta")
    fun criarPasta(@Body pasta: Pasta): Call<Pasta?>

    @DELETE("/pasta/excluirPasta")
    fun excluirPasta(@Query("pasta") pasta: Long): Call<Unit>

    @DELETE("/anexo/excluirAnexoPorIdEmail")
    fun excluirAnexoPorIdEmail(@Query("idEmail") id_email: Long): Call<Unit>

    @DELETE("/anexoRespostaEmail/excluirAnexoPorIdRespostaEmail")
    fun excluirAnexoPorIdRespostaEmail(@Query("idRespostaEmail") id_resposta_email: Long): Call<Unit>

    @DELETE("/respostaEmail/excluirRespostaEmailPorIdEmail")
    fun excluirRespostaEmailPorIdEmail(@Query("idEmail") id_email: Long): Call<Unit>

    @DELETE("/email/excluirEmail")
    fun excluirEmail(@Query("idEmail") id_email: Long): Call<Unit>

    @DELETE("/agendaConvidado/excluirPorIdAgenda")
    fun excluirPorIdAgenda(@Query("idAgenda") id_agenda: Long): Call<Unit>

    @DELETE("/agenda/excluiAgenda")
    fun excluiAgenda(@Query("idAgenda") id_agenda: Long): Call<Unit>

    @DELETE("/respostaEmail/excluirRespostaEmail")
    fun excluirRespostaEmail(@Query("idRespostaEmail") id_resposta_email: Long): Call<Unit>


    @DELETE("/alteracao/excluiAlteracaoPorIdEmailEIdUsuario")
    fun excluiAlteracaoPorIdEmailEIdUsuario(@Query("idEmail") id_email: Long, @Query("idUsuario") id_usuario: Long): Call<Unit>


    @PATCH("/alteracao/atualizarExcluidoPorIdEmailEIdusuario")
    fun atualizarExcluidoPorIdEmailEIdusuario(@Query("excluido") excluido: Boolean,@Query("idEmail") id_email: Long, @Query("idUsuario") id_usuario: Long): Call<Unit>


    @PATCH("/alteracao/atualizarSpamPorIdEmailEIdusuario")
    fun atualizarSpamPorIdEmailEIdusuario(@Query("spam") spam: Boolean,@Query("idEmail") id_email: Long, @Query("idUsuario") id_usuario: Long): Call<Unit>


    @PATCH("/agenda/atualizaVisivelPorIdAgenda")
    fun atualizaVisivelPorIdAgenda(@Query("idAgenda") id_agenda: Long, @Query("visivel") visivel: Boolean): Call<Unit>

    @PATCH("/alteracao/atualizarArquivadoPorIdEmailEIdusuario")
    fun atualizarArquivadoPorIdEmailEIdusuario(@Query("arquivado") arquivado: Boolean,@Query("idEmail") id_email: Long, @Query("idUsuario") id_usuario: Long): Call<Unit>

    @GET("/anexo/listarAnexosIdEmail")
    fun listarAnexosIdEmail(): Call<List<Long>?>

    @GET("/anexo/listarAnexosArraybytePorIdEmail")
    fun listarAnexosArraybytePorIdEmail(@Query("idEmail") id_email: Long): Call<List<ByteArray>?>

    @GET("/anexoRespostaEmail/listarAnexosArrayBytePorIdRespostaEmail")
    fun listarAnexosArrayBytePorIdRespostaEmail(@Query("idRespostaEmail") id_resposta_email: Long): Call<List<ByteArray>?>

    @GET("/agenda/listarAgendaPorIdEmailEIdUsuario")
    fun listarAgendaPorIdEmailEIdUsuario(@Query("idEmail") id_email: Long, @Query("idUsuario") id_usuario: Long): Call<List<Agenda>?>


    @GET("/respostaEmail/listarRespostasEmailPorIdEmail")
    fun listarRespostasEmailPorIdEmail(@Query("idEmail") id_email: Long): Call<List<RespostaEmail>?>


    @GET("/respostaEmail/listarRespostaEmailPorIdRespostaEmail")
    fun listarRespostaEmailPorIdRespostaEmail(@Query("idRespostaEmail") id_resposta_email: Long): Call<RespostaEmail?>



    @GET("/alteracao/listarAltIdUsuarioPorIdEmail")
    fun listarAltIdUsuarioPorIdEmail(@Query("idEmail") id_email: Long): Call<List<Long>?>


    @GET("/alteracao/listarAlteracaoPorIdUsuarioEIdPasta")
    fun listarAlteracaoPorIdUsuarioEIdPasta(@Query("idUsuario") id_usuario: Long, @Query("pasta") pasta: Long): Call<List<Alteracao>?>

    @PATCH("/alteracao/atualizarPastaPorIdAlteracao")
    fun atualizarPastaPorIdAlteracao(@Query("pasta") pasta: Long?, @Query("idAlteracao") id_alteracao: Long): Call<Unit>

    @PATCH("/usuario/desselecionarUsuarioSelecionadoAtual")
    fun desselecionarUsuarioSelecionadoAtual(): Call<Unit>

    @PATCH("/usuario/selecionarUsuario")
    fun selecionarUsuario(@Query("idUsuario") id_usuario: Long): Call<Unit>

    @PATCH("/usuario/atualizaAutenticaUsuario")
    fun atualizaAutenticaUsuario(@Query("idUsuario") id_usuario: Long, @Query("autenticado") autenticado: Boolean): Call<Unit>

    @PATCH("/alteracao/atualizarLidoPorIdEmailEIdusuario")
    fun atualizarLidoPorIdEmailEIdusuario(@Query("lido") lido: Boolean, @Query("idEmail") id_email: Long, @Query("idUsuario") id_usuario: Long): Call<Unit>

    @PATCH("/alteracao/atualizarPastaPorIdEmailEIdUsuario")
    fun atualizarPastaPorIdEmailEIdUsuario(@Query("pasta") pasta: Long?, @Query("idEmail") id_email: Long, @Query("idUsuario") id_usuario: Long): Call<Unit>

    @PATCH("/alteracao/atualizarImportantePorIdEmail")
    fun atualizarImportantePorIdEmail(@Query("importante") pasta: Boolean, @Query("idEmail") id_email: Long, @Query("idUsuario") id_usuario: Long): Call<Unit>

    @GET("/agenda/listarGrupoRepeticao")
    fun listarGrupoRepeticao(): Call<List<Int>?>

    @GET("/agenda/listarAgendaPorId")
    fun listarAgendaPorId(@Query("idAgenda") id_agenda: Long): Call<Agenda?>

    @GET("/convidado/listarConvidadoPorId")
    fun listarConvidadoPorId(@Query("idConvidado") id_convidado: Long): Call<Convidado?>

    @GET("/agendaConvidado/listarIdConvidadoPorAgenda")
    fun listarIdConvidadoPorAgenda(@Query("idAgenda") id_agenda: Long): Call<List<Long>?>

    @DELETE("/agendaConvidado/excluirPorIdAgendaEIdConvidado")
    fun excluirPorIdAgendaEIdConvidado(@Query("idAgenda") id_agenda: Long, @Query("idConvidado") id_convidado: Long): Call<Unit?>

    @DELETE("/agenda/excluirPorGrupoRepeticao")
    fun excluirPorGrupoRepeticao(@Query("grupoRepeticao") grupo_repeticao: Int): Call<Unit?>

    @DELETE("/agenda/excluirPorGrupoRepeticaoExcetoData")
    fun excluirPorGrupoRepeticaoExcetoData(@Query("grupoRepeticao") grupo_repeticao: Int, @Query("data") data: String): Call<Unit?>

    @DELETE("/agendaConvidado/excluirPorGrupoRepeticaoExcetoIdAgenda")
    fun excluirPorGrupoRepeticaoExcetoIdAgenda(@Query("grupoRepeticao") grupo_repeticao: Int, @Query("idAgenda") id_agenda: Long): Call<Unit?>

    @PUT("/agenda/atualizaOpcaoRepeticaoPorGrupoRepeticao")
    fun atualizaOpcaoRepeticaoPorGrupoRepeticao(@Query("grupoRepeticao") grupo_repeticao: Int, @Query("repeticao") repeticao: Int): Call<Unit?>

    @PUT("/agenda/atualizaOpcaoRepeticaoPorIdAgenda")
    fun atualizaOpcaoRepeticaoPorIdAgenda(@Query("idAgenda") id_agenda: Long, @Query("repeticao") repeticao: Int): Call<Unit?>

    @PUT("/agenda/atualizaAgenda")
    fun atualizaAgenda(@Body agenda: Agenda): Call<Unit?>

    @GET("/agenda/retornaValorAtualSeqPrimayKey")
    fun retornaValorAtualSeqPrimayKey(): Call<Long?>

    @POST("/agenda/criarAgenda")
    fun criarAgenda(@Body agenda: Agenda): Call<Agenda?>

    @POST("/agendaConvidado/criaAgendaConvidado")
    fun criarAgendaConvidado(@Body agendaConvidado: AgendaConvidado): Call<AgendaConvidado?>

    @POST("/aiQuestion/criarPergunta")
    fun criarPergunta(@Body aiQuestion: AiQuestion): Call<AiQuestion?>

    @GET("/aiQuestion/obterPergunta")
    fun obterPergunta(@Query("idQuestion") id_question: Long, @Query("idEmail") id_email: Long): Call<AiQuestion?>



}