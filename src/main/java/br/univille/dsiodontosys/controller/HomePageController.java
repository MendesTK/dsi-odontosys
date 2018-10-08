package br.univille.dsiodontosys.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.univille.dsiodontosys.service.MyUserDetailsService;

@Controller
@RequestMapping("/")
public class HomePageController {

	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@GetMapping("")
	public ModelAndView index() {
		return new ModelAndView("homepage");
	}
	
	@GetMapping("/redireciona")
	public ModelAndView redireciona() {
		Collection<? extends GrantedAuthority> colRoles = userDetailsService.getUserRoles();
		
		for(GrantedAuthority item : colRoles) {
			if(item.getAuthority().equals("ROLE_USER"))
				return new ModelAndView("redirect:/cliente-area");
		}
		
		return new ModelAndView("redirect:/home");
	}
}
