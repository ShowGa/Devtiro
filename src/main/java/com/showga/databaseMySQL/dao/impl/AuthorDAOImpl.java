package com.showga.databaseMySQL.dao.impl;

import com.showga.databaseMySQL.dao.AuthorDAO;
import com.showga.databaseMySQL.domain.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class AuthorDAOImpl implements AuthorDAO {
    private final JdbcTemplate jdbcTemplate;

    public AuthorDAOImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // write the method for manipulate the database with sql syntax

    @Override
    public void create(Author author) {
        jdbcTemplate.update("INSERT INTO author (id, name, age) VALUES (?, ?, ?)", author.getId(), author.getName(), author.getAge());
    }

    @Override
    public Optional<Author> findOne(Integer i) {
        List<Author> result = jdbcTemplate.query("SELECT * FROM author WHERE id = ?", new AuthorRowMapper(), i);

        return result.stream().findFirst();
    }

    @Override
    public List<Author> findMany() {
        List<Author> result = jdbcTemplate.query("SELECT * FROM author", new AuthorRowMapper() {
        });
        return result;
    }

    @Override
    public void update(Integer id, Author author) {
        jdbcTemplate.update("UPDATE author SET id = ?, name = ?, age = ? WHERE id = ?", author.getId(), author.getName(), author.getAge(), id);
    }

    @Override
    public void delete(Integer i) {
        jdbcTemplate.update("DELETE FROM author WHERE id = ?", i);
    }


    // RowMapper
    public static class AuthorRowMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Author.builder().id(rs.getInt("id")).name(rs.getString("name")).age(rs.getInt("age")).build();
        }
    }
}
