package br.univille.dsiodontosys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import br.univille.dsiodontosys.model.Procedimento;
import br.univille.dsiodontosys.repository.DentistaRepository;
import br.univille.dsiodontosys.repository.ProcedimentoRepository;

@Controller
@RequestMapping("/procedimento")
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
	public ModelAndView createForm(@ModelAttribute Procedimento procedimento) {
		List<Dentista> listaDentista = this.dentistaRepository.findAll();
		
		
		HashMap<String, Object> dados = new HashMap<String, Object>();
		dados.put("listadentistas", listaDentista);
		dados.put("nvdentistahabilitado", new Dentista());
		
		
		
		return new ModelAndView("procedimento/form", dados);
	}
	
	@PostMapping(params = "save")
	public ModelAndView save(Procedimento procedimento, Dentista nvdentistahabilitado, BindingResult result, RedirectAttributes redirect) {

		procedimento = this.procedimentoRepository.save(procedimento);

		return new ModelAndView("redirect:/procedimento");
	}
	
	@PostMapping(params= "insertdent")
    public ModelAndView insertproc(Procedimento procedimento, Dentista nvdentistahabilitado, BindingResult result, RedirectAttributes redirect) {
        List<Dentista> listaDentista = this.dentistaRepository.findAll();
        
    	procedimento.getListaDentistasAutorizados().add(nvdentistahabilitado);
    
        HashMap<String, Object> dados = new HashMap<String, Object>();
        dados.put("listadentistas", listaDentista);
        dados.put("nvdentistahabilitado", new Dentista());
        
        
        return new ModelAndView("procedimento/form",dados);
    }
    /*
	@PostMapping(params= {"removedent"})
    public ModelAndView removeproc(@RequestParam(name = "removeproc") int index, Consulta consulta, ProcedimentoRealizado novoprocrealizado, BindingResult result, RedirectAttributes redirect) {
        List<Medico> listaMedico = this.medicoRepository.findAll();
        List<Paciente> listaPaciente = this.pacienteRepository.findAll();
        List<Procedimento> listaProcedimento = this.procedimentoRepository.findAll();
        
        consulta.getListaProcedimentos().remove(index);
    
        HashMap<String, Object> dados = new HashMap<String, Object>();
        dados.put("consulta", consulta);
        dados.put("listaMedico", listaMedico);
        dados.put("listaPaciente", listaPaciente);
        dados.put("listaProcedimento", listaProcedimento);
        dados.put("novoprocrealizado", new ProcedimentoRealizado());
        
        
        return new ModelAndView("consulta/form",dados);
    }
*/
	@GetMapping(value = "/alterar/{id}")
	public ModelAndView alterarForm(@PathVariable("id") Procedimento procedimento) {
		return new ModelAndView("procedimento/form", "procedimento", procedimento);
	}

	@GetMapping(value = "remover/{id}")
	public ModelAndView remover(@PathVariable("id") Long id, RedirectAttributes redirect) {
		this.procedimentoRepository.deleteById(id);
		return new ModelAndView("redirect:/procedimento");
	}

}
