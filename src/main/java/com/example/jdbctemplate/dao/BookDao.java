package com.example.jdbctemplate.dao;

import com.example.jdbctemplate.model.Book;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

public interface BookDao {

    List<Book> getAllBooks();

    Book readBook(int id);

    void createBook(String bookName, String keyword, List<Integer> authorIds);

    void deleteBook(int bookId);

    void updateBook(Integer bookId, String bookName, String keyword, List<Integer> authorIds);

    List<Book> searchBooksByName(String name);

    List<Book> searchBooksByAuthor(String query);
}

