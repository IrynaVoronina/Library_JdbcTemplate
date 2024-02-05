package com.example.jdbctemplate.service;

import com.example.jdbctemplate.model.Book;

import java.util.List;

public interface BookOperationService {

    List<Book> getAllBooks();

    Book readBook(int bookId);

    void createBook(String bookName, String keyword, List<Integer> authorIds);

    void updateBook(Integer bookId, String bookName, String keyword, List<Integer> authorIds);

    void deleteBook(int bookId);

    List<Book> searchBooksByName(String name);

    List<Book> searchBooksByAuthor(String query);
}
