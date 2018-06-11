package br.univille.dsiodontosys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.univille.dsiodontosys.model.Dentista;
import br.univille.dsiodontosys.model.Paciente;

@Repository
public interface DentistaRepository extends JpaRepository<Dentista, Long> {

}
