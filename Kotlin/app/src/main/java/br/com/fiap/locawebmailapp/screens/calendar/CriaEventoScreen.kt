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
import androidx.compose.material.icons.filled.Close
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
import br.com.fiap.locawebmailapp.components.calendar.MailOptionsDialog
import br.com.fiap.locawebmailapp.components.calendar.PeopleSelectorDalog
import br.com.fiap.locawebmailapp.components.calendar.RepeatSelectorDialog
import br.com.fiap.locawebmailapp.components.calendar.TextFieldCalendar
import br.com.fiap.locawebmailapp.components.calendar.TimeSelectorDialog
import br.com.fiap.locawebmailapp.components.general.ErrorComponent
import br.com.fiap.locawebmailapp.components.general.RowIconButton
import br.com.fiap.locawebmailapp.model.Agenda
import br.com.fiap.locawebmailapp.model.AgendaConvidado
import br.com.fiap.locawebmailapp.model.Alteracao
import br.com.fiap.locawebmailapp.model.Convidado
import br.com.fiap.locawebmailapp.model.Email
import br.com.fiap.locawebmailapp.model.Usuario
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiCriarAgenda
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiCriarAgendaConvidado
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiCriarAlteracao
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiCriarEmail
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarConvidado
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarGrupoRepeticao
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarUsuarioSelecionado
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiRetornaUsarioPorEmail
import br.com.fiap.locawebmailapp.utils.checkInternetConnectivity
import br.com.fiap.locawebmailapp.utils.completeStringDateToDate
import br.com.fiap.locawebmailapp.utils.convertTo12Hours
import br.com.fiap.locawebmailapp.utils.dateToCompleteStringDate
import br.com.fiap.locawebmailapp.utils.listaParaString
import br.com.fiap.locawebmailapp.utils.localDateToMillis
import br.com.fiap.locawebmailapp.utils.returnColor
import br.com.fiap.locawebmailapp.utils.returnOneMonthFromDate
import br.com.fiap.locawebmailapp.utils.returnStringRepeatOption
import br.com.fiap.locawebmailapp.utils.stringToLocalDate
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Stable
@Composable
fun CriaEventoScreen(navController: NavController) {
    val taskTitle = remember {
        mutableStateOf("")
    }
    val taskDescription = remember {
        mutableStateOf("")
    }
    val allDay = remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

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


    val timePickerState = rememberTimePickerState(
        initialHour = LocalDateTime.now().hour,
        initialMinute = LocalDateTime.now().minute
    )
    val showTimePicker = remember { mutableStateOf(false) }
    val nowTime = LocalDateTime.now()
    val patternTime = if (timePickerState.is24hour) "HH:mm" else "hh:mm a";
    val timeShow = remember {
        mutableStateOf(nowTime.format(DateTimeFormatter.ofPattern(patternTime)))
    }
    val time = remember {
        mutableStateOf(nowTime.format(DateTimeFormatter.ofPattern("HH:mm")))
    }

    val datePickerState = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Picker, initialSelectedDateMillis = localDateToMillis(
            LocalDate.now()
        )
    )
    val openDialogDatePicker = remember { mutableStateOf(false) }
    val timezoneFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US).apply {
        timeZone = TimeZone.getTimeZone("GMT")
    }

    val millisToLocalDate = datePickerState.selectedDateMillis?.let {
        stringToLocalDate(timezoneFormatter.format(it))
    }
    val selectedDate = remember {
        mutableStateOf(dateToCompleteStringDate(LocalDate.now()))
    }

    val listConvidado = remember {
        mutableStateOf(listOf<Convidado>())
    }

    val usuarioSelecionado = remember {
        mutableStateOf<Usuario?>(null)
    }

    LaunchedEffect(key1 = Unit) {
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

        callLocaMailApiListarConvidado(
            onSuccess = { listConvidadoRetornado ->

                if (listConvidadoRetornado != null) {
                    listConvidado.value = listConvidadoRetornado
                }

            },
            onError = {
                isError.value = true
                isLoading.value = false
            }
        )
    }

    val selectedMailOption = remember {
        mutableStateOf(1)
    }

    val openDialogMailOptions = remember { mutableStateOf(false) }


    val openDialogColorPicker = remember { mutableStateOf(false) }
    val selectedColor = remember { mutableStateOf(1) }

    val openDialogRepeatPicker = remember { mutableStateOf(false) }
    val selectedRepeat = remember { mutableStateOf(1) }

    val agenda = Agenda()
    val agendaConvidado = AgendaConvidado()

    val isErrorTitle = remember { mutableStateOf(false) }

    val openDialogPeoplePicker = remember { mutableStateOf(false) }

    val listConvidadoSelected = remember {
        mutableStateListOf<Convidado>()
    }

    val listConvidadoText = remember {
        mutableStateOf("")
    }

    val toastMessageMailDraftSaved = stringResource(id = R.string.toast_maildraft_saved)

    val email = Email()

    val calendarEventDraftInvite = stringResource(R.string.calendar_event_draft_invite)
    val calendarEventDraftInviteText = stringResource(R.string.calendar_event_draft_invitetext)
    val calendarEventDraftDescribeText = stringResource(R.string.calendar_event_draft_describetext)
    val calendarEventDraftDateText = stringResource(R.string.calendar_event_draft_datetext)
    val calendarEventDraftEverydayUntil =
        stringResource(R.string.calendar_event_draft_everydayuntil)
    val calendarEventDraftHourText = stringResource(R.string.calendar_event_draft_hourtext)
    val calendarEventDraftAllDay = stringResource(R.string.calendar_event_draft_allday)
    val toastMessageEventInviteSent = stringResource(id = R.string.toast_event_invitesent)

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
            Column {

                RowIconButton(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    onClickFirstButton = {
                        val previousBackStackEntry = navController.previousBackStackEntry
                        if (previousBackStackEntry != null) {
                            previousBackStackEntry.savedStateHandle.set(
                                "data",
                                LocalDate.now().toString()
                            )
                        }
                        navController.popBackStack()
                    },
                    onClickSecondButton = {
                        if (taskTitle.value.isEmpty()) {
                            isErrorTitle.value = true

                        } else {

                            if (usuarioSelecionado.value != null) {
                                agenda.proprietario = usuarioSelecionado.value!!.nome
                                agenda.id_usuario = usuarioSelecionado.value!!.id_usuario
                            }

                            agenda.nome = taskTitle.value
                            agenda.descritivo = taskDescription.value

                            agenda.data =
                                if (millisToLocalDate.toString().equals("null")) LocalDate.now()
                                    .toString() else millisToLocalDate!!.toString()
                            agenda.horario = time.value
                            agenda.cor = selectedColor.value
                            agenda.repeticao = selectedRepeat.value
                            agenda.tarefa = false


                            if (agenda.repeticao == 2) {

                                callLocaMailApiListarGrupoRepeticao(
                                    onSuccess = { listGrupoRepeticaoRetornado ->
                                        if (listGrupoRepeticaoRetornado != null) {
                                            if (listGrupoRepeticaoRetornado.isNotEmpty()) {
                                                agenda.grupo_repeticao =
                                                    listGrupoRepeticaoRetornado
                                                        .last() + 1
                                            }

                                            for (day in returnOneMonthFromDate(agenda.data)) {
                                                agenda.data = day

                                                callLocaMailApiCriarAgenda(
                                                    agenda,
                                                    onSuccess = { agendaCriadaRetornada ->
                                                        if (agendaCriadaRetornada != null) {
                                                            agendaConvidado.id_agenda =
                                                                agendaCriadaRetornada.id_agenda
                                                            for (convidado in listConvidadoSelected) {
                                                                agendaConvidado.id_convidado =
                                                                    convidado.id_convidado

                                                                callLocaMailApiCriarAgendaConvidado(
                                                                    agendaConvidado,
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



                                                agendaConvidado.grupo_repeticao =
                                                    agenda.grupo_repeticao

                                            }
                                        }
                                    },
                                    onError = {
                                        isError.value = true
                                        isLoading.value = false
                                    }
                                )
                            } else {

                                callLocaMailApiCriarAgenda(
                                    agenda,
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

                            val previousBackStackEntry = navController.previousBackStackEntry
                            if (previousBackStackEntry != null) {
                                previousBackStackEntry.savedStateHandle.set(
                                    "data",
                                    if (millisToLocalDate.toString().equals("null")) LocalDate.now()
                                        .toString() else millisToLocalDate!!.toString()
                                )
                            }

                            if (listConvidadoSelected.isNotEmpty()) {
                                if (selectedMailOption.value == 2) {
                                    email.agenda_atrelada = true

                                    if (usuarioSelecionado.value != null) {
                                        email.remetente = usuarioSelecionado.value!!.email
                                        email.id_usuario = usuarioSelecionado.value!!.id_usuario
                                    }

                                    email.destinatario =
                                        listaParaString(listConvidadoSelected.map { it.email })
                                    email.cc = ""
                                    email.cco = ""
                                    email.assunto = "Convite de agenda: ${agenda.nome}"
                                    email.corpo =
                                        "${agenda.proprietario} $calendarEventDraftInviteText ${agenda.nome}! \n" +
                                                "$calendarEventDraftDescribeText ${agenda.descritivo}\n" +
                                                "$calendarEventDraftDateText ${
                                                    if (agenda.repeticao == 2) dateToCompleteStringDate(
                                                        completeStringDateToDate(selectedDate.value)
                                                    ) +
                                                            " $calendarEventDraftEverydayUntil ${
                                                                dateToCompleteStringDate(
                                                                    stringToLocalDate(agenda.data)
                                                                )
                                                            }" else dateToCompleteStringDate(
                                                        stringToLocalDate(
                                                            agenda.data
                                                        )
                                                    )
                                                }\n" +
                                                "$calendarEventDraftHourText ${

                                                    if (allDay.value) {
                                                        calendarEventDraftAllDay
                                                    } else {
                                                        if (timePickerState.is24hour) agenda.horario else convertTo12Hours(
                                                            agenda.horario
                                                        )
                                                    }
                                                }"
                                    email.enviado = true
                                    email.editavel = false


                                    callLocaMailApiCriarEmail(
                                        email,
                                        onSuccess = { emailRetornado ->

                                            if (emailRetornado != null && usuarioSelecionado.value != null) {
                                                val rowId = emailRetornado.id_email
                                                callLocaMailApiCriarAlteracao(
                                                    Alteracao(
                                                        alt_id_email = rowId,
                                                        alt_id_usuario = usuarioSelecionado.value!!.id_usuario
                                                    ),
                                                    onSuccess = {

                                                    },
                                                    onError = {
                                                        isError.value = true
                                                        isLoading.value = false
                                                    }
                                                )

                                                for (convidadoSelected in listConvidadoSelected) {
                                                    callLocaMailApiRetornaUsarioPorEmail(
                                                        convidadoSelected.email,
                                                        onSuccess = { usuarioExistenteRetornado ->

                                                            if (usuarioExistenteRetornado != null) {
                                                                callLocaMailApiCriarAlteracao(
                                                                    Alteracao(
                                                                        alt_id_email = rowId,
                                                                        alt_id_usuario = usuarioExistenteRetornado.id_usuario
                                                                    ),
                                                                    onSuccess = {

                                                                    },
                                                                    onError = {
                                                                        isError.value = true
                                                                        isLoading.value = false
                                                                    }
                                                                )

                                                                agenda.id_email = rowId
                                                                agenda.nome = taskTitle.value
                                                                agenda.descritivo =
                                                                    taskDescription.value
                                                                agenda.proprietario =
                                                                    usuarioSelecionado.value!!.nome
                                                                agenda.data =
                                                                    if (millisToLocalDate.toString()
                                                                            .equals("null")
                                                                    ) LocalDate.now()
                                                                        .toString() else millisToLocalDate!!.toString()
                                                                agenda.horario = time.value
                                                                agenda.cor = selectedColor.value
                                                                agenda.repeticao =
                                                                    selectedRepeat.value
                                                                agenda.tarefa = false
                                                                agenda.id_usuario =
                                                                    usuarioExistenteRetornado.id_usuario
                                                                agenda.visivel = false

                                                                if (agenda.repeticao == 2) {


                                                                    callLocaMailApiListarGrupoRepeticao(
                                                                        onSuccess = { listGrupoRepeticaoRetornado ->

                                                                            if (listGrupoRepeticaoRetornado != null) {
                                                                                if (listGrupoRepeticaoRetornado.isNotEmpty()
                                                                                ) {
                                                                                    agenda.grupo_repeticao =
                                                                                        listGrupoRepeticaoRetornado
                                                                                            .last() + 1
                                                                                }
                                                                            }
                                                                        },
                                                                        onError = {
                                                                            isError.value = true
                                                                            isLoading.value = false
                                                                        }
                                                                    )


                                                                    for (day in returnOneMonthFromDate(
                                                                        agenda.data
                                                                    )) {
                                                                        agenda.data = day


                                                                        callLocaMailApiCriarAgenda(
                                                                            agenda,
                                                                            onSuccess = { agendaRetornado ->

                                                                                if (agendaRetornado != null) {
                                                                                    agendaConvidado.id_agenda =
                                                                                        agendaRetornado.id_agenda
                                                                                    for (convidado in listConvidadoSelected) {
                                                                                        agendaConvidado.id_convidado =
                                                                                            convidado.id_convidado
                                                                                        callLocaMailApiCriarAgendaConvidado(
                                                                                            agendaConvidado,
                                                                                            onSuccess = {

                                                                                            },
                                                                                            onError = {
                                                                                                isError.value =
                                                                                                    true
                                                                                                isLoading.value =
                                                                                                    false
                                                                                            }
                                                                                        )
                                                                                    }
                                                                                    agendaConvidado.grupo_repeticao =
                                                                                        agenda.grupo_repeticao
                                                                                }
                                                                            },
                                                                            onError = {
                                                                                isError.value = true
                                                                                isLoading.value =
                                                                                    false
                                                                            }
                                                                        )
                                                                    }

                                                                } else {

                                                                    callLocaMailApiCriarAgenda(
                                                                        agenda,
                                                                        onSuccess = { agendaRetornado ->

                                                                            if (agendaRetornado != null) {
                                                                                agendaConvidado.id_agenda =
                                                                                    agendaRetornado.id_agenda
                                                                                for (convidado in listConvidadoSelected) {
                                                                                    agendaConvidado.id_convidado =
                                                                                        convidado.id_convidado

                                                                                    callLocaMailApiCriarAgendaConvidado(
                                                                                        agendaConvidado,
                                                                                        onSuccess = {

                                                                                        },
                                                                                        onError = {
                                                                                            isError.value =
                                                                                                true
                                                                                            isLoading.value =
                                                                                                false
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
                                                            }
                                                        },
                                                        onError = {
                                                            isError.value = true
                                                            isLoading.value = false
                                                        }
                                                    )
                                                    Toast.makeText(
                                                        context,
                                                        toastMessageEventInviteSent,
                                                        Toast.LENGTH_LONG
                                                    )
                                                        .show()
                                                }
                                            }
                                        },
                                        onError = {
                                            isError.value = true
                                            isLoading.value = false
                                        }
                                    )

                                } else if (selectedMailOption.value == 3) {

                                    if (usuarioSelecionado.value != null) {
                                        email.remetente = usuarioSelecionado.value!!.email
                                        email.id_usuario = usuarioSelecionado.value!!.id_usuario
                                    }


                                    email.agenda_atrelada = true

                                    email.destinatario =
                                        listaParaString(listConvidadoSelected.map { it.email })
                                    email.cc = ""
                                    email.cco = ""
                                    email.assunto = "$calendarEventDraftInvite ${agenda.nome}"
                                    email.corpo =
                                        "${agenda.proprietario} $calendarEventDraftInviteText ${agenda.nome}! \n" +
                                                "$calendarEventDraftDescribeText ${agenda.descritivo}\n" +
                                                "$calendarEventDraftDateText ${
                                                    if (agenda.repeticao == 2) dateToCompleteStringDate(
                                                        completeStringDateToDate(selectedDate.value)
                                                    ) +
                                                            " $calendarEventDraftEverydayUntil ${
                                                                dateToCompleteStringDate(
                                                                    stringToLocalDate(agenda.data)
                                                                )
                                                            }" else dateToCompleteStringDate(
                                                        stringToLocalDate(
                                                            agenda.data
                                                        )
                                                    )
                                                }\n" +
                                                "$calendarEventDraftHourText ${

                                                    if (allDay.value) {
                                                        calendarEventDraftAllDay
                                                    } else {
                                                        if (timePickerState.is24hour) agenda.horario else convertTo12Hours(
                                                            agenda.horario
                                                        )
                                                    }
                                                }"
                                    email.enviado = false
                                    email.editavel = true

                                    callLocaMailApiCriarEmail(
                                        email,
                                        onSuccess = {
                                            Toast.makeText(
                                                context,
                                                toastMessageMailDraftSaved,
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
                            }
                            navController.popBackStack()
                        }
                    },
                    iconFirstButton = Icons.Filled.Close,
                    textSecondButton = stringResource(id = R.string.calendar_save_button),
                    descriptionFirstButton = "",
                    colorsSecondButton = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.lcweb_red_1)
                    ),
                    colorSecondTextButton = colorResource(id = R.color.white)
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
                    isReadOnly = false,
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

                    if (usuarioSelecionado.value != null) {
                        Text(
                            text = "${stringResource(id = R.string.calendar_organizer_text)}: ${
                                if (usuarioSelecionado.value!!.nome.length > 25) {
                                    "${
                                        usuarioSelecionado.value!!.nome.take(
                                            25
                                        )
                                    }..."
                                } else {
                                    usuarioSelecionado.value!!.nome
                                }
                            }",
                            modifier = Modifier.padding(5.dp),
                            color = colorResource(id = R.color.lcweb_gray_1),
                            fontSize = 20.sp
                        )
                    }


                }

                Button(
                    onClick = {
                        openDialogPeoplePicker.value = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(5.dp)
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
                        listConvidado = listConvidado,
                        listConvidadoText = listConvidadoText,
                        listConvidadoSelected = listConvidadoSelected,
                        usuarioSelecionado = usuarioSelecionado
                    )
                }

                if (listConvidadoSelected.isNotEmpty()) {
                    Button(
                        onClick = {
                            openDialogMailOptions.value = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Icon(
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp),
                            painter = painterResource(id = R.drawable.envelope_open_text_solid),
                            contentDescription = stringResource(id = R.string.content_desc_mail_options),
                            tint = colorResource(id = R.color.lcweb_gray_1)
                        )

                        Text(
                            text = stringResource(id = R.string.calendar_event_mail_options),
                            color = colorResource(id = R.color.lcweb_gray_1),
                            fontSize = 20.sp,
                            modifier = Modifier.padding(horizontal = 5.dp)
                        )

                        MailOptionsDialog(
                            openDialogMailOptions = openDialogMailOptions,
                            selectedMailOption = selectedMailOption
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp),
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
                            uncheckedTrackColor = colorResource(id = R.color.lcweb_gray_1)
                        )
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
                            containerColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(5.dp)
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
                                containerColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(5.dp)
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
                        containerColor = Color.Transparent
                    )
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
                    }

                    ColorSelectorDalog(
                        openDialogColorPicker = openDialogColorPicker,
                        selectedColor = selectedColor
                    )
                }

                Button(
                    onClick = { openDialogRepeatPicker.value = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    )
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
                    isReadOnly = false,
                    maxLines = 3,
                    isError = false
                )
            }
        }
    }
}