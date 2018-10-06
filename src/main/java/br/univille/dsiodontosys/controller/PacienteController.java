package br.univille.dsiodontosys.controller;

import java.util.List;
import java.util.Optional;

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
import br.univille.dsiodontosys.repository.SystemUserRepository;

@Controller
@RequestMapping("/paciente")
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
public class PacienteController {

	@Autowired
	private PacienteRepository pacienteRepository;
	
	@Autowired
	private SystemUserRepository systemUserRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private String cpfAntigo;
	private SystemUser localuser;

	@GetMapping("")
	public ModelAndView index() {
		List<Paciente> listaPaciente = this.pacienteRepository.findAll();

		return new ModelAndView("paciente/index", "listapac", listaPaciente);
	}

	@GetMapping("/novo")
	public String createForm(@ModelAttribute Paciente paciente) {
		paciente.setUser(new SystemUser());
		return "paciente/form";
	}

	@PostMapping(params = "form")
	public ModelAndView save(@Valid Paciente paciente, BindingResult result, RedirectAttributes redirect) {
		paciente.getUser().setUsername(paciente.getEmail());
		paciente.getUser().setRole("ROLE_USER");
		paciente.getUser().setPassword((passwordEncoder.encode(paciente.getUser().getPassword())));

		Optional<Paciente> cpfPaci = this.pacienteRepository.findByCpf(paciente.getCpf());
		if (cpfPaci.isPresent()) {
			System.out.println(cpfPaci.get().getCpf() + " já existe carai!");
		} else {

			paciente = this.pacienteRepository.save(paciente);
		}

		return new ModelAndView("redirect:/paciente");
	}

	@PostMapping(params = "formalterar")
	public ModelAndView saveAlteracao(@Valid Paciente paciente, BindingResult result, RedirectAttributes redirect) {
		localuser.setUsername(paciente.getEmail());
		paciente.setUser(localuser);
		Optional<Paciente> cpfPaci = this.pacienteRepository.findByCpf(paciente.getCpf());
		if (cpfAntigo.equals(paciente.getCpf())) {
			paciente = this.pacienteRepository.save(paciente);
		} else if (cpfPaci.isPresent()) {
			System.out.println(cpfPaci.get().getCpf() + " já existe carai!");
		} else {
			paciente = this.pacienteRepository.save(paciente);
		}
		return new ModelAndView("redirect:/paciente");
	}
	
	@PostMapping(params = "formalterarsenha")
	public ModelAndView saveAlterSenha(@Valid SystemUser systemUser, BindingResult result, RedirectAttributes redirect) {
		systemUser = this.systemUserRepository.save(systemUser);
		return new ModelAndView("redirect:/paciente");
	}

	@GetMapping(value = "/alterar/{id}")
	public ModelAndView alterarForm(@PathVariable("id") Paciente paciente) {
		cpfAntigo = paciente.getCpf();
		localuser = paciente.getUser();
		return new ModelAndView("paciente/formalterar", "paciente", paciente);
	}
	
	@GetMapping(value = "/alterarsenha/{id}")
	public ModelAndView alterarSenhaForm(@PathVariable("id") SystemUser systemUser) {
		
		return new ModelAndView("paciente/formalterarsenha", "systemUser", systemUser);
	}

	@GetMapping(value = "remover/{id}")
	public ModelAndView remover(@PathVariable("id") Paciente paciente, RedirectAttributes redirect) {
		this.pacienteRepository.deleteById(paciente.getId());
		this.systemUserRepository.deleteById(paciente.getUser().getId());
		return new ModelAndView("redirect:/paciente");
	}
}