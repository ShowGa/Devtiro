package com.showga.databaseMySQL.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.showga.databaseMySQL.TestDataUtils;
import com.showga.databaseMySQL.domain.dto.BookDto;
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
public class BookControllerIntegrationTests {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testThatCreateBookReturnHttpStatus201Created() throws Exception {

        BookDto bookDto = TestDataUtils.createTestBookDto(null);

        String createBookJSON = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/book/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBookJSON)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateBookReturnCreatedBook() throws Exception {

        BookDto bookDto = TestDataUtils.createTestBookDto(null);

        String createBookJSON = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/book/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBookJSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle())
        );
    }
}
