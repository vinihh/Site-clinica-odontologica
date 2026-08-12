package br.com.dms.dms.repository;

import br.com.dms.dms.model.EspecialidadesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EspecialidadesRepository extends JpaRepository<EspecialidadesModel, Long> {
    
    List<EspecialidadesModel> findByFuncionarioIdFuncionario(Long idFuncionario);

    List<EspecialidadesModel> findByDsEspecialidade(String dsEspecialidade);
}