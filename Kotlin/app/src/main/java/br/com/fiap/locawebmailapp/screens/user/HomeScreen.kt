package br.com.fiap.llocalweb.screens


import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.locawebmailapp.R
import br.com.fiap.locawebmailapp.components.general.ErrorComponent
import br.com.fiap.locawebmailapp.database.repository.UsuarioRepository
import br.com.fiap.locawebmailapp.model.Usuario
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiRetornaUsarioPorEmail
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApicriarUsuario
import br.com.fiap.locawebmailapp.utils.checkInternetConnectivity
import br.com.fiap.locawebmailapp.utils.generateSha256
import kotlinx.coroutines.launch
import java.io.IOException

@Composable
fun HomeScreen(navController: NavController) {
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

    val emailExistente = remember {
        mutableStateOf<Usuario?>(null)
    }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {

        try {
            isConnectedStatus.value = checkInternetConnectivity(context)
            if (!isConnectedStatus.value) {
                isLoading.value = false
            } else {
                emailExistente.value = callLocaMailApiRetornaUsarioPorEmail("dev@locaweb.com.br")
                if (emailExistente.value != null) {
                    navController.navigate("loginscreen") {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                }
                else {
                    isLoading.value = false
                }
            }
        }
        catch (t: IOException) {
            isLoading.value = false
        }
        catch (t: Throwable) {
            Log.i("erro", t.stackTraceToString())
            isError.value = true
            isLoading.value = false
        }
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
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .padding(top = 150.dp),
                    painter = painterResource(id = R.drawable.locaweb),
                    contentDescription = stringResource(id = R.string.content_desc_lcweb_logo)
                )
                Column {
                    Text(
                        text = stringResource(id = R.string.register_welcome_title),
                        modifier = Modifier
                            .padding(30.dp)
                            .align(Alignment.CenterHorizontally),
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.lcweb_gray_1)
                    )

                    Button(
                        onClick = {

                            isLoading.value = true
                            val senha = generateSha256("@quweuqweusudausdu@123323Sdsdiadi1j23asd123S\$\$\$%232@#1skls")

                            val usuario = Usuario(
                                nome = "Dev",
                                email = "dev@locaweb.com.br",
                                senha = senha,
                                autenticado = false,
                                selected_user = false
                            )

                            coroutineScope.launch {
                                callLocaMailApicriarUsuario(usuario)
                                isLoading.value = false
                                navController.navigate("loginscreen")
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(10.dp)
                            .width(330.dp)
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.lcweb_red_1)),
                        shape = RoundedCornerShape(15.dp)
                    ) {
                        Text(
                            text = stringResource(
                                id = R.string.register_screen_advance),
                            color = colorResource(id = R.color.white))
                    }
                }
            }

        }
    }
}