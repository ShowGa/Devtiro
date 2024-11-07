package com.showga.databaseMySQL;

import com.showga.databaseMySQL.domain.dto.AuthorDto;
import com.showga.databaseMySQL.domain.dto.BookDto;
import com.showga.databaseMySQL.domain.entity.Author;
import com.showga.databaseMySQL.domain.entity.Book;

public final class TestDataUtils {

    private TestDataUtils() {}

//    public static <T> T createTestAuthor(int times) {
//        List<Author> authors = new ArrayList<>();
//
//        for (int i = 0; i < times; i++) {
//            authors.add(Author.builder()
//                    .id(i + 1)
//                    .name("ShowGa Hsiao " + (i + 1))
//                    .age(20)
//                    .build());
//        }
//
//        return times == 1 ? (T) authors.get(0) : (T) authors; // 强制转换
//    }

    public static Author createTestAuthor() {

        return Author.builder().id(1).name("ShowGa Hsiao").age(20).build();
    }


    public static Author createTestAuthor2() {

        return Author.builder().id(2).name("ShowGa Hsiao2").age(20).build();
    }

    public static Author createTestAuthor3() {

        return Author.builder().id(3).name("ShowGa Hsiao3").age(20).build();
    }

    public static AuthorDto createTestAuthorDto() {
        return AuthorDto.builder().id(1).name("ShowGa Hsiao").age(20).build();
    }

    public static Book createTestBook(final Author author) {
        return Book.builder().isbn("484-5454-4-1").title("ShowGod in the World").author(author).build();
    }

    public static Book createTestBook2(final Author author) {
        return Book.builder().isbn("484-5454-4-2").title("ShowGod in the World 2").author(author).build();
    }

    public static Book createTestBook3(final Author author) {
        return Book.builder().isbn("484-5454-4-3").title("ShowGod in the World 3").author(author).build();
    }

    public static BookDto createTestBookDto(final AuthorDto authorDto) {
        return BookDto.builder().isbn("484-5454-4-3").title("ShowGod in the World 3").author(authorDto).build();
    }
}
