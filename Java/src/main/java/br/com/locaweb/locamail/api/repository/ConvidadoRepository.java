package br.com.locaweb.locamail.api.repository;

import br.com.locaweb.locamail.api.model.Convidado;
import br.com.locaweb.locamail.api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ConvidadoRepository extends JpaRepository<Convidado, Long> {

    @Query(value = "SELECT * from T_LCW_CONVIDADO", nativeQuery = true)
    public List<Convidado> listarConvidado();

    @Query(value = "SELECT * from T_LCW_CONVIDADO where id_convidado = :id_convidado", nativeQuery = true)
    public Convidado listarConvidadoPorId(@Param("id_convidado") Long id_convidado);

    @Query(value = "SELECT email FROM T_LCW_CONVIDADO WHERE email = :email AND ROWNUM = 1", nativeQuery = true)
    public String verificarConvidadoExiste(@Param("email") String email);

}
