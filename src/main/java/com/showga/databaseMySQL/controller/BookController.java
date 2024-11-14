package com.showga.databaseMySQL.controller;

import com.showga.databaseMySQL.domain.dto.AuthorDto;
import com.showga.databaseMySQL.domain.dto.BookDto;
import com.showga.databaseMySQL.domain.entity.Book;
import com.showga.databaseMySQL.mappers.Mapper;
import com.showga.databaseMySQL.service.BookService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        // check before save
        Boolean isExists = bookService.isExists(isbn);

        // create the book and return the data
        Book createdBookEntity = bookService.createUpdateBook(isbn, bookEntity);
        // mapping
        BookDto savedUpdatedBookDto = bookMapper.mapTo(createdBookEntity);

        if (isExists) {
            // update
            return new ResponseEntity<>(bookMapper.mapTo(createdBookEntity), HttpStatus.OK);
        } else {
            // convert the data entity to BookDto and send to client
            return new ResponseEntity<>(bookMapper.mapTo(createdBookEntity), HttpStatus.CREATED);
        }
    }

    @GetMapping("/books")
    public List<BookDto> findAllBooks() {

        // get all the books from the database
        List<Book> books = bookService.findAll();

        // convert the data into MapDto and send to client
        return books.stream().map(bookMapper::mapTo).collect(Collectors.toList());

    }

    @GetMapping("/book/{isbn}")
    public ResponseEntity<BookDto> findOneBook(@PathVariable("isbn") String isbn) {

        // get the book
        Optional<Book> foundBook = bookService.findOne(isbn);

        // convert the Entity to Dto and send back
        // reason to use ResponseEntity is to specify the response status
        return foundBook.map(book -> {
            BookDto bookDto = bookMapper.mapTo(book);
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PatchMapping(path = "/book/{isbn}")
    public ResponseEntity<BookDto> partialUpdate(
            @PathVariable String isbn,
            @RequestBody BookDto bookDto
    ) {
        boolean bookExists = bookService.isExists(isbn);
        if (!bookExists) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Convert bookDto to bookEntity
        Book bookEntity = bookMapper.mapFrom(bookDto);
        Book patchedBook = bookService.partialUpdate(isbn, bookEntity);

        return new ResponseEntity<>(bookMapper.mapTo(patchedBook), HttpStatus.OK);
    }

    @DeleteMapping(path = "/books/{isbn}")
    public ResponseEntity deleteBook(@PathVariable("isbn") String isbn) {
        bookService.delete(isbn);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}