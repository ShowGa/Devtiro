package com.showga.databaseMySQL.service;

import com.showga.databaseMySQL.domain.entity.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    Author save(Author author);

    List<Author> findAll();

    Optional<Author> findOne(Integer authorId);

    boolean isExists(Integer id);
}
