package br.univille.dsiodontosys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.univille.dsiodontosys.model.SystemUser;

@Repository
public interface SystemUserRepository extends JpaRepository<SystemUser, Long> {
	
	public SystemUser findByUsername(String username);


}
