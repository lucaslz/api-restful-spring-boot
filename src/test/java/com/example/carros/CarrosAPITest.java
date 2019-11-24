package com.example.carros;

import com.example.carros.dominio.Carro;
import com.example.carros.dto.CarroDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CarrosApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarrosAPITest {
    @Autowired
    protected TestRestTemplate rest;

    private ResponseEntity<CarroDTO> getCarro(String url) {
        return rest.withBasicAuth("admin", "123").getForEntity(url, CarroDTO.class);
    }

    private ResponseEntity<List<CarroDTO>> getCarros(String url) {
        return rest.withBasicAuth("admin", "123").exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CarroDTO>>() {
        });
    }

    @Test
    public void testSave() {
        Carro carro = new Carro();
        carro.setNome("Porshe");
        carro.setTipo("esportivos");

        //Inserindo carro
        ResponseEntity<?> response = rest.withBasicAuth("admin", "123").postForEntity("/api/v1/carros", carro, null);

        //Verificando se o carro foi criado
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        //Bucando o carro
        String location = response.getHeaders().get("location").get(0);
        System.err.println("Location: " + location);
        CarroDTO c = getCarro(location).getBody();
        assertNotNull(c);

        //Deletando o carro
        rest.delete(location);

        //Verificar se deletou
        assertEquals(HttpStatus.OK, getCarro(location).getStatusCode());
    }

    @Test
    public void testLista() {
        List<CarroDTO> carros = getCarros("/api/v1/carros").getBody();
        assertNotNull(carros);
        assertEquals(30, carros.size());
    }

    @Test
    public void testListaPorTipo() {
        assertEquals(10, getCarros("/api/v1/carros/tipo/classicos").getBody().size());
        assertEquals(11, getCarros("/api/v1/carros/tipo/esportivos").getBody().size());
        assertEquals(10, getCarros("/api/v1/carros/tipo/luxo").getBody().size());

        assertEquals(HttpStatus.NO_CONTENT, getCarros("/api/v1/carros/tipo/xxx").getStatusCode());
    }

    @Test
    public void testGetOk() {
        ResponseEntity<CarroDTO> responseEntity = getCarro("/api/v1/carros/11");
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

        CarroDTO c = responseEntity.getBody();
        assertEquals("Ferrari FF", c.getNome());
    }

    @Test
    public void testGetNotFound() {
        ResponseEntity<?> responseEntity = getCarro("/api/v1/carros/1100");
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NO_CONTENT);
    }
}