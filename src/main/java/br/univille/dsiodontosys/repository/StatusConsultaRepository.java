package br.univille.dsiodontosys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.univille.dsiodontosys.model.StatusConsulta;

@Repository
public interface StatusConsultaRepository extends JpaRepository<StatusConsulta, Long>{
	
	public StatusConsulta findByStatus(String status);

}
