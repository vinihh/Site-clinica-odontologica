package br.com.dms.dms.repository;

import br.com.dms.dms.model.PacModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface PacRepository extends JpaRepository<PacModel, Long> {

    List<PacModel> findByNmPacienteContainingIgnoreCase(String nome);

    boolean existsByDsCpf(String cpf);

    boolean existsByDsRg(String rg);

    PacModel findByDsCpf(String dsCpf);

}