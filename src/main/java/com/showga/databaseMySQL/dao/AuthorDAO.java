package com.showga.databaseMySQL.dao;

import com.showga.databaseMySQL.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDAO {
    void create(Author author);

    // return with author wrapped in an Optional or null in Optional
    // Make the type safe
    Optional<Author> findOne(Integer i);

    List<Author> findMany();

    void update(Integer id, Author author);

    void delete(Integer i);
}
