package com.showga.databaseMySQL.service;

import com.showga.databaseMySQL.domain.entity.Book;

public interface BookService {

    Book createBook(String isbn, Book book);

}
