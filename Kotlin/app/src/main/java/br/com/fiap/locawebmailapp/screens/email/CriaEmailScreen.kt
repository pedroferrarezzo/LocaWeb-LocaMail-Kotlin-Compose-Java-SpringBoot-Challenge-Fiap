package br.com.fiap.locawebmailapp.screens.email

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.locawebmailapp.R
import br.com.fiap.locawebmailapp.components.email.ColumnCrudMail
import br.com.fiap.locawebmailapp.components.email.RowCrudMail
import br.com.fiap.locawebmailapp.components.general.ErrorComponent
import br.com.fiap.locawebmailapp.model.Alteracao
import br.com.fiap.locawebmailapp.model.Anexo
import br.com.fiap.locawebmailapp.model.Convidado
import br.com.fiap.locawebmailapp.model.Email
import br.com.fiap.locawebmailapp.model.Usuario
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiCriarAlteracao
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiCriarAnexo
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiCriarConvidado
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiCriarEmail
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarUsuarioSelecionado
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarUsuarios
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiVerificarConvidadoExiste
import br.com.fiap.locawebmailapp.utils.bitmapToByteArray
import br.com.fiap.locawebmailapp.utils.checkInternetConnectivity
import br.com.fiap.locawebmailapp.utils.listaParaString
import br.com.fiap.locawebmailapp.utils.pickImageFromGallery
import br.com.fiap.locawebmailapp.utils.validateEmail


