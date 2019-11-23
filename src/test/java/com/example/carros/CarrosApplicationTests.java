package com.example.carros;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.carros.dominio.Carro;
import com.example.carros.dominio.CarroService;
import com.example.carros.dto.CarroDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
class CarrosApplicationTests {
	
	@Autowired
	private CarroService carroService;
	
	@Test
	public void testSave() {
		Carro carro = new Carro();
		carro.setNome("Ferrari");
		carro.setTipo("Luxo");
		
		CarroDTO c = carroService.save(carro);
		assertNotNull(c);
		
		Long id = c.getId();
		assertNotNull(id);
		
		Optional<CarroDTO> op = carroService.findById(id);
		assertTrue(op.isPresent());
		
		c = op.get();
		assertEquals("Ferrari", c.getNome());
		assertEquals("Luxo", c.getTipo());
		
		carroService.delete(id);
		assertFalse(carroService.findById(id).isPresent());
	}
	
	@Test
	public void testLista() {
		List<CarroDTO> carros = carroService.getCarros();
		System.err.println("O tamanho é: " + carros.size());
		assertEquals(1, carros.size());
	}
}