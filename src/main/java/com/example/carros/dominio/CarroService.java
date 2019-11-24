package com.example.carros.dominio;

import com.example.carros.dto.CarroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarroService {
	
	@Autowired
	private CarroRepository carroRepository;
	
	public List<CarroDTO> getCarros() {
		return carroRepository.findAll().stream().map(CarroDTO::create).collect(Collectors.toList());
	}

	public CarroDTO findById(Long id) {
		return carroRepository.findById(id)
				.map(CarroDTO::create)
				.orElseThrow(() -> new ObjectNotFoundException("Carro não encontrado"));
	}

	public List<CarroDTO> findByTipo(String tipo) {
		return carroRepository.findByTipo(tipo).stream().map(CarroDTO::create).collect(Collectors.toList());
	}

	public CarroDTO save(Carro carro) {
		Assert.isNull(carro.getId(), "Não foi possível atualizar o registro");
		return CarroDTO.create(carroRepository.save(carro));
	}

	public CarroDTO update(Long id, Carro carro) {
		return carroRepository.findById(id).map(carroDataBase -> {
			carroDataBase.setNome(carro.getNome());
			carroDataBase.setTipo(carro.getTipo());
			
			carroRepository.save(carroDataBase);
			return CarroDTO.create(carroDataBase);
		}).orElse(null);
	}

	public void delete(Long id) {
		carroRepository.deleteById(id);
	}
}
