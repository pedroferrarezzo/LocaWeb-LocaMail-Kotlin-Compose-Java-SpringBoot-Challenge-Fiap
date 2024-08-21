package br.com.locaweb.locamail.api.utils;

import br.com.locaweb.locamail.api.dto.email.EmailComAlteracaoDto;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ObjectMapperUtils {

    public static List<EmailComAlteracaoDto> emailComAlteracaoObjectMapper(List<Object[]> listRaw) {
        List<EmailComAlteracaoDto> emailList = new ArrayList<>();

        for (Object[] row : listRaw) {

            EmailComAlteracaoDto dto = new EmailComAlteracaoDto();

            dto.setId_email(((BigDecimal) row[0]).longValue());
            dto.setId_usuario(((BigDecimal) row[1]).longValue());
            dto.setRemetente((String) row[2]);
            dto.setDestinatario((String) row[3]);
            dto.setCc((String) row[4]);
            dto.setCco((String) row[5]);
            dto.setAssunto((String) row[6]);

            Clob corpo = (Clob) row[7];
            try (Reader reader = corpo.getCharacterStream();
                 StringWriter w = new StringWriter()) {
                char[] buffer = new char[4096];
                int charsRead;
                while ((charsRead = reader.read(buffer)) != -1) {
                    w.write(buffer, 0, charsRead);
                }
                dto.setCorpo(w.toString());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            dto.setEditavel(((BigDecimal) row[8]).intValue() == 1);
            dto.setEnviado(((BigDecimal) row[9]).intValue() == 1);
            dto.setHorario((String) row[10]);
            dto.setData((String) row[11]);
            dto.setAgenda_atrelada(((BigDecimal) row[12]).intValue() == 1);
            dto.setId_alteracao(((BigDecimal) row[13]).longValue());
            dto.setAlt_id_usuario(((BigDecimal) row[14]).longValue());
            dto.setAlt_id_email(((BigDecimal) row[15]).longValue());

            if (row[16] != null) {
                dto.setId_pasta(((BigDecimal) row[16]).longValue());
            }

            dto.setImportante(((BigDecimal) row[17]).intValue() == 1);
            dto.setLido(((BigDecimal) row[18]).intValue() == 1);
            dto.setArquivado(((BigDecimal) row[19]).intValue() == 1);
            dto.setExcluido(((BigDecimal) row[20]).intValue() == 1);
            dto.setSpam(((BigDecimal) row[21]).intValue() == 1);


            emailList.add(dto);
        }

        return emailList;

    }
}
