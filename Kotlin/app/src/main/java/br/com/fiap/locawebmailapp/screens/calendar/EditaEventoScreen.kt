package br.com.fiap.locawebmailapp.screens.calendar

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.fiap.locawebmailapp.R
import br.com.fiap.locawebmailapp.components.calendar.ColorSelectorDalog
import br.com.fiap.locawebmailapp.components.calendar.DateSelectorDialog
import br.com.fiap.locawebmailapp.components.calendar.PeopleSelectorDalog
import br.com.fiap.locawebmailapp.components.calendar.RepeatSelectorDialog
import br.com.fiap.locawebmailapp.components.calendar.RowTwoIconCondition
import br.com.fiap.locawebmailapp.components.calendar.TextFieldCalendar
import br.com.fiap.locawebmailapp.components.calendar.TimeSelectorDialog
import br.com.fiap.locawebmailapp.components.general.ErrorComponent
import br.com.fiap.locawebmailapp.model.Agenda
import br.com.fiap.locawebmailapp.model.AgendaConvidado
import br.com.fiap.locawebmailapp.model.Convidado
import br.com.fiap.locawebmailapp.model.Usuario
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiAtualizaAgenda
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiAtualizaOpcaoRepeticaoPorGrupoRepeticao
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiAtualizaOpcaoRepeticaoPorIdAgenda
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiCriarAgenda
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiCriarAgendaConvidado
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiExcluiAgenda
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiExcluirPorGrupoRepeticao
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiExcluirPorGrupoRepeticaoExcetoData
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiExcluirPorGrupoRepeticaoExcetoIdAgenda
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiExcluirPorIdAgenda
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiExcluirPorIdAgendaEIdConvidado
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarAgendaPorId
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarConvidado
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarIdConvidadoPorAgenda
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarUsuarioSelecionado
import br.com.fiap.locawebmailapp.utils.checkInternetConnectivity
import br.com.fiap.locawebmailapp.utils.convertTo12Hours
import br.com.fiap.locawebmailapp.utils.localDateToMillis
import br.com.fiap.locawebmailapp.utils.returnColor
import br.com.fiap.locawebmailapp.utils.returnHourAndMinuteSeparate
import br.com.fiap.locawebmailapp.utils.returnListConvidado
import br.com.fiap.locawebmailapp.utils.returnOneMonthFromDate
import br.com.fiap.locawebmailapp.utils.returnStringRepeatOption
import br.com.fiap.locawebmailapp.utils.stringToDate
import br.com.fiap.locawebmailapp.utils.stringToLocalDate
import br.com.fiap.locawebmailapp.utils.validateIfAllDay
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.TimeZone

