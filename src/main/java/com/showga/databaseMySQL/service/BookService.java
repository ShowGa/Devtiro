package com.showga.databaseMySQL.service;

import com.showga.databaseMySQL.domain.dto.BookDto;
import com.showga.databaseMySQL.domain.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Book createUpdateBook(String isbn, Book book);

    List<Book> findAll();

    Optional<Book> findOne(String isbn);

    boolean isExists(String isbn);

    Book partialUpdate(String isbn, Book book);

    void delete(String isbn);
}
