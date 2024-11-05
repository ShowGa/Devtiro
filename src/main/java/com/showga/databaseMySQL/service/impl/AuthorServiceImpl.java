package com.showga.databaseMySQL.service.impl;

import com.showga.databaseMySQL.domain.entity.Author;
import com.showga.databaseMySQL.repositories.AuthorRepository;
import com.showga.databaseMySQL.service.AuthorService;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;

    // constructor injection => allow to skip @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author createAuthor(Author author) {

        // Spring JPA auto does
        return authorRepository.save(author);
    }

}
