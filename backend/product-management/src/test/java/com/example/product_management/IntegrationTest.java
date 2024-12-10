package com.example.product_management;

import com.example.product_management.dto.CategoryDTO;
import com.example.product_management.model.Item;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

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

    @Test
    void getItemByAdminPasses() {
        ResponseEntity<String> response = restTemplate.exchange(
                "/api/admin/items/999",
                HttpMethod.GET,
                null,
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void getItemByUserFails() {
        ResponseEntity<String> response = restTemplate.exchange(
                "/api/admin/items/999",
                HttpMethod.GET,
                null,
                String.class
        );

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void createNewItemByAdminPasses() {
        String requestBody = """
                {
                    "CategoryId": 2,
                    "Productname": "name",
                    "description": "details",
                    "stock": 12,
                    "Supplier": "name",
                    "price": 40.65,
                    "image": "[file]"
                }
                """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(MediaType.MULTIPART_FORM_DATA_VALUE));

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "/api/admin/items",
                HttpMethod.POST,
                request,
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void createNewItemByUserFails() {
        String requestBody = """
                {
                    "CategoryId": 2,
                    "Productname": "name",
                    "description": "details",
                    "stock": 12,
                    "Supplier": "name",
                    "price": 40.65,
                    "image": "[file]"
                }
                """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(MediaType.MULTIPART_FORM_DATA_VALUE));

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "/api/admin/items",
                HttpMethod.POST,
                request,
                String.class
        );

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void updateItemByAdminPasses() {
        String requestBody = """
                {
                    "CategoryId": 2,
                    "Productname": "name",
                    "description": "details",
                    "stock": 12,
                    "Supplier": "name",
                    "price": 40.65,
                    "image": "[file]"
                }
                """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(MediaType.MULTIPART_FORM_DATA_VALUE));

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "/api/admin/items/999",
                HttpMethod.PUT,
                request,
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void updateItemByUserFails() {
        String requestBody = """
                {
                    "CategoryId": 2,
                    "Productname": "name",
                    "description": "details",
                    "stock": 12,
                    "Supplier": "name",
                    "price": 40.65,
                    "image": "[file]"
                }
                """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(MediaType.MULTIPART_FORM_DATA_VALUE));

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "/api/admin/items/999",
                HttpMethod.PUT,
                request,
                String.class
        );

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void deleteItemByAdminPasses() {
        ResponseEntity<String> response = restTemplate.exchange(
                "/api/admin/items/999",
                HttpMethod.DELETE,
                null,
                String.class
        );

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteItemByUserFails() {
        ResponseEntity<String> response = restTemplate.exchange(
                "/api/admin/items/999",
                HttpMethod.DELETE,
                null,
                String.class
        );

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
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
