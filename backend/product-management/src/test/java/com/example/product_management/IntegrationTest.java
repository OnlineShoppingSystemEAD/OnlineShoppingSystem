package com.example.product_management;

import com.example.product_management.dto.CategoryDTO;
import com.example.product_management.dto.ItemDTO;
import com.example.product_management.model.Item;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.data.domain.Page;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.basePath = "/api";

        // Configure encoder
        RestAssured.config = RestAssured.config()
            .encoderConfig(EncoderConfig.encoderConfig()
                .encodeContentTypeAs("multipart/form-data", ContentType.MULTIPART));
    }

    @Test
    void contextLoadsSuccessfully() {
        assertTrue(true, "Context loads successfully");
    }

    @Test
    void getItemsPasses() {
        ResponseEntity<String> response = restTemplate.exchange(
                "/api/items",
                HttpMethod.GET,
                null,
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void getAllCategoriesPasses() {
        ResponseEntity<String> response = restTemplate.exchange(
                "/api/categories",
                HttpMethod.GET,
                null,
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
    @Disabled
    @Test
    void getItemByAdminPasses() {
        ResponseEntity<String> response = restTemplate.exchange(
                "/api/items/5",
                HttpMethod.GET,
                null,
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void NonExistingItemReturnsNotFound() {
        ResponseEntity<String> response = restTemplate.exchange(
                "/api/admin/items/999",
                HttpMethod.GET,
                null,
                String.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldRejectCategoryInImproperFormat() throws IOException {
        // Create test file
        File imageFile = new File("src/test/java/resources/test-image.jpg");
        
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Test Category");
        categoryDTO.setDescription("Test Description");

        // Convert CategoryDTO to JSON string
        String categoryJson = new ObjectMapper().writeValueAsString(categoryDTO);

        given()
            .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
            .multiPart("image", imageFile)
            .multiPart("category", categoryJson, MediaType.APPLICATION_JSON_VALUE)
            .formParam("userId", 1)  // Changed to formParam
            // .formParam("role", "ADMIN")  // Changed to formParam
        .when()
            .post("/categories")
        .then()
            .statusCode(400)
            .body("status", equalTo(400));
    }

    // @Disabled
    @Test
        void createCategoryWithImageDuplicateNameFails() throws IOException {
            // Create test file
            File imageFile = new File("src/test/java/resources/test-image.jpg");
            
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setName("Test Category 123");
            categoryDTO.setDescription("Test Description");

            // Convert CategoryDTO to JSON string
            String categoryJson = new ObjectMapper().writeValueAsString(categoryDTO);

            given()
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .multiPart("image", imageFile)
                .multiPart("category", categoryJson, MediaType.APPLICATION_JSON_VALUE)
                .formParam("userId", 1)  // Changed to formParam
                .formParam("role", "ADMIN")  // Changed to formParam
            .when()
                .post("/categories")
            .then()
                .statusCode(500)
                .body("status", equalTo(500));
        }

    @Disabled
    @Test
        void shouldCreateCategoryWithoutImage() throws IOException {
        
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Test Category");
        categoryDTO.setDescription("Test Description");

        // Convert CategoryDTO to JSON string
        String categoryJson = new ObjectMapper().writeValueAsString(categoryDTO);

        given()
            .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
            .multiPart("category", categoryJson, MediaType.APPLICATION_JSON_VALUE)
            .formParam("userId", 1)  // Changed to formParam
            .formParam("role", "ADMIN")  // Changed to formParam
        .when()
            .post("/categories")
        .then()
            .statusCode(200)
            .body("status", equalTo(200))
            .body("data.name", equalTo("Test Category"));
    }

    @Disabled
    @Test
    void createNewItemByAdminPasses() throws JsonProcessingException {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setCategoryId(2);
        itemDTO.setName("Test product name");
        itemDTO.setDescription("Test description");
        itemDTO.setQuantity(12);
        itemDTO.setPrice(40.65);

        File imageFile = new File("src/test/java/resources/test-image.jpg");
        List<MultipartFile> images = List.of();

        // Convert ItemDTO to JSON string
        String itemJson = new ObjectMapper().writeValueAsString(itemDTO);


        given()
            .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
            .multiPart("images", imageFile)
            .multiPart("item", itemJson, MediaType.APPLICATION_JSON_VALUE)
        
        .when()
            .post("/items")

        .then()
            .statusCode(201);
    }

   
    @Test
    void getCategoriesByUserPasses() {
        ResponseEntity<String> response = restTemplate.exchange(
                "/api/categories",
                HttpMethod.GET,
                null,
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void getItemsByUserPasses() {
        ResponseEntity<String> response = restTemplate.exchange(
                "/api/items",
                HttpMethod.GET,
                null,
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}
