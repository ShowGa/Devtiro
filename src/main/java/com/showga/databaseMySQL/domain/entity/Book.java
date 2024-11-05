package com.showga.databaseMySQL.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "books")
public class Book {

    @Id
    private String isbn;

    private String title;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")
    private Author author;
    // private Integer author_id;
}


// @ManyToOne(cascade = CascadeType.ALL)
    // Retrieve the author when get the book back
    // Make a change on the author will be able to save into the database (persisted in database)