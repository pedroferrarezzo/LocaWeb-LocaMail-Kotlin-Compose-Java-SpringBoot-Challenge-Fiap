package br.com.fiap.locawebmailapp.screens.email

import android.graphics.Bitmap
import android.net.Uri
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import br.com.fiap.locawebmailapp.components.email.ColumnCrudMail
import br.com.fiap.locawebmailapp.components.email.RowCrudMail
import br.com.fiap.locawebmailapp.components.general.ErrorComponent
import br.com.fiap.locawebmailapp.model.Alteracao
import br.com.fiap.locawebmailapp.model.Anexo
import br.com.fiap.locawebmailapp.model.Convidado
import br.com.fiap.locawebmailapp.model.Email
import br.com.fiap.locawebmailapp.model.Usuario
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiAtualizarEmail
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiCriarAlteracao
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiCriarAnexo
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiCriarConvidado
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiExcluirAnexoPorIdEmail
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiExcluirEmail
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarAnexosArraybytePorIdEmail
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarEmailPorId
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarUsuarioSelecionado
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarUsuarios
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiVerificarConvidadoExiste
import br.com.fiap.locawebmailapp.utils.bitmapToByteArray
import br.com.fiap.locawebmailapp.utils.byteArrayToBitmap
import br.com.fiap.locawebmailapp.utils.checkInternetConnectivity
import br.com.fiap.locawebmailapp.utils.listaParaString
import br.com.fiap.locawebmailapp.utils.pickImageFromGallery
import br.com.fiap.locawebmailapp.utils.stringParaLista
import br.com.fiap.locawebmailapp.utils.validateEmail
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditaEmailScreen(navController: NavController, idEmail: Long) {

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

    val toastMessageWait = stringResource(id = R.string.toast_api_wait)

    val email = remember {
        mutableStateOf<Email?>(null)
    }

    val anexo = Anexo()
    val convidado = Convidado()
    val usuarioSelecionado = remember {
        mutableStateOf<Usuario?>(null)
    }

    var anexoArrayByteList: List<ByteArray> = listOf()
    var bitmapList = remember { mutableStateListOf<Bitmap>() }

    LaunchedEffect(key1 = Unit) {
        callLocaMailApiListarEmailPorId(
            idEmail,
            onSuccess = {

                    emailRetornado ->

                if (emailRetornado != null) {
                    isLoading.value = false
                    email.value = emailRetornado


                }

            },
            onError = {
                isError.value = true
                isLoading.value = false
            }
        )

        callLocaMailApiListarUsuarioSelecionado(
            onSuccess = { usuarioSelecionadoRetornado ->

                usuarioSelecionado.value = usuarioSelecionadoRetornado

            },
            onError = {
                isError.value = true
                isLoading.value = false
            }
        )

        callLocaMailApiListarAnexosArraybytePorIdEmail(
            idEmail,
            onSuccess = { listArrayByteRetornado ->

                if (listArrayByteRetornado != null) {
                    anexoArrayByteList = listArrayByteRetornado
                    bitmapList = anexoArrayByteList.map {
                        byteArrayToBitmap(it)
                    }.toMutableStateList()
                }
            },
            onError = {
                isError.value = true
                isLoading.value = false
            }
        )

    }

    val destinatarioText = remember {
        mutableStateOf("")
    }

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    val cc = remember {
        mutableStateOf("")
    }

    val cco = remember {
        mutableStateOf("")
    }


    val isErrorPara = remember {
        mutableStateOf(false)
    }

    val isErrorCc = remember {
        mutableStateOf(false)
    }

    val isErrorCco = remember {
        mutableStateOf(false)
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

    val toastMessageDraftSaved = stringResource(id = R.string.toast_maildraft_saved)
    val toastMessageDraftDeleted = stringResource(id = R.string.toast_maildraft_deleted)
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
            if (email.value != null) {

                val destinatarios =
                    remember {
                        if (email.value!!.destinatario.equals("")) mutableStateListOf<String>() else stringParaLista(
                            email.value!!.destinatario
                        ).toMutableStateList()
                    }

                val assuntoText = remember {
                    mutableStateOf(email.value!!.assunto)
                }

                val corpoMailText = remember {
                    mutableStateOf(email.value!!.corpo)
                }

                val ccs =
                    remember {
                        if (email.value!!.cc.equals("")) mutableStateListOf<String>() else stringParaLista(
                            email.value!!.cc
                        ).toMutableStateList()
                    }

                val ccos = remember {
                    if (email.value!!.cco.equals("")) mutableStateListOf<String>() else stringParaLista(
                        email.value!!.cco
                    ).toMutableStateList()
                }

                Column(
                    modifier = Modifier.fillMaxSize()
                ) {

                    RowCrudMail(
                        onClickBack = {
                            if (listaParaString(destinatarios) != email.value!!.destinatario
                                || listaParaString(ccos) != email.value!!.cco
                                || listaParaString(ccs) != email.value!!.cc
                                || assuntoText.value != email.value!!.assunto
                                || corpoMailText.value != email.value!!.corpo
                                || bitmapList.isNotEmpty()
                                || anexoArrayByteList.isNotEmpty()
                            ) {
                                isLoading.value = true

                                email.value!!.destinatario =
                                    if (destinatarios.isNotEmpty()) listaParaString(destinatarios) else ""
                                email.value!!.cc =
                                    if (ccs.isNotEmpty()) listaParaString(ccs) else ""
                                email.value!!.cco =
                                    if (ccos.isNotEmpty()) listaParaString(ccos) else ""
                                email.value!!.assunto = assuntoText.value
                                email.value!!.corpo = corpoMailText.value
                                email.value!!.enviado = false
                                email.value!!.editavel = true
                                anexo.id_email = email.value!!.id_email

                                callLocaMailApiAtualizarEmail(
                                    email.value!!,
                                    onSuccess = {
                                        isLoading.value = false

                                    },
                                    onError = {
                                        isError.value = true
                                        isLoading.value = false
                                    }
                                )

                                callLocaMailApiExcluirAnexoPorIdEmail(
                                    anexo.id_email,
                                    onSuccess = {

                                    },
                                    onError = {
                                        isError.value = true
                                        isLoading.value = false
                                    }
                                )

                                if (bitmapList.isNotEmpty()) {
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
                                Toast.makeText(context, toastMessageDraftSaved, Toast.LENGTH_LONG)
                                    .show()
                                navController.popBackStack()

                            } else {
                                navController.popBackStack()
                            }

                        },
                        onClickPaperClip = {
                            launcher.launch("image/*")
                        },
                        onClickSend = {
                            if (destinatarios.isNotEmpty() || ccos.isNotEmpty() || ccs.isNotEmpty()) {
                                isLoading.value = true
                                email.value!!.destinatario =
                                    if (destinatarios.isNotEmpty()) listaParaString(destinatarios) else ""
                                email.value!!.cc =
                                    if (ccs.isNotEmpty()) listaParaString(ccs) else ""
                                email.value!!.cco =
                                    if (ccos.isNotEmpty()) listaParaString(ccos) else ""
                                email.value!!.assunto = assuntoText.value
                                email.value!!.corpo = corpoMailText.value
                                email.value!!.enviado = true
                                email.value!!.editavel = false
                                email.value!!.data = "${LocalDate.now()}"
                                email.value!!.horario =
                                    LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
                                anexo.id_email = email.value!!.id_email

                                callLocaMailApiAtualizarEmail(
                                    email.value!!,
                                    onSuccess = {
                                        isLoading.value = false

                                    },
                                    onError = {
                                        isError.value = true
                                        isLoading.value = false
                                    }
                                )

                                callLocaMailApiExcluirAnexoPorIdEmail(
                                    anexo.id_email,
                                    onSuccess = {

                                    },
                                    onError = {
                                        isError.value = true
                                        isLoading.value = false
                                    }
                                )

                                val todosDestinatarios = arrayListOf<String>()
                                todosDestinatarios.addAll(destinatarios)
                                todosDestinatarios.addAll(ccos)
                                todosDestinatarios.addAll(ccs)

                                if (!todosDestinatarios.contains(email.value!!.remetente)) todosDestinatarios.add(
                                    email.value!!.remetente
                                )


                                callLocaMailApiListarUsuarios(
                                    onSuccess = { listUsuarioRetornado ->

                                        if (listUsuarioRetornado != null) {
                                            for (usuario in listUsuarioRetornado) {
                                                if (todosDestinatarios.contains(usuario.email)) {

                                                    callLocaMailApiCriarAlteracao(
                                                        Alteracao(
                                                            alt_id_email = email.value!!.id_email,
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

                                for (destinatario in todosDestinatarios) {


                                    callLocaMailApiVerificarConvidadoExiste(
                                        destinatario,
                                        onSuccess = { verificaConvidadoExisteRetornado ->

                                            if (verificaConvidadoExisteRetornado != null) {
                                                val convidadoExistente =
                                                    verificaConvidadoExisteRetornado

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

                                            }

                                        },
                                        onError = {
                                            isError.value = true
                                            isLoading.value = false
                                        }
                                    )
                                }

                                if (bitmapList.isNotEmpty()) {
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

                                Toast.makeText(context, toastMessageMailSent, Toast.LENGTH_LONG)
                                    .show()
                                navController.popBackStack()
                            } else {
                                Toast.makeText(context, toastMessageMailDest, Toast.LENGTH_LONG)
                                    .show()
                            }

                        },
                        bitmapList = bitmapList,
                        isDraft = true,
                        onClickDeleteDraft = {
                            isLoading.value = true
                            navController.popBackStack()

                            callLocaMailApiExcluirAnexoPorIdEmail(
                                email.value!!.id_email,
                                onSuccess = {

                                },
                                onError = {
                                    isError.value = true
                                    isLoading.value = false
                                }
                            )

                            callLocaMailApiExcluirEmail(
                                email.value!!.id_email,
                                onSuccess = {
                                    isLoading.value = false
                                    Toast.makeText(
                                        context,
                                        toastMessageDraftDeleted,
                                        Toast.LENGTH_LONG
                                    )
                                        .show()
                                },
                                onError = {
                                    isError.value = true
                                    isLoading.value = false
                                }
                            )

                        })

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
                        onClickExpandCc = { expandedCc.value = !expandedCc.value },
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
}