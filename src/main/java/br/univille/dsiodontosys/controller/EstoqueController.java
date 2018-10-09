package br.univille.dsiodontosys.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.univille.dsiodontosys.model.Estoque;
import br.univille.dsiodontosys.model.Material;
import br.univille.dsiodontosys.repository.EstoqueRepository;
import br.univille.dsiodontosys.repository.MaterialRepository;

@Controller
@RequestMapping("/estoque")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class EstoqueController {

	@Autowired
	private EstoqueRepository estoqueRepository;

	@Autowired
	private MaterialRepository materialRepository;

	@GetMapping("")
	public ModelAndView index() {
		List<Estoque> listaEstoque = this.estoqueRepository.findAll();

		return new ModelAndView("estoque/index", "listaestoque", listaEstoque);
	}

	@GetMapping("/NovoMovimento")
	public ModelAndView movimentaEstoque(@ModelAttribute Estoque estoque) {
		List<Material> listaMaterial = this.materialRepository.findAll();

		HashMap<String, Object> dados = new HashMap<String, Object>();
		dados.put("listamaterial", listaMaterial);
		return new ModelAndView("estoque/form", dados);

	}

	@PostMapping(params = "form")
	public ModelAndView save(@Valid Estoque estoque, BindingResult result, RedirectAttributes redirect) {
		Date dataHoraAtual = new Date();
		estoque.setData(dataHoraAtual);
		
		Optional<Material> material = materialRepository.findById(estoque.getMaterialMovimentado().getId());
		
		if(material.isPresent()) {
			Material mat = material.get();
			if(estoque.getMovimento().equalsIgnoreCase("entrada")) {
				mat.setTotalEstoque(mat.getTotalEstoque() + estoque.getQuantidade());
			}else {
				mat.setTotalEstoque(mat.getTotalEstoque() - estoque.getQuantidade());
			}
			materialRepository.save(mat);
		}
		
		estoque = this.estoqueRepository.save(estoque);

		return new ModelAndView("redirect:/estoque");
	}

}
