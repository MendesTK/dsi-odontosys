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

import br.univille.dsiodontosys.model.Dentista;
import br.univille.dsiodontosys.repository.DentistaRepository;

@Controller
@RequestMapping("/dentista")
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
public class DentistaController {

	@Autowired
	private DentistaRepository dentistaRepository;

	@GetMapping("")
	public ModelAndView index() {
		List<Dentista> listaDentista = this.dentistaRepository.findAll();

		return new ModelAndView("dentista/index", "listadent", listaDentista);
	}

	@GetMapping("/novo")
	public String createForm(@ModelAttribute Dentista dentista) {
		return "dentista/form";
	}

	@PostMapping(params = "form")
	public ModelAndView save(@Valid Dentista dentista, BindingResult result, RedirectAttributes redirect) {

		dentista = this.dentistaRepository.save(dentista);

		return new ModelAndView("redirect:/dentista");
	}

	@GetMapping(value = "/alterar/{id}")
	public ModelAndView alterarForm(@PathVariable("id") Dentista dentista) {
		return new ModelAndView("dentista/form", "dentista", dentista);
	}

	@GetMapping(value = "remover/{id}")
	public ModelAndView remover(@PathVariable("id") Long id, RedirectAttributes redirect) {
		this.dentistaRepository.deleteById(id);
		return new ModelAndView("redirect:/dentista");
	}
}