@Stable
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditaEventoScreen(navController: NavController, id_agenda: Int) {

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

    val listTodoConvidado = remember {
        mutableStateOf(listOf<Convidado>())
    }

    val listConvidadoText = remember {
        mutableStateOf("")
    }

    val usuarioSelecionado = remember {
        mutableStateOf<Usuario?>(null)
    }

    val agenda = remember {
        mutableStateOf<Agenda?>(null)
    }

    val listIdConvidado = remember {
        mutableStateOf(listOf<Long>())
    }

    val listConvidadoSelected = remember {
        mutableStateListOf<Convidado>()
    }

    val taskTitle = remember {
        mutableStateOf("")
    }
    val taskDescription = remember {
        mutableStateOf("")
    }
    val allDay = remember {
        mutableStateOf(false)
    }

    val selectedDate = remember {
        mutableStateOf("")
    }
    val selectedRepeat = remember { mutableStateOf(-1) }
    val selectedColor = remember { mutableStateOf(-1) }


    LaunchedEffect(key1 = Unit) {

        callLocaMailApiListarUsuarioSelecionado(
            onSuccess = { usuarioSelecionadoRetornado ->

                if (usuarioSelecionadoRetornado != null) {
                    usuarioSelecionado.value = usuarioSelecionadoRetornado
                }

            },
            onError = {
                isLoading.value = false
                isError.value = true
            }
        )

        callLocaMailApiListarAgendaPorId(
            id_agenda = id_agenda.toLong(),
            onSuccess = { agendaRetornada ->
                if (agendaRetornada != null) {
                    isLoading.value = false
                    agenda.value = agendaRetornada
                    taskTitle.value = agenda.value!!.nome
                    taskDescription.value = agenda.value!!.descritivo
                    allDay.value = validateIfAllDay(agenda.value!!.horario)
                    selectedDate.value = stringToDate(agenda.value!!.data)
                    selectedRepeat.value = agenda.value!!.repeticao
                    selectedColor.value = agenda.value!!.cor

                    callLocaMailApiListarIdConvidadoPorAgenda(
                        agenda.value!!.id_agenda,
                        onSuccess = { listIdConvidadoRetornado ->

                            if (listIdConvidadoRetornado != null) {
                                listIdConvidado.value = listIdConvidadoRetornado

                                returnListConvidado(
                                    listIdConvidado = listIdConvidado.value,
                                    isLoading = isLoading,
                                    isError = isError,
                                    listConvidadoSelected
                                )


                            }

                        },
                        onError = {
                            isLoading.value = false
                            isError.value = true
                        }
                    )
                }
            },
            onError = {
                isLoading.value = false
                isError.value = true
            }
        )

        callLocaMailApiListarConvidado(
            onSuccess = { listConvidadoRetornado ->

                if (listConvidadoRetornado != null) {
                    listTodoConvidado.value = listConvidadoRetornado
                }
            },
            onError = {
                isLoading.value = false
                isError.value = true
            }
        )
    }


    val agendaConvidado = AgendaConvidado()


    val isEdit = remember {
        mutableStateOf(false)
    }

    val showTimePicker = remember { mutableStateOf(false) }


    val openDialogDatePicker = remember { mutableStateOf(false) }
    val timezoneFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US).apply {
        timeZone = TimeZone.getTimeZone("GMT")
    }

    val openDialogColorPicker = remember { mutableStateOf(false) }


    val openDialogRepeatPicker = remember { mutableStateOf(false) }


    val isErrorTitle = remember { mutableStateOf(false) }

    val openDialogPeoplePicker = remember { mutableStateOf(false) }


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

            if (agenda.value != null) {
                val timePickerState = rememberTimePickerState(
                    initialHour = if (agenda.value != null && agenda.value!!.horario != "1") returnHourAndMinuteSeparate(
                        agenda.value!!.horario
                    ).first() else LocalDateTime.now().hour,
                    initialMinute = if (agenda.value != null && agenda.value!!.horario != "1") returnHourAndMinuteSeparate(
                        agenda.value!!.horario
                    ).last() else LocalDateTime.now().minute
                )
                val timeShow = remember {
                    mutableStateOf(
                        if (timePickerState.is24hour) agenda.value!!.horario else convertTo12Hours(
                            agenda.value!!.horario
                        )
                    )
                }

                val time = remember {
                    mutableStateOf(timeShow.value)
                }

                val initialDate = if (agenda.value != null) agenda.value!!.data else ""

                val datePickerState = rememberDatePickerState(
                    initialDisplayMode = DisplayMode.Picker,
                    initialSelectedDateMillis = if (agenda.value != null) localDateToMillis(
                        stringToLocalDate(
                            agenda.value!!.data
                        )
                    ) else 0
                )

                val millisToLocalDate = datePickerState.selectedDateMillis?.let {
                    stringToLocalDate(timezoneFormatter.format(it))
                }

                Box() {
                    Column {
                        RowTwoIconCondition(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            onClickFirstButton = {
                                val previousBackStackEntry = navController.previousBackStackEntry
                                if (previousBackStackEntry != null) {
                                    previousBackStackEntry.savedStateHandle.set(
                                        "data",
                                        agenda.value!!.data
                                    )
                                }
                                navController.popBackStack()
                            },
                            onClickSecondButton = {
                                isEdit.value = true
                            },
                            iconFirstButton = Icons.Filled.Close,
                            iconSecondButton = Icons.Filled.Edit,
                            descriptionFirstButton = "",
                            descriptionSecondButton = "",
                            textSecondButtonText = stringResource(id = R.string.calendar_update_button),
                            onClickSecondButtonText = {
                                if (taskTitle.value.isEmpty()) {
                                    isErrorTitle.value = true

                                } else {
                                    isLoading.value = true
                                    agenda.value!!.nome = taskTitle.value
                                    agenda.value!!.descritivo = taskDescription.value
                                    agenda.value!!.horario = time.value
                                    if (time.value == "Todo dia") {
                                        agenda.value!!.horario = "1"
                                    }
                                    agenda.value!!.cor = selectedColor.value
                                    agenda.value!!.data =
                                        if (millisToLocalDate.toString()
                                                .equals("null")
                                        ) LocalDate.now()
                                            .toString() else millisToLocalDate!!.toString()

                                    if (selectedRepeat.value == agenda.value!!.repeticao) {

                                        agenda.value!!.repeticao = selectedRepeat.value
                                        agendaConvidado.id_agenda = agenda.value!!.id_agenda

                                        callLocaMailApiAtualizaAgenda(
                                            agenda.value!!,
                                            onSuccess = {

                                                for (convidado in listConvidadoSelected) {
                                                    if (!(listIdConvidado.value.contains(convidado.id_convidado))) {
                                                        agendaConvidado.id_convidado =
                                                            convidado.id_convidado

                                                        callLocaMailApiCriarAgendaConvidado(
                                                            agendaConvidado,
                                                            onSuccess = {

                                                            },
                                                            onError = {
                                                                isLoading.value = false
                                                                isError.value = true
                                                            }
                                                        )
                                                    }
                                                }

                                                for (id in listIdConvidado.value) {
                                                    if (!(listConvidadoSelected.any {
                                                            it.id_convidado == id
                                                        })) {

                                                        callLocaMailApiExcluirPorIdAgendaEIdConvidado(
                                                            agenda.value!!.id_agenda,
                                                            id,
                                                            onSuccess = {

                                                            },
                                                            onError = {
                                                                isLoading.value = false
                                                                isError.value = true
                                                            }
                                                        )
                                                    }
                                                }
                                            },
                                            onError = {
                                                isLoading.value = false
                                                isError.value = true

                                            }
                                        )
                                    } else if (agenda.value!!.repeticao == 2 && selectedDate.value != stringToDate(
                                            initialDate
                                        )
                                    ) {

                                        callLocaMailApiExcluirPorGrupoRepeticao(
                                            agenda.value!!.grupo_repeticao,
                                            onSuccess = {

                                            },
                                            onError = {
                                                isLoading.value = false
                                                isError.value = true
                                            }
                                        )

                                        for (day in returnOneMonthFromDate(agenda.value!!.data)) {
                                            agenda.value!!.id_agenda = 0
                                            agenda.value!!.data = day
                                            agendaConvidado.grupo_repeticao =
                                                agenda.value!!.grupo_repeticao

                                            callLocaMailApiCriarAgenda(
                                                agenda.value!!,
                                                onSuccess = { agendaRetornada ->
                                                    if (agendaRetornada != null) {
                                                        agendaConvidado.id_agenda =
                                                            agendaRetornada.id_agenda

                                                        for (convidado in listConvidadoSelected) {
                                                            agendaConvidado.id_convidado =
                                                                convidado.id_convidado

                                                            callLocaMailApiCriarAgendaConvidado(
                                                                agendaConvidado,
                                                                onSuccess = {

                                                                },
                                                                onError = {
                                                                    isLoading.value = false
                                                                    isError.value = true
                                                                }
                                                            )
                                                        }
                                                    }
                                                },
                                                onError = {
                                                    isLoading.value = false
                                                    isError.value = true
                                                }
                                            )
                                        }
                                    } else if (selectedRepeat.value == 1 && agenda.value!!.repeticao == 2) {


                                        callLocaMailApiExcluirPorGrupoRepeticaoExcetoIdAgenda(
                                            agenda.value!!.grupo_repeticao,
                                            agenda.value!!.id_agenda,
                                            onSuccess = {

                                            },
                                            onError = {
                                                isLoading.value = false
                                                isError.value = true
                                            }

                                        )

                                        callLocaMailApiExcluirPorGrupoRepeticaoExcetoData(
                                            agenda.value!!.grupo_repeticao,
                                            agenda.value!!.data,
                                            onSuccess = {

                                            },
                                            onError = {
                                                isLoading.value = false
                                                isError.value = true
                                            }
                                        )

                                        callLocaMailApiAtualizaOpcaoRepeticaoPorGrupoRepeticao(
                                            agenda.value!!.grupo_repeticao,
                                            1,
                                            onSuccess = {

                                            },
                                            onError = {
                                                isLoading.value = false
                                                isError.value = true
                                            }

                                        )
                                    } else if (selectedRepeat.value == 2 && agenda.value!!.repeticao == 1 && selectedDate.value != agenda.value!!.data) {


                                        callLocaMailApiExcluirPorIdAgenda(
                                            id_agenda.toLong(),
                                            onSuccess = {
                                                callLocaMailApiExcluiAgenda(
                                                    id_agenda.toLong(),
                                                    onSuccess = {

                                                    },
                                                    onError = {
                                                        isLoading.value = false
                                                        isError.value = true

                                                    }
                                                )
                                            },
                                            onError = {
                                                isLoading.value = false
                                                isError.value = true

                                            }
                                        )

                                        for (day in returnOneMonthFromDate(agenda.value!!.data)) {
                                            agenda.value!!.repeticao = 2
                                            agenda.value!!.id_agenda = 0
                                            agenda.value!!.data = day


                                            callLocaMailApiCriarAgenda(
                                                agenda.value!!,
                                                onSuccess = { agendaRetornada ->

                                                    if (agendaRetornada != null) {
                                                        agendaConvidado.id_agenda =
                                                            agendaRetornada.id_agenda
                                                        for (convidado in listConvidadoSelected) {
                                                            agendaConvidado.id_convidado =
                                                                convidado.id_convidado


                                                            callLocaMailApiCriarAgendaConvidado(
                                                                agendaConvidado,
                                                                onSuccess = {

                                                                },
                                                                onError = {
                                                                    isLoading.value =
                                                                        false
                                                                    isError.value = true
                                                                }
                                                            )
                                                        }
                                                    }

                                                },
                                                onError = {
                                                    isLoading.value = false
                                                    isError.value = true
                                                }
                                            )

                                            agendaConvidado.grupo_repeticao =
                                                agenda.value!!.grupo_repeticao
                                        }
                                    } else if (selectedRepeat.value == 2 && agenda.value!!.repeticao == 1) {
                                        agenda.value!!.repeticao = 2
                                        callLocaMailApiAtualizaOpcaoRepeticaoPorIdAgenda(
                                            agenda.value!!.id_agenda,
                                            2,
                                            onSuccess = {

                                            },
                                            onError = {
                                                isLoading.value = false
                                                isError.value = true
                                            }
                                        )

                                        for (day in returnOneMonthFromDate(agenda.value!!.data)) {
                                            if (agenda.value!!.data != day) {
                                                agenda.value!!.id_agenda = 0
                                                agenda.value!!.data = day

                                                callLocaMailApiCriarAgenda(
                                                    agenda.value!!,
                                                    onSuccess = { agendaRetornada ->

                                                        if (agendaRetornada != null) {
                                                            agendaConvidado.id_agenda =
                                                                agendaRetornada.id_agenda
                                                            for (convidado in listConvidadoSelected) {
                                                                agendaConvidado.id_convidado =
                                                                    convidado.id_convidado
                                                                callLocaMailApiCriarAgendaConvidado(
                                                                    agendaConvidado,
                                                                    onSuccess = {

                                                                    },
                                                                    onError = {
                                                                        isLoading.value =
                                                                            false
                                                                        isError.value =
                                                                            true
                                                                    }

                                                                )
                                                            }
                                                        }
                                                    },
                                                    onError = {
                                                        isLoading.value = false
                                                        isError.value = true
                                                    }
                                                )



                                                agendaConvidado.grupo_repeticao =
                                                    agenda.value!!.grupo_repeticao
                                            }
                                        }
                                    }
                                    val previousBackStackEntry =
                                        navController.previousBackStackEntry
                                    if (previousBackStackEntry != null) {
                                        previousBackStackEntry.savedStateHandle.set(
                                            "data",
                                            if (millisToLocalDate.toString()
                                                    .equals("null")
                                            ) LocalDate.now()
                                                .toString() else millisToLocalDate!!.toString()
                                        )
                                    }
                                    isLoading.value = false
                                    navController.popBackStack()
                                }
                            },
                            colorText = colorResource(id = R.color.white),
                            colorsSecondButtonText = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.lcweb_red_1)
                            ),
                            isEdit = isEdit.value
                        )

                        TextFieldCalendar(
                            value = taskTitle.value,
                            onValueChange = {
                                isErrorTitle.value = it.isEmpty()
                                taskTitle.value = it
                            },
                            placeholderText = stringResource(id = R.string.calendar_event_placeholder_title),
                            fontSizePlaceHolder = 25.sp,
                            fontSizeTextStyle = 25.sp,
                            paddingTextField = 20.dp,
                            descriptionTrailingIcon = "",
                            iconTrailingIcon = Icons.Outlined.DateRange,
                            isReadOnly = !isEdit.value,
                            maxLines = 2,
                            isError = isErrorTitle.value
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 10.dp)
                        ) {
                            Icon(
                                modifier = Modifier
                                    .width(30.dp)
                                    .height(30.dp),
                                imageVector = Icons.Filled.AccountCircle,
                                contentDescription = stringResource(id = R.string.content_desc_user),
                                tint = colorResource(id = R.color.lcweb_gray_1)
                            )

                            Text(
                                text = "${stringResource(id = R.string.calendar_organizer_text)}: ${
                                    if (agenda != null)
                                        if (agenda.value!!.proprietario.length > 25) {
                                            "${
                                                agenda.value!!.proprietario.take(
                                                    25
                                                )
                                            }..."
                                        } else {
                                            agenda.value!!.proprietario
                                        }
                                    else ""
                                }",
                                modifier = Modifier.padding(5.dp),
                                color = colorResource(id = R.color.lcweb_gray_1),
                                fontSize = 20.sp
                            )
                        }

                        Button(
                            onClick = {
                                openDialogPeoplePicker.value = true
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                disabledContentColor = colorResource(id = R.color.lcweb_gray_1)
                            ),
                            shape = RoundedCornerShape(5.dp),
                            enabled = isEdit.value

                        ) {
                            Icon(
                                modifier = Modifier
                                    .width(30.dp)
                                    .height(30.dp),
                                painter = painterResource(id = R.drawable.user_group),
                                contentDescription = stringResource(id = R.string.content_desc_group_user),
                                tint = colorResource(id = R.color.lcweb_gray_1)
                            )

                            Text(
                                text = stringResource(id = R.string.calendar_event_participants),
                                color = colorResource(id = R.color.lcweb_gray_1),
                                fontSize = 20.sp,
                                modifier = Modifier.padding(horizontal = 5.dp)
                            )

                            PeopleSelectorDalog(
                                openDialogPeoplePicker = openDialogPeoplePicker,
                                listConvidado = listTodoConvidado,
                                listConvidadoText = listConvidadoText,
                                listConvidadoSelected = listConvidadoSelected,
                                usuarioSelecionado = usuarioSelecionado
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .width(30.dp)
                                        .height(30.dp),
                                    painter = painterResource(id = R.drawable.clock_solid),
                                    contentDescription = stringResource(id = R.string.content_desc_clock),
                                    tint = colorResource(id = R.color.lcweb_gray_1)
                                )

                                Text(
                                    text = stringResource(id = R.string.calendar_all_day),
                                    modifier = Modifier.padding(5.dp),
                                    color = colorResource(id = R.color.lcweb_gray_1),
                                    fontSize = 20.sp
                                )
                            }

                            Switch(
                                checked = allDay.value,
                                onCheckedChange = {
                                    time.value = if (!allDay.value) "1" else LocalDateTime.now()
                                        .format(DateTimeFormatter.ofPattern(if (timePickerState.is24hour) "HH:mm" else "hh:mm a"))
                                    timeShow.value = time.value
                                    allDay.value = !allDay.value
                                },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = colorResource(id = R.color.white),
                                    uncheckedThumbColor = colorResource(id = R.color.white),
                                    uncheckedBorderColor = colorResource(id = R.color.lcweb_gray_1),
                                    checkedBorderColor = colorResource(id = R.color.lcweb_red_1),
                                    checkedTrackColor = colorResource(id = R.color.lcweb_red_1),
                                    uncheckedTrackColor = colorResource(id = R.color.lcweb_gray_1),
                                    disabledCheckedTrackColor = colorResource(id = R.color.lcweb_red_1),
                                    disabledUncheckedTrackColor = colorResource(id = R.color.lcweb_gray_1),
                                    disabledCheckedThumbColor = colorResource(id = R.color.lcweb_gray_3),
                                    disabledUncheckedThumbColor = colorResource(id = R.color.lcweb_gray_3)
                                ),
                                enabled = isEdit.value
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Button(
                                onClick = {
                                    openDialogDatePicker.value = true
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent,
                                    disabledContainerColor = Color.Transparent,
                                    disabledContentColor = colorResource(id = R.color.lcweb_gray_1)
                                ),
                                shape = RoundedCornerShape(5.dp),
                                enabled = isEdit.value
                            ) {
                                Text(
                                    text = selectedDate.value,
                                    color = colorResource(id = R.color.lcweb_gray_1),
                                    fontSize = 20.sp
                                )

                                DateSelectorDialog(
                                    openDialogDatePicker = openDialogDatePicker,
                                    selectedDate = selectedDate,
                                    millisToLocalDate = millisToLocalDate,
                                    datePickerState = datePickerState
                                )
                            }

                            if (!allDay.value) {
                                Button(
                                    onClick = {
                                        showTimePicker.value = true
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Transparent,
                                        disabledContainerColor = Color.Transparent,
                                        disabledContentColor = colorResource(id = R.color.lcweb_gray_1)
                                    ),
                                    shape = RoundedCornerShape(5.dp),
                                    enabled = isEdit.value,

                                    ) {
                                    Text(
                                        text = timeShow.value,
                                        color = colorResource(id = R.color.lcweb_gray_1),
                                        fontSize = 20.sp,
                                        textAlign = TextAlign.Center
                                    )

                                    TimeSelectorDialog(
                                        showTimePicker = showTimePicker,
                                        time = time,
                                        timeShow = timeShow,
                                        timePickerState = timePickerState
                                    )
                                }
                            }
                        }

                        Button(
                            onClick = { openDialogColorPicker.value = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                disabledContentColor = colorResource(id = R.color.lcweb_gray_1)
                            ),
                            enabled = isEdit.value
                        ) {
                            Row(
                                modifier = Modifier.padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .width(30.dp)
                                        .height(30.dp)
                                        .background(returnColor(option = selectedColor.value))
                                )
                                Text(
                                    text = stringResource(id = R.string.calendar_choose_color),
                                    fontSize = 20.sp,
                                    color = colorResource(id = R.color.lcweb_gray_1),
                                    modifier = Modifier.padding(5.dp)
                                )

                                ColorSelectorDalog(
                                    openDialogColorPicker = openDialogColorPicker,
                                    selectedColor = selectedColor
                                )
                            }

                            ColorSelectorDalog(
                                openDialogColorPicker = openDialogColorPicker,
                                selectedColor = selectedColor
                            )
                        }

                        Button(
                            onClick = { openDialogRepeatPicker.value = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                disabledContentColor = colorResource(id = R.color.lcweb_gray_1)
                            ),
                            enabled = isEdit.value
                        ) {
                            Row(
                                modifier = Modifier.padding(15.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Refresh,
                                    contentDescription = stringResource(id = R.string.content_desc_calendar_repeat),
                                    modifier = Modifier
                                        .width(30.dp)
                                        .height(30.dp),
                                    tint = colorResource(id = R.color.lcweb_gray_1)
                                )
                                Text(
                                    text = returnStringRepeatOption(selectedRepeat.value),
                                    fontSize = 20.sp,
                                    color = colorResource(id = R.color.lcweb_gray_1),
                                    modifier = Modifier.padding(5.dp)
                                )
                            }

                            RepeatSelectorDialog(
                                openDialogRepeatPicker = openDialogRepeatPicker,
                                selectedRepeat = selectedRepeat
                            )
                        }

                        TextFieldCalendar(
                            value = taskDescription.value,
                            onValueChange = {
                                taskDescription.value = it
                            },
                            placeholderText = stringResource(id = R.string.calendar_event_description_text),
                            fontSizePlaceHolder = 20.sp,
                            fontSizeTextStyle = 20.sp,
                            paddingTextField = 20.dp,
                            descriptionTrailingIcon = "",
                            iconTrailingIcon = Icons.Outlined.Info,
                            isReadOnly = !isEdit.value,
                            maxLines = 4,
                            isError = false
                        )
                    }

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .align(Alignment.BottomCenter),
                        onClick = {


                            callLocaMailApiExcluirPorIdAgenda(
                                agenda.value!!.id_agenda,
                                onSuccess = {
                                    callLocaMailApiExcluiAgenda(
                                        agenda.value!!.id_agenda,
                                        onSuccess = {

                                        },
                                        onError = {
                                            isLoading.value = false
                                            isError.value = true

                                        }
                                    )
                                },
                                onError = {
                                    isLoading.value = false
                                    isError.value = true

                                }
                            )

                            val previousBackStackEntry = navController.previousBackStackEntry
                            if (previousBackStackEntry != null) {
                                previousBackStackEntry.savedStateHandle.set(
                                    "data",
                                    agenda.value!!.data
                                )
                            }
                            navController.popBackStack()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.lcweb_red_1)
                        ),
                        shape = RectangleShape
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = stringResource(id = R.string.calendar_event_mark))
                            Icon(
                                imageVector = Icons.Filled.CheckCircle,
                                contentDescription = stringResource(id = R.string.content_desc_event)
                            )
                        }

                    }
                }

            }
        }
    }
}