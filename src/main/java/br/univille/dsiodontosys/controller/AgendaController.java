package br.univille.dsiodontosys.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
import br.univille.dsiodontosys.repository.AgendaRepository;
import br.univille.dsiodontosys.repository.PacienteRepository;
import br.univille.dsiodontosys.repository.ProcedimentoRepository;
import br.univille.dsiodontosys.repository.StatusConsultaRepository;

@Controller
@RequestMapping("/agenda")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AgendaController {

	@Autowired
	private AgendaRepository agendaRepository;

	@Autowired
	private PacienteRepository pacienteRepository;

	@Autowired
	private ProcedimentoRepository procedimentoRepository;

	@Autowired
	private StatusConsultaRepository statusConsultaRepository;

	@GetMapping("")
	public ModelAndView index() {
		List<Agenda> listaConsulta = this.agendaRepository.findAll();

		return new ModelAndView("agenda/index", "listacons", listaConsulta);
	}

	@GetMapping("/AgendarConsulta")
	public ModelAndView agendaConsulta(@ModelAttribute Agenda agenda) {

		List<Paciente> listaPacientes = pacienteRepository.findAll();
		List<StatusConsulta> listaStatus = statusConsultaRepository.findAll();
		List<Procedimento> listaProcedimentos = procedimentoRepository.findAll();
		List<Dentista> listaDentistas = new ArrayList<Dentista>();

		HashMap<String, Object> dados = new HashMap<String, Object>();
		dados.put("listapacientes", listaPacientes);
		dados.put("listaStatusConsulta", listaStatus);
		dados.put("listaprocedimentos", listaProcedimentos);
		dados.put("listadentistas", listaDentistas);

		return new ModelAndView("agenda/form", dados);
	}

	@PostMapping(params = "form", value = "/atualizarmedicos")
	public ModelAndView atualizarmedicos(Agenda agenda, BindingResult result, RedirectAttributes redirect) {

		List<Dentista> listaDentistas = new ArrayList<Dentista>();

		if (agenda.getProcedimento() != null) {
			listaDentistas.addAll(agenda.getProcedimento().getListaDentistasAutorizados());
		}

		List<Paciente> listaPacientes = pacienteRepository.findAll();
		List<StatusConsulta> listaStatus = statusConsultaRepository.findAll();
		List<Procedimento> listaProcedimentos = procedimentoRepository.findAll();

		HashMap<String, Object> dados = new HashMap<String, Object>();
		dados.put("listapacientes", listaPacientes);
		dados.put("listaStatusConsulta", listaStatus);
		dados.put("listaprocedimentos", listaProcedimentos);
		dados.put("listadentistas", listaDentistas);

		return new ModelAndView("agenda/form", dados);
	}

	@PostMapping(params = "form")
	public ModelAndView save(@Valid Agenda agenda, BindingResult result, RedirectAttributes redirect) {
		agenda = this.agendaRepository.save(agenda);

		return new ModelAndView("redirect:/agenda");
	}

	@GetMapping(value = "/alterar/{id}")
	public ModelAndView alterarForm(@PathVariable("id") Agenda agenda) {
		List<Paciente> listaPacientes = this.pacienteRepository.findAll();
		List<StatusConsulta> listaStatus = statusConsultaRepository.findAll();
		List<Procedimento> listaProcedimentos = procedimentoRepository.findAll();
		List<Dentista> listaDentistas = new ArrayList<Dentista>();

		if (agenda.getProcedimento() != null) {
			listaDentistas.addAll(agenda.getProcedimento().getListaDentistasAutorizados());
		}
		
		HashMap<String, Object> dados = new HashMap<String, Object>();
		dados.put("agenda", agenda);
		dados.put("listapacientes", listaPacientes);
		dados.put("listaStatusConsulta", listaStatus);
		dados.put("listaprocedimentos", listaProcedimentos);
		dados.put("listadentistas", listaDentistas);

		return new ModelAndView("agenda/formalterar", dados);
	}

}
