package br.com.fiap.locawebmailapp.screens.email

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.locawebmailapp.R
import br.com.fiap.locawebmailapp.components.email.EmailCreateButton
import br.com.fiap.locawebmailapp.components.email.EmailViewButton
import br.com.fiap.locawebmailapp.components.email.RowSearchBar
import br.com.fiap.locawebmailapp.components.email.TopButton
import br.com.fiap.locawebmailapp.components.general.ErrorComponent
import br.com.fiap.locawebmailapp.components.general.ModalNavDrawer
import br.com.fiap.locawebmailapp.model.EmailComAlteracao
import br.com.fiap.locawebmailapp.model.Pasta
import br.com.fiap.locawebmailapp.model.RespostaEmail
import br.com.fiap.locawebmailapp.model.Usuario
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiAtualizarExcluidoPorIdEmailEIdusuario
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiAtualizarLidoPorIdEmailEIdusuario
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiExcluiAgenda
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiExcluiAlteracaoPorIdEmailEIdUsuario
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiExcluirAnexoPorIdEmail
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiExcluirAnexoPorIdRespostaEmail
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiExcluirEmail
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiExcluirPorIdAgenda
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiExcluirRespostaEmail
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarAgendaPorIdEmailEIdUsuario
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarAlteracaoPorIdEmail
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarAnexosIdEmail
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarEmailsLixeiraPorIdUsuario
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarPastasPorIdUsuario
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarRespostasEmailPorIdEmail
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarUsuarioSelecionado
import br.com.fiap.locawebmailapp.utils.checkInternetConnectivity


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailsExcluidosScreen(navController: NavController) {
    val context = LocalContext.current

    val isConnectedStatus = remember {
        mutableStateOf(checkInternetConnectivity(context))
    }
    val isLoading = remember {
        mutableStateOf(true)
    }

    val isError = remember {
        mutableStateOf(false)
    }

    val listUsuariosNaoAutenticados = remember {
        mutableStateListOf<Usuario>()
    }

    val toastMessageWait = stringResource(id = R.string.toast_api_wait)

    val selectedDrawer = remember {
        mutableStateOf("8")
    }

    val textSearchBar = remember {
        mutableStateOf("")
    }

    val expandedPasta = remember {
        mutableStateOf(true)
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val scope = rememberCoroutineScope()

    val timeState = rememberTimePickerState()

    val openDialogUserPicker = remember {
        mutableStateOf(false)
    }


    val lixeiraEmailList = remember {
        mutableStateOf(listOf<EmailComAlteracao>())
    }

    val usuarioSelecionado = remember {
        mutableStateOf<Usuario?>(null)
    }

    val listPastaState = remember {
        mutableStateListOf<Pasta>()
    }

    val attachEmailList = remember {
        mutableStateListOf<Long?>()
    }

    val lixeiraStateEmailLst = remember {
        mutableStateListOf<EmailComAlteracao>()
    }

    LaunchedEffect(key1 = Unit) {

        callLocaMailApiListarUsuarioSelecionado(
            onSuccess = { usuarioSelecionadoRetornado ->

                if (usuarioSelecionadoRetornado != null) {
                    usuarioSelecionado.value = usuarioSelecionadoRetornado

                    callLocaMailApiListarEmailsLixeiraPorIdUsuario(
                        usuarioSelecionado.value!!.id_usuario,
                        onSuccess = { listMailRetornado ->
                            isLoading.value = false
                            if (listMailRetornado != null) {
                                lixeiraEmailList.value = listMailRetornado
                                lixeiraEmailList.value.forEach { email ->
                                    if (!lixeiraStateEmailLst.contains(email)) {
                                        lixeiraStateEmailLst.add(email)
                                    }
                                }
                            }
                        },
                        onError = {
                            isError.value = true
                            isLoading.value = false
                        }
                    )


                    callLocaMailApiListarPastasPorIdUsuario(
                        usuarioSelecionado.value!!.id_usuario,
                        onSuccess = { listPastaRetornado ->

                            if (listPastaRetornado != null) {
                                listPastaState.addAll(listPastaRetornado)
                            }

                        },
                        onError = {
                            isError.value = true
                            isLoading.value = false
                        }
                    )

                    callLocaMailApiListarAnexosIdEmail(
                        onSuccess = { listLongRetornado ->
                            if (listLongRetornado != null) {
                                attachEmailList.addAll(listLongRetornado)
                            }
                        },
                        onError = { error ->
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


    val openDialogPastaCreator = remember {
        mutableStateOf(false)
    }

    val textPastaCreator = remember {
        mutableStateOf("")
    }


    val selectedDrawerPasta = remember {
        mutableStateOf("")
    }

    val toastMessageMailsDeleted = stringResource(id = R.string.toast_mails_delete)
    val toastMessageMailMovedFromTrash = stringResource(id = R.string.toast_mail_moved_from_trash)
    val toastMessageFolderDeleted = stringResource(id = R.string.toast_folder_deleted)


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
            ModalNavDrawer(
                selectedDrawer = selectedDrawer,
                navController = navController,
                drawerState = drawerState,
                expandedPasta = expandedPasta,
                openDialogPastaCreator = openDialogPastaCreator,
                textPastaCreator = textPastaCreator,
                selectedDrawerPasta = selectedDrawerPasta,
                context = context,
                listPastaState = listPastaState,
                scope = scope,
                toastMessageFolderDeleted = toastMessageFolderDeleted,
                isLoading = isLoading,
                isError = isError
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column {
                        RowSearchBar(
                            drawerState = drawerState,
                            scope = scope,
                            openDialogUserPicker = openDialogUserPicker,
                            textSearchBar = textSearchBar,
                            applyStateListUserSelectorDialog = {
                                if (usuarioSelecionado.value != null) {
                                    isLoading.value = true
                                    callLocaMailApiListarEmailsLixeiraPorIdUsuario(
                                        usuarioSelecionado.value!!.id_usuario,
                                        onSuccess = { listMailRetornado ->
                                            isLoading.value = false
                                            if (listMailRetornado != null) {
                                                listMailRetornado.forEach { email ->
                                                    if (!lixeiraStateEmailLst.contains(email)) {
                                                        lixeiraStateEmailLst.add(email)
                                                    }
                                                }
                                            }
                                        },
                                        onError = {
                                            isError.value = true
                                            isLoading.value = false
                                        }
                                    )
                                }
                            },
                            usuarioSelecionado = usuarioSelecionado,
                            stateEmailList = lixeiraStateEmailLst,
                            placeholderTextFieldSearch = stringResource(id = R.string.mail_main_searchbar),
                            navController = navController,
                            isLoading = isLoading,
                            isError = isError,
                            listUsuariosNaoAutenticados = listUsuariosNaoAutenticados
                        )

                        if (lixeiraStateEmailLst.isNotEmpty()) {
                            TopButton(
                                textButton = stringResource(id = R.string.mail_generic_clean_all),
                                onClick = {
                                    if (usuarioSelecionado.value != null) {
                                        isLoading.value = true
                                        for (email in lixeiraEmailList.value) {

                                            callLocaMailApiExcluiAlteracaoPorIdEmailEIdUsuario(
                                                email.id_email,
                                                usuarioSelecionado.value!!.id_usuario,
                                                onSuccess = {
                                                    callLocaMailApiListarAlteracaoPorIdEmail(
                                                        email.id_email,
                                                        onSuccess = { listAlteracaoRetornado ->
                                                            if (listAlteracaoRetornado != null) {
                                                                if (listAlteracaoRetornado.isEmpty()) {

                                                                    callLocaMailApiExcluirAnexoPorIdEmail(
                                                                        email.id_email,
                                                                        onSuccess = {

                                                                        },
                                                                        onError = {
                                                                            isError.value = true
                                                                            isLoading.value = false
                                                                        }
                                                                    )

                                                                    callLocaMailApiListarRespostasEmailPorIdEmail(
                                                                        email.id_email,
                                                                        onSuccess = { listRespostaRetornado ->
                                                                            if (listRespostaRetornado != null) {
                                                                                if (!listRespostaRetornado.isEmpty()) {
                                                                                    for (respostaEmail in listRespostaRetornado) {

                                                                                        callLocaMailApiExcluirAnexoPorIdRespostaEmail(
                                                                                            respostaEmail.id_resposta_email,
                                                                                            onSuccess = {

                                                                                            },
                                                                                            onError = {
                                                                                                isError.value =
                                                                                                    true
                                                                                                isLoading.value =
                                                                                                    false
                                                                                            }
                                                                                        )
                                                                                        callLocaMailApiExcluirRespostaEmail(
                                                                                            respostaEmail.id_resposta_email,
                                                                                            onSuccess = {

                                                                                            },
                                                                                            onError = {
                                                                                                isError.value = true
                                                                                                isLoading.value =
                                                                                                    false
                                                                                            }
                                                                                        )
                                                                                    }
                                                                                    callLocaMailApiExcluirEmail(
                                                                                        email.id_email,
                                                                                        onSuccess = {
                                                                                        },
                                                                                        onError = {
                                                                                            isError.value = true
                                                                                            isLoading.value = false
                                                                                        }
                                                                                    )
                                                                                }
                                                                                else {
                                                                                    callLocaMailApiExcluirEmail(
                                                                                        email.id_email,
                                                                                        onSuccess = {
                                                                                        },
                                                                                        onError = {
                                                                                            isError.value = true
                                                                                            isLoading.value = false
                                                                                        }
                                                                                    )
                                                                                }
                                                                            }
                                                                        },
                                                                        onError = {
                                                                            isError.value = true
                                                                            isLoading.value = false
                                                                        }
                                                                    )
                                                                }

                                                                callLocaMailApiListarAgendaPorIdEmailEIdUsuario(
                                                                    email.id_email,
                                                                    usuarioSelecionado.value!!.id_usuario,
                                                                    onSuccess = { listAgendaRetornado ->

                                                                        if (listAgendaRetornado != null) {
                                                                            val agendaEmailList =
                                                                                listAgendaRetornado

                                                                            if (agendaEmailList.isNotEmpty()) {
                                                                                for (agenda in agendaEmailList) {
                                                                                    callLocaMailApiExcluirPorIdAgenda(
                                                                                        agenda.id_agenda,
                                                                                        onSuccess = {

                                                                                        },
                                                                                        onError = {
                                                                                            isError.value =
                                                                                                true
                                                                                            isLoading.value =
                                                                                                false
                                                                                        }
                                                                                    )

                                                                                    callLocaMailApiExcluiAgenda(
                                                                                        agenda.id_agenda,
                                                                                        onSuccess = {

                                                                                        },
                                                                                        onError = {
                                                                                            isError.value =
                                                                                                true
                                                                                            isLoading.value =
                                                                                                false
                                                                                        }
                                                                                    )
                                                                                }
                                                                            }
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
                                                    lixeiraStateEmailLst.remove(email)
                                                },
                                                onError = {
                                                    isError.value = true
                                                    isLoading.value = false
                                                }
                                            )
                                        }
                                        Toast.makeText(
                                            context,
                                            toastMessageMailsDeleted,
                                            Toast.LENGTH_LONG
                                        )
                                            .show()
                                        isLoading.value = false
                                    }
                                })

                            LazyColumn(reverseLayout = false) {
                                items(lixeiraStateEmailLst, key = {
                                    it.id_alteracao
                                }) {

                                    if (
                                        it.assunto.contains(
                                            textSearchBar.value,
                                            ignoreCase = true
                                        ) ||
                                        it.corpo.contains(
                                            textSearchBar.value,
                                            ignoreCase = true
                                        )
                                    ) {
                                        val isRead = remember {
                                            mutableStateOf(it.lido)
                                        }

                                        val redLcWeb = colorResource(id = R.color.lcweb_red_1)

                                        val respostasEmail = remember {
                                            mutableStateOf(listOf<RespostaEmail>())
                                        }

                                        callLocaMailApiListarRespostasEmailPorIdEmail(
                                            it.id_email,
                                            onSuccess = { listRespostaEmailRetornado ->
                                                if (listRespostaEmailRetornado != null) {
                                                    respostasEmail.value =
                                                        listRespostaEmailRetornado
                                                }
                                            },
                                            onError = {
                                                isError.value = true
                                                isLoading.value = false
                                            }
                                        )

                                        EmailViewButton(
                                            onClickButton = {
                                                if (!isRead.value) {

                                                    callLocaMailApiAtualizarLidoPorIdEmailEIdusuario(
                                                        isRead.value,
                                                        it.id_email,
                                                        it.alt_id_usuario,
                                                        onSuccess = {
                                                            isRead.value = true
                                                        },
                                                        onError = {
                                                            isError.value = true
                                                            isLoading.value = false
                                                        }
                                                    )
                                                }

                                                navController.navigate("visualizaemailscreen/${it.id_email}/false")
                                            },
                                            isRead = isRead,
                                            redLcWeb = redLcWeb,
                                            respostasEmail = respostasEmail.value,
                                            attachEmailList = attachEmailList,
                                            timeState = timeState,
                                            email = it,
                                            moveIconOption = true,
                                            onClickMoveButton = {

                                                callLocaMailApiAtualizarExcluidoPorIdEmailEIdusuario(
                                                    false,
                                                    it.id_email,
                                                    it.alt_id_usuario,
                                                    onSuccess = {
                                                        Toast.makeText(
                                                            context,
                                                            toastMessageMailMovedFromTrash,
                                                            Toast.LENGTH_LONG
                                                        ).show()
                                                    },
                                                    onError = {
                                                        isError.value = true
                                                        isLoading.value = false
                                                    }
                                                )
                                                lixeiraStateEmailLst.remove(it)
                                            },
                                            importantIconOption = false,
                                            usuarioSelecionado = usuarioSelecionado,
                                        )
                                    }
                                }
                            }
                        }
                    }

                    if (lixeiraStateEmailLst.isEmpty()) {
                        Image(
                            painter = painterResource(id = R.drawable.recycling),
                            contentDescription = stringResource(id = R.string.content_desc_recyclinimage),
                            modifier = Modifier
                                .size(250.dp)
                                .align(Alignment.Center)
                        )
                    }

                    EmailCreateButton(
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.BottomEnd),
                        onClick = {
                            navController.navigate("criaemailscreen")
                        }
                    )
                }
            }

        }
    }
}

