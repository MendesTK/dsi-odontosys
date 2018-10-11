package br.univille.dsiodontosys.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.univille.dsiodontosys.model.Agenda;
import br.univille.dsiodontosys.model.Dentista;
import br.univille.dsiodontosys.model.Paciente;
import br.univille.dsiodontosys.model.Procedimento;
import br.univille.dsiodontosys.model.StatusConsulta;
import br.univille.dsiodontosys.model.SystemUser;
import br.univille.dsiodontosys.repository.AgendaRepository;
import br.univille.dsiodontosys.repository.PacienteRepository;
import br.univille.dsiodontosys.repository.ProcedimentoRepository;
import br.univille.dsiodontosys.repository.StatusConsultaRepository;
import br.univille.dsiodontosys.repository.SystemUserRepository;
import br.univille.dsiodontosys.service.MyUserDetailsService;

@Controller
@RequestMapping("/cliente-area")
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
public class ClientAreaController {
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Autowired
	private SystemUserRepository systemUserRepository;
	
	@Autowired
	private AgendaRepository agendaRepository;
	
	@Autowired
	private PacienteRepository pacienteRepository;
	
	@Autowired
	private ProcedimentoRepository procedimentoRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private StatusConsultaRepository statusConsultaRepository;
	
	
	@GetMapping("")
	public ModelAndView index(@ModelAttribute Agenda agenda) {

		return new ModelAndView("cliente-area/index");
	}
	
	@GetMapping("/consulta")
	public ModelAndView agenda(@ModelAttribute Agenda agenda) {
		
		List<Agenda> consultaPaciente = agendaRepository.findByPacienteUser(myUserDetailsService.getUserLogged());
		
		return new ModelAndView("cliente-area/consulta/index", "listacons", consultaPaciente);
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
	
	@PostMapping(params = "form",value="/consulta/AgendarConsulta=")
	public ModelAndView atualizarmedicos(Agenda agenda, BindingResult result, RedirectAttributes redirect) {
		
		List<Dentista> listaDentistas = new ArrayList<Dentista>();
		
		if (agenda.getProcedimento()!= null) {
			listaDentistas.addAll(agenda.getProcedimento().getListaDentistasAutorizados());
		}
		
		List<Paciente> paciente = pacienteRepository.findByUser(myUserDetailsService.getUserLogged());
		StatusConsulta status = statusConsultaRepository.findByStatus("Pendente");
		List<Procedimento> listaProcedimentos = procedimentoRepository.findAll();
		
		HashMap<String, Object> dados = new HashMap<String, Object>();
		dados.put("paciente", paciente);
		dados.put("status", status);
		dados.put("listaprocedimentos", listaProcedimentos);
		dados.put("listadentistas", listaDentistas);
		
		return new ModelAndView("cliente-area/consulta/form", dados);
	}
	
	@PostMapping(params = "form", value="/consulta")
	public ModelAndView saveConsulta(@Valid Agenda agenda, BindingResult result, RedirectAttributes redirect) {		
		
		agenda = this.agendaRepository.save(agenda);
		
		return new ModelAndView("redirect:/cliente-area/consulta");
	}
	
	@GetMapping(value = "/consulta/alterar/{id}")
	public ModelAndView alterarForm(@PathVariable("id") Agenda agenda) {
		List<Paciente> paciente = pacienteRepository.findByUser(myUserDetailsService.getUserLogged());
		StatusConsulta status = statusConsultaRepository.findByStatus("Pendente");
		List<Procedimento> listaProcedimentos = procedimentoRepository.findAll();
		List<Dentista> listaDentistas = new ArrayList<Dentista>();

		if (agenda.getProcedimento() != null) {
			listaDentistas.addAll(agenda.getProcedimento().getListaDentistasAutorizados());
		}
		
		HashMap<String, Object> dados = new HashMap<String, Object>();
		dados.put("agenda", agenda);
		dados.put("paciente", paciente);
		dados.put("status", status);
		dados.put("listaprocedimentos", listaProcedimentos);
		dados.put("listadentistas", listaDentistas);
		
		return new ModelAndView("cliente-area/consulta/form", dados);
	}
	
	//------------------------
	
	
	
	@GetMapping("/cadastro")
	public ModelAndView cadastro(@ModelAttribute Paciente paciente) {
		
		List<Paciente> consultaPaciente = pacienteRepository.findByUser(myUserDetailsService.getUserLogged());
		paciente = consultaPaciente.get(0);
		
		return new ModelAndView("cliente-area/cadastro/index", "paciente", paciente);
	}
	
	@GetMapping("/cadastro/alterar")
	public ModelAndView alterarCadastro(@ModelAttribute Paciente paciente) {
		
		List<Paciente> consultaPaciente = pacienteRepository.findByUser(myUserDetailsService.getUserLogged());
		paciente = consultaPaciente.get(0);
		
		return new ModelAndView("cliente-area/cadastro/form", "paciente", paciente);
	}
	
	@PostMapping(params = "form", value="/cadastro")
	public ModelAndView saveCadastroAlteracao(@Valid Paciente paciente, BindingResult result, RedirectAttributes redirect) {
		paciente.setUser(myUserDetailsService.getUserLogged());
		paciente.getUser().setUsername(paciente.getEmail());

		paciente = this.pacienteRepository.save(paciente);
		
		return new ModelAndView("redirect:/cliente-area/cadastro");
	}
	
	@GetMapping("/cadastro/alterarsenha")
	public ModelAndView alterarSenhaForm(@ModelAttribute SystemUser systemUser) {
		
		systemUser = myUserDetailsService.getUserLogged();
		
		return new ModelAndView("cliente-area/cadastro/formalterarsenha", "systemUser", systemUser);
	}
	
	@PostMapping(params = "form",value="/cadastro/alterarsenha")
	public ModelAndView saveAlterSenha(@Valid SystemUser systemUser, BindingResult result, RedirectAttributes redirect) {
		SystemUser systemUserold = systemUserRepository.getOne(systemUser.getId());
		
		systemUserold.setPassword(passwordEncoder.encode(systemUser.getPassword()));
		systemUser = this.systemUserRepository.save(systemUserold);
		return new ModelAndView("redirect:/cliente-area/cadastro");
	}
	
	
	
}
