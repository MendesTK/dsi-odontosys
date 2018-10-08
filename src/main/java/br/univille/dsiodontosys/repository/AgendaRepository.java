package br.univille.dsiodontosys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.univille.dsiodontosys.model.Agenda;
import br.univille.dsiodontosys.model.SystemUser;


@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {
	List<Agenda> findByPacienteUser(SystemUser user);
}