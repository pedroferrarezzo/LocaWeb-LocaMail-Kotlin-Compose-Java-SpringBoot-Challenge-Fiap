package br.com.fiap.locawebmailapp.screens.email

import android.util.Log
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
import br.com.fiap.locawebmailapp.components.email.EmailDraftViewButton
import br.com.fiap.locawebmailapp.components.email.RowSearchBar
import br.com.fiap.locawebmailapp.components.email.TopButton
import br.com.fiap.locawebmailapp.components.general.ErrorComponent
import br.com.fiap.locawebmailapp.components.general.ModalNavDrawer
import br.com.fiap.locawebmailapp.model.Email
import br.com.fiap.locawebmailapp.model.Pasta
import br.com.fiap.locawebmailapp.model.Usuario
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiExcluirAnexoPorIdEmail
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiExcluirEmail
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarAnexosIdEmail
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarEmailsEditaveisPorRemetente
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarPastasPorIdUsuario
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarUsuarioSelecionado
import br.com.fiap.locawebmailapp.utils.checkInternetConnectivity


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailsEditaveisScreen(navController: NavController) {
    val selectedDrawer = remember {
        mutableStateOf("5")
    }

    val textSearchBar = remember {
        mutableStateOf("")
    }

    val expandedPasta = remember {
        mutableStateOf(true)
    }

    val context = LocalContext.current

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val scope = rememberCoroutineScope()

    val timeState = rememberTimePickerState()

    val openDialogUserPicker = remember {
        mutableStateOf(false)
    }

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

    val editableEmailList = remember {
        mutableStateOf(listOf<Email>())
    }


    val editableEmailStateList = remember {
        mutableStateListOf<Email>()
    }

    val attachEmailList = remember {
        mutableStateListOf<Long?>(null)
    }

    val listPastaState = remember {
        mutableStateListOf<Pasta>()
    }

    val listUsuariosNaoAutenticados = remember {
        mutableStateListOf<Usuario>()
    }

    LaunchedEffect(key1 = Unit) {
        callLocaMailApiListarUsuarioSelecionado(
            onSuccess = { usuariosSelecionadosRetornado ->

                if (usuariosSelecionadosRetornado != null) {
                    usuarioSelecionado.value = usuariosSelecionadosRetornado

                    callLocaMailApiListarEmailsEditaveisPorRemetente(
                        usuarioSelecionado.value!!.email,
                        onSuccess = { listEmailsEditaveisRetornados ->
                            isLoading.value = false
                            if (listEmailsEditaveisRetornados != null) {
                                editableEmailList.value = listEmailsEditaveisRetornados
                                editableEmailList.value.forEach { email ->
                                    if (!editableEmailStateList.contains(email)) {
                                        editableEmailStateList.add(email)
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
                        onSuccess = { listPastasRetornado ->
                            if (listPastasRetornado != null) {
                                listPastaState.addAll(listPastasRetornado)
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

        callLocaMailApiListarAnexosIdEmail(
            onSuccess = { listAnexoIdEmailRetornado ->

                if (listAnexoIdEmailRetornado != null) {
                    attachEmailList.addAll(listAnexoIdEmailRetornado)
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

    val toastMessageDraftsDeleted = stringResource(id = R.string.toast_maildrafts_deleted)
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
                                isLoading.value = true

                                if (usuarioSelecionado.value != null) {
                                    callLocaMailApiListarEmailsEditaveisPorRemetente(
                                        usuarioSelecionado.value!!.email,
                                        onSuccess = { listEmailsEditaveisRetorno ->
                                            isLoading.value = false
                                            if (listEmailsEditaveisRetorno != null) {
                                                listEmailsEditaveisRetorno.forEach { email ->
                                                    if (!editableEmailStateList.contains(email)) {
                                                        editableEmailStateList.add(email)
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
                            stateEmailList = editableEmailStateList,
                            placeholderTextFieldSearch = stringResource(id = R.string.mail_main_searchbar),
                            navController = navController,
                            isLoading = isLoading,
                            isError = isError,
                            listUsuariosNaoAutenticados = listUsuariosNaoAutenticados
                        )

                        if (!editableEmailStateList.isEmpty()) {

                            TopButton(
                                textButton = stringResource(id = R.string.mail_generic_clean_all),
                                onClick = {
                                    for (email in editableEmailList.value) {

                                        callLocaMailApiExcluirAnexoPorIdEmail(
                                            email.id_email,
                                            onSuccess = {
                                            },
                                            onError = {
                                                isError.value = true
                                                isLoading.value = false
                                            }
                                        )

                                        callLocaMailApiExcluirEmail(
                                            email.id_email,
                                            onSuccess = {
                                                editableEmailStateList.remove(email)
                                                Toast.makeText(
                                                    context,
                                                    toastMessageDraftsDeleted,
                                                    Toast.LENGTH_LONG
                                                )
                                                    .show()
                                            },
                                            onError = {
                                                isError.value = true
                                                isLoading.value = false
                                            }
                                        )
                                    }
                                })

                            LazyColumn(reverseLayout = false) {
                                items(editableEmailStateList, key = {
                                    it.id_email
                                }) {
                                    if (
                                        it.assunto.contains(
                                            textSearchBar.value,
                                            ignoreCase = true
                                        ) ||
                                        it.corpo.contains(textSearchBar.value, ignoreCase = true)
                                    ) {
                                        EmailDraftViewButton(
                                            onClickButton = { navController.navigate("editaemailscreen/${it.id_email}") },
                                            attachEmailList = attachEmailList,
                                            timeState = timeState,
                                            email = it
                                        )
                                    }
                                }
                            }
                        }
                    }

                    if (editableEmailStateList.isEmpty()) {
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