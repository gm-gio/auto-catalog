package com.java.course.auto_catalog.controller.test;

import com.java.course.auto_catalog.dto.*;
import com.java.course.auto_catalog.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private CarService carService;

    private String baseUrl;

    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/api/v1/manufacturers";
    }

    @Test
    public void shouldCreateNewCarWithManufacturerIdAndModelId() {

        Long manufacturerId = 1L;
        Long modelId = 1L;

        CarWithoutIdDTO carWithoutIdDTO = new CarWithoutIdDTO();
        CarDTO newCar = new CarDTO(1L, 2020);

        when(carService.save(manufacturerId, modelId, carWithoutIdDTO)).thenReturn(newCar);

        ResponseEntity<CarDTO> responseEntity = restTemplate.postForEntity(
                baseUrl + "/" + manufacturerId + "/models/" + modelId + "/cars/new",
                carWithoutIdDTO, CarDTO.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getCarId()).isEqualTo(1L);
        assertThat(responseEntity.getBody().getCarYear()).isEqualTo(2020);
    }

    @Test
    public void shouldRemoveCarWithID() {

        Long manufacturerId = 1L;
        Long modelId = 1L;
        Long carId = 1L;

        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                baseUrl + "/" + manufacturerId + "/models/" + modelId + "/cars/" + carId,
                HttpMethod.DELETE, null, Void.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }


    @Test
    public void shouldUpdateCar() {

        Long manufacturerId = 1L;
        Long modelId = 1L;
        Long carId = 1L;

        CarUpdateDTO carUpdateDTO = new CarUpdateDTO();
        CarDTO updatedCarDTO = new CarDTO(1L, 2020);

        when(carService.updateCar(manufacturerId, modelId, carId, carUpdateDTO)).thenReturn(updatedCarDTO);

        ResponseEntity<CarDTO> responseEntity = restTemplate.exchange(
                baseUrl + "/" + manufacturerId + "/models/" + modelId + "/cars/" + carId,
                HttpMethod.PUT, new HttpEntity<>(carUpdateDTO), CarDTO.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getCarId()).isEqualTo(1L);
        assertThat(responseEntity.getBody().getCarYear()).isEqualTo(2020);
    }

    @Test
    public void shouldGetCarById() {
        Long manufacturerId = 1L;
        Long modelId = 1L;
        Long carId = 1L;

        CarDTO carDTO = new CarDTO(1L, 2020);

        when(carService.findById(manufacturerId, modelId, carId)).thenReturn(carDTO);

        ResponseEntity<CarDTO> responseEntity = restTemplate.getForEntity(
                baseUrl + "/" + manufacturerId + "/models/" + modelId + "/cars/" + carId,
                CarDTO.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getCarId()).isEqualTo(1L);
        assertThat(responseEntity.getBody().getCarYear()).isEqualTo(2020);
    }


    @Test
    public void shouldSearchCarWithMultipleParameters() {
        Long manufacturerId = 1L;
        Long modelId = 1L;
        Integer minYear = 2000;
        Integer maxYear = 2021;
        Set<Long> categoryIds = new HashSet<>(Arrays.asList(1L, 2L));
        Pageable pageable = PageRequest.of(0, 10);

        CarSearchDTO searchDTO = new CarSearchDTO(manufacturerId, modelId, minYear, maxYear, categoryIds);
        Page<CarSearchDTO> page = new PageImpl<>(Collections.singletonList(searchDTO));
        when(carService.getAllCars(searchDTO, pageable)).thenReturn(page);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl + "/" + manufacturerId + "/models/" + modelId + "/cars")
                .queryParam("minYear", minYear)
                .queryParam("maxYear", maxYear)
                .queryParam("categoryIds", categoryIds.stream().map(Object::toString).collect(Collectors.joining(",")))
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize());

        ResponseEntity<RestPageImpl<CarSearchDTO>> responseEntity = restTemplate.exchange(
                builder.buildAndExpand(manufacturerId, modelId).toUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<RestPageImpl<CarSearchDTO>>() {
                }
        );

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getTotalElements()).isEqualTo(1);
        assertThat(responseEntity.getBody().getContent().get(0)).isEqualToComparingFieldByField(searchDTO);
    }
}
