package br.univille.dsiodontosys.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.univille.dsiodontosys.model.Agenda;
import br.univille.dsiodontosys.model.Dentista;
import br.univille.dsiodontosys.model.Paciente;
import br.univille.dsiodontosys.model.Procedimento;
import br.univille.dsiodontosys.model.StatusConsulta;
import br.univille.dsiodontosys.repository.AgendaRepository;
import br.univille.dsiodontosys.repository.DentistaRepository;
import br.univille.dsiodontosys.repository.PacienteRepository;
import br.univille.dsiodontosys.repository.ProcedimentoRepository;
import br.univille.dsiodontosys.repository.StatusConsultaRepository;
import br.univille.dsiodontosys.service.MyUserDetailsService;

@Controller
@RequestMapping("/cliente-area")
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
public class ClientAreaController {
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Autowired
	private AgendaRepository agendaRepository;
	
	@Autowired
	private PacienteRepository pacienteRepository;
	
	@Autowired
	private ProcedimentoRepository procedimentoRepository;
	
	@Autowired
	private DentistaRepository dentistaRepository;
	
	@Autowired
	private StatusConsultaRepository statusConsultaRepository;
	
	@GetMapping("")
	public ModelAndView index(@ModelAttribute Agenda agenda) {

		return new ModelAndView("cliente-area/index");
	}
	
	@GetMapping("/consulta")
	public ModelAndView consultas(@ModelAttribute Agenda agenda) {
		
		List<Agenda> consultasPaciente = agendaRepository.findByPacienteUser(myUserDetailsService.getUserLogged());
		
		return new ModelAndView("cliente-area/consulta/index", "listacons", consultasPaciente);
	}
	
	@GetMapping("/consulta/AgendarConsulta")
	public ModelAndView agendaConsulta(@ModelAttribute Agenda agenda) {	
		List<Paciente> paciente = pacienteRepository.findByUser(myUserDetailsService.getUserLogged());
		StatusConsulta status = statusConsultaRepository.findByStatus("Pendente");
		List<Procedimento> listaProcedimentos = procedimentoRepository.findAll();
		List<Dentista> listaDentistas = new ArrayList<Dentista>();
		
		HashMap<String, Object> dados = new HashMap<String, Object>();
		dados.put("paciente", paciente);
		dados.put("status", status);
		dados.put("listaprocedimentos", listaProcedimentos);
		dados.put("listadentistas", listaDentistas);
		
		return new ModelAndView("cliente-area/consulta/form", dados);
	}
	
	
	
}
