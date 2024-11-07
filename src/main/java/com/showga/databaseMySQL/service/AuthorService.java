package com.showga.databaseMySQL.service;

import com.showga.databaseMySQL.domain.entity.Author;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    Author createAuthor(Author author);

    List<Author> findAll();

    Optional<Author> findOne(Integer authorId);
}
