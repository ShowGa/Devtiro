package com.showga.databaseMySQL.service;

import com.showga.databaseMySQL.domain.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Book createBook(String isbn, Book book);

    List<Book> findAll();

    Optional<Book> findOne(String isbn);
}
