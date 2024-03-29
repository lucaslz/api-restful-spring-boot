package com.example.carros.dto;

import com.example.carros.dominio.Carro;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class CarroDTO {

	private Long id;
	private String tipo;
	private String nome;
	private String descricao;
	private String urlFoto;
	private String urlVideo;
	private String latitude;
	private String longitude;
	
	public static CarroDTO create(Carro c) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(c, CarroDTO.class);
	}
}
