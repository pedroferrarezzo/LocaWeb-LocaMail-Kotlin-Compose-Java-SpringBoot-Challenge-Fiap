package br.com.locaweb.locamail.api.repository;

import br.com.locaweb.locamail.api.model.Convidado;
import br.com.locaweb.locamail.api.model.Pasta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PastaRepository extends JpaRepository<Pasta, Long> {

    @Query(value = "SELECT * FROM T_LCW_PASTA where id_usuario = :id_usuario", nativeQuery = true)
    public List<Pasta> listarPastasPorIdUsuario(Long id_usuario);

}
