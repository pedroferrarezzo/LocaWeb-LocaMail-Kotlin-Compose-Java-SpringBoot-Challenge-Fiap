package br.com.locaweb.locamail.api.repository;

import br.com.locaweb.locamail.api.model.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = "SELECT * FROM T_LCW_USUARIO where email = :email", nativeQuery = true)
    Usuario retornaUsarioPorEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM T_LCW_USUARIO where id_usuario = :id_usuario", nativeQuery = true)
    Usuario retornaUsuarioPorId(@Param("id_usuario") Long id_usuario);

    @Query(value = "SELECT * FROM T_LCW_USUARIO where email != 'dev@locaweb.com.br'", nativeQuery = true)
    List<Usuario> listarUsuarios();

    @Query(value = "SELECT * FROM T_LCW_USUARIO where selected_user = 0 AND email != 'dev@locaweb.com.br'", nativeQuery = true)
    List<Usuario> listarUsuariosNaoSelecionados();

    @Query(value = "SELECT * FROM T_LCW_USUARIO where selected_user = 1 AND email != 'dev@locaweb.com.br'", nativeQuery = true)
    Usuario listarUsuarioSelecionado();

    @Transactional
    @Modifying
    @Query(value = "UPDATE T_LCW_USUARIO SET selected_user = 0 where selected_user = 1", nativeQuery = true)
    void desselecionarUsuarioSelecionadoAtual();

    @Transactional
    @Modifying
    @Query(value = "UPDATE T_LCW_USUARIO SET selected_user = 1 where id_usuario = :id_usuario", nativeQuery = true)
    void selecionarUsuario(@Param("id_usuario") Long id_usuario);

    @Transactional
    @Modifying
    @Query(value = "UPDATE T_LCW_USUARIO SET autenticado = :autenticado where id_usuario = :id_usuario", nativeQuery = true)
    void atualizaAutenticaUsuario(@Param("id_usuario") Long id_usuario, @Param("autenticado") Boolean autenticado);

    @Query(value = "SELECT * FROM T_LCW_USUARIO where autenticado = 1 AND email != 'dev@locaweb.com.br'", nativeQuery = true)
    List<Usuario> listarUsuariosAutenticados();

}
