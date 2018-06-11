package br.univille.dsiodontosys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.univille.dsiodontosys.model.Agenda;


@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {

}