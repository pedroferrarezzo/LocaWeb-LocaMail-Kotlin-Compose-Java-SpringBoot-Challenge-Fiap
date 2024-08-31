package br.com.fiap.locawebmailapp.utils

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import br.com.fiap.locawebmailapp.database.repository.ConvidadoRepository
import br.com.fiap.locawebmailapp.model.Convidado
import br.com.fiap.locawebmailapp.model.EmailComAlteracao
import br.com.fiap.locawebmailapp.model.IdConvidado
import br.com.fiap.locawebmailapp.model.RespostaEmail
import br.com.fiap.locawebmailapp.utils.api.callLocaMailApiListarConvidadoPorId

fun listaParaString(lista: List<String>): String {
    return lista.withIndex().joinToString(", ") { it.value }
}

fun stringParaLista(str: String): List<String> {
    return str.split(", ")
}

fun atualizarTodosDestinatariosList(todosDestinatarios: ArrayList<String>, email: EmailComAlteracao, respostaEmailList: List<RespostaEmail>){

    todosDestinatarios.addAll(stringParaLista(email.destinatario))
    todosDestinatarios.addAll(stringParaLista(email.cc))
    todosDestinatarios.addAll(stringParaLista(email.cco))

    for (resposta in respostaEmailList) {
        for (destinatario in stringParaLista(resposta.destinatario)) {
            if (!todosDestinatarios.contains(destinatario)) {
                todosDestinatarios.add(destinatario)
            }
        }

        for (cc in stringParaLista(resposta.cc)) {
            if (!todosDestinatarios.contains(cc)) {
                todosDestinatarios.add(cc)
            }
        }

        for (cco in stringParaLista(resposta.cco)) {
            if (!todosDestinatarios.contains(cco)) {
                todosDestinatarios.add(cco)
            }
        }
    }
}

fun returnListConvidado(
    listIdConvidado: List<Long>,
    isLoading: MutableState<Boolean>,
    isError: MutableState<Boolean>,
    listConvidado: SnapshotStateList<Convidado>
) {

    for (id in listIdConvidado) {

        callLocaMailApiListarConvidadoPorId(
            id,
            onSuccess = {
                convidadoRetornado ->
                if (convidadoRetornado != null) {
                    listConvidado.add(convidadoRetornado)
                }
            },
            onError = {
                isError.value = true
                isLoading.value = false
            }
        )
    }
}