package com.java.course.auto_catalog.controller.test;

import com.java.course.auto_catalog.dto.*;
import com.java.course.auto_catalog.service.ModelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
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
public class ModelControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private ModelService modelService;

    private String baseUrl;

    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/api/v1/manufacturers";
    }


    @Test
    void shouldCreatNewModel() {

        Long manufacturerId = 1L;

        ModelWithoutIdDTO modelWithoutIdDTO = new ModelWithoutIdDTO("ModelTest", "ModelCategoryTest");

        ModelDTO savedModelDTO = new ModelDTO(1L, "ModelTest", "ModelCategoryTest");


        when(modelService.save(manufacturerId, modelWithoutIdDTO)).thenReturn(savedModelDTO);

        ResponseEntity<ModelDTO> responseEntity = restTemplate.postForEntity(baseUrl + "/" + manufacturerId + "/models/new",
                modelWithoutIdDTO, ModelDTO.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(Objects.requireNonNull(responseEntity.getBody()).getModelId()).isEqualTo(1L);
        assertThat(responseEntity.getBody().getModelName()).isEqualTo("ModelTest");
        assertThat(responseEntity.getBody().getModelCategory()).isEqualTo("ModelCategoryTest");
    }

    @Test
    public void shouldRemoveModelById() {

        Long manufacturerId = 1L;

        Long modelId = 1L;

        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                baseUrl + "/" + manufacturerId + "/models/" + modelId,
                HttpMethod.DELETE, null, Void.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void shouldUpdateModel() {

        Long manufacturerId = 1L;
        Long modelId = 1L;

        ModelUpdateDTO modelUpdateDTO = new ModelUpdateDTO();
        ModelDTO updatedModelDTO = new ModelDTO(1L, "UpdatedName", "UpdatedModelCategory");

        when(modelService.update(manufacturerId, modelId, modelUpdateDTO)).thenReturn(updatedModelDTO);

        ResponseEntity<ModelDTO> responseEntity = restTemplate.exchange(
                baseUrl + "/" + manufacturerId + "/models/" + modelId,
                HttpMethod.PUT, new HttpEntity<>(modelUpdateDTO), ModelDTO.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getModelId()).isEqualTo(1L);
        assertThat(responseEntity.getBody().getModelName()).isEqualTo("UpdatedName");
        assertThat(responseEntity.getBody().getModelCategory()).isEqualTo("UpdatedModelCategory");
    }

    @Test
    void shouldGetModelById() {

        Long manufacturerId = 1L;
        Long modelId = 1L;

        ModelDTO modelDTO = new ModelDTO(1L,"TestName","TestModelCategory");

        when(modelService.findById(manufacturerId, modelId)).thenReturn(modelDTO);

        ResponseEntity<ModelDTO> responseEntity = restTemplate.getForEntity(
                baseUrl + "/" + manufacturerId + "/models/" + modelId,
                ModelDTO.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getModelId()).isEqualTo(1L);
        assertThat(responseEntity.getBody().getModelName()).isEqualTo("TestName");
        assertThat(responseEntity.getBody().getModelCategory()).isEqualTo("TestModelCategory");
    }

    @Test
    void shouldGetAllModels() {

        Long manufacturerId = 1L;

        ModelDTO model = new ModelDTO(1L, "ModelName", "ModelCategory");
        ModelDTO model2 = new ModelDTO(2L, "ModelName2", "ModelCategory2");

        List<ModelDTO> modelList = Arrays.asList(model, model2);

        when(modelService.getAllModelsByManufacturer(manufacturerId)).thenReturn(modelList);

        ResponseEntity<List<ModelDTO>> responseEntity = restTemplate.exchange(
                baseUrl + "/" + manufacturerId + "/models",
                org.springframework.http.HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ModelDTO>>() {
                });

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().size()).isEqualTo(2);

        assertThat(responseEntity.getBody().get(0).getModelName()).isEqualTo("ModelName");
        assertThat(responseEntity.getBody().get(0).getModelCategory()).isEqualTo("ModelCategory");
        assertThat(responseEntity.getBody().get(1).getModelName()).isEqualTo("ModelName2");
        assertThat(responseEntity.getBody().get(1).getModelCategory()).isEqualTo("ModelCategory2");
    }
}



