package com.showga.databaseMySQL.dao.impl;

import com.showga.databaseMySQL.TestDataUtils;
import com.showga.databaseMySQL.domain.Author;
import com.showga.databaseMySQL.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookDAOImplIntegrationTests {

    private AuthorDAOImpl authorDAO;
    private BookDAOImpl underTest;

    // underTest injected from the tart get Impl
    @Autowired
    public BookDAOImplIntegrationTests(BookDAOImpl underTest, AuthorDAOImpl authorDAO) {
        this.underTest = underTest;
        this.authorDAO = authorDAO;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled() {
        Author author = TestDataUtils.createTestAuthor();
        Book book = TestDataUtils.createTestBook();

        // author
        authorDAO.create(author);
        // Test crate and find book
        book.setAuthor_id(author.getId());

        underTest.create(book);
        Optional<Book> result = underTest.find(book.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatMultipleAuthorCanBeCreatedAndRecalled() {
        Author author = TestDataUtils.createTestAuthor();
        authorDAO.create(author);

        Book book = TestDataUtils.createTestBook();
        book.setAuthor_id(author.getId());
        underTest.create(book);
        Book book2 = TestDataUtils.createTestBook2();
        book2.setAuthor_id(author.getId());
        underTest.create(book2);
        Book book3 = TestDataUtils.createTestBook3();
        book3.setAuthor_id(author.getId());
        underTest.create(book3);


        List<Book> result = underTest.findMany();

        assertThat(result).hasSize(3).containsExactly(book, book2, book3);
    }

    @Test
    public void testThatBookCanBeUpdated() {
        // create book first
        // store
        // change the created book title
        // get the book
        // check the book in database is same as the created book

        // author has to exist
        Author author = TestDataUtils.createTestAuthor();
        authorDAO.create(author);

        Book book = TestDataUtils.createTestBook();
        book.setAuthor_id(author.getId());
        underTest.create(book);
        // change the title in create book
        book.setTitle("Dicker");
        underTest.update(book.getIsbn(), book);

        // get the updated book with isbn
        Optional<Book> result = underTest.find(book.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatBookCanBedeleted() {
        // create book first
        // store
        // change the created book title
        // get the book
        // check the book in database is same as the created book

        // author has to exist
        Author author = TestDataUtils.createTestAuthor();
        authorDAO.create(author);

        Book book = TestDataUtils.createTestBook();
        book.setAuthor_id(author.getId());
        underTest.create(book);
        // delete the book test
        underTest.delete(book.getIsbn());

        // get the updated book with isbn
        Optional<Book> result = underTest.find(book.getIsbn());

        System.out.println(result);

        assertThat(result).isEmpty();
    }
}
