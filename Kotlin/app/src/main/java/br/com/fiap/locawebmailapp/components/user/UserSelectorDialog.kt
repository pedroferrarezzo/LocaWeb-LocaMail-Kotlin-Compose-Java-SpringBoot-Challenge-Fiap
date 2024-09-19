package br.com.fiap.locawebmailapp.components.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import br.com.fiap.locawebmailapp.R
import br.com.fiap.locawebmailapp.model.Usuario
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiAtualizaAutenticaUsuario
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiDesselecionarUsuarioSelecionadoAtual
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarUsuariosAutenticados
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiSelecionarUsuario
import br.com.fiap.locawebmailapp.utils.byteArrayToBitmap

@Composable
fun <T> UserSelectorDalog(
    openDialogUserPicker: MutableState<Boolean>,
    usuarioSelecionado: MutableState<Usuario?>,
    stateList: SnapshotStateList<T> = mutableStateListOf(),
    applyStateList: () -> Unit = {},
    navController: NavController,
    isLoading: MutableState<Boolean>,
    isError: MutableState<Boolean>,
    listUsuariosNaoAutenticados: SnapshotStateList<Usuario>
) {
    if (openDialogUserPicker.value) {
        Dialog(onDismissRequest = {
            openDialogUserPicker.value = false
            listUsuariosNaoAutenticados.clear()
        }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(5.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSystemInDarkTheme()) {
                        colorResource(id = R.color.lcweb_gray_5)
                    } else {
                        colorResource(id = R.color.white)
                    }
                ),
                elevation = CardDefaults.cardElevation(10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start,
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                isLoading.value = true
                                openDialogUserPicker.value = false
                                callLocaMailApiAtualizaAutenticaUsuario(
                                    usuarioSelecionado.value!!.id_usuario,
                                    false,
                                    onSuccess = {
                                        callLocaMailApiDesselecionarUsuarioSelecionadoAtual(
                                            onSuccess = {
                                                callLocaMailApiListarUsuariosAutenticados(
                                                    onSuccess = { listaUsuarioRetornado ->

                                                        val usuariosAutenticados =
                                                            listaUsuarioRetornado

                                                        if (usuariosAutenticados!!.isNotEmpty()) {

                                                            callLocaMailApiSelecionarUsuario(
                                                                usuariosAutenticados.last().id_usuario,
                                                                onSuccess = {
                                                                    isLoading.value = false
                                                                    navController.navigate("emailmainscreen") {
                                                                        popUpTo(navController.graph.id) {
                                                                            inclusive = true
                                                                        }
                                                                    }
                                                                },
                                                                onError = { error ->
                                                                    isLoading.value = false
                                                                    isError.value = true

                                                                }
                                                            )
                                                        } else {
                                                            isLoading.value = false
                                                            openDialogUserPicker.value = false
                                                            navController.navigate("loginscreen") {
                                                                popUpTo(navController.graph.id) {
                                                                    inclusive = true
                                                                }
                                                            }
                                                        }
                                                    },
                                                    onError = { error ->
                                                        isLoading.value = false
                                                        isError.value = true

                                                    }
                                                )
                                            },
                                            onError = { error ->
                                                isLoading.value = false
                                                isError.value = true
                                            }
                                        )
                                    },
                                    onError = { error ->
                                        isLoading.value = false
                                        isError.value = true

                                    }
                                )
                            },
                            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.lcweb_red_1)),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(5.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.user_dialog_exit),
                                modifier = Modifier.align(Alignment.CenterVertically),
                                color = colorResource(id = R.color.white),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Button(
                            onClick = {
                                openDialogUserPicker.value = false
                                navController.navigate("signupscreen")
                            },
                            modifier = Modifier
                                .padding(horizontal = 5.dp),
                            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.lcweb_red_1)),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(5.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.user_dialog_create),
                                modifier = Modifier.align(Alignment.CenterVertically),
                                color = colorResource(id = R.color.white),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    HorizontalDivider(
                        color = colorResource(id = R.color.lcweb_red_1)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth()
                    ) {

                        if (usuarioSelecionado.value != null) {
                            Image(
                                bitmap = byteArrayToBitmap(usuarioSelecionado.value!!.profile_image).asImageBitmap(),
                                contentDescription = stringResource(id = R.string.content_desc_iconregister),
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(shape = CircleShape)
                            )

                            Column {
                                Text(
                                    text = if (usuarioSelecionado.value!!.nome.length > 25) {
                                        "${
                                            usuarioSelecionado.value!!.nome.take(
                                                25
                                            )
                                        }..."
                                    } else {
                                        usuarioSelecionado.value!!.nome
                                    },
                                    color = colorResource(id = R.color.lcweb_gray_1)
                                )
                                Text(
                                    text = if (usuarioSelecionado.value!!.email.length > 25) {
                                        "${
                                            usuarioSelecionado.value!!.email.take(
                                                25
                                            )
                                        }..."
                                    } else {
                                        usuarioSelecionado.value!!.email
                                    },
                                    color = colorResource(id = R.color.lcweb_gray_1)
                                )
                            }

                        }
                    }

                    HorizontalDivider(
                        color = colorResource(id = R.color.lcweb_red_1)
                    )



                    if (!listUsuariosNaoAutenticados.isEmpty()) {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(listUsuariosNaoAutenticados) {
                                Button(
                                    onClick = {
                                        openDialogUserPicker.value = false

                                        callLocaMailApiListarUsuariosAutenticados(
                                            onSuccess = { listUsuarioRetornado ->

                                                val usuariosAutenticados = listUsuarioRetornado

                                                if (usuariosAutenticados!!.any { usuarioAutenticado ->
                                                        usuarioAutenticado.email == it.email
                                                    }) {

                                                    val usuario = it


                                                    callLocaMailApiDesselecionarUsuarioSelecionadoAtual(
                                                        onSuccess = {

                                                            callLocaMailApiSelecionarUsuario(
                                                                usuario.id_usuario,
                                                                onSuccess = {
                                                                    usuarioSelecionado.value =
                                                                        usuario
                                                                    stateList.clear()
                                                                    applyStateList()
                                                                    navController.navigate("emailmainscreen") {
                                                                        popUpTo(navController.graph.id) {
                                                                            inclusive = true
                                                                        }
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
                                                } else {
                                                    navController.navigate("loginscreen")
                                                }


                                            },
                                            onError = { error ->
                                                isLoading.value = false
                                                isError.value = true
                                            }
                                        )


                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Transparent,
                                        contentColor = colorResource(id = R.color.lcweb_gray_1)
                                    ),
                                    shape = RectangleShape,
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
                                            .padding(horizontal = 5.dp)
                                            .fillMaxWidth()
                                    ) {
                                        Image(
                                            bitmap = byteArrayToBitmap(it.profile_image).asImageBitmap(),
                                            contentDescription = stringResource(id = R.string.content_desc_iconregister),
                                            modifier = Modifier
                                                .size(50.dp)
                                                .clip(shape = CircleShape)
                                        )

                                        Column {
                                            Text(
                                                text = if (it.nome.length > 25) {
                                                    "${
                                                        it.nome.take(
                                                            25
                                                        )
                                                    }..."
                                                } else {
                                                    it.nome
                                                },
                                                color = colorResource(id = R.color.lcweb_gray_1)
                                            )
                                            Text(
                                                text = if (it.email.length > 25) {
                                                    "${
                                                        it.email.take(
                                                            25
                                                        )
                                                    }..."
                                                } else {
                                                    it.email
                                                },
                                                color = colorResource(id = R.color.lcweb_gray_1)
                                            )
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}