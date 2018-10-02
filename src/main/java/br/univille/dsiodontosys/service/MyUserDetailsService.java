package br.univille.dsiodontosys.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.univille.dsiodontosys.model.SystemUser;
import br.univille.dsiodontosys.repository.SystemUserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
    private SystemUserRepository systemUserRepository;
 
	@Override
	public org.springframework.security.core.userdetails.User loadUserByUsername(String username) {
		SystemUser user = systemUserRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}else {
			if(!user.isEnabled()) {
				throw new AccessDeniedException("403 returned");
			}
		}

		Collection<SimpleGrantedAuthority> listGrants = new ArrayList<>();
		listGrants.add(new SimpleGrantedAuthority(user.getRole()));
		return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),listGrants);
	}

	public Collection<? extends GrantedAuthority> getUserRoles(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getAuthorities();
	} 

	public SystemUser getUserLogged() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		SystemUser user = systemUserRepository.findByUsername(auth.getName());
		return user;
	}

}
