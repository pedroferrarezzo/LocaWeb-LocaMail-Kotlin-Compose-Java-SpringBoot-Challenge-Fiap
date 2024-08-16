package br.com.locaweb.locamail.api.repository;

import br.com.locaweb.locamail.api.dto.UsuarioExibicaoDto;
import br.com.locaweb.locamail.api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = "SELECT * FROM T_LCW_USUARIO where email = :email", nativeQuery = true)
    Usuario retornaUsarioPorEmail(String email);

    @Query(value = "SELECT * FROM T_LCW_USUARIO where id_usuario = :id_usuario", nativeQuery = true)
    Usuario retornaUsuarioPorId(Long id_usuario);

    @Query(value = "SELECT * FROM T_LCW_USUARIO where email != 'dev@locaweb.com.br'", nativeQuery = true)
    List<Usuario> listarUsuarios();

    @Query(value = "SELECT * FROM T_LCW_USUARIO where selected_user = 0 AND email != 'dev@locaweb.com.br'", nativeQuery = true)
    List<Usuario> listarUsuariosNaoSelecionados();

    @Query(value = "SELECT * FROM T_LCW_USUARIO where selected_user = 1 AND email != 'dev@locaweb.com.br'", nativeQuery = true)
    Usuario listarUsuarioSelecionado();

    @Query(value = "UPDATE T_LCW_USUARIO SET selected_user = 0 where selected_user = 1", nativeQuery = true)
    void desselecionarUsuarioSelecionadoAtual();

    @Query(value = "UPDATE T_LCW_USUARIO SET selected_user = 1 where id_usuario = :id_usuario", nativeQuery = true)
    void selecionarUsuario(Long id_usuario);

    @Query(value = "UPDATE T_LCW_USUARIO SET autenticado = :autenticado where id_usuario = :id_usuario", nativeQuery = true)
    void atualizaAutenticaUsuario(Long id_usuario, Boolean autenticado);

    @Query(value = "SELECT * FROM T_LCW_USUARIO where autenticado = 1 AND email != 'dev@locaweb.com.br'", nativeQuery = true)
    List<Usuario> listarUsuariosAutenticados();

}
