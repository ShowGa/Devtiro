package com.showga.databaseMySQL.dao.impl;

import com.showga.databaseMySQL.TestDataUtils;
import com.showga.databaseMySQL.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorDAOImplIntegrationTests {
    // Test Author created in database using createmethod and findOne to retrieve the data

    private AuthorDAOImpl underTest;

    @Autowired
    public AuthorDAOImplIntegrationTests(AuthorDAOImpl underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled() {
        Author author = TestDataUtils.createTestAuthor();

        // test create and find author
        underTest.create(author);
        Optional<Author> result = underTest.findOne(author.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testMultipleAuthorCanBeCreateAndRecalled() {
        Author author = TestDataUtils.createTestAuthor();
        Author author2 = TestDataUtils.createTestAuthor2();
        Author author3 = TestDataUtils.createTestAuthor3();
        underTest.create(author);
        underTest.create(author2);
        underTest.create(author3);

        List<Author> result = underTest.findMany();

        assertThat(result).hasSize(3).containsExactly(author, author2, author3);


    }

    @Test
    public void testThatAuthorCanBeUpdated() {
        // Create author
        Author author = TestDataUtils.createTestAuthor();
        underTest.create(author);
        author.setName("Updated Name");
        underTest.update(author.getId(), author);

        Optional<Author> result = underTest.findOne(author.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatAuthorCanBeDeleted() {
        Author author = TestDataUtils.createTestAuthor();
        underTest.create(author);
        underTest.delete(author.getId());
        Optional<Author> result = underTest.findOne(author.getId());

        System.out.println(result);

        assertThat(result).isEmpty();
    }
}
