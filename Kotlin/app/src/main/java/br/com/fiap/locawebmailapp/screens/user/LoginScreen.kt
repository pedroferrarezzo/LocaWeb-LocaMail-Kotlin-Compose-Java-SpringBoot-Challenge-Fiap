package br.com.fiap.llocalweb.screens

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.locawebmailapp.R
import br.com.fiap.locawebmailapp.components.general.ErrorComponent
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiAtualizaAutenticaUsuario
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiDesselecionarUsuarioSelecionadoAtual
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiRetornaUsarioPorEmail
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiSelecionarUsuario
import br.com.fiap.locawebmailapp.utils.checkInternetConnectivity
import br.com.fiap.locawebmailapp.utils.generateSha256
import br.com.fiap.locawebmailapp.utils.validateEmail
import br.com.fiap.locawebmailapp.utils.validatePassword

@Composable
fun LoginScreen(navController: NavController) {

    var email by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    var password by remember {
        mutableStateOf("")
    }

    val isErrorPassword = remember {
        mutableStateOf(false)
    }

    val isErrorEmail = remember {
        mutableStateOf(false)
    }
    val passwordVisibility = remember { mutableStateOf(false) }

    val toastMessageNotExistEmail = stringResource(id = R.string.toast_not_exist_email)
    val toastMessageIncorrectPassword = stringResource(id = R.string.toast_wrong_password)

    val isLoading = remember {
        mutableStateOf(false)
    }
    val isError = remember {
        mutableStateOf(false)
    }
    val toastMessageWait = stringResource(id = R.string.toast_api_wait)
    val isConnectedStatus = remember {
        mutableStateOf(checkInternetConnectivity(context))
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
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .width(280.dp)
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 25.dp),
                    text = stringResource(id = R.string.register_welcome_comeback),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.lcweb_gray_1),
                    lineHeight = 40.sp
                )
                Image(
                    painter = painterResource(id = R.drawable.authenticationimage),
                    contentDescription = stringResource(id = R.string.content_desc_welcome_image),
                    modifier = Modifier
                        .width(250.dp)
                        .height(250.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,

                    ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .padding(10.dp)
                            .width(330.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorResource(id = R.color.lcweb_red_1),
                            unfocusedBorderColor = colorResource(id = R.color.lcweb_gray_1),
                            unfocusedLabelColor = colorResource(id = R.color.lcweb_gray_1),
                            focusedLabelColor = colorResource(id = R.color.lcweb_red_1),
                            cursorColor = colorResource(id = R.color.lcweb_red_1),
                            focusedTextColor = colorResource(id = R.color.lcweb_gray_1),
                            unfocusedTextColor = colorResource(id = R.color.lcweb_gray_1),
                            errorPlaceholderColor = colorResource(id = R.color.lcweb_red_1),
                            errorBorderColor = colorResource(id = R.color.lcweb_red_1),
                            errorCursorColor = colorResource(id = R.color.lcweb_red_1),
                            errorLabelColor = colorResource(id = R.color.lcweb_red_1),
                            errorTextColor = colorResource(id = R.color.lcweb_red_1)
                        ),
                        value = email,
                        onValueChange = {
                            isErrorEmail.value = false
                            email = it
                        },
                        label = {
                            Text(
                                stringResource(id = R.string.register_screen_email)
                            )
                        },
                        placeholder = {
                            Text(stringResource(id = R.string.register_screen_insertmail))
                        },
                        textStyle = TextStyle(textDecoration = TextDecoration.None),
                        singleLine = true,
                        isError = isErrorEmail.value

                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .padding(10.dp)
                            .width(330.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorResource(id = R.color.lcweb_red_1),
                            unfocusedBorderColor = colorResource(id = R.color.lcweb_gray_1),
                            unfocusedLabelColor = colorResource(id = R.color.lcweb_gray_1),
                            focusedLabelColor = colorResource(id = R.color.lcweb_red_1),
                            cursorColor = colorResource(id = R.color.lcweb_red_1),
                            focusedTextColor = colorResource(id = R.color.lcweb_gray_1),
                            unfocusedTextColor = colorResource(id = R.color.lcweb_gray_1),
                            errorPlaceholderColor = colorResource(id = R.color.lcweb_red_1),
                            errorBorderColor = colorResource(id = R.color.lcweb_red_1),
                            errorCursorColor = colorResource(id = R.color.lcweb_red_1),
                            errorLabelColor = colorResource(id = R.color.lcweb_red_1),
                            focusedTrailingIconColor = colorResource(id = R.color.lcweb_gray_1),
                            unfocusedTrailingIconColor = colorResource(id = R.color.lcweb_gray_1),
                            errorTrailingIconColor = colorResource(id = R.color.lcweb_red_1),
                            errorTextColor = colorResource(id = R.color.lcweb_red_1)
                        ),
                        visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                        value = password,
                        onValueChange = {
                            isErrorPassword.value = false
                            password = it
                        },
                        trailingIcon = {
                            IconButton(onClick = {
                                passwordVisibility.value = !passwordVisibility.value
                            }) {
                                if (passwordVisibility.value) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.eye_slash_regular),
                                        contentDescription = stringResource(id = R.string.content_desc_eye_slash),
                                        modifier = Modifier.size(20.dp)
                                    )
                                } else {
                                    Icon(
                                        painter = painterResource(id = R.drawable.eye_regular),
                                        contentDescription = stringResource(id = R.string.content_desc_eye),
                                        Modifier.size(20.dp)
                                    )
                                }
                            }
                        },
                        label = {
                            Text(stringResource(id = R.string.register_screen_pass))
                        },
                        placeholder = {
                            Text(stringResource(id = R.string.register_screen_insertpass))
                        },
                        textStyle = TextStyle(textDecoration = TextDecoration.None),
                        singleLine = true,
                        isError = isErrorPassword.value
                    )
                    Text(
                        text = stringResource(id = R.string.register_forget_pass),
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(end = 35.dp),
                        color = colorResource(id = R.color.lcweb_red_1)
                    )
                }

                if (isErrorPassword.value || isErrorEmail.value) {
                    if (email == "") {
                        Text(
                            text = stringResource(id = R.string.user_email_required),
                            color = colorResource(id = R.color.lcweb_red_1)
                        )
                    } else if (password == "") {
                        Text(
                            text = stringResource(id = R.string.user_password_required),
                            color = colorResource(id = R.color.lcweb_red_1)
                        )
                    } else if (!validateEmail(email)) {
                        Text(
                            text = stringResource(id = R.string.user_email_format),
                            color = colorResource(id = R.color.lcweb_red_1),
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                    } else if (password.length < 8) {
                        Text(
                            text = stringResource(id = R.string.user_password_length),
                            color = colorResource(id = R.color.lcweb_red_1)
                        )
                    } else if (!validatePassword(password = password)) {
                        Text(
                            text = stringResource(id = R.string.user_password_format),
                            color = colorResource(id = R.color.lcweb_red_1),
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                        passwordVisibility.value = true
                    }
                }

                Button(
                    onClick = {
                        try {
                            isErrorEmail.value = if (email == "") true else false
                            isErrorPassword.value = if (password == "") true else false
                            isErrorEmail.value = !validateEmail(email)
                            isErrorPassword.value = !validatePassword(password = password)

                            if (!isErrorEmail.value && !isErrorPassword.value) {
                                isLoading.value = true
                                callLocaMailApiRetornaUsarioPorEmail(
                                    email = email,
                                    onSuccess = { usuarioRetornado ->
                                        val usuarioExistente = usuarioRetornado
                                        if (usuarioExistente != null) {
                                            if (usuarioExistente.senha == generateSha256(password)) {
                                                callLocaMailApiDesselecionarUsuarioSelecionadoAtual(
                                                    onSuccess = {
                                                        callLocaMailApiSelecionarUsuario(
                                                            id_usuario = usuarioExistente.id_usuario,
                                                            onSuccess = {
                                                                callLocaMailApiAtualizaAutenticaUsuario(
                                                                    id_usuario = usuarioExistente.id_usuario,
                                                                    autenticado = true,
                                                                    onSuccess = {
                                                                        isLoading.value = false
                                                                        navController.navigate("emailmainscreen") {
                                                                            popUpTo(navController.graph.id) {
                                                                                inclusive = true
                                                                            }
                                                                        }
                                                                    },
                                                                    onError = {error ->
                                                                        isError.value = true
                                                                        isLoading.value = false
                                                                    }
                                                                )
                                                            },
                                                            onError = {error ->
                                                                isError.value = true
                                                                isLoading.value = false
                                                            }
                                                        )
                                                    },
                                                    onError = {error ->
                                                        isError.value = true
                                                        isLoading.value = false
                                                    }
                                                )
                                            } else {
                                                isLoading.value = false
                                                isErrorPassword.value = true
                                                Toast.makeText(
                                                    context,
                                                    toastMessageIncorrectPassword,
                                                    Toast.LENGTH_LONG
                                                )
                                                    .show()
                                            }
                                        } else {
                                            isLoading.value = false
                                            isErrorEmail.value = true
                                            Toast.makeText(
                                                context,
                                                toastMessageNotExistEmail,
                                                Toast.LENGTH_LONG
                                            )
                                                .show()
                                        }
                                    },
                                    onError = { error ->
                                        isError.value = true
                                        isLoading.value = false
                                    }
                                )
                            }
                        } catch (t: Throwable) {
                            isError.value = true
                            isLoading.value = false
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 10.dp)
                        .width(330.dp)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.lcweb_red_1)),
                    shape = RoundedCornerShape(15.dp)
                ) {


                    Text(
                        text = stringResource(id = R.string.register_screen_login),
                        color = colorResource(id = R.color.white)
                    )
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 30.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.register_screen_question),
                        color = colorResource(id = R.color.lcweb_gray_1)
                    )
                    Text(
                        text = stringResource(id = R.string.register_register),
                        color = colorResource(id = R.color.lcweb_red_1),
                        modifier = Modifier.clickable(enabled = true, onClick = {
                            navController.navigate("signupscreen")
                        })
                    )
                }
            }
        }
    }
}