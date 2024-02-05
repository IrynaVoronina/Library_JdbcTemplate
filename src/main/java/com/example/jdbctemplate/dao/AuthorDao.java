package com.example.jdbctemplate.dao;
import java.util.List;
import com.example.jdbctemplate.model.Author;

public interface AuthorDao {

    List<Author> getAllAuthors();
    Author read(int id);
    Author create(Author author);
    Author updateAuthor(Author author);
    void delete(Author author);
}

