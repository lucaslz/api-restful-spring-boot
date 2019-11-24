package com.example.carros.api;

import com.example.carros.dominio.Carro;
import com.example.carros.dominio.CarroService;
import com.example.carros.dto.CarroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/carros")
public class CarroController {
	
	@Autowired
	private CarroService carroService;
	
	@GetMapping
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	public ResponseEntity<?> getCarros() {
		List<CarroDTO> carroDTO = carroService.getCarros();
		return !carroDTO.isEmpty() ?
				ResponseEntity.ok(carroDTO) :
					ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getCarro(@PathVariable("id") Long id) {
		CarroDTO carro = carroService.findById(id);
		return ResponseEntity.ok(carro);
	}
	
	@GetMapping("/tipo/{tipo}")
	public ResponseEntity<?> findByTipo(@PathVariable("tipo") String tipo) {
		List<CarroDTO> carroDTO =  carroService.findByTipo(tipo);
		
		return !carroDTO.isEmpty() ?
				ResponseEntity.ok(carroDTO) :
					ResponseEntity.noContent().build();
	}
	
	@PostMapping
	@Secured({"ROLE_ADMIN"})
	public ResponseEntity<?> post(@RequestBody Carro carro) {
		CarroDTO c = carroService.save(carro);
		URI location = getURI(c.getId());
		return ResponseEntity.created(location).build();
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
		return ResponseEntity.ok().build();
	}
}
