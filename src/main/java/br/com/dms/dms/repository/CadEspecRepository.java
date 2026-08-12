package br.com.dms.dms.repository;

import br.com.dms.dms.model.CadEspecModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CadEspecRepository extends JpaRepository<CadEspecModel, Long> {

    boolean existsByDsCpf(String cpf);

    boolean existsByDsCro(String cro);
}