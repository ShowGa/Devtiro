package com.showga.databaseMySQL.service.impl;

import com.showga.databaseMySQL.domain.entity.Book;
import com.showga.databaseMySQL.repositories.BookRepository;
import com.showga.databaseMySQL.service.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    // get the jpa repository
    private BookRepository bookRepository;

    // constructor injection
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book createBook(String isbn, Book book) {
        // set isbn
        book.setIsbn(isbn);

        return bookRepository.save(book);
    }
}
