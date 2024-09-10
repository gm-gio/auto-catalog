package com.java.course.auto_catalog.controller.test;

import com.java.course.auto_catalog.dto.ManufacturerDTO;
import com.java.course.auto_catalog.dto.ManufacturerWithoutIdDTO;
import com.java.course.auto_catalog.service.ManufacturerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ManufacturerControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private ManufacturerService manufacturerService;

    private String baseUrl;

    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/api/v1/manufacturers";
    }


    @Test
    void shouldCreatNewManufacturerWithoutID() {

        ManufacturerWithoutIdDTO manufacturerWithoutIdDTO = new ManufacturerWithoutIdDTO("Lada");

        ManufacturerDTO savedManufacturerDTO = new ManufacturerDTO();
        savedManufacturerDTO.setManufacturerId(1L);
        savedManufacturerDTO.setManufacturerName("Lada");
        when(manufacturerService.save(manufacturerWithoutIdDTO)).thenReturn(savedManufacturerDTO);

        ResponseEntity<ManufacturerDTO> responseEntity = restTemplate.postForEntity(baseUrl + "/new", manufacturerWithoutIdDTO, ManufacturerDTO.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(Objects.requireNonNull(responseEntity.getBody()).getManufacturerId()).isEqualTo(1L);
        assertThat(responseEntity.getBody().getManufacturerName()).isEqualTo("Lada");
    }

    @Test
    void shouldDeleteManufacturerById() {
        Long manufacturerId = 1L;

        ResponseEntity<Void> responseEntity = restTemplate.exchange(baseUrl + "/" + manufacturerId, HttpMethod.DELETE, null, Void.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }


    @Test
    void shouldGetManufacturerById() {

        Long manufacturerId = 1L;

        ManufacturerDTO manufacturerDTO = new ManufacturerDTO(1L, "Toyota");

        when(manufacturerService.getManufacturerById(manufacturerId)).thenReturn(manufacturerDTO);

        ResponseEntity<ManufacturerDTO> responseEntity = restTemplate.getForEntity(baseUrl + "/" + manufacturerId, ManufacturerDTO.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(Objects.requireNonNull(responseEntity.getBody()).getManufacturerId()).isEqualTo(manufacturerId);
        assertThat(responseEntity.getBody().getManufacturerName()).isEqualTo("Toyota");
    }

    @Test
    void shouldGetAllManufacturers() {

        ManufacturerDTO manufacturer1 = new ManufacturerDTO(1L, "Lada");
        ManufacturerDTO manufacturer2 = new ManufacturerDTO(2L, "Toyota");

        List<ManufacturerDTO> manufacturers = Arrays.asList(manufacturer1, manufacturer2);

        when(manufacturerService.getAllManufacturers()).thenReturn(manufacturers);

        ResponseEntity<List<ManufacturerDTO>> responseEntity = restTemplate.exchange(
                baseUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ManufacturerDTO>>() {
                }
        );

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody()).hasSize(2);
        assertThat(responseEntity.getBody()).containsExactlyInAnyOrder(manufacturer1, manufacturer2);
    }
}
