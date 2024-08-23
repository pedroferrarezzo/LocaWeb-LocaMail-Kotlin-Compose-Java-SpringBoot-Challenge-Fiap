package br.com.fiap.locawebmailapp.service

import br.com.fiap.locawebmailapp.model.Agenda
import br.com.fiap.locawebmailapp.model.Alteracao
import br.com.fiap.locawebmailapp.model.Convidado
import br.com.fiap.locawebmailapp.model.Email
import br.com.fiap.locawebmailapp.model.EmailComAlteracao
import br.com.fiap.locawebmailapp.model.Pasta
import br.com.fiap.locawebmailapp.model.RespostaEmail
import br.com.fiap.locawebmailapp.model.Usuario
import br.com.fiap.locawebmailapp.model.UsuarioSemSenha
import br.com.fiap.locawebmailapp.model.ai.GeminiRequest
import br.com.fiap.locawebmailapp.model.ai.GeminiResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface LocaMailApiService {

    @GET("/usuario/retornaUsarioPorEmail")
    fun retornaUsarioPorEmail(@Query("email") email: String): Call<Usuario?>

    @GET("/usuario/listarUsuarioSelecionado")
    fun listarUsuarioSelecionado(): Call<Usuario?>

    @POST("/usuario/criarUsuario")
    fun criarUsuario(@Body usuario: Usuario): Call<UsuarioSemSenha?>

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

    @GET("/pasta/listarPastasPorIdUsuario")
    fun listarPastasPorIdUsuario(@Query("idUsuario") id_usuario: Long): Call<List<Pasta>?>

    @GET("/usuario/listarUsuariosAutenticados")
    fun listarUsuariosAutenticados(): Call<List<Usuario>?>

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
    fun atualizarPastaPorIdEmailEIdUsuario(@Query("pasta") pasta: Long, @Query("idEmail") id_email: Long, @Query("idUsuario") id_usuario: Long): Call<Unit>

    @PATCH("/alteracao/atualizarImportantePorIdEmail")
    fun atualizarImportantePorIdEmail(@Query("importante") pasta: Boolean, @Query("idEmail") id_email: Long, @Query("idUsuario") id_usuario: Long): Call<Unit>



}