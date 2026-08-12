package br.com.dms.dms.repository;

import br.com.dms.dms.model.LoginModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface LoginRepository extends JpaRepository<LoginModel, Long> {

    LoginModel findByDsUsuarioAndDsSenha(String dsUsuario, String dsSenha);

    LoginModel findByDsUsuario(String dsUsuario);

}