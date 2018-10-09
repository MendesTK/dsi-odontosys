package br.univille.dsiodontosys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.univille.dsiodontosys.model.StatusConsulta;
import br.univille.dsiodontosys.model.SystemUser;
import br.univille.dsiodontosys.repository.SystemUserRepository;
import br.univille.dsiodontosys.repository.StatusConsultaRepository;

@Component
public class StartupEventListenerBean {

	@Autowired
    private SystemUserRepository systemUserRepository;
	
	@Autowired
	private StatusConsultaRepository statusConsultaRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(systemUserRepository.findByUsername("user") == null) {
            SystemUser user = new SystemUser();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user"));
            user.setRole("ROLE_USER");
            systemUserRepository.save(user);
        }
        
        if(systemUserRepository.findByUsername("admin") == null) {
            SystemUser user = new SystemUser();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setRole("ROLE_ADMIN");
            systemUserRepository.save(user);
        }
        
        //Criar os status padrão
        if(statusConsultaRepository.findByStatus("Pendente") == null) {
        	StatusConsulta status = new StatusConsulta();
        	status.setStatus("Pendente");
        	status.setDescricao("Consulta a ser realizada.");
        	statusConsultaRepository.save(status);
        }
        
        if(statusConsultaRepository.findByStatus("Finalizado") == null) {
        	StatusConsulta status = new StatusConsulta();
        	status.setStatus("Finalizado");
        	status.setDescricao("Consulta finalizada.");
        	statusConsultaRepository.save(status);
        }
       
       


    }
}
