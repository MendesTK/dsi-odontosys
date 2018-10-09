package br.univille.dsiodontosys.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.univille.dsiodontosys.model.Dentista;
import br.univille.dsiodontosys.model.Procedimento;
import br.univille.dsiodontosys.repository.DentistaRepository;
import br.univille.dsiodontosys.repository.ProcedimentoRepository;
import br.univille.dsiodontosys.valueobject.DentistaSelecionado;

@Controller
@RequestMapping("/procedimento")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class ProcedimentoController {

	@Autowired
	private ProcedimentoRepository procedimentoRepository;

	@Autowired
	private DentistaRepository dentistaRepository;

	@GetMapping("")
	public ModelAndView index() {
		List<Procedimento> listaProcedimento = this.procedimentoRepository.findAll();

		return new ModelAndView("procedimento/index", "listaproc", listaProcedimento);
	}

	@GetMapping("/novo")
	public ModelAndView createForm(@ModelAttribute Procedimento procedimento, DentistaSelecionado dentistaSelecionado) {
		List<Dentista> listaDentista = this.dentistaRepository.findAll();
		
		
		HashMap<String, Object> dados = new HashMap<String, Object>();
		dados.put("listadentistas", listaDentista);
		dados.put("dentistaSelecionado", new DentistaSelecionado());
		
		
		
		return new ModelAndView("procedimento/form", dados);
	}
	
	@PostMapping(params = "save")
	public ModelAndView save(Procedimento procedimento, DentistaSelecionado dentistaSelecionado, BindingResult result, RedirectAttributes redirect) {

		procedimento = this.procedimentoRepository.save(procedimento);

		return new ModelAndView("redirect:/procedimento");
	}
	
	@PostMapping(params= "insertdent")
    public ModelAndView insertproc(Procedimento procedimento, DentistaSelecionado dentistaSelecionado, BindingResult result, RedirectAttributes redirect) {
        List<Dentista> listaDentista = this.dentistaRepository.findAll();
        
		procedimento.getListaDentistasAutorizados().add(this.dentistaRepository.getOne(dentistaSelecionado.getDentistaSelecionado().getId()));
    
        HashMap<String, Object> dados = new HashMap<String, Object>();
        dados.put("listadentistas", listaDentista);
        dados.put("dentistaSelecionado", new DentistaSelecionado());
        
        
        return new ModelAndView("procedimento/form",dados);
    }
	@PostMapping(params= {"removedent"})
    public ModelAndView removeproc(@RequestParam(name = "removedent") int index, Procedimento procedimento, DentistaSelecionado dentistaSelecionado, BindingResult result, RedirectAttributes redirect) {
		List<Dentista> listaDentista = this.dentistaRepository.findAll();
        
    
		procedimento.getListaDentistasAutorizados().remove(index);
		
        HashMap<String, Object> dados = new HashMap<String, Object>();
        dados.put("listadentistas", listaDentista);
        dados.put("dentistaSelecionado", new DentistaSelecionado());
        
        
        return new ModelAndView("procedimento/form",dados);
    }

	@GetMapping(value = "/alterar/{id}")
	public ModelAndView alterarForm(@PathVariable("id") Procedimento procedimento) {
		List<Dentista> listaDentista = this.dentistaRepository.findAll();
    
        HashMap<String, Object> dados = new HashMap<String, Object>();
        dados.put("listadentistas", listaDentista);
        dados.put("dentistaSelecionado", new DentistaSelecionado());
        dados.put("procedimento", procedimento);
        
        
        return new ModelAndView("procedimento/form",dados);
	}

	@GetMapping(value = "remover/{id}")
	public ModelAndView remover(@PathVariable("id") Long id, RedirectAttributes redirect) {
		this.procedimentoRepository.deleteById(id);
		return new ModelAndView("redirect:/procedimento");
	}

}
