package br.com.fiap.locawebmailapp.screens.calendar

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import br.com.fiap.locawebmailapp.components.calendar.Calendar
import br.com.fiap.locawebmailapp.components.calendar.CardAgenda
import br.com.fiap.locawebmailapp.components.calendar.Day
import br.com.fiap.locawebmailapp.components.calendar.ExpandedShadowDropdown
import br.com.fiap.locawebmailapp.components.email.RowSearchBar
import br.com.fiap.locawebmailapp.components.general.ErrorComponent
import br.com.fiap.locawebmailapp.components.general.ModalNavDrawer
import br.com.fiap.locawebmailapp.components.general.ShadowBox
import br.com.fiap.locawebmailapp.model.Agenda
import br.com.fiap.locawebmailapp.model.EmailComAlteracao
import br.com.fiap.locawebmailapp.model.Pasta
import br.com.fiap.locawebmailapp.model.Usuario
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarAgendaPorDia
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarCorAgendaPorDia
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarPastasPorIdUsuario
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarUsuarioSelecionado
import br.com.fiap.locawebmailapp.utils.checkInternetConnectivity
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.daysOfWeek
import java.time.LocalDate
import java.time.YearMonth

@OptIn(ExperimentalMaterial3Api::class)
@Stable
@Composable
fun CalendarMainScreen(navController: NavController, data: LocalDate = LocalDate.now()) {
    val context = LocalContext.current

    val expanded = remember {
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

    var selectedDate by remember { mutableStateOf<LocalDate?>(data) }
    val listTask = remember {
        mutableStateOf(listOf<Agenda>())
    }

    val listColorTask = remember {
        mutableStateOf(listOf<Int>())
    }

    val textSearchBar = remember {
        mutableStateOf("")
    }

    val expandedPasta = remember {
        mutableStateOf(true)
    }

    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(100) }
    val endMonth = remember { currentMonth.plusMonths(100) }
    val daysOfWeek = remember { daysOfWeek() }
    val stateHorizontalCalendar = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first()
    )

    val selectedDrawer = remember {
        mutableStateOf("6")
    }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val listUsuariosNaoAutenticados = remember {
        mutableStateListOf<Usuario>()
    }

    val usuarioSelecionado = remember {
        mutableStateOf<Usuario?>(null)
    }

    val listPastaState = remember {
        mutableStateListOf<Pasta>()
    }

    LaunchedEffect(key1 = Unit) {

        callLocaMailApiListarUsuarioSelecionado(
            onSuccess = {
                usuarioSelecionadoRetornado ->

                if (usuarioSelecionadoRetornado != null) {
                    usuarioSelecionado.value = usuarioSelecionadoRetornado

                    callLocaMailApiListarPastasPorIdUsuario(
                        usuarioSelecionado.value!!.id_usuario,
                        onSuccess = { listPastaRetornado ->
                            listPastaState.addAll(listPastaRetornado!!)
                            isLoading.value = false
                        },
                        onError = { error ->
                            isError.value = true
                            isLoading.value = false
                        }
                    )

                    callLocaMailApiListarAgendaPorDia(
                        selectedDate.toString(),
                        usuarioSelecionado.value!!.id_usuario,
                        onSuccess = {
                                listAgendaPorDiaRetornado ->
                            isLoading.value = false

                            if (listAgendaPorDiaRetornado != null) {
                                listTask.value = listAgendaPorDiaRetornado

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

    }

    val onClickDay = remember {
        { day: CalendarDay ->
            selectedDate = day.date
            if (usuarioSelecionado.value != null) {
                isLoading.value = true
                callLocaMailApiListarAgendaPorDia(
                    selectedDate.toString(),
                    usuarioSelecionado.value!!.id_usuario,
                    onSuccess = {
                            listAgendaPorDiaRetornado ->

                        if (listAgendaPorDiaRetornado != null) {
                            listTask.value = listAgendaPorDiaRetornado
                            isLoading.value = false
                        }

                    },
                    onError = {
                        isError.value = true
                        isLoading.value = false
                    }
                )
            }
        }
    }

    val openDialogPastaCreator = remember {
        mutableStateOf(false)
    }

    val textPastaCreator = remember {
        mutableStateOf("")
    }


    val openDialogUserPicker = remember {
        mutableStateOf(false)
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
                isLoading = isLoading,
                isError = isError
            ) {

                Box(modifier = Modifier.fillMaxSize()) {
                    Column {

                        RowSearchBar<EmailComAlteracao>(
                            drawerState = drawerState,
                            scope = scope,
                            openDialogUserPicker = openDialogUserPicker,
                            textSearchBar = textSearchBar,
                            usuarioSelecionado = usuarioSelecionado,
                            placeholderTextFieldSearch = stringResource(id = R.string.calendar_main_searchbar),
                            navController = navController,
                            isError = isError,
                            isLoading = isLoading,
                            listUsuariosNaoAutenticados = listUsuariosNaoAutenticados
                        )

                        Calendar(
                            stateHorizontalCalendar = stateHorizontalCalendar,
                            daysOfWeek = daysOfWeek,
                            dayContent = { day ->

                                if (usuarioSelecionado.value != null) {
//                                    callLocaMailApiListarCorAgendaPorDia(
//                                        day.date.toString(),
//                                        usuarioSelecionado.value!!.id_usuario,
//                                        onSuccess = {
//                                                listColorTaskRetornado ->
//
//                                            if (listColorTaskRetornado != null) {
//                                                listColorTask.value = listColorTaskRetornado
//                                            }
//                                        },
//                                        onError = {
//                                            isError.value = true
//                                            isLoading.value = false
//
//                                        }
//                                    )
                                }
                                Day(
                                    day = day,
                                    selectedDate = selectedDate == day.date,
                                    listColorTask = listColorTask.value,
                                    onClick = onClickDay
                                )
                            })

                        LazyColumn(
                            reverseLayout = false,
                            content = {
                                items(
                                    listTask.value,
                                    key = {
                                        it.id_agenda
                                    }
                                ) {
                                    if (
                                        it.nome.contains(textSearchBar.value, ignoreCase = true) ||
                                        it.descritivo.contains(textSearchBar.value, ignoreCase = true)
                                    ) {
                                        CardAgenda(
                                            selectedDate = selectedDate,
                                            isTask = it.tarefa,
                                            agenda = it,
                                            navController = navController,
                                            timePickerState = rememberTimePickerState()
                                        )
                                    }
                                }
                            }
                        )
                    }


                    ShadowBox(expanded = expanded)

                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.BottomEnd)
                    ) {
                        ExpandedShadowDropdown(navController = navController, expanded = expanded)

                        Button(
                            onClick = { expanded.value = true },
                            elevation = ButtonDefaults.buttonElevation(4.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.lcweb_red_1)),
                            modifier = Modifier
                                .width(70.dp)
                                .height(70.dp)
                                .padding(vertical = 5.dp),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = stringResource(id = R.string.content_desc_add),
                                tint = colorResource(id = R.color.white)
                            )
                        }
                    }
                }
            }
        }
    }
}
