package com.showga.databaseMySQL.dao.impl;

import com.showga.databaseMySQL.TestDataUtils;
import com.showga.databaseMySQL.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

//import static org.mockito.ArgumentMatchers.any;
import static com.showga.databaseMySQL.TestDataUtils.createTestAuthor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

// Makes the test runner to be able to use the Makito functionality
@ExtendWith(MockitoExtension.class)
public class AuthorDAOImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AuthorDAOImpl underTest;

    @Test
    public void testThatCreateAuthorGeneratesCorrectSql() {
        Author author = TestDataUtils.createTestAuthor();

        underTest.create(author);

        // 1. makito verify
        // 2. verify the certain method was called on jdbctemplate with a particular set of args
        // 3. Follow 2 => pass the sql string with "prepared statement" (sql injection)
        verify(jdbcTemplate).update(eq("INSERT INTO author (id, name, age) VALUES (?, ?, ?)"), eq(1), eq("ShowGa Hsiao"), eq(20));
    }

    // Map the data from sql
    @Test
    public void testThatFindOneGeneratedTheCorrectSql() {
        underTest.findOne(1);
        verify(jdbcTemplate).query(eq("SELECT * FROM author WHERE id = ?"), ArgumentMatchers.<AuthorDAOImpl.AuthorRowMapper>any(), eq(1));
    }

    // 7.7 test findMany()
    @Test
    public void testThatFindManyGeneratedTheCorrectSql() {
        underTest.findMany();
        verify(jdbcTemplate).query(
                eq("SELECT * FROM author"), ArgumentMatchers.<AuthorDAOImpl.AuthorRowMapper>any()
        );

    }

    // 7.8 test update ?????????????????????????????????
    @Test
    public void testThatUpdateGeneratesCorrectSql() {
        Author author = TestDataUtils.createTestAuthor();
        underTest.update(2, author);
        verify(jdbcTemplate).update(eq("UPDATE author SET id = ?, name = ?, age = ? WHERE id = ?"), eq(1), eq("ShowGa Hsiao"), eq(20), eq(2));
    }

    // 7.9 test delete
    @Test
    public void testThatDeleteGeneratesCorrectSql() {
        underTest.delete(1);
        verify(jdbcTemplate).update(eq("DELETE FROM author WHERE id = ?"), eq(1));
    }
}
