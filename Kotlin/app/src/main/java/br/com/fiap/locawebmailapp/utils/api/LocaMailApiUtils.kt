package br.com.fiap.locawebmailapp.utils.api

import br.com.fiap.locawebmailapp.model.Alteracao
import br.com.fiap.locawebmailapp.model.Convidado
import br.com.fiap.locawebmailapp.model.Email
import br.com.fiap.locawebmailapp.model.EmailComAlteracao
import br.com.fiap.locawebmailapp.model.Pasta
import br.com.fiap.locawebmailapp.model.RespostaEmail
import br.com.fiap.locawebmailapp.model.Usuario
import br.com.fiap.locawebmailapp.model.UsuarioSemSenha
import br.com.fiap.locawebmailapp.service.LocaMailApiFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

fun callLocaMailApiListarUsuarioSelecionado(
    onSuccess: (Usuario?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val call = LocaMailApiFactory().getLocaMailApiFactory().listarUsuarioSelecionado()

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

fun callLocaMailApiListarEmailsPorDestinatario(
    destinatario: String,
    id_usuario: Long,
    onSuccess: (List<EmailComAlteracao>?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val call = LocaMailApiFactory().getLocaMailApiFactory().listarEmailsPorDestinatario(destinatario, id_usuario)

    call.enqueue(object : Callback<List<EmailComAlteracao>?> {
        override fun onResponse(call: Call<List<EmailComAlteracao>?>, response: Response<List<EmailComAlteracao>?>) {
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
        override fun onFailure(call: Call<List<EmailComAlteracao>?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiListarPastasPorIdUsuario(
    id_usuario: Long,
    onSuccess: (List<Pasta>?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val call = LocaMailApiFactory().getLocaMailApiFactory().listarPastasPorIdUsuario(id_usuario)

    call.enqueue(object : Callback<List<Pasta>?> {
        override fun onResponse(call: Call<List<Pasta>?>, response: Response<List<Pasta>?>) {
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
        override fun onFailure(call: Call<List<Pasta>?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiListarAnexosIdEmail(
    onSuccess: (List<Long>?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val call = LocaMailApiFactory().getLocaMailApiFactory().listarAnexosIdEmail()

    call.enqueue(object : Callback<List<Long>?> {
        override fun onResponse(call: Call<List<Long>?>, response: Response<List<Long>?>) {
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
        override fun onFailure(call: Call<List<Long>?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiListarRespostasEmailPorIdEmail(
    id_email: Long,
    onSuccess: (List<RespostaEmail>?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val call = LocaMailApiFactory().getLocaMailApiFactory().listarRespostasEmailPorIdEmail(id_email)

    call.enqueue(object : Callback<List<RespostaEmail>?> {
        override fun onResponse(call: Call<List<RespostaEmail>?>, response: Response<List<RespostaEmail>?>) {
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
        override fun onFailure(call: Call<List<RespostaEmail>?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiListarAlteracaoPorIdUsuarioEIdPasta(
    id_usuario: Long,
    pasta: Long,
    onSuccess: (List<Alteracao>?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val call = LocaMailApiFactory().getLocaMailApiFactory().listarAlteracaoPorIdUsuarioEIdPasta(id_usuario, pasta)

    call.enqueue(object : Callback<List<Alteracao>?> {
        override fun onResponse(call: Call<List<Alteracao>?>, response: Response<List<Alteracao>?>) {
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
        override fun onFailure(call: Call<List<Alteracao>?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiListarUsuariosAutenticados(
    onSuccess: (List<Usuario>?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val call = LocaMailApiFactory().getLocaMailApiFactory().listarUsuariosAutenticados()

    call.enqueue(object : Callback<List<Usuario>?> {
        override fun onResponse(call: Call<List<Usuario>?>, response: Response<List<Usuario>?>) {
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
        override fun onFailure(call: Call<List<Usuario>?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiListarUsuariosNaoSelecionados(
    onSuccess: (List<Usuario>?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val call = LocaMailApiFactory().getLocaMailApiFactory().listarUsuariosNaoSelecionados()

    call.enqueue(object : Callback<List<Usuario>?> {
        override fun onResponse(call: Call<List<Usuario>?>, response: Response<List<Usuario>?>) {
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
        override fun onFailure(call: Call<List<Usuario>?>, t: Throwable) {
            onError(t)
        }
    })
}




fun callLocaMailApiAtualizarPastaPorIdAlteracao(
    id_alteracao: Long,
    pasta: Long?,
    onSuccess: (Unit?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val call = LocaMailApiFactory().getLocaMailApiFactory().atualizarPastaPorIdAlteracao(pasta, id_alteracao)

    call.enqueue(object : Callback<Unit> {
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


fun callLocaMailApiCriarPasta(
    pasta: Pasta,
    onSuccess: (Pasta?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory().criarPasta(pasta)

    callLocaMailApiService.enqueue(object : Callback<Pasta?> {
        override fun onResponse(call: Call<Pasta?>, response: Response<Pasta?>) {
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

        override fun onFailure(call: Call<Pasta?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiExcluirPasta(
    pasta: Long,
    onSuccess: (Unit?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory().excluirPasta(pasta)

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

fun callLocaMailApiAtualizarLidoPorIdEmailEIdusuario(
    lido: Boolean,
    id_email: Long,
    id_usuario: Long,
    onSuccess: (Unit?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory().atualizarLidoPorIdEmailEIdusuario(lido, id_email, id_usuario)

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

fun callLocaMailApiAtualizarPastaPorIdEmailEIdUsuario(
    pasta: Long,
    id_email: Long,
    id_usuario: Long,
    onSuccess: (Unit?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory().atualizarPastaPorIdEmailEIdUsuario(pasta, id_email, id_usuario)

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

fun callLocaMailApiAtualizarImportantePorIdEmail(
    importante: Boolean,
    id_email: Long,
    id_usuario: Long,
    onSuccess: (Unit?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory().atualizarImportantePorIdEmail(importante, id_email, id_usuario)

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








