package com.showga.databaseMySQL.service;

import com.showga.databaseMySQL.domain.entity.Author;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AuthorService {

    Author createAuthor(Author author);

    List<Author> findAll();
}
