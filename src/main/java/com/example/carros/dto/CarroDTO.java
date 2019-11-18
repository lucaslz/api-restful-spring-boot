package com.example.carros.dto;

import org.modelmapper.ModelMapper;

import com.example.carros.dominio.Carro;

import lombok.Data;

@Data
public class CarroDTO {

	private Long id;
	private String tipo;
	private String nome;
	
	public static CarroDTO create(Carro c) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(c, CarroDTO.class);
	}
}
