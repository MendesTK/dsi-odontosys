package br.univille.dsiodontosys.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.univille.dsiodontosys.model.Paciente;
import br.univille.dsiodontosys.model.SystemUser;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

	public Optional<Paciente> findByCpf(String cpf);
	
	public List<Paciente> findByUser(SystemUser user);


}
