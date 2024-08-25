package br.com.fiap.locawebmailapp.screens.email

import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.locawebmailapp.R
import br.com.fiap.locawebmailapp.components.email.ColumnEmailDetails
import br.com.fiap.locawebmailapp.components.email.RowTopOptionsViewEmail
import br.com.fiap.locawebmailapp.components.general.ErrorComponent
import br.com.fiap.locawebmailapp.model.Agenda
import br.com.fiap.locawebmailapp.model.Alteracao
import br.com.fiap.locawebmailapp.model.Email
import br.com.fiap.locawebmailapp.model.RespostaEmail
import br.com.fiap.locawebmailapp.model.Usuario
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiAtualizaVisivelPorIdAgenda
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiAtualizarArquivadoPorIdEmailEIdusuario
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiAtualizarExcluidoPorIdEmailEIdusuario
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiAtualizarImportantePorIdEmail
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiAtualizarSpamPorIdEmailEIdusuario
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiExcluiAgenda
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiExcluiAlteracaoPorIdEmailEIdUsuario
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiExcluirAnexoPorIdEmail
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiExcluirAnexoPorIdRespostaEmail
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiExcluirEmail
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiExcluirPorIdAgenda
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiExcluirRespostaEmail
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiExcluirRespostaEmailPorIdEmail
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarAgendaPorIdEmailEIdUsuario
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarAlteracaoPorIdEmail
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarAlteracaoPorIdEmailEIdUsuario
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarAnexosArraybytePorIdEmail
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarEmailPorId
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarRespostasEmailPorIdEmail
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarUsuarioSelecionado
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiRetornaUsarioPorEmail
import br.com.fiap.locawebmailapp.utils.atualizarIsImportantParaUsuariosRelacionados
import br.com.fiap.locawebmailapp.utils.atualizarTodosDestinatariosList
import br.com.fiap.locawebmailapp.utils.atualizarisArchiveParaUsuariosRelacionados
import br.com.fiap.locawebmailapp.utils.atualizarisExcluidoParaUsuariosRelacionados
import br.com.fiap.locawebmailapp.utils.atualizarisSpamParaUsuariosRelacionados
import br.com.fiap.locawebmailapp.utils.byteArrayToBitmap
import br.com.fiap.locawebmailapp.utils.checkInternetConnectivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisualizaEmailScreen(
    timeState: TimePickerState = rememberTimePickerState(),
    navController: NavController,
    idEmail: Long,
    isTodasContasScreen: Boolean,
) {

    val context = LocalContext.current

    val toastMessageMailDeleted = stringResource(id = R.string.toast_mail_delete)
    val toastMessageDraftMailDeleted = stringResource(id = R.string.toast_maildraftresp_delete)
    val toastMessageMailMovedToTrashBin = stringResource(id = R.string.toast_mail_moved_trash_bin)
    val toastMessageMailMovedToTrash = stringResource(id = R.string.toast_mail_moved_trash)
    val toastMessageInviteDeleted = stringResource(id = R.string.toast_event_invitedeleted)
    val toastMessageInviteAccepted = stringResource(id = R.string.toast_event_inviteaccepted)

    val isConnectedStatus = remember {
        mutableStateOf(checkInternetConnectivity(context))
    }
    val isLoading = remember {
        mutableStateOf(true)
    }

    val isError = remember {
        mutableStateOf(false)
    }

    val toastMessageWait = stringResource(id = R.string.toast_api_wait)

    val usuarioSelecionado = remember {
        mutableStateOf<Usuario?>(null)
    }

    val anexoBitMapList = remember {
        mutableStateListOf<Bitmap?>()
    }

    var agendaEmailStateList: SnapshotStateList<Agenda>? = null

    var agendaEmailList: List<Agenda>? = null

    var alteracao: Alteracao? = null


    val todosDestinatarios = arrayListOf<String>()

    val respostasEmailStateList: SnapshotStateList<RespostaEmail?> = remember {
        mutableStateListOf<RespostaEmail?>()
    }
    var respostasEmailList: List<RespostaEmail>? = null

    val isAgendaAtrelada = remember {
        mutableStateOf<Boolean?>(null)
    }

    if (agendaEmailList != null) {
        isAgendaAtrelada.value = if (agendaEmailList.isNotEmpty()) true else false
    }

    val isImportant = remember {
        if (alteracao != null) mutableStateOf(alteracao!!.importante) else mutableStateOf(false)
    }

    val isArchive = remember {
        if (alteracao != null) mutableStateOf(alteracao!!.arquivado) else mutableStateOf(false)
    }

    val isSpam = remember {
        if (alteracao != null) mutableStateOf(alteracao!!.spam) else mutableStateOf(false)
    }

    val isExcluido = remember {
        if (alteracao != null) mutableStateOf(alteracao!!.excluido) else mutableStateOf(false)
    }


    val email: MutableState<Email?> = remember {
        mutableStateOf(null)
    }


    LaunchedEffect(key1 = Unit) {
        if (usuarioSelecionado.value != null) {

            callLocaMailApiListarAlteracaoPorIdEmailEIdUsuario(
                idEmail,
                usuarioSelecionado.value!!.id_usuario,
                onSuccess = { alteracaoRetornada ->

                    alteracao = alteracaoRetornada
                },
                onError = { error ->
                    isError.value = true
                    isLoading.value = false
                }
            )

            callLocaMailApiListarAgendaPorIdEmailEIdUsuario(
                idEmail,
                usuarioSelecionado.value!!.id_usuario,
                onSuccess = { listAgendaRetornado ->

                    agendaEmailList = listAgendaRetornado

                    if (agendaEmailList != null) {
                        agendaEmailStateList = agendaEmailList!!.toMutableStateList()
                    }
                },
                onError = { error ->
                    isError.value = true
                    isLoading.value = false
                }
            )

        }
        callLocaMailApiListarRespostasEmailPorIdEmail(
            idEmail,
            onSuccess = { repostaEmailRetornada ->

                if (repostaEmailRetornada != null) {
                    respostasEmailList = repostaEmailRetornada
                    respostasEmailList!!.forEach { resposta ->
                        if (!respostasEmailStateList.contains(resposta)) {
                            respostasEmailStateList.add(resposta)
                        }
                    }
                }
            },
            onError = { error ->
                isError.value = true
                isLoading.value = false
            }
        )

        callLocaMailApiListarAnexosArraybytePorIdEmail(
            idEmail,
            onSuccess = { byteArrayListRetornado ->
                val anexoArrayByteList = byteArrayListRetornado;

                if (anexoArrayByteList != null) {
                    anexoBitMapList.addAll(anexoArrayByteList.map {
                        byteArrayToBitmap(it)
                    })
                }
            },
            onError = { error ->
                isError.value = true
                isLoading.value = false
            }
        )

        callLocaMailApiListarUsuarioSelecionado(
            onSuccess = { usuarioRetornado ->
                usuarioSelecionado.value = usuarioRetornado
            },
            onError = { error ->
                isError.value = true
                isLoading.value = false

            }
        )

        callLocaMailApiListarEmailPorId(
            idEmail,
            onSuccess = { emailRetornado ->
                email.value = emailRetornado

                if (email.value != null) {
                    isLoading.value = false
                    if (isTodasContasScreen) {
                        if (respostasEmailList != null) {
                            atualizarTodosDestinatariosList(
                                todosDestinatarios,
                                email.value!!,
                                respostasEmailList!!
                            )
                        }
                        atualizarIsImportantParaUsuariosRelacionados(
                            todosDestinatarios,
                            isImportant,
                            email.value!!,
                            isLoading = isLoading,
                            isError = isError
                        )

                        atualizarisArchiveParaUsuariosRelacionados(
                            todosDestinatarios,
                            isArchive,
                            email.value!!,
                            isLoading = isLoading,
                            isError = isError
                        )


                        atualizarisSpamParaUsuariosRelacionados(
                            todosDestinatarios,
                            isSpam,
                            email.value!!,
                            isLoading = isLoading,
                            isError = isError
                        )

                        atualizarisExcluidoParaUsuariosRelacionados(
                            todosDestinatarios,
                            isExcluido,
                            email.value!!,
                            isLoading = isLoading,
                            isError = isError
                        )
                    }

                }
            },
            onError = { error ->
                isError.value = true
                isLoading.value = false

            }
        )
    }

    if (isLoading.value) {
        BackHandler {
            Toast.makeText(
                context,
                toastMessageWait,
                Toast.LENGTH_LONG
            ).show()
        }
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(100.dp, 100.dp),
                color = colorResource(id = R.color.lcweb_red_1)
            )
        }
    } else {
        if (!isConnectedStatus.value) {
            Box {
                ErrorComponent(
                    title = stringResource(id = R.string.ai_error_oops),
                    subtitle = stringResource(id = R.string.ai_error_verifynet),
                    painter = painterResource(id = R.drawable.notfound),
                    descriptionimage = stringResource(id = R.string.content_desc_nonet),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(400.dp, 400.dp),
                    modifierButton = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                        .height(50.dp)
                        .align(Alignment.BottomCenter),
                    textButton = stringResource(id = R.string.ai_button_return),
                    buttonChange = {
                        navController.popBackStack()
                    }
                )
            }
        } else if (isError.value) {
            Box {
                ErrorComponent(
                    title = stringResource(id = R.string.ai_error_oops),
                    subtitle = stringResource(id = R.string.ai_error_apiproblem),
                    painter = painterResource(id = R.drawable.bugfixing),
                    descriptionimage = stringResource(id = R.string.content_desc_apiproblem),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(400.dp, 400.dp),
                    modifierButton = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                        .height(50.dp)
                        .align(Alignment.BottomCenter),
                    textButton = stringResource(id = R.string.ai_button_return),
                    buttonChange = {
                        navController.popBackStack()
                    }
                )
            }
        } else {

            if (email.value != null) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    RowTopOptionsViewEmail(
                        onClickBack = {
                            navController.popBackStack()
                        },
                        onClickDelete = {
                            if (isExcluido.value) {

                                if (usuarioSelecionado.value != null) {
                                    callLocaMailApiExcluiAlteracaoPorIdEmailEIdUsuario(
                                        email.value!!.id_email,
                                        usuarioSelecionado.value!!.id_usuario,
                                        onSuccess = {

                                            callLocaMailApiListarAlteracaoPorIdEmail(
                                                email.value!!.id_email,
                                                onSuccess = { listAlteracaoRetornado ->

                                                    val alteracaoList = listAlteracaoRetornado

                                                    if (alteracaoList!!.isEmpty()) {

                                                        callLocaMailApiExcluirAnexoPorIdEmail(
                                                            email.value!!.id_email,
                                                            onSuccess = {
                                                            },
                                                            onError = { error ->
                                                                isError.value = true
                                                                isLoading.value = false
                                                            }

                                                        )

                                                        callLocaMailApiListarRespostasEmailPorIdEmail(
                                                            email.value!!.id_email,
                                                            onSuccess = { listRespostaRetornado ->
                                                                if (listRespostaRetornado != null) {
                                                                    for (respostaEmail in listRespostaRetornado) {
                                                                        callLocaMailApiExcluirAnexoPorIdRespostaEmail(
                                                                            respostaEmail.id_resposta_email,
                                                                            onSuccess = {

                                                                            },
                                                                            onError = { error ->
                                                                                isError.value = true
                                                                                isLoading.value =
                                                                                    false
                                                                            }
                                                                        )
                                                                    }

                                                                }
                                                            },
                                                            onError = { error ->
                                                                isError.value = true
                                                                isLoading.value = false
                                                            }
                                                        )

                                                        callLocaMailApiExcluirRespostaEmailPorIdEmail(
                                                            email.value!!.id_email,
                                                            onSuccess = {
                                                            },
                                                            onError = { error ->
                                                                isError.value = true
                                                                isLoading.value = false
                                                            }
                                                        )

                                                        callLocaMailApiExcluirEmail(
                                                            email.value!!.id_email,
                                                            onSuccess = {
                                                                Toast.makeText(
                                                                    context,
                                                                    toastMessageMailDeleted,
                                                                    Toast.LENGTH_LONG
                                                                ).show()

                                                            },
                                                            onError = { error ->
                                                                isError.value = true
                                                                isLoading.value = false
                                                            }
                                                        )
                                                    }
                                                },
                                                onError = { error ->
                                                    isError.value = true
                                                    isLoading.value = false
                                                }
                                            )
                                        },
                                        onError = { error ->
                                            isError.value = true
                                            isLoading.value = false
                                        }
                                    )
                                }

                                if (agendaEmailStateList != null) {

                                    if (agendaEmailStateList!!.isNotEmpty()) {

                                        if (agendaEmailList != null) {

                                            for (agenda in agendaEmailList!!) {

                                                callLocaMailApiExcluirPorIdAgenda(
                                                    agenda.id_agenda,
                                                    onSuccess = {

                                                    },
                                                    onError = { error ->
                                                        isError.value = true
                                                        isLoading.value = false
                                                    }
                                                )

                                                callLocaMailApiExcluiAgenda(
                                                    agenda.id_agenda,
                                                    onSuccess = {
                                                        agendaEmailStateList!!.remove(agenda)

                                                    },
                                                    onError = { error ->
                                                        isError.value = true
                                                        isLoading.value = false
                                                    }
                                                )
                                            }

                                            Toast.makeText(
                                                context,
                                                toastMessageInviteDeleted,
                                                Toast.LENGTH_LONG
                                            ).show()

                                        }


                                    }

                                }
                                navController.popBackStack()

                            } else {
                                if (isTodasContasScreen) {
                                    for (destinatario in todosDestinatarios) {
                                        if (destinatario.isNotBlank()) {
                                            callLocaMailApiRetornaUsarioPorEmail(
                                                destinatario,
                                                onSuccess = { usuarioRetornado ->

                                                    val usuario = usuarioRetornado

                                                    val idDestinatario =
                                                        if (usuario != null) usuario.id_usuario else null

                                                    if (idDestinatario != null) {

                                                        callLocaMailApiAtualizarExcluidoPorIdEmailEIdusuario(
                                                            true,
                                                            email.value!!.id_email,
                                                            idDestinatario,
                                                            onSuccess = {
                                                                Toast.makeText(
                                                                    context,
                                                                    toastMessageMailMovedToTrashBin,
                                                                    Toast.LENGTH_LONG
                                                                ).show()
                                                            },
                                                            onError = { error ->
                                                                isError.value = true
                                                                isLoading.value = false
                                                            }
                                                        )
                                                    }
                                                },
                                                onError = { error ->
                                                    isError.value = true
                                                    isLoading.value = false
                                                }
                                            )
                                        }
                                    }
                                } else {

                                    if (usuarioSelecionado.value != null) {
                                        callLocaMailApiAtualizarExcluidoPorIdEmailEIdusuario(
                                            true,
                                            email.value!!.id_email,
                                            usuarioSelecionado.value!!.id_usuario,
                                            onSuccess = {
                                                Toast.makeText(
                                                    context,
                                                    toastMessageMailMovedToTrash,
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            },
                                            onError = { error ->
                                                isError.value = true
                                                isLoading.value = false

                                            }
                                        )
                                    }
                                }
                                navController.popBackStack()
                            }
                        },
                        isExcluido = isExcluido,
                        isTodasContasScreen = isTodasContasScreen,
                        onClickReply = { navController.navigate("criarespostaemailscreen?id_email=${idEmail}") },
                        onClickSpam = {
                            if (isTodasContasScreen) {
                                isSpam.value = !isSpam.value
                                for (destinatario in todosDestinatarios) {
                                    if (destinatario.isNotBlank()) {

                                        callLocaMailApiRetornaUsarioPorEmail(
                                            destinatario,
                                            onSuccess = { usuarioRetornado ->

                                                val usuario = usuarioRetornado


                                                val idDestinatario =
                                                    if (usuario != null) usuario.id_usuario else null

                                                if (idDestinatario != null) {

                                                    callLocaMailApiAtualizarSpamPorIdEmailEIdusuario(
                                                        isSpam.value,
                                                        email.value!!.id_email,
                                                        idDestinatario,
                                                        onSuccess = {

                                                        },
                                                        onError = { error ->
                                                            isError.value = true
                                                            isLoading.value = false
                                                        }
                                                    )
                                                }
                                            },
                                            onError = { error ->
                                                isError.value = true
                                                isLoading.value = false
                                            }
                                        )


                                    }
                                }
                            } else {
                                isSpam.value = !isSpam.value

                                if (usuarioSelecionado.value != null) {
                                    callLocaMailApiAtualizarSpamPorIdEmailEIdusuario(
                                        isSpam.value,
                                        email.value!!.id_email,
                                        usuarioSelecionado.value!!.id_usuario,
                                        onSuccess = {

                                        },
                                        onError = { error ->
                                            isError.value = true
                                            isLoading.value = false
                                        }
                                    )

                                }
                            }
                        },
                        onClickFavorite = {
                            if (isTodasContasScreen) {
                                isImportant.value = !isImportant.value
                                for (destinatario in todosDestinatarios) {
                                    if (destinatario.isNotBlank()) {

                                        callLocaMailApiRetornaUsarioPorEmail(
                                            destinatario,
                                            onSuccess = { usuarioRetornado ->

                                                val usuario = usuarioRetornado

                                                val idDestinatario =
                                                    if (usuario != null) usuario.id_usuario else null

                                                if (idDestinatario != null) {

                                                    callLocaMailApiAtualizarImportantePorIdEmail(
                                                        isImportant.value,
                                                        email.value!!.id_email,
                                                        idDestinatario,
                                                        onSuccess = {
                                                        },
                                                        onError = { error ->
                                                            isError.value = true
                                                            isLoading.value = false
                                                        }
                                                    )

                                                }

                                            },
                                            onError = { error ->
                                                isError.value = true
                                                isLoading.value = false
                                            }
                                        )


                                    }
                                }
                            } else {
                                isImportant.value = !isImportant.value

                                if (usuarioSelecionado.value != null) {
                                    callLocaMailApiAtualizarImportantePorIdEmail(
                                        isImportant.value,
                                        email.value!!.id_email,
                                        usuarioSelecionado.value!!.id_usuario,
                                        onSuccess = {
                                        },
                                        onError = { error ->
                                            isError.value = true
                                            isLoading.value = false
                                        }
                                    )

                                }
                            }
                        },
                        onClickArchive = {
                            if (isTodasContasScreen) {
                                isArchive.value = !isArchive.value
                                for (destinatario in todosDestinatarios) {
                                    if (destinatario.isNotBlank()) {


                                        callLocaMailApiRetornaUsarioPorEmail(
                                            destinatario,
                                            onSuccess = { usuarioRetornado ->

                                                val usuario = usuarioRetornado

                                                val idDestinatario =
                                                    if (usuario != null) usuario.id_usuario else null

                                                if (idDestinatario != null) {

                                                    callLocaMailApiAtualizarArquivadoPorIdEmailEIdusuario(
                                                        isArchive.value,
                                                        email.value!!.id_email,
                                                        idDestinatario,
                                                        onSuccess = {

                                                        },
                                                        onError = { error ->
                                                            isError.value = true
                                                            isLoading.value = false
                                                        }
                                                    )
                                                }

                                            },
                                            onError = { error ->
                                                isError.value = true
                                                isLoading.value = false
                                            }
                                        )


                                    }
                                }
                            } else {
                                isArchive.value = !isArchive.value
                                if (usuarioSelecionado.value != null) {

                                    callLocaMailApiAtualizarArquivadoPorIdEmailEIdusuario(
                                        isArchive.value,
                                        email.value!!.id_email,
                                        usuarioSelecionado.value!!.id_usuario,
                                        onSuccess = {

                                        },
                                        onError = { error ->
                                            isError.value = true
                                            isLoading.value = false
                                        }
                                    )
                                }
                            }
                        },
                        isSpam = isSpam,
                        isImportant = isImportant,
                        isArchive = isArchive,
                        isAgendaAtrelada = isAgendaAtrelada
                    )


                    ColumnEmailDetails(
                        onClickDraftRespostaEmailDelete = { respostaEmail ->

                            callLocaMailApiExcluirAnexoPorIdRespostaEmail(
                                respostaEmail.id_resposta_email,
                                onSuccess = {

                                },
                                onError = { error ->
                                    isError.value = true
                                    isLoading.value = false
                                }
                            )

                            callLocaMailApiExcluirRespostaEmail(
                                respostaEmail.id_resposta_email,
                                onSuccess = {
                                    if (respostasEmailStateList != null) {
                                        respostasEmailStateList.remove(respostaEmail)
                                        Toast.makeText(
                                            context,
                                            toastMessageDraftMailDeleted,
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                },
                                onError = {
                                    isError.value = true
                                    isLoading.value = false
                                }
                            )
                        },
                        onClickDraftRespostaEmailEdit = { respostaEmail ->
                            navController.navigate("editarespostaemailscreen/${respostaEmail.id_resposta_email}")
                        },
                        email = email.value!!,
                        anexoBitMapList = anexoBitMapList,
                        timeState = timeState,
                        usuarioSelecionado = usuarioSelecionado,
                        respostasEmailStateList = respostasEmailStateList,
                        isTodasContasScreen = isTodasContasScreen,
                        onClickReply = {
                            navController.navigate("criarespostaemailscreen?id_email=${idEmail}&id_resposta_email=${it.id_resposta_email}")
                        },
                        isAgendaAtrelada = isAgendaAtrelada,
                        onClickAcceptInviteButton = {
                            if (agendaEmailList != null) {
                                for (agenda in agendaEmailList!!) {

                                    callLocaMailApiAtualizaVisivelPorIdAgenda(
                                        agenda.id_agenda,
                                        true,
                                        onSuccess = {
                                            if (agendaEmailStateList != null) {
                                                agendaEmailStateList!!.remove(agenda)
                                            }
                                        },
                                        onError = {
                                            isError.value = true
                                            isLoading.value = false
                                        }
                                    )
                                }

                                Toast.makeText(
                                    context,
                                    toastMessageInviteAccepted,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        },
                        onClickRejectInviteButton = {

                            if (agendaEmailList != null) {
                                for (agenda in agendaEmailList!!) {

                                    callLocaMailApiExcluirPorIdAgenda(
                                        agenda.id_agenda,
                                        onSuccess = {

                                        },
                                        onError = {
                                            isError.value = true
                                            isLoading.value = false
                                        }
                                    )

                                    callLocaMailApiExcluiAgenda(
                                        agenda.id_agenda,
                                        onSuccess = {
                                            if (agendaEmailStateList != null) {
                                                agendaEmailStateList!!.remove(agenda)
                                            }
                                        },
                                        onError = {
                                            isError.value = true
                                            isLoading.value = false
                                        }
                                    )
                                }
                                Toast.makeText(
                                    context,
                                    toastMessageInviteDeleted,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        },
                        agendaEmailStateList = agendaEmailStateList,
                        isLoading = isLoading,
                        isError = isError
                    )
                }
            }
        }

    }
}
