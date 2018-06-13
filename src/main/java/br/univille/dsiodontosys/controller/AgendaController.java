package br.univille.dsiodontosys.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.univille.dsiodontosys.model.Agenda;
import br.univille.dsiodontosys.model.Paciente;
import br.univille.dsiodontosys.repository.AgendaRepository;
import br.univille.dsiodontosys.repository.PacienteRepository;

@Controller
@RequestMapping("/agenda")
public class AgendaController {
	
	@Autowired
	private AgendaRepository agendaRepository;
	
	@Autowired
	private PacienteRepository pacienteRepository;
	
	
	@GetMapping("")
	public ModelAndView index() {
		List<Agenda> listaConsulta = this.agendaRepository.findAll();
		
		return new ModelAndView("agenda/index", "listacons", listaConsulta);
	}
	
	
	@GetMapping("/AgendarConsulta")
	public ModelAndView agendaConsulta(@ModelAttribute Agenda agenda) {
		//List<Agenda> listaConsulta = this.agendaRepository.findAll();
		
		List<Paciente> listaPacientes = pacienteRepository.findAll();
		
		return new ModelAndView("agenda/form","listapacientes", listaPacientes);
	}
	
	@PostMapping(params = "form")
	public ModelAndView save(@Valid Agenda agenda, BindingResult result, RedirectAttributes redirect) {

		agenda = this.agendaRepository.save(agenda);

		return new ModelAndView("redirect:/agenda");
	}
	

}
