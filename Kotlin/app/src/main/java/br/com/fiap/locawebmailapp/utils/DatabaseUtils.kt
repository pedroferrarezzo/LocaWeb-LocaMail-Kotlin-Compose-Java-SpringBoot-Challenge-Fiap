package br.com.fiap.locawebmailapp.utils

import androidx.compose.runtime.MutableState
import br.com.fiap.locawebmailapp.model.Email
import br.com.fiap.locawebmailapp.model.EmailComAlteracao
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarAlteracaoPorIdEmailEIdUsuario
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiRetornaUsarioPorEmail
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiVerificarArquivadoPorIdEmailEIdUsuario
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiVerificarExcluidoPorIdEmailEIdUsuario
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiVerificarImportantePorIdEmailEIdUsuario
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiVerificarLidoPorIdEmailEIdUsuario
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiVerificarSpamPorIdEmailEIdUsuario

fun atualizarIsImportantParaUsuariosRelacionados(
    todosDestinatarios: List<String>,
    isImportant: MutableState<Boolean>,
    email: EmailComAlteracao,
    isLoading: MutableState<Boolean>,
    isError: MutableState<Boolean>
) {
    for (destinatario in todosDestinatarios) {

        var importante: Boolean? = null

        if (destinatario.isNotBlank()) {

            callLocaMailApiRetornaUsarioPorEmail(
                destinatario,
                onSuccess = {
                    usuarioRetornado ->

                    val usuario = usuarioRetornado

                    val idDestinatario =
                        if (usuario != null) usuario.id_usuario else null

                    if (idDestinatario != null) {

                        callLocaMailApiListarAlteracaoPorIdEmailEIdUsuario(
                            email.id_email,
                            idDestinatario,
                            onSuccess = {
                                alteracaoRetornado ->
                                val alteracao = alteracaoRetornado

                                if (alteracao != null) {

                                    callLocaMailApiVerificarImportantePorIdEmailEIdUsuario(
                                        email.id_email,
                                        idDestinatario,
                                        onSuccess = {
                                            importanteRetornado ->

                                            importante = importanteRetornado

                                        },
                                        onError = {
                                            isError.value = true
                                            isLoading.value = false
                                        }
                                    )
                                }

                            },
                            onError = {
                                isError.value = true
                                isLoading.value = false
                            }
                        )
                    }

                },
                onError = {
                    isError.value = true
                    isLoading.value = false
                }
            )

            if (importante != null) {
                isImportant.value = importante!!
                if (!isImportant.value){
                    break
                }
            }

        }
    }
}

fun atualizarisArchiveParaUsuariosRelacionados(
    todosDestinatarios: List<String>,
    isArchive: MutableState<Boolean>,
    email: Email,
    isLoading: MutableState<Boolean>,
    isError: MutableState<Boolean>
) {
    for (destinatario in todosDestinatarios) {
        var arquivado: Boolean? = null

        if (destinatario.isNotBlank()) {

            callLocaMailApiRetornaUsarioPorEmail(
                destinatario,
                onSuccess = {
                    usuarioRetornado ->

                    val usuario = usuarioRetornado

                    val idDestinatario =
                        if (usuario != null) usuario.id_usuario else null

                    if (idDestinatario != null) {

                        callLocaMailApiListarAlteracaoPorIdEmailEIdUsuario(
                            email.id_email,
                            idDestinatario,
                            onSuccess = {
                                alteracaoRetornada ->
                                val alteracao = alteracaoRetornada
                                if (alteracao != null) {
                                    callLocaMailApiVerificarArquivadoPorIdEmailEIdUsuario(
                                        email.id_email,
                                        idDestinatario,
                                        onSuccess = {
                                            arquivadoRetornado ->

                                            arquivado = arquivadoRetornado
                                        },
                                        onError = {
                                            isError.value = true
                                            isLoading.value = false
                                        }
                                    )
                                }

                            },
                            onError = {
                                isError.value = true
                                isLoading.value = false
                            }
                        )
                    }
                },
                onError = {
                    isError.value = true
                    isLoading.value = false
                }
            )
            if (arquivado != null) {
                isArchive.value = arquivado!!
                if(!isArchive.value){
                    break
                }
            }

        }
    }
}


