package com.showga.databaseMySQL.repositories;

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

//    @Test
//    public void testThatMultipleBooksCanBeCreatedAndRecalled() {
//        Author author = TestDataUtils.createTestAuthor();
//        authorDAO.create(author);
//
//        Book book = TestDataUtils.createTestBook();
//        book.setAuthor_id(author.getId());
//        underTest.create(book);
//        Book book2 = TestDataUtils.createTestBook2();
//        book2.setAuthor_id(author.getId());
//        underTest.create(book2);
//        Book book3 = TestDataUtils.createTestBook3();
//        book3.setAuthor_id(author.getId());
//        underTest.create(book3);
//
//
//        List<Book> result = underTest.findMany();
//
//        assertThat(result).hasSize(3).containsExactly(book, book2, book3);
//    }

//    @Test
//    public void testThatBookCanBeUpdated() {
//        // create book first
//        // store
//        // change the created book title
//        // get the book
//        // check the book in database is same as the created book
//
//        // author has to exist
//        Author author = TestDataUtils.createTestAuthor();
//        authorDAO.create(author);
//
//        Book book = TestDataUtils.createTestBook();
//        book.setAuthor_id(author.getId());
//        underTest.create(book);
//        // change the title in create book
//        book.setTitle("Dicker");
//        underTest.update(book.getIsbn(), book);
//
//        // get the updated book with isbn
//        Optional<Book> result = underTest.find(book.getIsbn());
//
//        assertThat(result).isPresent();
//        assertThat(result.get()).isEqualTo(book);
//    }
//
//    @Test
//    public void testThatBookCanBedeleted() {
//        // create book first
//        // store
//        // change the created book title
//        // get the book
//        // check the book in database is same as the created book
//
//        // author has to exist
//        Author author = TestDataUtils.createTestAuthor();
//        authorDAO.create(author);
//
//        Book book = TestDataUtils.createTestBook();
//        book.setAuthor_id(author.getId());
//        underTest.create(book);
//        // delete the book test
//        underTest.delete(book.getIsbn());
//
//        // get the updated book with isbn
//        Optional<Book> result = underTest.find(book.getIsbn());
//
//        System.out.println(result);
//
//        assertThat(result).isEmpty();
//    }
}
