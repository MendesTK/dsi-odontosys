package br.univille.dsiodontosys.controller;

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

import br.univille.dsiodontosys.model.StatusConsulta;
import br.univille.dsiodontosys.repository.StatusConsultaRepository;

@Controller
@RequestMapping("/statusConsulta")
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
public class StatusConsultaController {

	@Autowired
	private StatusConsultaRepository statusConsultaRepository;
	
	@GetMapping("")
	public ModelAndView index() {
		List<StatusConsulta> listaStatusConsulta = this.statusConsultaRepository.findAll();

		return new ModelAndView("statusConsulta/index", "listastatus", listaStatusConsulta);
	}
	
	@GetMapping("/novo")
	public String createForm(@ModelAttribute StatusConsulta statusConsulta) {
		return "statusConsulta/form";
	}

	@PostMapping(params = "form")
	public ModelAndView save(@Valid StatusConsulta statusConsulta, BindingResult result, RedirectAttributes redirect) {

		statusConsulta = this.statusConsultaRepository.save(statusConsulta);

		return new ModelAndView("redirect:/statusConsulta");
	}

	@GetMapping(value = "/alterar/{id}")
	public ModelAndView alterarForm(@PathVariable("id") StatusConsulta statusConsulta) {
		return new ModelAndView("statusConsulta/form", "statusConsulta", statusConsulta);
	}

	@GetMapping(value = "remover/{id}")
	public ModelAndView remover(@PathVariable("id") Long id, RedirectAttributes redirect) {
		this.statusConsultaRepository.deleteById(id);
		return new ModelAndView("redirect:/statusConsulta");
	}
	
}
