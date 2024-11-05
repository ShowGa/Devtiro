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
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_id_seq")
    private Integer id;

    private String name;

    private Integer age;
}

// @Entity tells the spring data jpa for using this Object as entity
// @Table tells which table be mapped by this object
// @Id mark(identify) the primary key
// @GeneratedValue auto generate the sequence id