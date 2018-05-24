package br.univille.dsiodontosys.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.univille.dsiodontosys.model.Cliente;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

	@GetMapping("")
	public ModelAndView index() {
		 List<Cliente> listaCliente = new ArrayList<Cliente>();
	        
	        Cliente p1 = new Cliente();
	        p1.setNome("Zezinho");
	        p1.setSexo("Masculino");
	        
	        listaCliente.add(p1);
	        
	        return new ModelAndView("Cliente/index","listapac",listaCliente);
	}
}
