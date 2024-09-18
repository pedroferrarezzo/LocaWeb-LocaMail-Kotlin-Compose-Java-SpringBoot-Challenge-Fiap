package br.com.locaweb.locamail.api.dto.email;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record EmailCadastroDto (

        Long id_email,

        @NotNull(message = "O ID do usuário é obrigatório!")
        Long id_usuario,

        @NotNull(message = "O remetente é obrigatório!")
        @Email(message = "O email deve estar no formato correto!")
        String remetente,

        @NotNull(message = "O destinatário é obrigatório!")
        String destinatario,

        @NotNull(message = "O campo CC é obrigatório!")
        String cc,

        @NotNull(message = "O campo CCO é obrigatório!")
        String cco,

        @NotNull(message = "O assunto é obrigatório!")
        String assunto,

        @NotNull(message = "O corpo do email é obrigatório!")
        String corpo,

        @NotNull(message = "O status de edição é obrigatório!")
        Boolean editavel,

        @NotNull(message = "O status de envio é obrigatório!")
        Boolean enviado,

        @NotNull(message = "O horário é obrigatório!")
        String horario,

        @NotNull(message = "A data é obrigatória!")
        String data,

        @NotNull(message = "O status de agenda atrelada é obrigatório!")
        Boolean agenda_atrelada,

        Boolean is_spam

) {

}

