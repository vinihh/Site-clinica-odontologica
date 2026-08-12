package br.com.dms.dms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.dms.dms.model.EmailPacModel;

public interface EmailPacRepository extends JpaRepository<EmailPacModel, Long> {}