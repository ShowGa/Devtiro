package com.showga.databaseMySQL.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.showga.databaseMySQL.TestDataUtils;
import com.showga.databaseMySQL.domain.entity.Author;
import com.showga.databaseMySQL.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTests {

    private AuthorService authorService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public AuthorControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper, AuthorService authorService) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.authorService = authorService;
    }

    // Test the response status
    @Test
    public void testThatCreateAuthorSuccessfullyReturnsHttp201Created() throws Exception {
        Author testAuthor1 = TestDataUtils.createTestAuthor();
        testAuthor1.setId(null);
        String authorJSON = objectMapper.writeValueAsString(testAuthor1);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/author")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJSON)
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    // see if response the expected data
    @Test
    public void testThatCreateAuthorSuccessfullyReturnsSavedAuthor() throws Exception {
        Author testAuthor1 = TestDataUtils.createTestAuthor();
        testAuthor1.setId(null);
        String authorJSON = objectMapper.writeValueAsString(testAuthor1);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/author")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("ShowGa Hsiao")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(20)
        );
    }

    @Test
    public void testThatFindAllAuthorsReturnsHttpStatus200() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testThatFindAllAuthorsSuccessfullyReturns() throws Exception {
        // Create an Object in m2 database
        Author author1 = TestDataUtils.createTestAuthor();
        authorService.createAuthor(author1);

        // check find and return the data successfully
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value("ShowGa Hsiao")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].age").value(20)
        );
    }

    @Test
    public void testThatFindOneAuthorReturnsHttpStatus200() throws Exception {

        Author testAuthor = TestDataUtils.createTestAuthor();
        authorService.createAuthor(testAuthor);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/author/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testThatFindOneAuthorsSuccessfullyReturns() throws Exception {
        // Create an Object in m2 database
        Author author1 = TestDataUtils.createTestAuthor();
        authorService.createAuthor(author1);

        // check find and return the data successfully
        mockMvc.perform(
                MockMvcRequestBuilders.get("/author/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("ShowGa Hsiao")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(20)
        );
    }
}