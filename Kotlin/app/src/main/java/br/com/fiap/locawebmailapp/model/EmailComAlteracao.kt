package br.com.fiap.locawebmailapp.model

import androidx.room.Embedded
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


data class EmailComAlteracao(
        var id_email: Long = 0,
        var id_usuario: Long = 0,
        var remetente: String = "",
        var destinatario: String = "",
        var cc: String = "",
        var cco: String = "",
        var assunto: String = "",
        var corpo: String = "",
        var editavel: Boolean = false,
        var enviado: Boolean = false,
        var horario: String = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")),
        var data: String = "${LocalDate.now()}",
        var agenda_atrelada: Boolean = false,
        var id_alteracao: Long = 0,
        var alt_id_usuario: Long = 1,
        var alt_id_email: Long = 1,
        var id_pasta: Long? = null,
        var importante: Boolean = false,
        var lido: Boolean = false,
        var arquivado: Boolean = false,
        var excluido: Boolean = false,
        var spam: Boolean = false
)