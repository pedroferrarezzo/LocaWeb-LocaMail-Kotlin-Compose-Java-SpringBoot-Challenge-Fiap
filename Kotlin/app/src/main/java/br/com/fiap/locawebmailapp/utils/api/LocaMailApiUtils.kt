package br.com.fiap.locawebmailapp.utils.api

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
import br.com.fiap.locawebmailapp.service.LocaMailApiFactory
import br.com.fiap.locawebmailapp.utils.getValidatedString
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
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
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
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
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
    val call = LocaMailApiFactory().getLocaMailApiFactory()
        .listarEmailsPorDestinatario(destinatario, id_usuario)



    call.enqueue(object : Callback<List<EmailComAlteracao>?> {
        override fun onResponse(
            call: Call<List<EmailComAlteracao>?>,
            response: Response<List<EmailComAlteracao>?>
        ) {
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.forEach {
                    it.cc = getValidatedString(it.cc)
                    it.cco = getValidatedString(it.cco)
                    it.corpo = getValidatedString(it.corpo)
                    it.destinatario = getValidatedString(it.destinatario)
                    it.assunto = getValidatedString(it.assunto)
                }
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<List<EmailComAlteracao>?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiListarEmailsEnviadosPorRemetente(
    remetente: String,
    id_usuario: Long,
    onSuccess: (List<EmailComAlteracao>?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val call = LocaMailApiFactory().getLocaMailApiFactory()
        .listarEmailsEnviadosPorRemetente(remetente, id_usuario)

    call.enqueue(object : Callback<List<EmailComAlteracao>?> {
        override fun onResponse(
            call: Call<List<EmailComAlteracao>?>,
            response: Response<List<EmailComAlteracao>?>
        ) {
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.forEach {
                    it.cc = getValidatedString(it.cc)
                    it.cco = getValidatedString(it.cco)
                    it.corpo = getValidatedString(it.corpo)
                    it.destinatario = getValidatedString(it.destinatario)
                    it.assunto = getValidatedString(it.assunto)
                }
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<List<EmailComAlteracao>?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiListarGrupoRepeticao(
    onSuccess: (List<Int>?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val call = LocaMailApiFactory().getLocaMailApiFactory()
        .listarGrupoRepeticao()

    call.enqueue(object : Callback<List<Int>?> {
        override fun onResponse(
            call: Call<List<Int>?>,
            response: Response<List<Int>?>
        ) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }
        override fun onFailure(call: Call<List<Int>?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiListarEmailsImportantesPorIdUsuario(
    id_usuario: Long,
    onSuccess: (List<EmailComAlteracao>?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val call = LocaMailApiFactory().getLocaMailApiFactory()
        .listarEmailsImportantesPorIdUsuario(id_usuario)

    call.enqueue(object : Callback<List<EmailComAlteracao>?> {
        override fun onResponse(
            call: Call<List<EmailComAlteracao>?>,
            response: Response<List<EmailComAlteracao>?>
        ) {
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.forEach {
                    it.cc = getValidatedString(it.cc)
                    it.cco = getValidatedString(it.cco)
                    it.corpo = getValidatedString(it.corpo)
                    it.destinatario = getValidatedString(it.destinatario)
                    it.assunto = getValidatedString(it.assunto)
                }
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<List<EmailComAlteracao>?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiListarEmailsArquivadosPorIdUsuario(
    id_usuario: Long,
    onSuccess: (List<EmailComAlteracao>?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val call = LocaMailApiFactory().getLocaMailApiFactory()
        .listarEmailsArquivadosPorIdUsuario(id_usuario)

    call.enqueue(object : Callback<List<EmailComAlteracao>?> {
        override fun onResponse(
            call: Call<List<EmailComAlteracao>?>,
            response: Response<List<EmailComAlteracao>?>
        ) {
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.forEach {
                    it.cc = getValidatedString(it.cc)
                    it.cco = getValidatedString(it.cco)
                    it.corpo = getValidatedString(it.corpo)
                    it.destinatario = getValidatedString(it.destinatario)
                    it.assunto = getValidatedString(it.assunto)
                }
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<List<EmailComAlteracao>?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiListarEmailsSpamPorIdUsuario(
    id_usuario: Long,
    onSuccess: (List<EmailComAlteracao>?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val call = LocaMailApiFactory().getLocaMailApiFactory()
        .listarEmailsSpamPorIdUsuario(id_usuario)

    call.enqueue(object : Callback<List<EmailComAlteracao>?> {
        override fun onResponse(
            call: Call<List<EmailComAlteracao>?>,
            response: Response<List<EmailComAlteracao>?>
        ) {
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.forEach {
                    it.cc = getValidatedString(it.cc)
                    it.cco = getValidatedString(it.cco)
                    it.corpo = getValidatedString(it.corpo)
                    it.destinatario = getValidatedString(it.destinatario)
                    it.assunto = getValidatedString(it.assunto)
                }
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<List<EmailComAlteracao>?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiListarEmailsSociaisPorIdUsuario(
    id_usuario: Long,
    onSuccess: (List<EmailComAlteracao>?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val call = LocaMailApiFactory().getLocaMailApiFactory()
        .listarEmailsSociaisPorIdUsuario(id_usuario)

    call.enqueue(object : Callback<List<EmailComAlteracao>?> {
        override fun onResponse(
            call: Call<List<EmailComAlteracao>?>,
            response: Response<List<EmailComAlteracao>?>
        ) {
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.forEach {
                    it.cc = getValidatedString(it.cc)
                    it.cco = getValidatedString(it.cco)
                    it.corpo = getValidatedString(it.corpo)
                    it.destinatario = getValidatedString(it.destinatario)
                    it.assunto = getValidatedString(it.assunto)
                }
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<List<EmailComAlteracao>?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiListarTodosEmails(
    onSuccess: (List<EmailComAlteracao>?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val call = LocaMailApiFactory().getLocaMailApiFactory()
        .listarTodosEmails()

    call.enqueue(object : Callback<List<EmailComAlteracao>?> {
        override fun onResponse(
            call: Call<List<EmailComAlteracao>?>,
            response: Response<List<EmailComAlteracao>?>
        ) {
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.forEach {
                    it.cc = getValidatedString(it.cc)
                    it.cco = getValidatedString(it.cco)
                    it.corpo = getValidatedString(it.corpo)
                    it.destinatario = getValidatedString(it.destinatario)
                    it.assunto = getValidatedString(it.assunto)
                }
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<List<EmailComAlteracao>?>, t: Throwable) {
            onError(t)
        }
    })
}


fun callLocaMailApiListarEmailsLixeiraPorIdUsuario(
    id_usuario: Long,
    onSuccess: (List<EmailComAlteracao>?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val call = LocaMailApiFactory().getLocaMailApiFactory()
        .listarEmailsLixeiraPorIdUsuario(id_usuario)

    call.enqueue(object : Callback<List<EmailComAlteracao>?> {
        override fun onResponse(
            call: Call<List<EmailComAlteracao>?>,
            response: Response<List<EmailComAlteracao>?>
        ) {
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.forEach {
                    it.cc = getValidatedString(it.cc)
                    it.cco = getValidatedString(it.cco)
                    it.corpo = getValidatedString(it.corpo)
                    it.destinatario = getValidatedString(it.destinatario)
                    it.assunto = getValidatedString(it.assunto)
                }
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<List<EmailComAlteracao>?>, t: Throwable) {
            onError(t)
        }
    })
}


fun callLocaMailApiListarEmailsEditaveisPorRemetente(
    remetente: String,
    onSuccess: (List<Email>?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val call = LocaMailApiFactory().getLocaMailApiFactory()
        .listarEmailsEditaveisPorRemetente(remetente)

    call.enqueue(object : Callback<List<Email>?> {
        override fun onResponse(
            call: Call<List<Email>?>,
            response: Response<List<Email>?>
        ) {
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.forEach {
                    it.cc = getValidatedString(it.cc)
                    it.cco = getValidatedString(it.cco)
                    it.corpo = getValidatedString(it.corpo)
                    it.destinatario = getValidatedString(it.destinatario)
                    it.assunto = getValidatedString(it.assunto)
                }
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<List<Email>?>, t: Throwable) {
            onError(t)
        }
    })
}


fun callLocaMailApiListarAgendaPorDia(
    data: String,
    id_usuario: Long,
    onSuccess: (List<Agenda>?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val call = LocaMailApiFactory().getLocaMailApiFactory()
        .listarAgendaPorDia(data, id_usuario)

    call.enqueue(object : Callback<List<Agenda>?> {
        override fun onResponse(
            call: Call<List<Agenda>?>,
            response: Response<List<Agenda>?>
        ) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }
        override fun onFailure(call: Call<List<Agenda>?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiObterPergunta(
    id_question: Long,
    id_email: Long,
    onSuccess: (AiQuestion?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val call = LocaMailApiFactory().getLocaMailApiFactory()
        .obterPergunta(id_question, id_email)

    call.enqueue(object : Callback<AiQuestion?> {
        override fun onResponse(
            call: Call<AiQuestion?>,
            response: Response<AiQuestion?>
        ) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }
        override fun onFailure(call: Call<AiQuestion?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiCriarPergunta(
    aiQuestion: AiQuestion,
    onSuccess: (AiQuestion?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val call = LocaMailApiFactory().getLocaMailApiFactory()
        .criarPergunta(aiQuestion)

    call.enqueue(object : Callback<AiQuestion?> {
        override fun onResponse(
            call: Call<AiQuestion?>,
            response: Response<AiQuestion?>
        ) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }
        override fun onFailure(call: Call<AiQuestion?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiListarCorAgendaPorDia(
    data: String,
    id_usuario: Long,
    onSuccess: (List<Int>?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val call = LocaMailApiFactory().getLocaMailApiFactory()
        .listarCorAgendaPorDia(data, id_usuario)

    call.enqueue(object : Callback<List<Int>?> {
        override fun onResponse(
            call: Call<List<Int>?>,
            response: Response<List<Int>?>
        ) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }
        override fun onFailure(call: Call<List<Int>?>, t: Throwable) {
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
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
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
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<List<Long>?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiListarAnexosArraybytePorIdEmail(
    id_email: Long,
    onSuccess: (List<ByteArray>?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val call =
        LocaMailApiFactory().getLocaMailApiFactory().listarAnexosArraybytePorIdEmail(id_email)

    call.enqueue(object : Callback<List<ByteArray>?> {
        override fun onResponse(
            call: Call<List<ByteArray>?>,
            response: Response<List<ByteArray>?>
        ) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<List<ByteArray>?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiListarAnexosArrayBytePorIdRespostaEmail(
    id_resposta_email: Long,
    onSuccess: (List<ByteArray>?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val call =
        LocaMailApiFactory().getLocaMailApiFactory()
            .listarAnexosArrayBytePorIdRespostaEmail(id_resposta_email)

    call.enqueue(object : Callback<List<ByteArray>?> {
        override fun onResponse(
            call: Call<List<ByteArray>?>,
            response: Response<List<ByteArray>?>
        ) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<List<ByteArray>?>, t: Throwable) {
            onError(t)
        }
    })
}


fun callLocaMailApiListarAgendaPorIdEmailEIdUsuario(
    id_email: Long,
    id_usuario: Long,
    onSuccess: (List<Agenda>?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val call =
        LocaMailApiFactory().getLocaMailApiFactory()
            .listarAgendaPorIdEmailEIdUsuario(id_email, id_usuario)

    call.enqueue(object : Callback<List<Agenda>?> {
        override fun onResponse(
            call: Call<List<Agenda>?>,
            response: Response<List<Agenda>?>
        ) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<List<Agenda>?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiListarEmailsPorPastaEIdUsuario(
    pasta: Long,
    id_usuario: Long,
    onSuccess: (List<EmailComAlteracao>?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val call =
        LocaMailApiFactory().getLocaMailApiFactory()
            .listarEmailsPorPastaEIdUsuario(id_usuario, pasta)

    call.enqueue(object : Callback<List<EmailComAlteracao>?> {
        override fun onResponse(
            call: Call<List<EmailComAlteracao>?>,
            response: Response<List<EmailComAlteracao>?>
        ) {
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.forEach {
                    it.cc = getValidatedString(it.cc)
                    it.cco = getValidatedString(it.cco)
                    it.corpo = getValidatedString(it.corpo)
                    it.destinatario = getValidatedString(it.destinatario)
                    it.assunto = getValidatedString(it.assunto)
                }
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<List<EmailComAlteracao>?>, t: Throwable) {
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
        override fun onResponse(
            call: Call<List<RespostaEmail>?>,
            response: Response<List<RespostaEmail>?>
        ) {
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.forEach {
                    it.cc = getValidatedString(it.cc)
                    it.cco = getValidatedString(it.cco)
                    it.corpo = getValidatedString(it.corpo)
                    it.destinatario = getValidatedString(it.destinatario)
                    it.assunto = getValidatedString(it.assunto)
                }
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<List<RespostaEmail>?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiListarRespostaEmailPorIdRespostaEmail(
    id_resposta_email: Long,
    onSuccess: (RespostaEmail?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val call = LocaMailApiFactory().getLocaMailApiFactory()
        .listarRespostaEmailPorIdRespostaEmail(id_resposta_email)

    call.enqueue(object : Callback<RespostaEmail?> {
        override fun onResponse(
            call: Call<RespostaEmail?>,
            response: Response<RespostaEmail?>
        ) {
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.cc = getValidatedString(response.body()!!.cc)
                response.body()!!.cco = getValidatedString(response.body()!!.cco)
                response.body()!!.corpo = getValidatedString(response.body()!!.corpo)
                response.body()!!.destinatario = getValidatedString(response.body()!!.destinatario)
                response.body()!!.assunto = getValidatedString(response.body()!!.assunto)
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<RespostaEmail?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiListarAlteracaoPorIdEmail(
    id_email: Long,
    onSuccess: (List<Alteracao>?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val call = LocaMailApiFactory().getLocaMailApiFactory().listarAlteracaoPorIdEmail(id_email)

    call.enqueue(object : Callback<List<Alteracao>?> {
        override fun onResponse(
            call: Call<List<Alteracao>?>,
            response: Response<List<Alteracao>?>
        ) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<List<Alteracao>?>, t: Throwable) {
            onError(t)
        }
    })
}


fun callLocaMailApiListarAltIdUsuarioPorIdEmail(
    id_email: Long,
    onSuccess: (List<Long>?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val call = LocaMailApiFactory().getLocaMailApiFactory().listarAltIdUsuarioPorIdEmail(id_email)

    call.enqueue(object : Callback<List<Long>?> {
        override fun onResponse(
            call: Call<List<Long>?>,
            response: Response<List<Long>?>
        ) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<List<Long>?>, t: Throwable) {
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
    val call = LocaMailApiFactory().getLocaMailApiFactory()
        .listarAlteracaoPorIdUsuarioEIdPasta(id_usuario, pasta)

    call.enqueue(object : Callback<List<Alteracao>?> {
        override fun onResponse(
            call: Call<List<Alteracao>?>,
            response: Response<List<Alteracao>?>
        ) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<List<Alteracao>?>, t: Throwable) {
            onError(t)
        }
    })
}


fun callLocaMailApiListarEmailPorId(
    id_email: Long,
    onSuccess: (Email?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val call = LocaMailApiFactory().getLocaMailApiFactory().listarEmailPorId(id_email)

    call.enqueue(object : Callback<Email?> {
        override fun onResponse(call: Call<Email?>, response: Response<Email?>) {
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.cc = getValidatedString(response.body()!!.cc)
                response.body()!!.cco = getValidatedString(response.body()!!.cco)
                response.body()!!.corpo = getValidatedString(response.body()!!.corpo)
                response.body()!!.destinatario = getValidatedString(response.body()!!.destinatario)
                response.body()!!.assunto = getValidatedString(response.body()!!.assunto)
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<Email?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiListarAlteracaoPorIdEmailEIdUsuario(
    id_email: Long,
    id_usuario: Long,
    onSuccess: (Alteracao?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val call = LocaMailApiFactory().getLocaMailApiFactory()
        .listarAlteracaoPorIdEmailEIdUsuario(id_email, id_usuario)

    call.enqueue(object : Callback<Alteracao?> {
        override fun onResponse(call: Call<Alteracao?>, response: Response<Alteracao?>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<Alteracao?>, t: Throwable) {
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
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
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
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
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
    val call = LocaMailApiFactory().getLocaMailApiFactory()
        .atualizarPastaPorIdAlteracao(pasta, id_alteracao)

    call.enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
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
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<Pasta?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiListarAgendaPorId(
    id_agenda: Long,
    onSuccess: (Agenda?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory().listarAgendaPorId(id_agenda)

    callLocaMailApiService.enqueue(object : Callback<Agenda?> {
        override fun onResponse(call: Call<Agenda?>, response: Response<Agenda?>) {
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.descritivo = getValidatedString(response.body()!!.descritivo)
                response.body()!!.nome = getValidatedString(response.body()!!.nome)
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<Agenda?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiListarConvidadoPorId(
    id_convidado: Long,
    onSuccess: (Convidado?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory().listarConvidadoPorId(id_convidado)

    callLocaMailApiService.enqueue(object : Callback<Convidado?> {
        override fun onResponse(call: Call<Convidado?>, response: Response<Convidado?>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<Convidado?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiListarIdConvidadoPorAgenda(
    id_agenda: Long,
    onSuccess: (List<Long>?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory().listarIdConvidadoPorAgenda(id_agenda)

    callLocaMailApiService.enqueue(object : Callback<List<Long>?> {
        override fun onResponse(call: Call<List<Long>?>, response: Response<List<Long>?>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<List<Long>?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiAtualizaAgenda(
    agenda: Agenda,
    onSuccess: (Unit?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory().atualizaAgenda(agenda)

    callLocaMailApiService.enqueue(object : Callback<Unit?> {
        override fun onResponse(call: Call<Unit?>, response: Response<Unit?>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<Unit?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiExcluirPorIdAgendaEIdConvidado(
    id_agenda: Long,
    id_convidado: Long,
    onSuccess: (Unit?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory().excluirPorIdAgendaEIdConvidado(id_agenda, id_convidado)

    callLocaMailApiService.enqueue(object : Callback<Unit?> {
        override fun onResponse(call: Call<Unit?>, response: Response<Unit?>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<Unit?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiExcluirPorGrupoRepeticao(
    grupo_repeticao: Int,
    onSuccess: (Unit?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory().excluirPorGrupoRepeticao(grupo_repeticao)

    callLocaMailApiService.enqueue(object : Callback<Unit?> {
        override fun onResponse(call: Call<Unit?>, response: Response<Unit?>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<Unit?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiExcluirPorGrupoRepeticaoExcetoData(
    grupo_repeticao: Int,
    data: String,
    onSuccess: (Unit?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory().excluirPorGrupoRepeticaoExcetoData(grupo_repeticao, data)

    callLocaMailApiService.enqueue(object : Callback<Unit?> {
        override fun onResponse(call: Call<Unit?>, response: Response<Unit?>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<Unit?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiExcluirPorGrupoRepeticaoExcetoIdAgenda(
    grupo_repeticao: Int,
    id_agenda: Long,
    onSuccess: (Unit?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory().excluirPorGrupoRepeticaoExcetoIdAgenda(grupo_repeticao, id_agenda)

    callLocaMailApiService.enqueue(object : Callback<Unit?> {
        override fun onResponse(call: Call<Unit?>, response: Response<Unit?>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<Unit?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiAtualizaOpcaoRepeticaoPorGrupoRepeticao(
    grupo_repeticao: Int,
    repeticao: Int,
    onSuccess: (Unit?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory().atualizaOpcaoRepeticaoPorGrupoRepeticao(grupo_repeticao, repeticao)

    callLocaMailApiService.enqueue(object : Callback<Unit?> {
        override fun onResponse(call: Call<Unit?>, response: Response<Unit?>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<Unit?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiAtualizaOpcaoRepeticaoPorIdAgenda(
    id_agenda: Long,
    repeticao: Int,
    onSuccess: (Unit?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory().atualizaOpcaoRepeticaoPorIdAgenda(id_agenda, repeticao)

    callLocaMailApiService.enqueue(object : Callback<Unit?> {
        override fun onResponse(call: Call<Unit?>, response: Response<Unit?>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<Unit?>, t: Throwable) {
            onError(t)
        }
    })
}


fun callLocaMailApiCriarAgenda(
    agenda: Agenda,
    onSuccess: (Agenda?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory().criarAgenda(agenda)

    callLocaMailApiService.enqueue(object : Callback<Agenda?> {
        override fun onResponse(call: Call<Agenda?>, response: Response<Agenda?>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<Agenda?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiCriarAgendaConvidado(
    agendaConvidado: AgendaConvidado,
    onSuccess: (AgendaConvidado?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory().criarAgendaConvidado(agendaConvidado)

    callLocaMailApiService.enqueue(object : Callback<AgendaConvidado?> {
        override fun onResponse(call: Call<AgendaConvidado?>, response: Response<AgendaConvidado?>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<AgendaConvidado?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiRetornaValorAtualSeqPrimayKey(
    onSuccess: (Long?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory().retornaValorAtualSeqPrimayKey()

    callLocaMailApiService.enqueue(object : Callback<Long?> {
        override fun onResponse(call: Call<Long?>, response: Response<Long?>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<Long?>, t: Throwable) {
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
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            onError(t)
        }
    })
}


fun callLocaMailApiExcluirAnexoPorIdEmail(
    id_email: Long,
    onSuccess: (Unit?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService =
        LocaMailApiFactory().getLocaMailApiFactory().excluirAnexoPorIdEmail(id_email)

    callLocaMailApiService.enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiExcluirAnexoPorIdRespostaEmail(
    id_resposta_email: Long,
    onSuccess: (Unit?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory()
        .excluirAnexoPorIdRespostaEmail(id_resposta_email)

    callLocaMailApiService.enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiExcluirRespostaEmailPorIdEmail(
    id_email: Long,
    onSuccess: (Unit?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService =
        LocaMailApiFactory().getLocaMailApiFactory().excluirRespostaEmailPorIdEmail(id_email)

    callLocaMailApiService.enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiExcluirEmail(
    id_email: Long,
    onSuccess: (Unit?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory().excluirEmail(id_email)

    callLocaMailApiService.enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiExcluirPorIdAgenda(
    id_agenda: Long,
    onSuccess: (Unit?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService =
        LocaMailApiFactory().getLocaMailApiFactory().excluirPorIdAgenda(id_agenda)

    callLocaMailApiService.enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiExcluiAgenda(
    id_agenda: Long,
    onSuccess: (Unit?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService =
        LocaMailApiFactory().getLocaMailApiFactory().excluiAgenda(id_agenda)

    callLocaMailApiService.enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiExcluirRespostaEmail(
    id_resposta_email: Long,
    onSuccess: (Unit?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService =
        LocaMailApiFactory().getLocaMailApiFactory().excluirRespostaEmail(id_resposta_email)

    callLocaMailApiService.enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiAtualizaVisivelPorIdAgenda(
    id_agenda: Long,
    visivel: Boolean,
    onSuccess: (Unit?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService =
        LocaMailApiFactory().getLocaMailApiFactory().atualizaVisivelPorIdAgenda(id_agenda, visivel)

    callLocaMailApiService.enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            onError(t)
        }
    })
}


fun callLocaMailApiExcluiAlteracaoPorIdEmailEIdUsuario(
    id_email: Long,
    id_usuario: Long,
    onSuccess: (Unit?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory()
        .excluiAlteracaoPorIdEmailEIdUsuario(id_email, id_usuario)

    callLocaMailApiService.enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            onError(t)
        }
    })
}


fun callLocaMailApiVerificarLidoPorIdEmailEIdUsuario(
    id_email: Long,
    id_usuario: Long,
    onSuccess: (Boolean?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory()
        .verificarLidoPorIdEmailEIdUsuario(id_email, id_usuario)

    callLocaMailApiService.enqueue(object : Callback<Boolean?> {
        override fun onResponse(call: Call<Boolean?>, response: Response<Boolean?>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<Boolean?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiVerificarExcluidoPorIdEmailEIdUsuario(
    id_email: Long,
    id_usuario: Long,
    onSuccess: (Boolean?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory()
        .verificarExcluidoPorIdEmailEIdUsuario(id_email, id_usuario)

    callLocaMailApiService.enqueue(object : Callback<Boolean?> {
        override fun onResponse(call: Call<Boolean?>, response: Response<Boolean?>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<Boolean?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiVerificarSpamPorIdEmailEIdUsuario(
    id_email: Long,
    id_usuario: Long,
    onSuccess: (Boolean?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory()
        .verificarSpamPorIdEmailEIdUsuario(id_email, id_usuario)

    callLocaMailApiService.enqueue(object : Callback<Boolean?> {
        override fun onResponse(call: Call<Boolean?>, response: Response<Boolean?>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<Boolean?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiVerificarArquivadoPorIdEmailEIdUsuario(
    id_email: Long,
    id_usuario: Long,
    onSuccess: (Boolean?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory()
        .verificarArquivadoPorIdEmailEIdUsuario(id_email, id_usuario)

    callLocaMailApiService.enqueue(object : Callback<Boolean?> {
        override fun onResponse(call: Call<Boolean?>, response: Response<Boolean?>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<Boolean?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiVerificarImportantePorIdEmailEIdUsuario(
    id_email: Long,
    id_usuario: Long,
    onSuccess: (Boolean?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory()
        .verificarImportantePorIdEmailEIdUsuario(id_email, id_usuario)

    callLocaMailApiService.enqueue(object : Callback<Boolean?> {
        override fun onResponse(call: Call<Boolean?>, response: Response<Boolean?>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<Boolean?>, t: Throwable) {
            onError(t)
        }
    })
}


fun callLocaMailApiCriarUsuario(
    usuario: Usuario,
    onSuccess: (UsuarioSemSenha?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory().criarUsuario(usuario)

    callLocaMailApiService.enqueue(object : Callback<UsuarioSemSenha?> {
        override fun onResponse(
            call: Call<UsuarioSemSenha?>,
            response: Response<UsuarioSemSenha?>
        ) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
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
    val callLocaMailApiService =
        LocaMailApiFactory().getLocaMailApiFactory().criarAlteracao(alteracao)

    callLocaMailApiService.enqueue(object : Callback<Alteracao?> {
        override fun onResponse(call: Call<Alteracao?>, response: Response<Alteracao?>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
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
    val callLocaMailApiService =
        LocaMailApiFactory().getLocaMailApiFactory().criarConvidado(convidado)

    callLocaMailApiService.enqueue(object : Callback<Convidado?> {
        override fun onResponse(call: Call<Convidado?>, response: Response<Convidado?>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
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
    val callLocaMailApiService =
        LocaMailApiFactory().getLocaMailApiFactory().verificarConvidadoExiste(email)

    callLocaMailApiService.enqueue(object : Callback<String?> {
        override fun onResponse(call: Call<String?>, response: Response<String?>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<String?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiListarConvidado(
    onSuccess: (List<Convidado>?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService =
        LocaMailApiFactory().getLocaMailApiFactory().listarConvidado()

    callLocaMailApiService.enqueue(object : Callback<List<Convidado>?> {
        override fun onResponse(call: Call<List<Convidado>?>, response: Response<List<Convidado>?>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<List<Convidado>?>, t: Throwable) {
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
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<Email?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiAtualizarEmail(
    email: Email,
    onSuccess: (Email?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory().atualizarEmail(email)

    callLocaMailApiService.enqueue(object : Callback<Email?> {
        override fun onResponse(call: Call<Email?>, response: Response<Email?>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<Email?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiAtualizarRespostaEmail(
    respostaEmail: RespostaEmail,
    onSuccess: (RespostaEmail?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService =
        LocaMailApiFactory().getLocaMailApiFactory().atualizarRespostaEmail(respostaEmail)

    callLocaMailApiService.enqueue(object : Callback<RespostaEmail?> {
        override fun onResponse(call: Call<RespostaEmail?>, response: Response<RespostaEmail?>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<RespostaEmail?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiCriarRespostaEmail(
    respostaEmail: RespostaEmail,
    onSuccess: (RespostaEmail?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService =
        LocaMailApiFactory().getLocaMailApiFactory().criarRespostaEmail(respostaEmail)

    callLocaMailApiService.enqueue(object : Callback<RespostaEmail?> {
        override fun onResponse(call: Call<RespostaEmail?>, response: Response<RespostaEmail?>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<RespostaEmail?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiCriarAnexoRespostaEmail(
    anexo: AnexoRespostaEmail,
    onSuccess: (AnexoRespostaEmail?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService =
        LocaMailApiFactory().getLocaMailApiFactory().criarAnexoRespostaEmail(anexo)

    callLocaMailApiService.enqueue(object : Callback<AnexoRespostaEmail?> {
        override fun onResponse(
            call: Call<AnexoRespostaEmail?>,
            response: Response<AnexoRespostaEmail?>
        ) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<AnexoRespostaEmail?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiCriarAnexo(
    anexo: Anexo,
    onSuccess: (Anexo?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory().criarAnexo(anexo)

    callLocaMailApiService.enqueue(object : Callback<Anexo?> {
        override fun onResponse(call: Call<Anexo?>, response: Response<Anexo?>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<Anexo?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiListarUsuarios(
    onSuccess: (List<Usuario>?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory().listarUsuarios()

    callLocaMailApiService.enqueue(object : Callback<List<Usuario>?> {
        override fun onResponse(call: Call<List<Usuario>?>, response: Response<List<Usuario>?>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<List<Usuario>?>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiDesselecionarUsuarioSelecionadoAtual(
    onSuccess: (Unit?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService =
        LocaMailApiFactory().getLocaMailApiFactory().desselecionarUsuarioSelecionadoAtual()

    callLocaMailApiService.enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
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
    val callLocaMailApiService =
        LocaMailApiFactory().getLocaMailApiFactory().selecionarUsuario(id_usuario)

    callLocaMailApiService.enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
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
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory()
        .atualizaAutenticaUsuario(id_usuario, autenticado)

    callLocaMailApiService.enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
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
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory()
        .atualizarLidoPorIdEmailEIdusuario(lido, id_email, id_usuario)

    callLocaMailApiService.enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiAtualizarPastaPorIdEmailEIdUsuario(
    pasta: Long?,
    id_email: Long,
    id_usuario: Long,
    onSuccess: (Unit?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory()
        .atualizarPastaPorIdEmailEIdUsuario(pasta, id_email, id_usuario)

    callLocaMailApiService.enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiAtualizarExcluidoPorIdEmailEIdusuario(
    excluido: Boolean,
    id_email: Long,
    id_usuario: Long,
    onSuccess: (Unit?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory()
        .atualizarExcluidoPorIdEmailEIdusuario(excluido, id_email, id_usuario)

    callLocaMailApiService.enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            onError(t)
        }
    })
}


fun callLocaMailApiAtualizarSpamPorIdEmailEIdusuario(
    spam: Boolean,
    id_email: Long,
    id_usuario: Long,
    onSuccess: (Unit?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory()
        .atualizarSpamPorIdEmailEIdusuario(spam, id_email, id_usuario)

    callLocaMailApiService.enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            onError(t)
        }
    })
}

fun callLocaMailApiAtualizarArquivadoPorIdEmailEIdusuario(
    arquivado: Boolean,
    id_email: Long,
    id_usuario: Long,
    onSuccess: (Unit?) -> Unit,
    onError: (Throwable) -> Unit
) {
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory()
        .atualizarArquivadoPorIdEmailEIdusuario(arquivado, id_email, id_usuario)

    callLocaMailApiService.enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
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
    val callLocaMailApiService = LocaMailApiFactory().getLocaMailApiFactory()
        .atualizarImportantePorIdEmail(importante, id_email, id_usuario)

    callLocaMailApiService.enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            if (response.isSuccessful && response.body() != null) {
                onSuccess(response.body()!!)
            } else if (response.isSuccessful == false) {
                onError(Throwable())
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            onError(t)
        }
    })
}








