package br.univille.dsiodontosys.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/cliente-area")
@PreAuthorize("hasRole('ROLE_USER')")
public class ClientAreaController {

	@GetMapping("")
	public ModelAndView index() {
		return new ModelAndView("cliente-area/index");
	}
	
}
