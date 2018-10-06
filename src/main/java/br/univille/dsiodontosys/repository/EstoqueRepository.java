package br.univille.dsiodontosys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.univille.dsiodontosys.model.Estoque;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
	
}
