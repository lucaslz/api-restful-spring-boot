package com.example.carros.api;

import java.net.URI;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	public ResponseEntity<?> post(@RequestBody Carro carro) {
		try {
			CarroDTO c = carroService.save(carro);		
			URI location = getURI(c.getId());
			return ResponseEntity.created(location).build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	private URI getURI(Long id) {
		return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(id).toUri();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> put(@PathVariable("id") Long id, @RequestBody Carro carro) {
		CarroDTO c = carroService.update(id, carro);
		return c != null ?
				ResponseEntity.ok(c) :
					ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		boolean ok = carroService.delete(id);
		
		return ok ?
				ResponseEntity.ok().build() :
					ResponseEntity.notFound().build();
	}
}
