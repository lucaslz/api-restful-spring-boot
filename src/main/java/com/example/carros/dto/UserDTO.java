package com.example.carros.dto;

import com.example.carros.dominio.Carro;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class UserDTO {

	private Long id;
	private String email;
	private String login;
	private String nome;
	private String senha;
	
	public static CarroDTO create(Carro c) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(c, CarroDTO.class);
	}
}
