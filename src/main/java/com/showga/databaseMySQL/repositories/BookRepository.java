package com.showga.databaseMySQL.repositories;

import com.showga.databaseMySQL.domain.entity.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, String> {
}
