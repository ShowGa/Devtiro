package com.showga.databaseMySQL.mappers.impl;

import com.showga.databaseMySQL.domain.dto.BookDto;
import com.showga.databaseMySQL.domain.entity.Book;
import com.showga.databaseMySQL.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookMapperImpl implements Mapper<Book, BookDto> {

    private final ModelMapper modelMapper;

    // get the ModelMapper injection from the MapperConfig(config) by constructor injection
    private BookMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // implement the method
    @Override
    public BookDto mapTo(Book book) {
        return modelMapper.map(book, BookDto.class);
    }

    @Override
    public Book mapFrom(BookDto bookDto) {
        return modelMapper.map(bookDto, Book.class);
    }
}
