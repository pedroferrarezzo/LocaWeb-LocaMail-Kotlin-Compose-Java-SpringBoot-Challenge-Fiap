package br.com.fiap.locawebmailapp.service

import br.com.fiap.locawebmailapp.model.Alteracao
import br.com.fiap.locawebmailapp.model.Convidado
import br.com.fiap.locawebmailapp.model.Email
import br.com.fiap.locawebmailapp.model.Usuario
import br.com.fiap.locawebmailapp.model.UsuarioSemSenha
import br.com.fiap.locawebmailapp.model.ai.GeminiRequest
import br.com.fiap.locawebmailapp.model.ai.GeminiResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LocaMailApiService {

    @GET("/usuario/retornaUsarioPorEmail")
    fun retornaUsarioPorEmail(@Query("email") email: String): Call<Usuario?>

    @POST("/usuario/criarUsuario")
    fun criarUsuario(@Body usuario: Usuario): Call<UsuarioSemSenha?>

    @POST("/alteracao/criarAlteracao")
    fun criarAlteracao(@Body alteracao: Alteracao): Call<Alteracao?>

    @POST("/convidado/criarConvidado")
    fun criarConvidado(@Body convidado: Convidado): Call<Convidado?>

    @GET("/convidado/verificarConvidadoExiste")
    fun verificarConvidadoExiste(@Query("email") email: String): Call<String?>

    @POST("/email/criarEmail")
    fun criarEmail(@Body email: Email): Call<Email?>

}