package br.com.fiap.locawebmailapp.utils.api

import android.util.Log
import br.com.fiap.locawebmailapp.model.Usuario
import br.com.fiap.locawebmailapp.service.LocaMailApiFactory
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

// USER_SCREEN

suspend fun callLocaMailApiRetornaUsarioPorEmail(email: String):Usuario {
    return suspendCoroutine { continuation ->
        val call = LocaMailApiFactory().getLocaMailApiFactory().retornaUsarioPorEmail(email)

        call.enqueue(object : Callback<Usuario> {
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                if (response.isSuccessful && response.body() != null) {
                    continuation.resume(response.body()!!)
                }
            }
            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                continuation.resumeWithException(t)
            }
        })
    }
}

suspend fun callLocaMailApicriarUsuario(usuario: Usuario): Usuario {

    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory().criarUsuario(usuario);

    return suspendCoroutine { continuation ->
        callLocaMailApiService.enqueue(object : Callback<Usuario> {
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                response.body()?.let {
                    continuation.resume(it)
                } ?: continuation.resumeWithException(Throwable(response.errorBody()!!.string()))
            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                continuation.resumeWithException(Throwable("API_PROBLEM"))
            }
        })
    }
}