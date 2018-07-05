package br.univille.dsiodontosys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.univille.dsiodontosys.model.Estoque;
import br.univille.dsiodontosys.model.Material;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
	
}
