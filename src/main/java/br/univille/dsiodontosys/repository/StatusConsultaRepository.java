package br.univille.dsiodontosys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.univille.dsiodontosys.model.StatusConsulta;

@Repository
public interface StatusConsultaRepository extends JpaRepository<StatusConsulta, Long>{
	
	public List<StatusConsulta> findByStatus(String status);

}
