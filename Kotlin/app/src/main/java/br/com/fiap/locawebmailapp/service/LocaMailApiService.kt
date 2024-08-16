package br.com.fiap.locawebmailapp.service

import br.com.fiap.locawebmailapp.model.Usuario
import br.com.fiap.locawebmailapp.model.ai.GeminiRequest
import br.com.fiap.locawebmailapp.model.ai.GeminiResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LocaMailApiService {

    @GET("/usuario/retornaUsarioPorEmail")
    fun retornaUsarioPorEmail(@Query("email") email: String): Call<Usuario>

    @POST("/usuario/criarUsuario")
    fun criarUsuario(@Body usuario: Usuario): Call<Usuario>
}