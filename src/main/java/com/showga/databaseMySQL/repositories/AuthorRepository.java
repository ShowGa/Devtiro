package com.showga.databaseMySQL.repositories;

import com.showga.databaseMySQL.domain.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// Repository Bean => can be injected from anywhere
@Repository
public interface AuthorRepository extends CrudRepository<Author, Integer> {

    Iterable<Author> ageLessThan(int age);

    // 8 - 9 HQL
    @Query("SELECT a FROM Author a WHERE a.age > ?1")
    Iterable<Author> findAuthorGreaterThan(int age);
}
