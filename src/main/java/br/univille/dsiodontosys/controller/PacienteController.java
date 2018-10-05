package br.univille.dsiodontosys.controller;

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

import br.univille.dsiodontosys.model.Paciente;
import br.univille.dsiodontosys.model.SystemUser;
import br.univille.dsiodontosys.repository.PacienteRepository;

@Controller
@RequestMapping("/paciente")
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
public class PacienteController {

	@Autowired
	private PacienteRepository pacienteRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("")
	public ModelAndView index() {
		List<Paciente> listaPaciente = this.pacienteRepository.findAll();

		return new ModelAndView("paciente/index", "listapac", listaPaciente);
	}

	@GetMapping("/novo")
	public String createForm(@ModelAttribute Paciente paciente) {
		return "paciente/form";
	}

	@PostMapping(params = "form")
	public ModelAndView save(@Valid Paciente paciente, BindingResult result, RedirectAttributes redirect) {
		
		paciente.getUser().setRole("ROLE_USER");
		paciente.getUser().setPassword((passwordEncoder.encode(paciente.getUser().getPassword())));
		
		paciente = this.pacienteRepository.save(paciente);
		

		return new ModelAndView("redirect:/paciente");
	}

	@GetMapping(value = "/alterar/{id}")
	public ModelAndView alterarForm(@PathVariable("id") Paciente paciente) {
		return new ModelAndView("paciente/form", "paciente", paciente);
	}

	@GetMapping(value="remover/{id}")
    public ModelAndView remover(@PathVariable ("id") Long id, RedirectAttributes redirect) {
        this.pacienteRepository.deleteById(id);
        return new ModelAndView("redirect:/paciente");
    }
}