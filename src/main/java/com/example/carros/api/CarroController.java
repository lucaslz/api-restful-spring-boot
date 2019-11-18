package com.example.carros.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.carros.dominio.CarroService;
import com.example.carros.dto.CarroDTO;
import com.example.carros.dominio.Carro;

@RestController
@RequestMapping("/api/v1/carros")
public class CarroController {
	
	@Autowired
	private CarroService carroService;
	
	@GetMapping
	public ResponseEntity<?> getCarros() {
		List<CarroDTO> carroDTO = carroService.getCarros();
		
		return !carroDTO.isEmpty() ?
				ResponseEntity.ok(carroDTO) :
					ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getCarro(@PathVariable("id") Long id) {
		return carroService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.noContent().build());
	}
	
	@GetMapping("/tipo/{tipo}")
	public ResponseEntity<?> findByTipo(@PathVariable("tipo") String tipo) {
		List<CarroDTO> carroDTO =  carroService.findByTipo(tipo);
		
		return !carroDTO.isEmpty() ?
				ResponseEntity.ok(carroDTO) :
					ResponseEntity.noContent().build();
	}
	
	@PostMapping
	public String post(@RequestBody Carro carro) {
		Carro carroSave = carroService.save(carro);
		return "Carro salvo com sucesso: " + carroSave.getId();
	}
	
	@PutMapping("/{id}")
	public String put(@PathVariable("id") Long id, @RequestBody Carro carro) {
		Carro c = carroService.update(id, carro);
		return "Carro atualizado com sucesso: " + c.getId();
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") Long id) {
		carroService.delete(id);
		return "Carro deletado com sucesso: " + id;
	}
}
