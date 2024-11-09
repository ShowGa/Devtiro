package com.showga.databaseMySQL.controller;

import com.showga.databaseMySQL.domain.dto.AuthorDto;
import com.showga.databaseMySQL.domain.entity.Author;
import com.showga.databaseMySQL.mappers.Mapper;
import com.showga.databaseMySQL.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class AuthorController {

    private AuthorService authorService;

    private Mapper<Author, AuthorDto> authorMapper;

    public AuthorController(AuthorService authorService, Mapper<Author, AuthorDto> authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    // Create author route
    // ResponseEntity allows us to control the response status
    @PostMapping(path = "/author")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author) {

        // Change the client data (@Request AuthorDto) to the author entity
        Author authorEntity = authorMapper.mapFrom(author);

        // get the created data from the Service (JpaRepository operation)
        Author createdAuthorEntity1 = authorService.save(authorEntity);

        // Change the created author (Author Entity) to AuthorDto and return

        return new ResponseEntity<>(authorMapper.mapTo(createdAuthorEntity1), HttpStatus.CREATED);
    }

    @GetMapping("/authors")
    public List<AuthorDto> getAllAuthors() {
        // get all the authors from database
        List<Author> authors = authorService.findAll();

        // convert the data into MapDto and send to client
        return authors.stream().map(authorMapper::mapTo).collect(Collectors.toList());

    }

    @GetMapping(path = "/author/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable("id") Integer authorId) {
        // author service find one
        Optional<Author> foundAuthor = authorService.findOne(authorId);

        // convert the Entity to Dto and send back
        // reason to use ResponseEntity is to specify the response status
        return foundAuthor.map(author -> {
            AuthorDto authorDto = authorMapper.mapTo(author);
            return new ResponseEntity<>(authorDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> fullUpdateAuthor(
            @PathVariable("id") Integer id,
            @RequestBody AuthorDto authorDto
    ) {

        // create isExists authorService to check if the author was existed
        if (!authorService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        };

        // Yes -> going for update
        authorDto.setId(id);
        Author author = authorMapper.mapFrom(authorDto);

        Author savedAuthor = authorService.save(author);

        return new ResponseEntity<>(authorMapper.mapTo(savedAuthor), HttpStatus.OK);
    }

    @PatchMapping(path = "author/{id}")
    public ResponseEntity<AuthorDto> partialUpdate(
            @PathVariable Integer id,
            @RequestBody AuthorDto authorDto
    ) {
        // create isExists authorService to check if the author was existed
        if (!authorService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        };

        // convert authorDto to authorEntity
        Author authorEntity = authorMapper.mapFrom(authorDto);
        Author patchedAuthor = authorService.partialUpdate(id, authorEntity);

        return new ResponseEntity<>(authorMapper.mapTo(patchedAuthor), HttpStatus.OK);
    }
}