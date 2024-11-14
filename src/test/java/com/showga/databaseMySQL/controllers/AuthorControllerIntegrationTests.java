package com.showga.databaseMySQL.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.showga.databaseMySQL.TestDataUtils;
import com.showga.databaseMySQL.domain.dto.AuthorDto;
import com.showga.databaseMySQL.domain.entity.Author;
import com.showga.databaseMySQL.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
        Author author1 = TestDataUtils.createTestAuthor();
        authorService.save(author1);

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
        authorService.save(testAuthor);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/author/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testThatFindOneAuthorsSuccessfullyReturns() throws Exception {
        // Create an Object in m2 database
        Author author1 = TestDataUtils.createTestAuthor();
        authorService.save(author1);

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

    @Test
    public void testThatFullUpdateAuthorReturnsHttpStatus404() throws Exception {
        AuthorDto testAuthorDto = TestDataUtils.createTestAuthorDto();
        String authorDtoJson = objectMapper.writeValueAsString(testAuthorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/author/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    public void testThatFullUpdateAuthorReturnsHttpStatus200() throws Exception {
        Author testAuthor = TestDataUtils.createTestAuthor();
        Author savedAuthor = authorService.save(testAuthor);

        AuthorDto testAuthorDto = TestDataUtils.createTestAuthorDto();
        String authorDtoJson = objectMapper.writeValueAsString(testAuthorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/author/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testThatFullUpdateExistingAuthor() throws Exception {
        // Create an Object in m2 database
        Author testAuthor = TestDataUtils.createTestAuthor();
        Author savedAuthor = authorService.save(testAuthor);

        Author testAuthor2 = TestDataUtils.createTestAuthor2();
        testAuthor2.setId(savedAuthor.getId());

        String authorDtoUpdateJson = objectMapper.writeValueAsString(testAuthor2);

        // check find and return the data successfully
        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(testAuthor2.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(testAuthor2.getAge())
        );
    }

    @Test
    public void testThatPartialUpdateAuthorReturnsHttpStatus200() throws Exception {
        Author testAuthor = TestDataUtils.createTestAuthor();
        Author savedAuthor = authorService.save(testAuthor);

        AuthorDto testAuthorDto = TestDataUtils.createTestAuthorDto();
        testAuthorDto.setName("Updated");
        String authorDtoJson = objectMapper.writeValueAsString(testAuthorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/author/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testPartialUpdateExistingAuthorReturnsUpdateAuthor() throws Exception {
        // Create an Object in m2 database
        Author testAuthor = TestDataUtils.createTestAuthor();
        Author savedAuthor = authorService.save(testAuthor);

        Author testAuthor2 = TestDataUtils.createTestAuthor2();
        testAuthor2.setName("Updated");
        String authorDtoUpdateJson = objectMapper.writeValueAsString(testAuthor2);

        // check find and return the data successfully
        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Updated")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(testAuthor2.getAge())
        );
    }

    @Test
    public void testThatDeleteAuthorReturnsHttpStatus204ForNonExitingAuthor() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    public void testThatDeleteAuthorReturnsHttpStatus204ForExitingAuthor() throws Exception {

        Author testAuthor = TestDataUtils.createTestAuthor();
        Author savedAuthor = authorService.save(testAuthor);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());

    }
}