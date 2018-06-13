package br.univille.dsiodontosys.repository;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.univille.dsiodontosys.model.Agenda;
import br.univille.dsiodontosys.model.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {


}
