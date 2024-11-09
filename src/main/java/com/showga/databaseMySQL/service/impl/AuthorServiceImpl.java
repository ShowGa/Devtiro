package com.showga.databaseMySQL.service.impl;

import com.showga.databaseMySQL.domain.entity.Author;
import com.showga.databaseMySQL.repositories.AuthorRepository;
import com.showga.databaseMySQL.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;

    // constructor injection => allow to skip @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author save(Author author) {

        // Spring JPA auto does
        return authorRepository.save(author);
    }

    @Override
    public List<Author> findAll() {
        return StreamSupport.stream(authorRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Optional<Author> findOne(Integer authorId) {
        return authorRepository.findById(authorId);
    }

    @Override
    public boolean isExists(Integer id) {

        return authorRepository.existsById(id);

    }

    @Override
    public Author partialUpdate(Integer id, Author authorEntity) {
        // set the provided information from the client
        authorEntity.setId(id);

        return authorRepository.findById(id).map(existingAuthor -> {
            Optional.ofNullable(authorEntity.getName()).ifPresent(existingAuthor::setName);
            Optional.ofNullable(authorEntity.getAge()).ifPresent(existingAuthor::setAge);
            return authorRepository.save(existingAuthor);
        }).orElseThrow(() -> new RuntimeException("Author does not exist"));
    }

}