@Composable
fun CriaEmailScreen(navController: NavController) {

    val context = LocalContext.current

    val email = Email()
    val anexo = Anexo()
    val convidado = Convidado()

    val isConnectedStatus = remember {
        mutableStateOf(checkInternetConnectivity(context))
    }
    val isLoading = remember {
        mutableStateOf(false)
    }

    val isError = remember {
        mutableStateOf(false)
    }

    val toastMessageWait = stringResource(id = R.string.toast_api_wait)


    val assuntoText = remember {
        mutableStateOf("")
    }

    val corpoMailText = remember {
        mutableStateOf("")
    }

    val destinatarios = remember { mutableStateListOf<String>() }
    val destinatarioText = remember {
        mutableStateOf("")
    }
    val cc = remember {
        mutableStateOf("")
    }
    val ccs = remember { mutableStateListOf<String>() }
    val cco = remember {
        mutableStateOf("")
    }
    val ccos = remember { mutableStateListOf<String>() }


    val isErrorPara = remember {
        mutableStateOf(false)
    }

    val isErrorCc = remember {
        mutableStateOf(false)
    }

    val isErrorCco = remember {
        mutableStateOf(false)
    }

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    val bitmapList = remember {
        mutableStateListOf<Bitmap>()
    }

    val usuarioSelecionado = remember {
        mutableStateOf<Usuario?>(null)
    }

    LaunchedEffect(key1 = Unit) {
        callLocaMailApiListarUsuarioSelecionado(
            onSuccess = { usuarioRetornado ->

                usuarioSelecionado.value = usuarioRetornado

            },
            onError = {
                isError.value = true
                isLoading.value = false
            }
        )
    }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->

            imageUri = uri
            pickImageFromGallery(
                context = context,
                imageUri = imageUri,
                bitmap = bitmap,
                bitmapList = bitmapList
            )
        }

    val expandedCc = remember {
        mutableStateOf(false)
    }

    val toastMessageMailDraftSaved = stringResource(id = R.string.toast_maildraft_saved)
    val toastMessageMailSent = stringResource(id = R.string.toast_mail_sent)
    val toastMessageMailDest = stringResource(id = R.string.toast_mail_dest)


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
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                RowCrudMail(
                    onClickBack = {
                        if (destinatarios.isNotEmpty() || ccos.isNotEmpty() || ccs.isNotEmpty() || bitmapList.isNotEmpty() ||
                            assuntoText.value != "" ||
                            corpoMailText.value != ""
                        ) {
                            isLoading.value = true

                            email.destinatario =
                                if (destinatarios.isNotEmpty()) listaParaString(destinatarios) else ""
                            email.cc = if (ccs.isNotEmpty()) listaParaString(ccs) else ""
                            email.cco = if (ccos.isNotEmpty()) listaParaString(ccos) else ""
                            email.assunto = assuntoText.value
                            email.corpo = corpoMailText.value
                            email.enviado = false
                            email.editavel = true

                            if (usuarioSelecionado.value != null) {
                                email.id_usuario = usuarioSelecionado.value!!.id_usuario
                                email.remetente = usuarioSelecionado.value!!.email
                            }

                            callLocaMailApiCriarEmail(
                                email,
                                onSuccess = { emailRetornado ->


                                    if (emailRetornado != null) {
                                        val rowId = emailRetornado.id_email

                                        if (bitmapList.isNotEmpty()) {
                                            anexo.id_email = rowId

                                            for (bitmap in bitmapList) {
                                                val byteArray = bitmapToByteArray(bitmap = bitmap)
                                                anexo.anexo = byteArray

                                                callLocaMailApiCriarAnexo(
                                                    anexo,
                                                    onSuccess = {
                                                    },
                                                    onError = {
                                                        isError.value = true
                                                        isLoading.value = false

                                                    }
                                                )
                                            }
                                        }
                                        isLoading.value = false
                                        Toast.makeText(
                                            context,
                                            toastMessageMailDraftSaved,
                                            Toast.LENGTH_LONG
                                        )
                                            .show()
                                    }
                                },
                                onError = {
                                    isLoading.value = false
                                    isError.value = true
                                }
                            )
                        }
                        navController.popBackStack()
                    },
                    onClickPaperClip = {
                        launcher.launch("image/*")
                    },
                    onClickSend = {
                        if (destinatarios.isNotEmpty() || ccos.isNotEmpty() || ccs.isNotEmpty()) {
                            isLoading.value = true

                            email.destinatario =
                                if (destinatarios.isNotEmpty()) listaParaString(destinatarios) else ""
                            email.cc = if (ccs.isNotEmpty()) listaParaString(ccs) else ""
                            email.cco = if (ccos.isNotEmpty()) listaParaString(ccos) else ""
                            email.assunto = assuntoText.value
                            email.corpo = corpoMailText.value
                            email.enviado = true
                            email.editavel = false

                            if (usuarioSelecionado.value != null) {
                                email.id_usuario = usuarioSelecionado.value!!.id_usuario
                                email.remetente = usuarioSelecionado.value!!.email
                            }

                            callLocaMailApiCriarEmail(
                                email,
                                onSuccess = { emailRetornado ->
                                    if (emailRetornado != null) {

                                        val todosDestinatarios = arrayListOf<String>()

                                        todosDestinatarios.addAll(destinatarios)
                                        todosDestinatarios.addAll(ccos)
                                        todosDestinatarios.addAll(ccs)

                                        for (destinatario in todosDestinatarios) {
                                            callLocaMailApiVerificarConvidadoExiste(
                                                email = destinatario,
                                                onSuccess = { convidadoExisteRetornado ->

                                                    val convidadoExistente =
                                                        convidadoExisteRetornado ?: ""

                                                    if (convidadoExistente != destinatario) {
                                                        convidado.email = destinatario

                                                        callLocaMailApiCriarConvidado(
                                                            convidado,
                                                            onSuccess = {

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

                                        val rowId = emailRetornado.id_email

                                        if (!todosDestinatarios.contains(email.remetente)) todosDestinatarios.add(
                                            email.remetente
                                        )

                                        callLocaMailApiListarUsuarios(
                                            onSuccess = { usuariosRetornados ->

                                                if (usuariosRetornados != null) {
                                                    for (usuario in usuariosRetornados) {
                                                        if (todosDestinatarios.contains(usuario.email)) {

                                                            callLocaMailApiCriarAlteracao(
                                                                Alteracao(
                                                                    alt_id_email = rowId,
                                                                    alt_id_usuario = usuario.id_usuario
                                                                ),
                                                                onSuccess = {
                                                                },
                                                                onError = {
                                                                    isError.value = true
                                                                    isLoading.value = false

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

                                        if (bitmapList.isNotEmpty()) {
                                            anexo.id_email = rowId

                                            for (bitmap in bitmapList) {
                                                val byteArray = bitmapToByteArray(bitmap = bitmap)
                                                anexo.anexo = byteArray

                                                callLocaMailApiCriarAnexo(
                                                    anexo,
                                                    onSuccess = {

                                                    },
                                                    onError = {
                                                        isError.value = true
                                                        isLoading.value = false
                                                    }
                                                )
                                            }
                                        }
                                    }
                                    isLoading.value = false
                                    Toast.makeText(
                                        context,
                                        toastMessageMailSent,
                                        Toast.LENGTH_LONG
                                    ).show()
                                    navController.popBackStack()

                                },
                                onError = {
                                    isError.value = true
                                    isLoading.value = false
                                }
                            )
                        } else {
                            Toast.makeText(
                                context,
                                toastMessageMailDest,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    },
                    bitmapList = bitmapList
                )

                ColumnCrudMail(
                    usuarioSelecionado = usuarioSelecionado,
                    destinatarios = destinatarios,
                    ccs = ccs,
                    ccos = ccos,
                    destinatarioText = destinatarioText,
                    cc = cc,
                    cco = cco,
                    assuntoText = assuntoText,
                    corpoMailText = corpoMailText,
                    expandedCc = expandedCc,
                    isErrorPara = isErrorPara,
                    isErrorCc = isErrorCc,
                    isErrorCco = isErrorCco,
                    onValueChangeTextFieldTo = {
                        destinatarioText.value = it
                        if (isErrorPara.value) isErrorPara.value = false
                    },
                    onClickExpandCc = {
                        expandedCc.value = !expandedCc.value
                    },
                    onClickAddDestinatario = {
                        if (destinatarios.contains(destinatarioText.value) || ccs.contains(
                                destinatarioText.value
                            ) || ccos.contains(destinatarioText.value)
                        ) {
                            isErrorPara.value = true
                        } else {
                            isErrorPara.value = !validateEmail(destinatarioText.value)
                        }
                        if (!isErrorPara.value) destinatarios.add(destinatarioText.value.toLowerCase())
                    },
                    onClickRemoveDestinatario = {
                        destinatarios.remove(it)
                    },
                    onValueChangeTextFieldCc = {
                        cc.value = it
                        if (isErrorCc.value) isErrorCc.value = false
                    },
                    onClickAddCc = {
                        if (destinatarios.contains(cc.value) || ccs.contains(cc.value) || ccos.contains(
                                cc.value
                            )
                        ) {
                            isErrorCc.value = true
                        } else {
                            isErrorCc.value = !validateEmail(cc.value)
                        }
                        if (!isErrorCc.value) ccs.add(cc.value.toLowerCase())
                    },
                    onClickRemoveCc = {
                        ccs.remove(it)
                    },
                    onValueChangeTextFieldCco = {
                        cco.value = it
                        if (isErrorCco.value) isErrorCco.value = false
                    },
                    onClickAddCco = {
                        if (destinatarios.contains(cco.value) || ccs.contains(cco.value) || ccos.contains(
                                cco.value
                            )
                        ) {
                            isErrorCco.value = true
                        } else {
                            isErrorCco.value = !validateEmail(cco.value)
                        }
                        if (!isErrorCco.value) ccos.add(cco.value.toLowerCase())
                    },
                    onClickRemoveCco = {
                        ccos.remove(it)
                    },
                    onValueChangeTextFieldAssunto = {
                        assuntoText.value = it
                    },
                    onValueChangeTextFieldCorpoMail = {
                        corpoMailText.value = it
                    }
                )
            }
        }
    }
}