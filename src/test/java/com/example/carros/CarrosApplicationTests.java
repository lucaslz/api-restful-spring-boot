package com.example.carros;

import com.example.carros.dominio.Carro;
import com.example.carros.dominio.CarroService;
import com.example.carros.dominio.ObjectNotFoundException;
import com.example.carros.dto.CarroDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

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

		c = carroService.findById(id);
		assertNotNull(c);
		
		assertEquals("Ferrari", c.getNome());
		assertEquals("Luxo", c.getTipo());
		
		carroService.delete(id);

		try {
			assertNull(carroService.findById(id));
			fail("O carro não existe no banco de dados");
		}catch (ObjectNotFoundException ex) {

		}
	}
	
	@Test
	public void testLista() {
		List<CarroDTO> carros = carroService.getCarros();
		System.err.println("Tamanho: " + carros.size());
		assertEquals(30, carros.size());
	}

	@Test
	public void testGet() {
		assertEquals(10, carroService.findByTipo("classicos").size());
		assertEquals(10, carroService.findByTipo("esportivos").size());
		assertEquals(10, carroService.findByTipo("luxo").size());

		assertEquals(0, carroService.findByTipo("x").size());
	}
}