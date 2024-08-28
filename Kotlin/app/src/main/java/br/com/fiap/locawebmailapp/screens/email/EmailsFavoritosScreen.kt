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
import br.com.fiap.locawebmailapp.components.general.ErrorComponent
import br.com.fiap.locawebmailapp.components.general.ModalNavDrawer
import br.com.fiap.locawebmailapp.model.EmailComAlteracao
import br.com.fiap.locawebmailapp.model.Pasta
import br.com.fiap.locawebmailapp.model.RespostaEmail
import br.com.fiap.locawebmailapp.model.Usuario
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiAtualizarImportantePorIdEmail
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiAtualizarLidoPorIdEmailEIdusuario
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarAnexosIdEmail
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarEmailsImportantesPorIdUsuario
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarPastasPorIdUsuario
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarRespostasEmailPorIdEmail
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarUsuarioSelecionado
import br.com.fiap.locawebmailapp.utils.checkInternetConnectivity


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailsFavoritosScreen(navController: NavController) {
    val selectedDrawer = remember {
        mutableStateOf("4")
    }

    val textSearchBar = remember {
        mutableStateOf("")
    }

    val expandedPasta = remember {
        mutableStateOf(true)
    }

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

    val usuarioSelecionado = remember {
        mutableStateOf<Usuario?>(null)
    }

    val favoriteEmailList = remember {
        mutableStateOf(listOf<EmailComAlteracao>())
    }

    val favoriteStateEmailLst = remember {
        mutableStateListOf<EmailComAlteracao>()
    }

    val listPastaState = remember {
        mutableStateListOf<Pasta>()
    }

    val attachEmailList = remember {
        mutableStateListOf<Long?>()
    }

    LaunchedEffect(key1 = Unit) {

        callLocaMailApiListarUsuarioSelecionado(
            onSuccess = { usuarioSelecionadoRetornado ->
                if (usuarioSelecionadoRetornado != null) {
                    usuarioSelecionado.value = usuarioSelecionadoRetornado

                    callLocaMailApiListarEmailsImportantesPorIdUsuario(
                        usuarioSelecionado.value!!.id_usuario,
                        onSuccess = { listMailImportanteRetornado ->
                            isLoading.value = false
                            if (listMailImportanteRetornado != null) {
                                favoriteEmailList.value = listMailImportanteRetornado
                                favoriteStateEmailLst.addAll(favoriteEmailList.value)
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
                            listPastaState.addAll(listPastaRetornado!!)
                        },
                        onError = { error ->
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

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val scope = rememberCoroutineScope()

    val timeState = rememberTimePickerState()

    val openDialogUserPicker = remember {
        mutableStateOf(false)
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
                isError = isError,
                isLoading = isLoading
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

                                isLoading.value = true
                                if (usuarioSelecionado.value != null) {
                                    callLocaMailApiListarEmailsImportantesPorIdUsuario(
                                        id_usuario = usuarioSelecionado.value!!.id_usuario,
                                        onSuccess = { listMailRetornado ->
                                            isLoading.value = false
                                            if (listMailRetornado != null) {
                                                listMailRetornado.forEach { email ->
                                                    if (!favoriteStateEmailLst.contains(email)) {
                                                        favoriteStateEmailLst.add(email)
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
                            stateEmailList = favoriteStateEmailLst,
                            placeholderTextFieldSearch = stringResource(id = R.string.mail_main_searchbar),
                            navController = navController,
                            isLoading = isLoading,
                            isError = isError,
                            listUsuariosNaoAutenticados = listUsuariosNaoAutenticados
                        )

                        if (favoriteStateEmailLst.isNotEmpty()) {

                            LazyColumn(reverseLayout = false) {
                                items(favoriteStateEmailLst.reversed(), key = {
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
                                        val isImportant = remember {
                                            mutableStateOf(it.importante)
                                        }

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
                                            onClickImportantButton = {
                                                isImportant.value = !isImportant.value
                                                callLocaMailApiAtualizarImportantePorIdEmail(
                                                    isImportant.value,
                                                    it.id_email,
                                                    it.alt_id_usuario,
                                                    onSuccess = {

                                                    },
                                                    onError = {
                                                        isError.value = true
                                                        isLoading.value = false
                                                    }
                                                )
                                                favoriteStateEmailLst.remove(it)
                                            },
                                            isImportant = isImportant,
                                            attachEmailList = attachEmailList,
                                            timeState = timeState,
                                            email = it,
                                            usuarioSelecionado = usuarioSelecionado,
                                        )
                                    }
                                }
                            }
                        }
                    }

                    if (favoriteStateEmailLst.isEmpty()) {
                        Image(
                            painter = painterResource(id = R.drawable.messagereceived),
                            contentDescription = stringResource(id = R.string.content_desc_nomailimage),
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