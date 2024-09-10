package com.java.course.auto_catalog.controller.test;

import com.java.course.auto_catalog.dto.*;
import com.java.course.auto_catalog.service.CategoryService;
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
public class CategoryControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private CategoryService categoryService;

    private String baseUrl;

    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/api/v1/cars";
    }

    @Test
    void shouldCreatNewCategory() {

        Long carId = 1L;
        CategoryWithoutIdDTO categoryWithoutIdDTO = new CategoryWithoutIdDTO("CategoryName");

        CategoryDTO newCategoryDTO = new CategoryDTO(1L,"CategoryName");

        when(categoryService.save(carId, categoryWithoutIdDTO)).thenReturn(newCategoryDTO);

        ResponseEntity<CategoryDTO> responseEntity = restTemplate.postForEntity(
                baseUrl + "/" + carId + "/categories/new",
                categoryWithoutIdDTO,
                CategoryDTO.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(Objects.requireNonNull(responseEntity.getBody()).getCategoryId()).isEqualTo(1L);
        assertThat(responseEntity.getBody().getCategoryName()).isEqualTo("CategoryName");
    }

    @Test
    void shouldRemoveCategoryById(){

      Long carId = 1L;
      Long categoryId = 1L;

        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                baseUrl + "/" + carId + "/categories/" + categoryId,
                HttpMethod.DELETE, null, Void.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void shouldUpdateCategory(){

        Long carId = 1L;
        Long categoryId = 1L;

        CategoryUpdateDTO categoryUpdateDTO = new CategoryUpdateDTO();
        CategoryDTO updatedCategoryDTO = new CategoryDTO(1L,"UpdatedName");

        when(categoryService.updateCategory(carId, categoryId, categoryUpdateDTO)).thenReturn(updatedCategoryDTO);

        ResponseEntity<CategoryDTO> responseEntity = restTemplate.exchange(
                baseUrl + "/" + carId + "/categories/" + categoryId,
                HttpMethod.PUT, new HttpEntity<>(categoryUpdateDTO), CategoryDTO.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getCategoryId()).isEqualTo(1L);
        assertThat(responseEntity.getBody().getCategoryName()).isEqualTo("UpdatedName");
    }

    @Test
    void shouldGetCategoryById(){

        Long carId= 1L;
        Long categoryId = 1L;

        CategoryDTO categoryDTO = new CategoryDTO(1L,"Category");

        when(categoryService.findById(carId, categoryId)).thenReturn(categoryDTO);

        ResponseEntity<CategoryDTO> responseEntity = restTemplate.getForEntity(
                baseUrl + "/" + carId + "/categories/" + categoryId,
                CategoryDTO.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getCategoryId()).isEqualTo(1L);
        assertThat(responseEntity.getBody().getCategoryName()).isEqualTo("Category");
    }

    @Test
    void shouldGetAllCategoriesByCarId(){

        Long carId = 1L;

        CategoryDTO categoryDTO1 = new CategoryDTO(1L,"Category1");
        CategoryDTO categoryDTO2 = new CategoryDTO(2L,"Category2");

        List<CategoryDTO> Categorylist = Arrays.asList(categoryDTO1, categoryDTO2);

        when(categoryService.getAllCategoryByCars(carId)).thenReturn(Categorylist);

        ResponseEntity<List<CategoryDTO>> responseEntity = restTemplate.exchange(
                baseUrl + "/" + carId + "/categories",
                org.springframework.http.HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CategoryDTO>>() {
                });

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().size()).isEqualTo(2);

        assertThat(responseEntity.getBody().get(0).getCategoryName()).isEqualTo("Category1");
        assertThat(responseEntity.getBody().get(1).getCategoryName()).isEqualTo("Category2");
    }
}
