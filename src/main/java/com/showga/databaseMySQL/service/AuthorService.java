package com.showga.databaseMySQL.service;

import com.showga.databaseMySQL.domain.entity.Author;
import org.springframework.stereotype.Service;

public interface AuthorService {

    Author createAuthor(Author author);

}
