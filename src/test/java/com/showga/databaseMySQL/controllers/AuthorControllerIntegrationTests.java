package com.showga.databaseMySQL.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.showga.databaseMySQL.TestDataUtils;
import com.showga.databaseMySQL.domain.entity.Author;
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

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public AuthorControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
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
}