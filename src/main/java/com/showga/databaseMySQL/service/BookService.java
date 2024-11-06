package com.showga.databaseMySQL.service;

import com.showga.databaseMySQL.domain.entity.Book;

import java.util.List;

public interface BookService {

    Book createBook(String isbn, Book book);

    List<Book> findAll();
}
