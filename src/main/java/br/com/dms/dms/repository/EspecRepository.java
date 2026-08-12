package br.com.dms.dms.repository;

import br.com.dms.dms.model.CadEspecModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EspecRepository extends JpaRepository<CadEspecModel, Long> {

    List<CadEspecModel> findByNmFuncionarioContainingIgnoreCase(String nmFuncionario);

    boolean existsByDsCpf(String dsCpf);
    boolean existsByDsCro(String dsCro);
}