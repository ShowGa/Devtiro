package com.showga.databaseMySQL.controller;

import com.showga.databaseMySQL.domain.dto.BookDto;
import com.showga.databaseMySQL.domain.entity.Book;
import com.showga.databaseMySQL.mappers.Mapper;
import com.showga.databaseMySQL.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
    // BookService
    private final BookService bookService;

    // Mapper
    private final Mapper<Book, BookDto> bookMapper;

    public BookController(BookService bookService, Mapper<Book, BookDto> bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @PostMapping(path = "/book/{isbn}")
    public ResponseEntity<BookDto> createAuthor(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto) {

        // map the bookDto to book entity
        Book bookEntity = bookMapper.mapFrom(bookDto);

        // create the book and return the data
        Book createdBookEntity = bookService.createBook(isbn, bookEntity);

        // convert the data entity to BookDto and send to client
        return new ResponseEntity<>(bookMapper.mapTo(createdBookEntity), HttpStatus.CREATED);

    }
}