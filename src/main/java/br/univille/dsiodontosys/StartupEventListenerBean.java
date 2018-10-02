package br.univille.dsiodontosys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.univille.dsiodontosys.model.SystemUser;
import br.univille.dsiodontosys.repository.SystemUserRepository;

@Component
public class StartupEventListenerBean {

	@Autowired
    private SystemUserRepository systemUserRepository;
	
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
        
        if(systemUserRepository.findByUsername("teste") == null) {
            SystemUser user = new SystemUser();
            user.setUsername("teste");
            user.setPassword(passwordEncoder.encode("teste"));
            user.setRole("ROLE_ADMIN");
            user.setClientCredential(true);
            systemUserRepository.save(user);
        }


    }
}
