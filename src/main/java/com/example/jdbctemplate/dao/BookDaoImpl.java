package com.example.jdbctemplate.dao;

import com.example.jdbctemplate.model.Author;
import com.example.jdbctemplate.model.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class BookDaoImpl implements BookDao {

    private final static String sqlSelectAllBooks = "SELECT b.book_id, b.name, b.keyword, a.author_id, " +
            "a.firstName, a.middleName, a.lastName, a.birthPlace " +
            "FROM Book b " +
            "LEFT JOIN book_author ba ON b.book_id = ba.book_id " +
            "LEFT JOIN Author a ON ba.author_id = a.author_id";
    private final static String sqlRead = "SELECT b.book_id, b.name, b.keyword, a.author_id, " +
            "a.firstName, a.middleName, a.lastName, a.birthPlace " +
            "FROM Book b " +
            "LEFT JOIN book_author ba ON b.book_id = ba.book_id " +
            "LEFT JOIN Author a ON ba.author_id = a.author_id " +
            "WHERE b.book_id = ?";

    private final static String sqlCreateBook = "INSERT INTO Book (name, keyword) VALUES (?,?)";
    private final static String sqlInsertBookAuthor = "INSERT INTO book_author (book_id, author_id) VALUES (?, ?)";
    private final static String sqlUpdateBook = "UPDATE Book SET name = ?, keyword = ? WHERE book_id = ?";
    private final static String sqlDeleteBookAuthors = "DELETE FROM book_author WHERE book_id = ?";
    private final static String sqlDeleteBook = "DELETE FROM Book WHERE book_id = ?";

    private final static String sqlSearchBooksByName = "SELECT b.book_id, b.name, b.keyword, " +
            "a.author_id, a.firstName, a.middleName, a.lastName, a.birthPlace " +
            "FROM Book b " +
            "LEFT JOIN book_author ba ON b.book_id = ba.book_id " +
            "LEFT JOIN Author a ON ba.author_id = a.author_id " +
            "WHERE LOWER(b.name) LIKE ?";

    private final static String sqlSearchBooksByAuthor = "SELECT b.book_id, b.name, b.keyword, " +
            "a.author_id, a.firstName, a.middleName, a.lastName, a.birthPlace " +
            "FROM Book b " +
            "LEFT JOIN book_author ba ON b.book_id = ba.book_id " +
            "LEFT JOIN Author a ON ba.author_id = a.author_id " +
            "WHERE LOWER(a.firstName) LIKE ? OR LOWER(a.middleName) LIKE ? OR LOWER(a.lastName) LIKE ?";

    JdbcTemplate jdbcTemplate;

    public BookDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Book> getAllBooks() {
        List<Book> bookList = jdbcTemplate.query(sqlSelectAllBooks, BookDaoImpl::mapRow);
        return getListOfCompleteBooks(bookList);
    }

    @Override
    public Book readBook(int id) {
        List<Book> books = jdbcTemplate.query(sqlRead, BookDaoImpl::mapRow, id);
        Map<Integer, Author> authorsMap = new HashMap<>();
        for (Book book : books) {
            authorsMap.putAll(book.getAuthors());
        }
        return new Book(id, books.get(0).getName(), books.get(0).getKeyword(), authorsMap);
    }

    @Override
    public void createBook(String bookName, String keyword, List<Integer> authorIds) {

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sqlCreateBook, new String[]{("book_id")});
            preparedStatement.setString(1, bookName);
            preparedStatement.setString(2, keyword);
            return preparedStatement;
        }, keyHolder);

        Integer bookId = keyHolder.getKey().intValue();

        for (Integer authorId : authorIds) {
            jdbcTemplate.update(sqlInsertBookAuthor, bookId, authorId);
        }
    }

    @Override
    public void deleteBook(int bookId) {
        jdbcTemplate.update(sqlDeleteBookAuthors, bookId);
        jdbcTemplate.update(sqlDeleteBook, bookId);
    }

    @Override
    public void updateBook(Integer bookId, String bookName, String keyword, List<Integer> authorIds) {

        jdbcTemplate.update(sqlDeleteBookAuthors, bookId);
        jdbcTemplate.update(sqlUpdateBook, bookName, keyword, bookId);

        for (Integer authorId : authorIds) {
            jdbcTemplate.update(sqlInsertBookAuthor, bookId, authorId);
        }
    }

    @Override
    public List<Book> searchBooksByName(String name) {
        String searchName = "%" + name.toLowerCase() + "%";
        List<Book> bookList = jdbcTemplate.query(sqlSearchBooksByName, BookDaoImpl::mapRow, searchName);
        return getListOfCompleteBooks(bookList);
    }

    @Override
    public List<Book> searchBooksByAuthor(String query) {
        String searchQuery = "%" + query.toLowerCase() + "%";
        List<Book> bookList = jdbcTemplate.query(sqlSearchBooksByAuthor,
                BookDaoImpl::mapRow, searchQuery, searchQuery, searchQuery);
        return getListOfCompleteBooks(bookList);
    }


    private static Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Book(rs.getInt("book_id"),
                rs.getString("name"),
                rs.getString("keyword"),
                new HashMap<>() {{
                    put(AuthorsDaoImpl.mapRow(rs, rowNum).getId(), AuthorsDaoImpl.mapRow(rs, rowNum));
                }});
    }

    private static List<Book> getListOfCompleteBooks(List<Book> bookList) {
        Map<Integer, Book> bookMap = new HashMap<>();

        for (Book book : bookList) {
            int bookId = book.getId();
            if (bookMap.containsKey(bookId)) {
                Book existingBook = bookMap.get(bookId);
                existingBook.getAuthors().putAll(book.getAuthors());
            } else {
                bookMap.put(bookId, book);
            }
        }
        return new ArrayList<>(bookMap.values());
    }
}
