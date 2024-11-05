package com.showga.databaseMySQL.controller;

import com.showga.databaseMySQL.domain.dto.AuthorDto;
import com.showga.databaseMySQL.domain.entity.Author;
import com.showga.databaseMySQL.mappers.Mapper;
import com.showga.databaseMySQL.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
        Author createdAuthorEntity1 = authorService.createAuthor(authorEntity);

        // Change the created author (Author Entity) to AuthorDto and return

        return new ResponseEntity<>(authorMapper.mapTo(createdAuthorEntity1), HttpStatus.CREATED);
    }
}
