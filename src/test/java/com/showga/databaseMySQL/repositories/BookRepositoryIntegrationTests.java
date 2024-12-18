package com.showga.databaseMySQL.repositories;

import com.showga.databaseMySQL.TestDataUtils;
import com.showga.databaseMySQL.domain.entity.Author;
import com.showga.databaseMySQL.domain.entity.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryIntegrationTests {

    private AuthorRepository authorDAO;
    private BookRepository underTest;

    // underTest injected from the tart get Impl
    @Autowired
    public BookRepositoryIntegrationTests(BookRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatBookCanBeCreatedAndRecalled() {
        Author author = TestDataUtils.createTestAuthor();
        Book book = TestDataUtils.createTestBook(author);

        // author create() no need because of the cascade in Book entity

        // Test create and find book
        underTest.save(book);
        Optional<Book> result = underTest.findById(book.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled() {
        Author author = TestDataUtils.createTestAuthor();

        Book book = TestDataUtils.createTestBook(author);
        underTest.save(book);
        Book book2 = TestDataUtils.createTestBook2(author);
        underTest.save(book2);
        Book book3 = TestDataUtils.createTestBook3(author);
        underTest.save(book3);


        Iterable<Book> result = underTest.findAll();

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

        Book book = TestDataUtils.createTestBook(author);
        underTest.save(book);
        // change the title in create book
        book.setTitle("Dicker");
        underTest.save(book);

        // get the updated book with isbn
        Optional<Book> result = underTest.findById(book.getIsbn());

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

        Book book = TestDataUtils.createTestBook(author);
        underTest.save(book);
        // delete the book test
        underTest.deleteById(book.getIsbn());

        // get the updated book with isbn
        Optional<Book> result = underTest.findById(book.getIsbn());

        System.out.println(result);

        assertThat(result).isEmpty();
    }
}
