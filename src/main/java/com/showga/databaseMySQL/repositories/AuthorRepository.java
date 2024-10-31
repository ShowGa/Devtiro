package com.showga.databaseMySQL.repositories;

import com.showga.databaseMySQL.domain.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// Repository Bean => can be injected from anywhere
@Repository
public interface AuthorRepository extends CrudRepository<Author, Integer> {

}
