package br.com.dms.dms.repository;
import br.com.dms.dms.model.PagamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<PagamentoModel, Long> {}