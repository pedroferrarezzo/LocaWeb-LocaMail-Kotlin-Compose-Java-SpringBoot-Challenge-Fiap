package br.com.locaweb.locamail.api.utils;

import br.com.locaweb.locamail.api.model.Convidado;
import br.com.locaweb.locamail.api.model.Email;
import br.com.locaweb.locamail.api.model.RespostaEmail;
import br.com.locaweb.locamail.api.repository.ConvidadoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListUtils {

    public static String listaParaString(List<String> lista) {
        return lista.stream()
                .collect(Collectors.joining(", "));
    }

    public static List<String> stringParaLista(String str) {
        if (str == null) {
            str = "";
        }
        return List.of(str.split(", "));
    }

    public static void atualizarTodosDestinatariosList(ArrayList<String> todosDestinatarios, Email email, List<RespostaEmail> respostaEmailList) {
        todosDestinatarios.addAll(stringParaLista(email.getDestinatario()));
        todosDestinatarios.addAll(stringParaLista(email.getCc()));
        todosDestinatarios.addAll(stringParaLista(email.getCco()));

        for (RespostaEmail resposta : respostaEmailList) {
            for (String destinatario : stringParaLista(resposta.getDestinatario())) {
                if (!todosDestinatarios.contains(destinatario)) {
                    todosDestinatarios.add(destinatario);
                }
            }

            for (String cc : stringParaLista(resposta.getCc())) {
                if (!todosDestinatarios.contains(cc)) {
                    todosDestinatarios.add(cc);
                }
            }

            for (String cco : stringParaLista(resposta.getCco())) {
                if (!todosDestinatarios.contains(cco)) {
                    todosDestinatarios.add(cco);
                }
            }
        }
    }

    public static List<Convidado> returnListConvidado(List<Long> listIdConvidado, ConvidadoRepository convidadoRepository) {
        List<Convidado> list = new ArrayList<>();
        for (Long id : listIdConvidado) {
            list.add(convidadoRepository.listarConvidadoPorId(id));
        }
        return list;
    }
}
