package com.example.jdbctemplate.service;

import com.example.jdbctemplate.dao.AuthorDao;
import com.example.jdbctemplate.dao.BookDao;
import com.example.jdbctemplate.model.Book;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookOperationServiceImpl implements BookOperationService {
    private final BookDao bookDao;

    public BookOperationServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAllBooks();
    }

    @Override
    public Book readBook(int id) {
        return bookDao.readBook(id);
    }

    @Override
    @Transactional
    public void createBook(String bookName, String keyword, List<Integer> authorIds) {
        bookDao.createBook(bookName, keyword, authorIds);
    }

    @Override
    @Transactional
    public void updateBook(Integer bookId, String bookName, String keyword, List<Integer> authorIds) {
        bookDao.updateBook(bookId, bookName, keyword, authorIds);
    }

    @Override
    @Transactional
    public void deleteBook(int bookId) {
        bookDao.deleteBook(bookId);
    }

    @Override
    public List<Book> searchBooksByName(String name) {
        return bookDao.searchBooksByName(name);
    }

    @Override
    public List<Book> searchBooksByAuthor(String query) {
        return bookDao.searchBooksByAuthor(query);
    }

}
