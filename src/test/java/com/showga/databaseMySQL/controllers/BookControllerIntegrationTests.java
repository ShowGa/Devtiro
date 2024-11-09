package com.showga.databaseMySQL.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.showga.databaseMySQL.TestDataUtils;
import com.showga.databaseMySQL.domain.dto.BookDto;
import com.showga.databaseMySQL.domain.entity.Book;
import com.showga.databaseMySQL.service.BookService;
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

    private BookService bookService;

    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper, BookService bookService) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.bookService = bookService;
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

    @Test
    public void testThatFindAllBooksReturnHttpStatus200() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFindAllBooksSuccessfullyReturns() throws Exception {

        // Create book in the memory database
        Book createdBook = TestDataUtils.createTestBook(null);
        bookService.createUpdateBook(createdBook.getIsbn(), createdBook);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].isbn").value("484-5454-4-1")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].title").value("ShowGod in the World")
        );

    }

    @Test
    public void testThatFindOneBookReturnHttpStatus200() throws Exception {

        Book testBook = TestDataUtils.createTestBook(null);
        bookService.createUpdateBook(testBook.getIsbn(), testBook);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/book/" + testBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFindOneBookReturnsHttp404WhenBookDoesNotExist() throws Exception {

        // Create book in the memory database
        Book testBook = TestDataUtils.createTestBook(null);
//        bookService.createBook(createdBook.getIsbn(), createdBook);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/book/" + testBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );

    }

    @Test
    public void testThatUpdateBookReturnHttpStatus200() throws Exception {
        Book testBookEntity = TestDataUtils.createTestBook(null);
        Book savedCreatedBook = bookService.createUpdateBook(testBookEntity.getIsbn(), testBookEntity);

        BookDto testBookDto = TestDataUtils.createTestBookDto(null);

        testBookDto.setIsbn(savedCreatedBook.getIsbn());

        String bookJson = objectMapper.writeValueAsString(testBookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/book/" + savedCreatedBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        // request body
                        .content(bookJson)
        ).andExpect(
                // test updated
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFullUpdateBookSuccessfullyReturns() throws Exception {

        // Create book in the memory database
        Book testBookEntity = TestDataUtils.createTestBook(null);
        Book savedCreatedBook = bookService.createUpdateBook(testBookEntity.getIsbn(), testBookEntity);

        BookDto testBookDto = TestDataUtils.createTestBookDto(null);

        testBookDto.setIsbn(savedCreatedBook.getIsbn());

        String bookJson = objectMapper.writeValueAsString(testBookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/book/" + savedCreatedBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("484-5454-4-1")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("ShowGod in the World 3")
        );

    }
}
