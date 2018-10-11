package br.univille.dsiodontosys.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.univille.dsiodontosys.model.Paciente;
import br.univille.dsiodontosys.model.SystemUser;
import br.univille.dsiodontosys.repository.PacienteRepository;

@Controller
@RequestMapping("/register")
public class RegisterController {

	@Autowired
	private PacienteRepository pacienteRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	
	@GetMapping("")
	public String createForm(@ModelAttribute Paciente paciente) {
		paciente.setUser(new SystemUser());
        return "cliente-area/register";
	}
	
	
	@PostMapping(params = "form")
	public ModelAndView save(@Valid Paciente paciente, BindingResult result, RedirectAttributes redirect) {
		paciente.getUser().setUsername(paciente.getEmail());
		paciente.getUser().setRole("ROLE_USER");
		paciente.getUser().setPassword(passwordEncoder.encode(paciente.getUser().getPassword()));

		Optional<Paciente> cpfPaci = this.pacienteRepository.findByCpf(paciente.getCpf());
		if (cpfPaci.isPresent()) {
			System.out.println(cpfPaci.get().getCpf() + " já existe carai!");
		} else {

			paciente = this.pacienteRepository.save(paciente);
		}
		return new ModelAndView("redirect:/login");
	}

	
}
