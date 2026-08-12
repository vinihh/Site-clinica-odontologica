package br.com.dms.dms.repository;

import br.com.dms.dms.model.AgendamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<AgendamentoModel, Long> {
    List<AgendamentoModel> findByPacienteNmPacienteContainingIgnoreCase(String nome);
    List<AgendamentoModel> findByFuncionarioIdFuncionarioAndDtConsulta(Long idFuncionario, LocalDate data);
}
