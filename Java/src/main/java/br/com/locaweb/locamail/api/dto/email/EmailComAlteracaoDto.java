package br.com.locaweb.locamail.api.dto.email;

import br.com.locaweb.locamail.api.model.Alteracao;
import br.com.locaweb.locamail.api.model.Email;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EmailComAlteracaoDto{

    private Long id_email;


    private Long id_usuario;


    private String remetente;


    private String destinatario;


    private String cc;


    private String cco;


    private String assunto;


    private String corpo;


    private Boolean editavel;


    private Boolean enviado;


    private String horario;


    private String data;


    private Boolean agenda_atrelada;



    private Long id_alteracao;


    private Long alt_id_usuario;


    private Long alt_id_email;


    private Long id_pasta;


    private Boolean importante;


    private Boolean lido;


    private Boolean arquivado;


    private Boolean excluido;


    private Boolean spam;
}

