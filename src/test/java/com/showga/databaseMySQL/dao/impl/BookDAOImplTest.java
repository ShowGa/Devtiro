package com.showga.databaseMySQL.dao.impl;

import com.showga.databaseMySQL.TestDataUtils;
import com.showga.databaseMySQL.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookDAOImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BookDAOImpl underTest;

    @Test
    public void testThatCreateBookGeneratesCorrectSql() {
        Book book = TestDataUtils.createTestBook();

        underTest.create(book);

        verify(jdbcTemplate).update(eq("INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)"), eq("484-5454-4-1"), eq("ShowGod in the World"), eq(1));
    }

    @Test
    public void testThatFindOneGeneratedTheCorrectSql() {
        underTest.find("484-5454-4-1");
        verify(jdbcTemplate).query(eq("SELECT * FROM books WHERE isbn = ?"), ArgumentMatchers.<BookDAOImpl.BookRowMapper>any(), eq("484-5454-4-1"));
    }

    @Test
    public void testThatFindGeneratedCorrectSql() {
        underTest.findMany();
        verify(jdbcTemplate).query(eq("SELECT * FROM books"), ArgumentMatchers.<BookDAOImpl.BookRowMapper>any());
    }

    @Test
    public void testThatUpdateGeneratesCorrectSql() {
        Book book = TestDataUtils.createTestBook();
        underTest.update("484-5454-4-1", book);
        verify(jdbcTemplate).update(eq("UPDATE books SET isbn = ?, title = ?, author_id = ? WHERE isbn = ?"), eq("484-5454-4-1"), eq("ShowGod in the World"),eq(1), eq("484-5454-4-1"));
    }

    @Test
    public void testThatDeleteGeneratesCorrectSql() {
        underTest.delete("484-5454-4-1");
        verify(jdbcTemplate).update("DELETE FROM books WHERE isbn = ?", "484-5454-4-1");
    }
}
