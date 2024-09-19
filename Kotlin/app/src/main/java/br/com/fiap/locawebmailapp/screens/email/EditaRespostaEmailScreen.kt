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
import br.com.fiap.locawebmailapp.model.AnexoRespostaEmail
import br.com.fiap.locawebmailapp.model.Convidado
import br.com.fiap.locawebmailapp.model.Email
import br.com.fiap.locawebmailapp.model.RespostaEmail
import br.com.fiap.locawebmailapp.model.Usuario
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiAtualizarLidoPorIdEmailEIdusuario
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiAtualizarRespostaEmail
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiCriarAlteracao
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiCriarAnexoRespostaEmail
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiCriarConvidado
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiExcluirAnexoPorIdRespostaEmail
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiExcluirRespostaEmail
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarAltIdUsuarioPorIdEmail
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarAnexosArrayBytePorIdRespostaEmail
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarEmailPorId
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarRespostaEmailPorIdRespostaEmail
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

@Composable
fun EditaRespostaEmailScreen(navController: NavController, idRespostaEmail: Long) {

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

    val respostaEmail = remember {
        mutableStateOf<RespostaEmail?>(null)
    }

    val emailResponder = remember {
        mutableStateOf<Email>(Email())
    }

    val usuarioSelecionado = remember {
        mutableStateOf<Usuario?>(null)
    }

    var alteracoesEmailAltIdUsuarioList: List<Long> = listOf()

    var anexoRespostaEmailArrayByteList: List<ByteArray> = listOf()

    val bitmapList = remember {
        mutableStateListOf<Bitmap>()
    }

    LaunchedEffect(key1 = Unit) {
        callLocaMailApiListarRespostaEmailPorIdRespostaEmail(
            idRespostaEmail,
            onSuccess = { respostaEmailRetornado ->

                if (respostaEmailRetornado != null) {
                    isLoading.value = false
                    respostaEmail.value = respostaEmailRetornado

                    callLocaMailApiListarEmailPorId(
                        respostaEmail.value!!.id_email,
                        onSuccess = { emailRetornado ->
                            if (emailRetornado != null) {
                                emailResponder.value = emailRetornado
                            }
                        },
                        onError = {
                            isError.value = true
                            isLoading.value = false
                        }
                    )

                    callLocaMailApiListarAltIdUsuarioPorIdEmail(
                        respostaEmail.value!!.id_email,
                        onSuccess = { listAltIdUsuarioRetornado ->

                            if (listAltIdUsuarioRetornado != null) {
                                alteracoesEmailAltIdUsuarioList = listAltIdUsuarioRetornado
                            }
                        },
                        onError = {
                            isError.value = true
                            isLoading.value = false
                        }
                    )

                    callLocaMailApiListarAnexosArrayBytePorIdRespostaEmail(
                        respostaEmail.value!!.id_resposta_email,
                        onSuccess = { listArrayByteRetornado ->
                            if (listArrayByteRetornado != null) {
                                anexoRespostaEmailArrayByteList = listArrayByteRetornado
                                bitmapList.addAll(anexoRespostaEmailArrayByteList.map {
                                    byteArrayToBitmap(it)
                                })
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

        callLocaMailApiListarUsuarioSelecionado(
            onSuccess = { usuarioSelecionadoRetornado ->

                if (usuarioSelecionadoRetornado != null) {
                    usuarioSelecionado.value = usuarioSelecionadoRetornado
                }

            },
            onError = {
                isError.value = true
                isLoading.value = false
            }
        )


    }


    val anexoRespostaEmail = AnexoRespostaEmail()
    val convidado = Convidado()

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

    val toastMessageDraftMailRespSaved = stringResource(id = R.string.toast_maildraftresp_saved)
    val toastMessageDraftMailRespSent = stringResource(id = R.string.toast_maildraft_sent)
    val toastMessageMailDest = stringResource(id = R.string.toast_mail_dest)
    val toastMessageDraftMailDeleted = stringResource(id = R.string.toast_maildraftresp_delete)


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
            if (respostaEmail.value != null) {
                val ccos = remember {
                    if (respostaEmail.value!!.cco.equals("")) mutableStateListOf<String>() else stringParaLista(
                        respostaEmail.value!!.cco
                    ).toMutableStateList()
                }

                val destinatarios =
                    remember {
                        if (respostaEmail.value!!.destinatario.equals("")) mutableStateListOf<String>() else stringParaLista(
                            respostaEmail.value!!.destinatario
                        ).toMutableStateList()
                    }

                val ccs =
                    remember {
                        if (respostaEmail.value!!.cc.equals("")) mutableStateListOf<String>() else stringParaLista(
                            respostaEmail.value!!.cc
                        ).toMutableStateList()
                    }
                val assuntoText = remember {
                    mutableStateOf(respostaEmail.value!!.assunto)
                }

                val corpoMailText = remember {
                    mutableStateOf(respostaEmail.value!!.corpo)
                }

                Column(
                    modifier = Modifier.fillMaxSize()
                ) {

                    RowCrudMail(
                        onClickBack = {
                            isLoading.value = true
                            if (listaParaString(destinatarios) != respostaEmail.value!!.destinatario
                                || listaParaString(ccos) != respostaEmail.value!!.cco
                                || listaParaString(ccs) != respostaEmail.value!!.cc
                                || assuntoText.value != respostaEmail.value!!.assunto
                                || corpoMailText.value != respostaEmail.value!!.corpo
                                || bitmapList.isNotEmpty()
                                || bitmapList.isEmpty()
                                || anexoRespostaEmailArrayByteList.isNotEmpty()
                            ) {
                                respostaEmail.value!!.destinatario =
                                    if (destinatarios.isNotEmpty()) listaParaString(destinatarios) else ""
                                respostaEmail.value!!.cc =
                                    if (ccs.isNotEmpty()) listaParaString(ccs) else ""
                                respostaEmail.value!!.cco =
                                    if (ccos.isNotEmpty()) listaParaString(ccos) else ""
                                respostaEmail.value!!.assunto = assuntoText.value
                                respostaEmail.value!!.corpo = corpoMailText.value
                                respostaEmail.value!!.enviado = false
                                respostaEmail.value!!.editavel = true
                                anexoRespostaEmail.id_resposta_email =
                                    respostaEmail.value!!.id_resposta_email

                                callLocaMailApiAtualizarRespostaEmail(
                                    respostaEmail.value!!,
                                    onSuccess = {
                                        isLoading.value = false

                                        callLocaMailApiExcluirAnexoPorIdRespostaEmail(
                                            anexoRespostaEmail.id_resposta_email,
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
                                                anexoRespostaEmail.anexo = byteArray

                                                callLocaMailApiCriarAnexoRespostaEmail(
                                                    anexoRespostaEmail,
                                                    onSuccess = {

                                                    },
                                                    onError = {
                                                        isError.value = true
                                                        isLoading.value = false
                                                    }
                                                )
                                            }
                                        }

                                        Toast.makeText(
                                            context,
                                            toastMessageDraftMailRespSaved,
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
                                navController.popBackStack()
                            }

                        },
                        onClickPaperClip = { launcher.launch("image/*") },
                        onClickSend = {
                            if (destinatarios.isNotEmpty() || ccos.isNotEmpty() || ccs.isNotEmpty()) {
                                isLoading.value = true
                                respostaEmail.value!!.destinatario =
                                    if (destinatarios.isNotEmpty()) listaParaString(destinatarios) else ""
                                respostaEmail.value!!.cc =
                                    if (ccs.isNotEmpty()) listaParaString(ccs) else ""
                                respostaEmail.value!!.cco =
                                    if (ccos.isNotEmpty()) listaParaString(ccos) else ""
                                respostaEmail.value!!.assunto = assuntoText.value
                                respostaEmail.value!!.corpo = corpoMailText.value
                                respostaEmail.value!!.enviado = true
                                respostaEmail.value!!.editavel = false
                                respostaEmail.value!!.data = "${LocalDate.now()}"
                                respostaEmail.value!!.horario =
                                    LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
                                anexoRespostaEmail.id_resposta_email =
                                    respostaEmail.value!!.id_resposta_email


                                callLocaMailApiAtualizarRespostaEmail(
                                    respostaEmail.value!!,
                                    onSuccess = {
                                        isLoading.value = false
                                        callLocaMailApiExcluirAnexoPorIdRespostaEmail(
                                            anexoRespostaEmail.id_resposta_email,
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
                                                anexoRespostaEmail.anexo = byteArray

                                                callLocaMailApiCriarAnexoRespostaEmail(
                                                    anexoRespostaEmail,
                                                    onSuccess = {

                                                    },
                                                    onError = {
                                                        isError.value = true
                                                        isLoading.value = false
                                                    }
                                                )
                                            }
                                        }

                                        val todosDestinatarios = arrayListOf<String>()
                                        todosDestinatarios.addAll(destinatarios)
                                        todosDestinatarios.addAll(ccos)
                                        todosDestinatarios.addAll(ccs)

                                        for (destinatario in todosDestinatarios) {
                                            callLocaMailApiVerificarConvidadoExiste(
                                                destinatario,
                                                onSuccess = { convidadoExisteRetornado ->

                                                    if (convidadoExisteRetornado != null) {
                                                        val convidadoExistente = convidadoExisteRetornado

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

                                        callLocaMailApiListarUsuarios(
                                            onSuccess = { usuariosRetornados ->
                                                if (usuariosRetornados != null) {
                                                    for (usuario in usuariosRetornados) {
                                                        if (todosDestinatarios.contains(usuario.email) && !alteracoesEmailAltIdUsuarioList.contains(
                                                                usuario.id_usuario
                                                            )
                                                        ) {
                                                            callLocaMailApiCriarAlteracao(
                                                                Alteracao(
                                                                    alt_id_email = respostaEmail.value!!.id_email,
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

                                                        if (usuarioSelecionado.value != null) {
                                                            if (todosDestinatarios.contains(usuario.email) && alteracoesEmailAltIdUsuarioList.contains(
                                                                    usuario.id_usuario
                                                                ) && usuario.id_usuario != usuarioSelecionado.value!!.id_usuario
                                                            ) {

                                                                callLocaMailApiAtualizarLidoPorIdEmailEIdusuario(
                                                                    false,
                                                                    id_email = respostaEmail.value!!.id_email,
                                                                    id_usuario = usuario.id_usuario,
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
                                                }
                                            },
                                            onError = {
                                                isError.value = true
                                                isLoading.value = false
                                            }
                                        )
                                        Toast.makeText(
                                            context,
                                            toastMessageDraftMailRespSent,
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
                        bitmapList = bitmapList,
                        isDraft = true,
                        onClickDeleteDraft = {
                            isLoading.value = true
                            callLocaMailApiExcluirAnexoPorIdRespostaEmail(
                                respostaEmail.value!!.id_resposta_email,
                                onSuccess = {

                                },
                                onError = {
                                    isError.value = true
                                    isLoading.value = false
                                }
                            )

                            callLocaMailApiExcluirRespostaEmail(
                                respostaEmail.value!!.id_resposta_email,
                                onSuccess = {
                                    isLoading.value = false
                                    Toast.makeText(
                                        context,
                                        toastMessageDraftMailDeleted,
                                        Toast.LENGTH_LONG
                                    ).show()
                                    navController.popBackStack()
                                },
                                onError = {
                                    isError.value = true
                                    isLoading.value = false
                                }
                            )
                        }
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
                            if (!stringParaLista(emailResponder.value.destinatario).contains(it) && !emailResponder.value.remetente.equals(
                                    it
                                )
                            ) {
                                destinatarios.remove(it)
                            }
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
                        },
                        isRespostaEmail = true,
                        showClearButtonAppear = {
                            (!stringParaLista(emailResponder.value.destinatario).contains(it) && !emailResponder.value.remetente.equals(
                                it
                            ))
                        }
                    )
                }
            }
        }

    }
}