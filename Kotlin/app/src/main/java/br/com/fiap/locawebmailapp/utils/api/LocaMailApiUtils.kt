package br.com.fiap.locawebmailapp.utils.api

import android.util.Log
import br.com.fiap.locawebmailapp.model.Alteracao
import br.com.fiap.locawebmailapp.model.Convidado
import br.com.fiap.locawebmailapp.model.Email
import br.com.fiap.locawebmailapp.model.Usuario
import br.com.fiap.locawebmailapp.model.UsuarioSemSenha
import br.com.fiap.locawebmailapp.service.LocaMailApiFactory
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

// USER_SCREENS

fun callLocaMailApiRetornaUsarioPorEmail(
    email: String,
    onSuccess: (Usuario?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val call = LocaMailApiFactory().getLocaMailApiFactory().retornaUsarioPorEmail(email)

    call.enqueue(object : Callback<Usuario?> {
        override fun onResponse(call: Call<Usuario?>, response: Response<Usuario?>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            }
            else if (response.isSuccessful == false) {
                onError(Throwable())
            }
            else {
                onSuccess(null)
            }
        }
        override fun onFailure(call: Call<Usuario?>, t: Throwable) {
            onError(t)
        }
    })
}


fun callLocaMailApicriarUsuario(
    usuario: Usuario,
    onSuccess: (UsuarioSemSenha?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory().criarUsuario(usuario)

    callLocaMailApiService.enqueue(object : Callback<UsuarioSemSenha?> {
        override fun onResponse(call: Call<UsuarioSemSenha?>, response: Response<UsuarioSemSenha?>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            }
            else if (response.isSuccessful == false) {
                onError(Throwable())
            }
            else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<UsuarioSemSenha?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiCriarAlteracao(
    alteracao: Alteracao,
    onSuccess: (Alteracao?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory().criarAlteracao(alteracao)

    callLocaMailApiService.enqueue(object : Callback<Alteracao?> {
        override fun onResponse(call: Call<Alteracao?>, response: Response<Alteracao?>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            }
            else if (response.isSuccessful == false) {
                onError(Throwable())
            }
            else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<Alteracao?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiCriarConvidado(
    convidado: Convidado,
    onSuccess: (Convidado?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory().criarConvidado(convidado)

    callLocaMailApiService.enqueue(object : Callback<Convidado?> {
        override fun onResponse(call: Call<Convidado?>, response: Response<Convidado?>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            }
            else if (response.isSuccessful == false) {
                onError(Throwable())
            }
            else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<Convidado?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiVerificarConvidadoExiste(
    email: String,
    onSuccess: (String?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory().verificarConvidadoExiste(email)

    callLocaMailApiService.enqueue(object : Callback<String?> {
        override fun onResponse(call: Call<String?>, response: Response<String?>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            }
            else if (response.isSuccessful == false) {
                onError(Throwable())
            }
            else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<String?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiCriarEmail(
    email: Email,
    onSuccess: (Email?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory().criarEmail(email)

    callLocaMailApiService.enqueue(object : Callback<Email?> {
        override fun onResponse(call: Call<Email?>, response: Response<Email?>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            }
            else if (response.isSuccessful == false) {
                onError(Throwable())
            }
            else {
                onSuccess(null)
            }
        }
        override fun onFailure(call: Call<Email?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiDesselecionarUsuarioSelecionadoAtual(
    onSuccess: (Unit?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory().desselecionarUsuarioSelecionadoAtual()

    callLocaMailApiService.enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            }
            else if (response.isSuccessful == false) {
                onError(Throwable())
            }
            else {
                onSuccess(null)
            }
        }
        override fun onFailure(call: Call<Unit>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiSelecionarUsuario(
    id_usuario: Long,
    onSuccess: (Unit?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory().selecionarUsuario(id_usuario)

    callLocaMailApiService.enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            }
            else if (response.isSuccessful == false) {
                onError(Throwable())
            }
            else {
                onSuccess(null)
            }
        }
        override fun onFailure(call: Call<Unit>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiAtualizaAutenticaUsuario(
    id_usuario: Long,
    autenticado: Boolean,
    onSuccess: (Unit?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory().atualizaAutenticaUsuario(id_usuario, autenticado)

    callLocaMailApiService.enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            }
            else if (response.isSuccessful == false) {
                onError(Throwable())
            }
            else {
                onSuccess(null)
            }
        }
        override fun onFailure(call: Call<Unit>, t: Throwable) {
            onError(t)
        }
    })
}








