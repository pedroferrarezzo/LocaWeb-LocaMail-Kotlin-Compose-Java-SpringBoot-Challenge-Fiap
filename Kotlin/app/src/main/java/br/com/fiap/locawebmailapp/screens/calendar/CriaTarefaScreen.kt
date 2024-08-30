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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.CheckCircle
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
import br.com.fiap.locawebmailapp.components.calendar.RepeatSelectorDialog
import br.com.fiap.locawebmailapp.components.calendar.TextFieldCalendar
import br.com.fiap.locawebmailapp.components.calendar.TimeSelectorDialog
import br.com.fiap.locawebmailapp.components.general.ErrorComponent
import br.com.fiap.locawebmailapp.components.general.RowIconButton
import br.com.fiap.locawebmailapp.model.Agenda
import br.com.fiap.locawebmailapp.model.Usuario
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiCriarAgenda
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarGrupoRepeticao
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarUsuarioSelecionado
import br.com.fiap.locawebmailapp.utils.checkInternetConnectivity
import br.com.fiap.locawebmailapp.utils.dateToCompleteStringDate
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

@Stable
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CriaTarefaScreen(navController: NavController) {
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

    val openDialogColorPicker = remember { mutableStateOf(false) }
    val selectedColor = remember { mutableStateOf(1) }

    val openDialogRepeatPicker = remember { mutableStateOf(false) }
    val selectedRepeat = remember { mutableStateOf(1) }

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
    }

    val agenda = Agenda()

    val isErrorTitle = remember { mutableStateOf(false) }


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

                            agenda.tarefa = true
                            agenda.data =
                                if (millisToLocalDate.toString().equals("null")) LocalDate.now()
                                    .toString() else millisToLocalDate!!.toString()
                            agenda.horario = time.value
                            agenda.cor = selectedColor.value
                            agenda.repeticao = selectedRepeat.value

                            if (agenda.repeticao == 2) {


                                callLocaMailApiListarGrupoRepeticao(
                                    onSuccess = { listGrupoRepeticaoRetornado ->

                                        if (listGrupoRepeticaoRetornado != null) {

                                            if (listGrupoRepeticaoRetornado.isNotEmpty()) {
                                                agenda.grupo_repeticao =
                                                    listGrupoRepeticaoRetornado.last() + 1
                                            }
                                            for (day in returnOneMonthFromDate(agenda.data)) {
                                                agenda.data = day
                                                callLocaMailApiCriarAgenda(
                                                    agenda,
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
                            } else {
                                callLocaMailApiCriarAgenda(
                                    agenda,
                                    onSuccess = {

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
                    placeholderText = stringResource(id = R.string.calendar_task_placeholder_title),
                    fontSizePlaceHolder = 25.sp,
                    fontSizeTextStyle = 25.sp,
                    paddingTextField = 20.dp,
                    descriptionTrailingIcon = "",
                    iconTrailingIcon = Icons.Outlined.CheckCircle,
                    isReadOnly = false,
                    maxLines = 2,
                    isError = isErrorTitle.value
                )

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
                    placeholderText = stringResource(id = R.string.calendar_task_description_text),
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