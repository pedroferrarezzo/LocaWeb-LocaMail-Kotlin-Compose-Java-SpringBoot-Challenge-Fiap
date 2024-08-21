package br.com.locaweb.locamail.api.repository;

import br.com.locaweb.locamail.api.model.Anexo;
import br.com.locaweb.locamail.api.model.Pasta;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AnexoRepository extends JpaRepository<Anexo, Long> {

    @Query(value = "SELECT id_email FROM T_LCW_ANEXO", nativeQuery = true)
    public List<Long> listarAnexosIdEmail();

    @Query(value = "SELECT id_email FROM T_LCW_ANEXO where id_email = :id_email LIMIT 1", nativeQuery = true)
    public Long verificarAnexoExistentePorIdEmail(@Param("id_email") Long id_email);

    @Query(value = "SELECT anexo FROM T_LCW_ANEXO where id_email = :id_email", nativeQuery = true)
    public List<byte[]> listarAnexosArrayBytePorIdEmail(@Param("id_email") Long id_email);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM T_LCW_ANEXO where id_email = :id_email", nativeQuery = true)
    public void excluirAnexoPorIdEmail(@Param("id_email") Long id_email);

}