fun atualizarisSpamParaUsuariosRelacionados(
    todosDestinatarios: List<String>,
    isSpam: MutableState<Boolean>,
    email: Email,
    isLoading: MutableState<Boolean>,
    isError: MutableState<Boolean>
) {
    for (destinatario in todosDestinatarios) {
        var spam: Boolean? = null
        if (destinatario.isNotBlank()) {
            callLocaMailApiRetornaUsarioPorEmail(
                destinatario,
                onSuccess = { usuarioRetornado ->
                    val usuario = usuarioRetornado
                    val idDestinatario =
                        if (usuario != null) usuario.id_usuario else null
                    if (idDestinatario != null) {
                        callLocaMailApiListarAlteracaoPorIdEmailEIdUsuario(
                            email.id_email,
                            idDestinatario,
                            onSuccess = { alteracaoRetornada ->
                                val alteracao = alteracaoRetornada
                                if (alteracao != null) {
                                    callLocaMailApiVerificarSpamPorIdEmailEIdUsuario(
                                        email.id_email,
                                        idDestinatario,
                                        onSuccess = { spamRetornado ->
                                            spam = spamRetornado

                                        },
                                        onError = {
                                            isError.value = true
                                            isLoading.value = false
                                        }
                                    )
                                }
                            },
                            onError = {
                                isError.value = true
                                isLoading.value = false
                            }
                        )


                    }

                },
                onError = {
                    isError.value = true
                    isLoading.value = false
                }
            )
            if (spam != null) {
                isSpam.value = spam!!
                if(!isSpam.value){
                    break
                }
            }

        }
    }
}


fun atualizarisExcluidoParaUsuariosRelacionados(
    todosDestinatarios: List<String>,
    isExcluido: MutableState<Boolean>,
    email: Email,
    isLoading: MutableState<Boolean>,
    isError: MutableState<Boolean>
) {
    for (destinatario in todosDestinatarios) {
        var excluido: Boolean? = null
        if (destinatario.isNotBlank()) {
            callLocaMailApiRetornaUsarioPorEmail(
                destinatario,
                onSuccess = { usuarioRetornado ->
                    val usuario = usuarioRetornado
                    val idDestinatario =
                        if (usuario != null) usuario.id_usuario else null
                    if (idDestinatario != null) {
                        callLocaMailApiListarAlteracaoPorIdEmailEIdUsuario(
                            email.id_email,
                            idDestinatario,
                            onSuccess = { alteracaoRetornada ->
                                val alteracao = alteracaoRetornada
                                if (alteracao != null) {
                                    callLocaMailApiVerificarExcluidoPorIdEmailEIdUsuario(
                                        email.id_email,
                                        idDestinatario,
                                        onSuccess = { excluidoRetornado ->
                                            excluido = excluidoRetornado
                                        },
                                        onError = {
                                            isError.value = true
                                            isLoading.value = false
                                        }
                                    )
                                }
                            },
                            onError = {
                                isError.value = true
                                isLoading.value = false
                            }
                        )
                    }
                },
                onError = {
                    isError.value = true
                    isLoading.value = false
                }
            )

            if (excluido != null) {
                isExcluido.value = excluido!!
                if (!isExcluido.value) {
                    break
                }
            }
        }
    }
}


fun atualizarisReadParaUsuariosRelacionados(
    todosDestinatarios: List<String>,
    isRead: MutableState<Boolean>,
    email: EmailComAlteracao,
    isLoading: MutableState<Boolean>,
    isError: MutableState<Boolean>
) {
    for (destinatario in todosDestinatarios) {

        var lido: Boolean? = null

        if (destinatario.isNotBlank()) {

            callLocaMailApiRetornaUsarioPorEmail(
                destinatario,
                onSuccess = {

                        usuarioRetornado ->
                    val usuario = usuarioRetornado

                    val idDestinatario =
                        if (usuario != null) usuario.id_usuario else null

                    if (idDestinatario != null) {

                        callLocaMailApiListarAlteracaoPorIdEmailEIdUsuario(
                            email.id_email,
                            idDestinatario,
                            onSuccess = { alteracaoRetornada ->

                                val alteracao = alteracaoRetornada

                                if (alteracao != null) {
                                    callLocaMailApiVerificarLidoPorIdEmailEIdUsuario(
                                        email.id_email,
                                        idDestinatario,
                                        onSuccess = { lidoRetorno ->

                                            lido = lidoRetorno

                                        },
                                        onError = {
                                            isError.value = true
                                            isLoading.value = false
                                        }
                                    )


                                }
                            },
                            onError = {
                                isError.value = true
                                isLoading.value = false
                            }
                        )
                    }
                },
                onError = {
                    isError.value = true
                    isLoading.value = false
                }
            )
            if (lido != null) {
                isRead.value = lido!!
                if (!isRead.value) {
                    break
                }
            }
        }
    }
}