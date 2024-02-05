package com.example.jdbctemplate.service;

import com.example.jdbctemplate.model.Author;

import java.security.KeyException;
import java.util.List;

public interface AuthorOperationService {
    List<Author> getAllAuthors();

    Author create(Author author);

    Author read(int id) throws KeyException;

    Author updateAuthor(Author author);

    void delete(Author author);
}
