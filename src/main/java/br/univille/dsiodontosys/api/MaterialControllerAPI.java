package br.univille.dsiodontosys.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.univille.dsiodontosys.model.Material;
import br.univille.dsiodontosys.repository.MaterialRepository;

@RestController
@RequestMapping("/api/materiais")
public class MaterialControllerAPI {
	
	@Autowired
	private MaterialRepository materialRepository;
	
	@GetMapping
	public ResponseEntity<List<Material>> listarMateriais() {
        List<Material> lista = materialRepository.findAll();
        return new ResponseEntity<List<Material>>(lista,HttpStatus.OK);
    }
	
	@PostMapping
    public ResponseEntity<?> save(@RequestBody Material material){
        materialRepository.save(material);
        return ResponseEntity.ok().build();
    }

}
